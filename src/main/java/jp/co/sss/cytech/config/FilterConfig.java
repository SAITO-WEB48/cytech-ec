package jp.co.sss.cytech.config; // configパッケージにあるクラス

import org.springframework.boot.web.servlet.FilterRegistrationBean;// Filterを登録するために使う
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jp.co.sss.cytech.filter.Message02Filter;
import jp.co.sss.cytech.filter.Message03Filter;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

    @Bean// Springが管理する部品として登録する
    public FilterRegistrationBean<Message02Filter> configMessage02Filter() {

        FilterRegistrationBean<Message02Filter> bean =
                new FilterRegistrationBean<Message02Filter>();//Filter設定を入れる箱を作る

        bean.setFilter(new Message02Filter());// Message02Filterを登録
        bean.setOrder(2);

        return bean; // 作成したFilter設定をSpringへ渡す
    }

    @Bean// Springが管理する部品として登録する
    public FilterRegistrationBean<Message03Filter> configMessage03Filter() {

        FilterRegistrationBean<Message03Filter> bean =
                new FilterRegistrationBean<Message03Filter>();

        bean.setFilter(new Message03Filter());
        bean.setOrder(1);

        return bean; // 作成したFilter設定をSpringへ渡す
    }
}