package net.dsa.ex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.ex.dto.PerfumeDTO;
import net.dsa.ex.service.PerfumeService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PerfumeController {
	
	private final PerfumeService ps;
	
	@GetMapping({"", "/"})
	public String main() {
		return "main";
	}
	
	@PostMapping("/submit")
	public String submitOrder(PerfumeDTO dto) {
		
		log.debug("설문조사 정보: {}", dto);
		
		try {
			ps.submit(dto);
			log.debug("응답 성공!");
		} catch (Exception e) {
			log.debug("[예외 발생] 응답 실패..");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/result")
	public String result(Model model) {
		
		List<PerfumeDTO> PerfumeList = ps.selectAll();
		int femaleCount = 0;
		int maleCount = 0;
		
		for (PerfumeDTO dto : PerfumeList) {
			if(dto.getGender().equals("남성")) {
				maleCount++;
			}else {
				femaleCount++;
			}
			
		}
		model.addAttribute("maleCount", maleCount);
		model.addAttribute("femaleCount", femaleCount);
		model.addAttribute("Perfume", PerfumeList);
		
		return "result";
	}
}
