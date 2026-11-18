package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.MtRestaurant;
import com.example.demo.form.MtRestaurantSearchForm;
import com.example.demo.service.MtRestaurantListService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MtRestaurantListController {
	private final MtRestaurantListService service;
	
	/*--- 検索ページ表示リクエスト -------------------------*/
	@PostMapping("/mt-show-search")
	public String restaurantList(
			@ModelAttribute MtRestaurantSearchForm form) {
		return "mt-restaurant-list"; 
	}
	
	/*--- 検索リクエスト -------------------------*/
	@PostMapping("/mt-search")
	public String mtSearch(
			@ModelAttribute MtRestaurantSearchForm form,
			Model model) {

		List<MtRestaurant> list
			= service.findByNameWildcard(form.getRestaurantName());
		
		model.addAttribute("restaurantList", list);
		
		return "mt-restaurant-list"; // 上のHTMLファイル名と一致
	}

}