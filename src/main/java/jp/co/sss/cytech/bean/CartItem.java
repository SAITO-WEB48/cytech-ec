package jp.co.sss.cytech.bean;

import jp.co.sss.cytech.entity.Item;

public class CartItem {

    // 商品情報
    private Item item;

    // カートに入れた数量
    private Integer quantity;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
 // 税抜きの合計金額
    public Integer getSubtotal() {
        return item.getPrice() * quantity;
    }

    // 税込みの合計金額
    public Integer getTaxIncludedSubtotal() {
        return item.getTaxIncludedPrice() * quantity;
    }
}