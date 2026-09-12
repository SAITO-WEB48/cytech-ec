package jp.co.sss.cytech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.sss.cytech.entity.Category;
import jp.co.sss.cytech.entity.ItemWithCategory;

public interface ItemWithCategoryRepository
        extends JpaRepository<ItemWithCategory, Integer> {

    List<ItemWithCategory> findByCategory(Category category);
    
    @Query("SELECT i FROM ItemWithCategory i WHERE i.id = :id")
    public List<ItemWithCategory> findByIdQuery(@Param("id") Integer id);
    
    
  //平均価格以上の商品だけ取得する
  //商品の平均価格を求める 計算の中に計算を入れる（サブクエリ）
    @Query("SELECT i FROM ItemWithCategory i WHERE i.price >= (SELECT AVG(i2.price) FROM ItemWithCategory i2)")
    public List<ItemWithCategory> findByPriceGreaterThanEqualAVGPriceQuery();
}
