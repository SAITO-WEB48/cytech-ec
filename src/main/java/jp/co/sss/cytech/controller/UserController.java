package jp.co.sss.cytech.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.form.UserRegisterForm;
import jp.co.sss.cytech.repository.UserRepository;

@Controller
public class UserController {

    // UserRepositoryを使えるようにする
    @Autowired
    UserRepository repository;

    // ユーザー新規登録画面を表示
    @RequestMapping(path = "/users/register", method = RequestMethod.GET)
    public String showRegister(UserRegisterForm form) {

        return "users/register";
    }

    // ユーザー新規登録処理
    @RequestMapping(path = "/users/register", method = RequestMethod.POST)
    public String register(
            @Valid @ModelAttribute UserRegisterForm form,
            BindingResult result) {

        // 入力チェックでエラーがあった場合
        if (result.hasErrors()) {
            return "users/register";
        }

        // Userオブジェクトを作る
        User user = new User();

        // FormからUserへ値を入れる
        user.setUserName(form.getUserName());

        // ひらがな
        user.setHiragana(form.getHiragana());

        // 電話番号
        user.setPhone(form.getPhone());

        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());

        // MySQLに保存
        repository.save(user);

        // 登録後はログイン画面へ
        return "redirect:/login";
    }
    
 // マイページ表示
    @RequestMapping(path = "/mypage", method = RequestMethod.GET)
    public String showMypage(
            HttpSession session,
            Model model) {

        // ログイン中のユーザーIDをSessionから取得
        Integer userId =
                (Integer) session.getAttribute("userId");

        // 確認用
        System.out.println("マイページ userId：" + userId);

        // userIdがない場合はログイン画面へ
        if (userId == null) {
            return "redirect:/login";
        }

        // ユーザー情報を取得
        User user =
                repository.findById(userId).orElse(null);

        // HTMLへ渡す
        model.addAttribute("user", user);

        return "users/mypage";
    }
    
}