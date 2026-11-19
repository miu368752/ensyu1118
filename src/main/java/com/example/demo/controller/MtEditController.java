package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.MtRestaurant;
import com.example.demo.form.MtRestaurantEditForm;
import com.example.demo.service.MtEditService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MtEditController {

	private final MtEditService service;
	
	/*--- 編集画面表示リクエスト ---*/
	@PostMapping("/mt-show-edit")
	public String mtShowEdit(@ModelAttribute MtRestaurantEditForm form) {
		return "mt-show-edit";
	}

	/*--- 更新リクエスト（編集画面より） ---*/
	@PostMapping("/mt-edit")
	public String mtEdit(
			@Validated @ModelAttribute MtRestaurantEditForm form,
			BindingResult result) {

		// 入力エラーがある場合には 編集画面に戻す
		if (result.hasErrors()) {
			return "mt-show-edit";
		}
		
		// 正常な場合に 編集確認画面に 遷移する
		return "mt-confirm-edit-review";
	}

	/*--- 更新リクエスト（編集確認画面より） ---*/
	@PostMapping("/mt-confirm-edit-review")
	public String confirmEditReview(
			@Validated @ModelAttribute MtRestaurantEditForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes) {

		// 入力エラーがある場合には 編集画面に戻す
		if (result.hasErrors()) {
			return "mt-show-edit";
		}

		MtRestaurant r = new MtRestaurant();
		r.setRestaurantId(form.getRestaurantId());   // 
		r.setRestaurantName(form.getRestaurantName());
		r.setCatchPhrase(form.getCatchPhrase());
		service.edit(r);
		
		redirectAttributes.addFlashAttribute("msg", "(店舗更新)");
		
		return "redirect:/mt-complete";            
	}

}