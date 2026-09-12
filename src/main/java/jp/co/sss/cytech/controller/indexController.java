package jp.co.sss.cytech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {
	
	//トップ画面表示
	@RequestMapping(path = "/")
	public String index() {
		
		return "index";
	}
	
	
	//遷移元画面表示
	@RequestMapping(path = "/before")
	public String before() {

	    return "before";
	}
    
	
	//遷移先画面表示
	@RequestMapping(path = "/after")
	public String after() {

	    return "after";
	}
	
	//画面遷移処理
	@RequestMapping(path = "/transition")
	public String transition() {
		
		return "sample_transition";
	}
	
	
	//フォワード処理
	@RequestMapping(path = "/index f")
	public String index_forwad() {
		
		return "index";
	}
	
	//リダイレクト処理
	@RequestMapping(path = "/index_r")
	public String index_redirext() {
		
		return "redirect:/";
	}
	
	//共通部分表示
	@RequestMapping("/layout_view") //ブラウザから /layout.view にアクセスされたら
	public String layout_view() {   //このメソッドが動いて
		
		return "layout_view";       //emplates/layout_view.html を表示する
	}  
	
}
	
	
	
