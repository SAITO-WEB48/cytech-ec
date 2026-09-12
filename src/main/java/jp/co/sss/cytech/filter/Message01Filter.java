package jp.co.sss.cytech.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component // Springに「このFilterを使います」と登録する
public class Message01Filter extends HttpFilter {

    @Override
    public void doFilter(
            HttpServletRequest request, // ブラウザから来た情報
            HttpServletResponse response,  // ブラウザへ返す情報
            FilterChain chain) // 次の処理へ渡す
            throws IOException, ServletException {

        System.out.println("Done Filter CyTech"); // コンソールに表示
        chain.doFilter(request, response);// Controllerへ処理を渡す
    }
}