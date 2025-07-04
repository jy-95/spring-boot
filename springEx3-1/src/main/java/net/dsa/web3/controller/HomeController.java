package net.dsa.web3.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.dto.StudentDTO;
import net.dsa.web3.service.StudentService;


@Controller
@Slf4j
@RequiredArgsConstructor
	public class HomeController {
	
	private final StudentService ss;
	
		@GetMapping({"", "/"})
		public String home(Model model) {
			List<StudentDTO> list = ss.selectAllData();
			log.debug("회원정보 전체조회: {}", list);
			model.addAttribute("list", list);
			
			return "home"; 
		}

	}

