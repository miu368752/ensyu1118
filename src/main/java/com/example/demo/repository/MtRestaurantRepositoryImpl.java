package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MtRestaurant;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MtRestaurantRepositoryImpl implements MtRestaurantRepository {

	private final JdbcTemplate jdbcTemplate;
	
	@Override
	public void add(MtRestaurant r) {

		String sql =
				" INSERT INTO m_restaurant " +
				" (restaurant_name, catch_phrase) " +
				" VALUES (?, ?) ";	

		jdbcTemplate.update(sql, 
				r.getRestaurantName(),
				r.getCatchPhrase()			);
		
	}
	@Override
	public List<MtRestaurant> selectByNameWildcard(String restaurantName) {

		String sql =
				" SELECT                                                 " + 
				"   mr.restaurant_id,                                    " +
				"   mr.restaurant_name,                                  " +
				"   mr.catch_phrase,                                     " +
				"   COUNT(tr.review_id) review_count                     " +
				" FROM                                                   " +
				"  	m_restaurant mr                                      " +
				"  	LEFT OUTER JOIN t_review tr                          " +
				"  	             ON mr.restaurant_id = tr.restaurant_id  " +
				" WHERE                                                  " +
				"  	mr.restaurant_name LIKE ?                            " +
				" GROUP BY                                               " +
				"  	mr.restaurant_id,                                    " +
				"  	mr.restaurant_name,                                  " +
				"  	mr.catch_phrase                                      " +
				" ORDER BY                                               " +
				"   mr.restaurant_id                                     ";
		String p = "%" + restaurantName + "%";	// プレースホルダの値
		
		// SQLで検索（プレースホルダ：p）
		List<Map<String, Object>> list 
				= jdbcTemplate.queryForList(sql, p);
		
		// 値の取得⇒結果の格納
		List<MtRestaurant> result = new ArrayList<MtRestaurant>(); // 結果の初期化
		for (Map<String, Object> one : list) {
			MtRestaurant restaurant = new MtRestaurant();
			restaurant.setRestaurantId((int)one.get("restaurant_id"));
			restaurant.setRestaurantName((String)one.get("restaurant_name"));
			restaurant.setCatchPhrase((String)one.get("catch_phrase"));
			int count = ((Long)one.get("review_count")).intValue(); // ← review_countをintで取得
			restaurant.setReviewSum(count); // ← ※MtRestaurant側の型がintの場合
			result.add(restaurant);
		}
		return result; 
	}

@Override
public void update(MtRestaurant r) {

	String sql =
			" UPDATE             " + 
			"   t_review         " + 
			" SET                " + 
			"   user_id = ? ,    " + 
			"   visit_date = ? , " + 
			"   rating = ?,      " + 
			"   comment = ?      " + 
			" WHERE              " + 
			"   review_id = ?    ";
	
	jdbcTemplate.update(sql, 
			r.getRestaurantName(),
			r.getCatchPhrase()			);
	
	
}
}

