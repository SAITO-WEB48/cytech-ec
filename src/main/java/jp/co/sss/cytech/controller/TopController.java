package jp.co.sss.cytech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.sss.cytech.repository.ItemRepository;

@Controller
public class TopController {

    @Autowired
    ItemRepository repository;

    @GetMapping("/top")
    public String index(Model model) {

        // 商品をすべて取得してTOP画面へ渡す
        model.addAttribute("items", repository.findAll());

        return "top/index";
    }
}