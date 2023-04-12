package ksmart.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String mainPage(Model model) {
		model.addAttribute("title", "메인화면");
		model.addAttribute("content", "spring boot mybatis project");
		return "main";
	}
}
