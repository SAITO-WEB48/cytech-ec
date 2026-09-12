package jp.co.sss.cytech.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.form.LoginForm;
import jp.co.sss.cytech.form.LoginFormWithAnnotation;
import jp.co.sss.cytech.form.LoginFormWithValidation;
import jp.co.sss.cytech.repository.UserRepository;

@Controller
public class SessionController {

    @Autowired
    UserRepository userRepository;

    // ログイン画面を表示
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {

        return "session/login";
    }

    // GETで送信されたユーザーIDを受け取る
    @RequestMapping(path = "/doLogin", method = RequestMethod.GET)
    public String doLoginGet(Integer userId) {

        System.out.println("ユーザーID:" + userId);

        return "session/login";
    }

    // 今回の実装用ログイン処理
    @RequestMapping(path = "/doLogin", method = RequestMethod.POST)
    public String doLoginPost(
            String email,
            String password,
            HttpSession session) {

        // メールアドレスからユーザーを検索
        User user = userRepository.findByEmail(email);

     // ユーザーが存在して、パスワードも一致した場合
        if (user != null && user.getPassword().equals(password)) {

            // ログイン情報をセッションに保存
            session.setAttribute("userId", user.getId());

            // ユーザー名もセッションに保存
            session.setAttribute("userName", user.getUserName());
            
            System.out.println("DBのuserId：" + user.getId());
            System.out.println("SessionのuserId：" + session.getAttribute("userId"));

            // TOP画面へ
            return "redirect:/top";
        }

        // メールアドレスまたはパスワードが違う場合
        return "session/login";
    }

    // LoginFormを使って入力内容を受け取る
    @RequestMapping(path = "/doLoginUsingForm", method = RequestMethod.POST)
    public String doLoginUsingForm(LoginForm form) {

        System.out.println("ユーザーID:" + form.getUserId());
        System.out.println("パスワード:" + form.getPassword());

        return "session/loginUsingForm";
    }

    // 入力チェック付きログイン画面を表示
    @RequestMapping(path = "/loginValidation", method = RequestMethod.GET)
    public String loginValidation(
            @ModelAttribute LoginFormWithValidation form) {

        return "session/loginValidation";
    }

    // 入力チェック付きログイン処理
    @RequestMapping(path = "/loginValidation", method = RequestMethod.POST)
    public String doLoginValidation(
            @Valid @ModelAttribute LoginFormWithValidation form,
            BindingResult result,
            HttpSession session) {

        // 入力チェックでエラーがあった場合
        if (result.hasErrors()) {

            return "session/loginValidation";
        }

        // ユーザーIDが123の場合はログイン成功
        if (form.getUserId() == 123) {

            session.setAttribute("userId", form.getUserId());

            return "redirect:/";
        }

        // ユーザーIDが123以外の場合
        return "session/loginValidation";
    }

    @RequestMapping(path = "/loginWithAnnotation", method = RequestMethod.GET)
    public String loginWithAnnotation(
            @ModelAttribute LoginFormWithAnnotation form) {

        return "session/loginWithAnnotation";
    }

    @RequestMapping(path = "/loginWithAnnotation", method = RequestMethod.POST)
    public String doLoginWithAnnotation(
            @Valid @ModelAttribute LoginFormWithAnnotation form,
            BindingResult result,
            HttpSession session) {

        if (result.hasErrors()) {

            return "session/loginWithAnnotation";
        }

        if (form.getUserId() == 123) {

            session.setAttribute("userId", form.getUserId());

            return "redirect:/";

        } else {

            return "session/loginWithAnnotation";
        }
    }
    
 // ログアウト処理
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {

        // セッションを削除
        session.invalidate();

        // ログイン画面へ
        return "redirect:/login";
    }
}
    



