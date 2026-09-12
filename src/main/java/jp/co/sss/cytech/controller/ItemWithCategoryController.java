package jp.co.sss.cytech.controller;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.cytech.entity.Category;
import jp.co.sss.cytech.entity.ItemWithCategory;
import jp.co.sss.cytech.repository.ItemWithCategoryRepository;

@Controller
public class ItemWithCategoryController {

    @Autowired
    ItemWithCategoryRepository repository; //repositoryを使えるようにする
    
    @PersistenceContext
    EntityManager entityManager; //entityManagerを使えるようにする

    @RequestMapping("/items/findItemAndCategory")
    public String showItemWithCategoryList(Model model) {

        model.addAttribute("items", repository.findAll());

        return "items/item_category_list";
    }
    
 // 外部参照による検索条件
    @RequestMapping("/items/searchByCategory/{categoryId}")
    public String searchByCategory(
            @PathVariable Integer categoryId,
            Model model) {

        // 外部参照先テーブルに対応付けたエンティティのオブジェクトを生成
        Category category = new Category();

        // Categoryオブジェクト内のidフィールドにパラメータの値を代入
        category.setId(categoryId);

        // Categoryのオブジェクト内のidフィールドを使用した条件検索を実行
        List<ItemWithCategory> items =
                repository.findByCategory(category);

        // 検索結果をリクエストスコープに保存
        model.addAttribute("items", items);

        // 商品一覧画面に遷移
        return "items/item_category_list";
    }
    
 // NamedQuery を利用したJPAQL
    @RequestMapping("/items/searchWithNamedQuery/{id}")
    public String searchQuery(
            @PathVariable Integer id,
            Model model) {

        Query query = entityManager.createNamedQuery("findByIdNamedQuery");

        query.setParameter("id", id);

        model.addAttribute("items", query.getResultList());

        return "items/item_category_list";
    }
 // @Queryを利用したJPQL
    @RequestMapping("/items/searchWithQuery/{id}")
    public String searchWithQuery(
            @PathVariable Integer id,
            Model model) {

        model.addAttribute(
                "items",
                repository.findByIdQuery(id));

        return "items/item_category_list";
    }
    
 // 平均以上のレコードを検索
    @RequestMapping("/items/searchAverage")
    public String searchWithQuery(Model model) {

        model.addAttribute(
                "items",
                repository.findByPriceGreaterThanEqualAVGPriceQuery());

        return "items/item_category_list";
    }
    
}