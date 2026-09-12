package jp.co.sss.cytech.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegisterForm {

    // ユーザー名空じゃないかチェック
    @NotBlank
    private String userName;

    // メールアドレス
    @NotBlank
    private String email;

    // パスワード８桁以上じゃないかチェック、半角英数字だけかチェック
    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String password;

    // パスワード確認用
    @NotBlank
    private String confirmPassword;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
 // ひらがな表記
    @NotBlank
    private String hiragana;

    // 電話番号
    @NotBlank
    private String phone;
    
    public String getHiragana() {
        return hiragana;
    }

    public void setHiragana(String hiragana) {
        this.hiragana = hiragana;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

