package jp.co.sss.cytech.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginFormWithValidation {

    @NotNull //userIdが未入力ではないかチェック
    @Max(value = 999) //999以下かチェック
    private Integer userId;

    @Pattern(regexp = "[a-zA-Z0-9]+$") //半角英数字だけかチェック
    @NotBlank //パスワードが空文字や空白だけではないかチェック
    @Size(max = 16) //16文字以内かチェック
    private String password;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}