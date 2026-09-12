package jp.co.sss.cytech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.cytech.entity.Review;
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

	    // どの商品への口コミか分かるように商品IDをHTMLへ渡す
	    model.addAttribute("itemId", id);

	    // 口コミ投稿画面を表示
	    return "items/review_form";
	}
	
	// 口コミ投稿
	@RequestMapping("/reviews/post")
	public String postReview(
	        Integer itemId,
	        String userName,
	        Integer rating,
	        String email,
	        String comment) {

	    // Reviewオブジェクトを作る
	    Review review = new Review();

	    // HTMLから受け取った内容をReviewに入れる
	    review.setItemId(itemId);
	    review.setUserName(userName);
	    review.setRating(rating);
	    review.setEmail(email);
	    review.setComment(comment);

	    // MySQLに口コミを保存
	    repository.save(review);

	    // 一覧画面へ戻る
	    return "redirect:/items/findAll" ;
	}
}