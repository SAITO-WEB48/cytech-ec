package jp.co.sss.cytech.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


//購入品詳細画面(purchase_detail.html)で入力された情報をまとめて受け取るクラス
public class PurchaseForm {

    private String name;

    private String address;

    private String apartment;

    // カード情報：必須・数字15桁
    @NotBlank(message = "カード情報を入力してください")
    @Pattern(
        regexp = "^[0-9]{15}$",
        message = "カード情報は15桁の数字で入力してください"
    )
    private String card;

    private Integer itemId;
    
    private String expirationDate;

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    
 // 以下 getter / setter
    // HTMLから受け取った値を設定したり、取得したりする
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}