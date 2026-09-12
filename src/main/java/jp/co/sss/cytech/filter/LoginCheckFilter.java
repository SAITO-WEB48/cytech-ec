package jp.co.sss.cytech.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@Component
public class LoginCheckFilter extends HttpFilter {

    @Override
    public void doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        // リクエストURLを取得
        String requestURL = request.getRequestURI();

        if (requestURL.endsWith("/loginValidation")
                || requestURL.endsWith("/users/register")) {

            // ログイン画面・新規登録画面は
            // ログインチェックをせずに通す
            chain.doFilter(request, response);

        } else {

            // セッション情報を取得
            HttpSession session = request.getSession();

            // セッション情報からユーザIDを取得
            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {

                // 未ログインの場合はログイン画面へ
                response.sendRedirect("/cytech/loginValidation");
                return;

            } else {

                chain.doFilter(request, response);
            }
        }
    }
}