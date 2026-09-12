package jp.co.sss.cytech.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.cytech.bean.CartItem;
import jp.co.sss.cytech.entity.Item;
import jp.co.sss.cytech.entity.Review;
import jp.co.sss.cytech.form.PurchaseForm;
import jp.co.sss.cytech.repository.ItemRepository;
import jp.co.sss.cytech.repository.ReviewRepository;


@Controller
public class ItemController {

	//Repositoryを使わせて！
    @Autowired
    ItemRepository repository;
    
    @Autowired
    ReviewRepository reviewRepository;

    
    
 // 商品一覧・検索
    @RequestMapping("/items/findAll")
    public String showItemList(
            String keyword,
            String category,
            Model model) {

        // 商品名あり・カテゴリあり
        if (keyword != null && !keyword.isEmpty()
                && category != null && !category.isEmpty()) {

            List<Item> items =
                    repository.findByNameContaining(keyword);

            items.removeIf(item ->
                    !category.equals(item.getCategory()));

            model.addAttribute("items", items);

        // 商品名だけ
        } else if (keyword != null && !keyword.isEmpty()) {

            model.addAttribute(
                    "items",
                    repository.findByNameContaining(keyword));

        // カテゴリだけ
        } else if (category != null && !category.isEmpty()) {

            model.addAttribute(
                    "items",
                    repository.findByCategory(category));

        // 何も指定なし
        } else {

            model.addAttribute(
                    "items",
                    repository.findAll());
        }

        // 選んだ条件をHTMLへ戻す
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);

        return "items/item_list";
    }
    

 // 商品詳細
    @RequestMapping("/items/detail/{id}")
    public String showItemDetail(@PathVariable Integer id, Model model) {

        // URLから受け取ったidの商品をMySQLから取得
        Item item = repository.findById(id).orElse(null);

        // この商品の口コミをMySQLから取得
        List<Review> reviews = reviewRepository.findByItemId(id);

        // 商品情報をHTMLへ渡す
        model.addAttribute("item", item);

        // 口コミをHTMLへ渡す
        model.addAttribute("reviews", reviews);

        // 商品詳細画面を表示
        return "items/item_detail";
    }
    
    
    // 購入品詳細画面
    @RequestMapping("/purchase/{id}")
    public String showPurchaseDetail(@PathVariable Integer id, Model model) {

        // idを使ってMySQLから購入する商品を取得
        Item item = repository.findById(id).orElse(null);

        // 取得した商品を購入品詳細画面へ渡す
        model.addAttribute("item", item);

        // 購入品詳細画面を表示
        return "items/purchase_detail";
    }
    
    
    // 購入品確認画面
    @RequestMapping("/purchase/confirm")
    public String showPurchaseConfirm(
            @Valid PurchaseForm form,
            BindingResult result,
            Model model) {

        // Formで受け取った商品IDから商品情報を取得
        Item item = repository.findById(form.getItemId()).orElse(null);

        // 入力された購入者情報をHTMLへ渡す
        model.addAttribute("purchaseForm", form);

        // 商品情報をHTMLへ渡す
        model.addAttribute("item", item);

        // 購入品確認画面を表示
        return "items/purchase_confirm";
    }
    
    
 // カート追加
    @RequestMapping("/cart/add/{id}")
    public String addCart(@PathVariable Integer id, HttpSession session, Model model) {

        // 商品IDから商品情報を取得
        Item item = repository.findById(id).orElse(null);

        // Sessionからカートを取得
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        // 初めてカートを使う場合
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // 商品＋数量を保存するCartItemを作る
        CartItem cartItem = new CartItem();

        // 商品情報をセット
        cartItem.setItem(item);

        // 最初は数量1個
        cartItem.setQuantity(1);

        // カートに追加
        cart.add(cartItem);

        // カートをSessionに保存
        session.setAttribute("cart", cart);

        // カート追加画面へ商品情報を渡す
        model.addAttribute("item", item);

        return "items/cart_add";
    }
    
   
 // カート内詳細画面
    @RequestMapping("/cart")
    public String showCart(HttpSession session, Model model) {

        // Sessionに保存しているカートの商品を取得
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        // カート全体の個数
        int totalQuantity = 0;

        // カート全体の税抜合計金額
        int totalPrice = 0;

        // カート全体の税込合計金額
        int totalTaxIncludedPrice = 0;

        // カートに商品が入っている場合
        if (cart != null) {

            // カートの商品を1つずつ計算
            for (CartItem cartItem : cart) {

                totalQuantity += cartItem.getQuantity();

                totalPrice +=
                        cartItem.getItem().getPrice()
                        * cartItem.getQuantity();

                totalTaxIncludedPrice +=
                        cartItem.getItem().getTaxIncludedPrice()
                        * cartItem.getQuantity();
            }
            
        }

        // カートの商品をHTMLへ渡す
        model.addAttribute("cart", cart);

        // 計算した合計をHTMLへ渡す
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalTaxIncludedPrice", totalTaxIncludedPrice);

        return "items/cart_detail";
    }
    
    //カート内詳細画面→購入品詳細画面(cart_purchase_detail.html)
    // カートからレジに進む
    @RequestMapping("/cart/purchase")
    public String showCartPurchase(
            HttpSession session,
            Model model) {

        // Sessionからカートを取得
        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        // 税込合計金額
        int totalTaxIncludedPrice = 0;

        // カートの商品を1つずつ計算
        if (cart != null) {

            for (CartItem cartItem : cart) {

                totalTaxIncludedPrice +=
                        cartItem.getItem().getTaxIncludedPrice()
                        * cartItem.getQuantity();
            }
        }

        // HTMLへ渡す
        model.addAttribute("cart", cart);
        model.addAttribute(
                "totalTaxIncludedPrice",
                totalTaxIncludedPrice);

        return "items/cart_purchase_detail";
    }
    
    
    // カート購入 → 購入品確認画面
    @RequestMapping("/cart/purchase/confirm")
    public String showCartPurchaseConfirm(
            PurchaseForm form,
            HttpSession session,
            Model model) {

        // Sessionからカートを取得
        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        // カート全体の税込合計金額
        int totalTaxIncludedPrice = 0;

        if (cart != null) {

            for (CartItem cartItem : cart) {

                totalTaxIncludedPrice +=
                        cartItem.getItem().getTaxIncludedPrice()
                        * cartItem.getQuantity();
            }
        }

        // 入力した購入者情報
        model.addAttribute("purchaseForm", form);

        // カートの商品
        model.addAttribute("cart", cart);

        // 合計金額
        model.addAttribute(
                "totalTaxIncludedPrice",
                totalTaxIncludedPrice);

        return "items/cart_purchase_confirm";
    }
    
    
    // カートの商品を削除
    @RequestMapping("/cart/delete/{id}")
    public String deleteCartItem(
            @PathVariable Integer id,
            HttpSession session) {

        // SessionからCartItemのカートを取得
        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        // カートが存在する場合
        if (cart != null) {

            // 押された商品IDと同じCartItemを削除
            cart.removeIf(cartItem ->
                    cartItem.getItem().getId().equals(id));

            // 削除後のカートをSessionに保存
            session.setAttribute("cart", cart);
        }

        // カート内詳細画面へ戻る
        return "redirect:/cart";
    }
    
    
    // カートの数量を変更
    @RequestMapping("/cart/update/{id}")
    public String updateCartQuantity(
            @PathVariable Integer id,
            Integer quantity,
            HttpSession session) {

        // Sessionからカートを取得
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        // カートが存在する場合
        if (cart != null) {

            // カートの中から同じ商品IDを探す
            for (CartItem cartItem : cart) {

                if (cartItem.getItem().getId().equals(id)) {

                    // 選んだ数量を保存
                    cartItem.setQuantity(quantity);

                    break;
                }
            }

            // 変更後のカートをSessionに保存
            session.setAttribute("cart", cart);
        }

        // カート内詳細画面へ戻る
        return "redirect:/cart";
    }
    
    
 // 購入完了画面
    @RequestMapping("/purchase/complete")
    public String showPurchaseComplete(
            PurchaseForm form,
            Model model) {

        // 商品IDから購入した商品を取得
        Item item = repository.findById(form.getItemId()).orElse(null);

        // 購入者情報を完了画面へ渡す
        model.addAttribute("purchaseForm", form);

        // 合計金額（税込）を完了画面へ渡す
        model.addAttribute("totalPrice", item.getTaxIncludedPrice());

        // 完了画面を表示
        return "items/complete";
    }
   
    
 // カート購入完了画面
    @RequestMapping("/cart/purchase/complete")
    public String showCartPurchaseComplete(
            PurchaseForm form,
            HttpSession session,
            Model model) {

        // Sessionからカートを取得
        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        // カート全体の税込合計金額
        int totalTaxIncludedPrice = 0;

        if (cart != null) {

            for (CartItem cartItem : cart) {

                totalTaxIncludedPrice +=
                        cartItem.getItem().getTaxIncludedPrice()
                        * cartItem.getQuantity();
            }
        }

        // 購入者情報を渡す
        model.addAttribute("purchaseForm", form);

        // カートの商品を渡す
        model.addAttribute("cart", cart);

        // 合計金額を渡す
        model.addAttribute(
                "totalTaxIncludedPrice",
                totalTaxIncludedPrice);

        // カート購入完了画面
        return "items/cart_purchase_complete";
    }
}
   
    
    
    
    
    
   