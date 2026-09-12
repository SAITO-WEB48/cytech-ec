package jp.co.sss.cytech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


//データを持つ場所
//Entity、ColumnがあるからMySQLとも繋がる
@Entity
public class Review {

    // 口コミID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // どの商品への口コミか
    @Column
    private Integer itemId;

    // ユーザー名
    @Column
    private String userName;

    // 評価（1～5）
    @Column
    private Integer rating;

    // メールアドレス
    @Column
    private String email;

    // コメント
    @Column(length = 300)
    private String comment;

    // 画像
    @Column
    private String image;
    
    
    //データを出し入れする方法
    
    //取り出すget
    public Integer getId() {
        return id;
    }
    //入れるset
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}