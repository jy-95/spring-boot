package net.dsa.web3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.dto.StudentDTO;
import net.dsa.web3.service.StudentService;

@Controller
@Slf4j
@RequestMapping("student")
@RequiredArgsConstructor
public class StudentController {
	
	private final StudentService ss;
	
	@GetMapping("info")
	public String info(@RequestParam (name="id") int id, Model model) {
		StudentDTO sd = ss.selectData(id);
		model.addAttribute("student", sd);
		return "student/info";
	}
	
	@GetMapping("register")
	public String registerForm() {
		return "student/registForm";
	}

	
	@PostMapping("regist")
	public String regist(StudentDTO sd) {
		
		log.debug("Form data 확인: {}", sd);
		
		ss.save(sd);
		return "redirect:/";
	}
	
	@GetMapping("edit")
	public String editForm(@RequestParam (name="id") int id, Model model) {
		
		StudentDTO sd = ss.selectData(id);
		model.addAttribute("student", sd);
		
		return "student/editForm";
	}
	
	@PostMapping("edit")
	public String update(StudentDTO member) {
		
		log.debug("수정할 회원정보: {}", member);
		try {
			ss.updateData(member);
			log.debug("수정 성공!");
		} catch (Exception e) {
			log.debug("수정 실패! 회원이 존재하지 않습니다.");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("delete")
	public String delete(@RequestParam(name = "id") int id) {
		
		log.debug("삭제할 id: {}", id);
		
		ss.deleteData(id);
		
		return "redirect:/";
	}
}
