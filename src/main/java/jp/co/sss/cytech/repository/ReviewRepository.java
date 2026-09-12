package jp.co.sss.cytech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.Review;

public interface ReviewRepository
        extends JpaRepository<Review, Integer> {

    // 商品IDに紐づく口コミを取得
    List<Review> findByItemId(Integer itemId);

}