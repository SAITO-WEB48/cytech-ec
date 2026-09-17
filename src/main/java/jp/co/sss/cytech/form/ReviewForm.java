package jp.co.sss.cytech.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReviewForm {

    private Integer itemId;

    @NotBlank(message = "ユーザー名を入力してください")
    private String userName;

    @Min(value = 1, message = "評価は1以上を選択してください")
    @Max(value = 5, message = "評価は5以下を選択してください")
    private Integer rating;

    private String email;

    @Size(max = 300, message = "コメントは300文字以内で入力してください")
    private String comment;

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

}