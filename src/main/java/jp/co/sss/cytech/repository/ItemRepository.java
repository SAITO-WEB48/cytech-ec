package jp.co.sss.cytech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{
	
	//降順
	List<Item> findAllByOrderByPriceDesc();
	
	//主キー以外の条件検索
	List<Item> findByPrice(Integer price);
	
	//複数列による検索条件
	List<Item> findByNameAndPrice(String name, Integer price);
	
	//曖昧検索
	List<Item> findByNameContaining(String name);
	
	// カテゴリ検索
	List<Item> findByCategory(String category);

}
