package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.MtRestaurant;
import com.example.demo.form.MtRestaurantRemoveForm;
import com.example.demo.service.MtRemoveService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MtRemoveController {

	private final MtRemoveService service;

	/*--- 削除確認画面表示リクエスト ---*/
	@PostMapping("/mt-show-remove")
	public String mtShowRemove(
			@Validated @ModelAttribute MtRestaurantRemoveForm form,
			BindingResult result) {

		if (result.hasErrors()) {
			throw new IllegalArgumentException("**mtShowRemove()**");
		}
		
		return "mt-confirm-remove";
	}

	/*--- 削除リクエスト（削除確認画面より） ---*/
	@PostMapping("/mt-confirm-remove")
	public String confirmRemove(
			@Validated @ModelAttribute MtRestaurantRemoveForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			throw new IllegalArgumentException("**confirmRemove()**");
		}

		MtRestaurant r = new MtRestaurant();
		r.setRestaurantId(form.getRestaurantId());
		r.setRestaurantName(form.getRestaurantName());
		r.setCatchPhrase(form.getCatchPhrase());
		service.delete(r);
		
		redirectAttributes.addFlashAttribute("msg", "(店舗削除)");
		
		return "redirect:/mt-complete";            
	}

}