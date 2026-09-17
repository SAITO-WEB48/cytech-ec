package jp.co.sss.cytech.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.cytech.entity.Review;
import jp.co.sss.cytech.form.ReviewForm;
import jp.co.sss.cytech.repository.ReviewRepository;



@Controller
public class ReviewController {
	
	@Autowired
	ReviewRepository repository;

	
	
	// 口コミ投稿画面を表示
	@RequestMapping("/reviews/form/{id}")
	public String showReviewForm(
	        @PathVariable Integer id,
	        Model model) {

	    // ReviewFormオブジェクトを作る
	    ReviewForm form = new ReviewForm();

	    // 商品IDをFormに入れる
	    form.setItemId(id);

	    // ReviewFormをHTMLへ渡す
	    model.addAttribute("reviewForm", form);

	    // 口コミ投稿画面を表示
	    return "items/review_form";
	}
	
	// 口コミ投稿
	@RequestMapping("/reviews/post")
	public String postReview(
	        @Valid @ModelAttribute ReviewForm form,
	        BindingResult result) {

		// 入力チェックでエラーがあった場合
		if (result.hasErrors()) {
		    return "items/review_form";
		}
		
	    // Reviewオブジェクトを作る
	    Review review = new Review();

	    // HTMLから受け取った内容をReviewに入れる
	    review.setItemId(form.getItemId());
	    review.setUserName(form.getUserName());
	    review.setRating(form.getRating());
	    review.setEmail(form.getEmail());
	    review.setComment(form.getComment());
	    // MySQLに口コミを保存
	    try {
	        repository.save(review);

	    } catch (Exception e) {
	        System.out.println("口コミの登録に失敗しました");
	        return "items/review_form";
	    }

	    // 一覧画面へ戻る
	    return "redirect:/items/findAll";
	}
}