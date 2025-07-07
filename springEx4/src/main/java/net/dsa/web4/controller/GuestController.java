package net.dsa.web4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web4.service.GuestService;

@Controller
@Slf4j
@RequestMapping("guest")
@RequiredArgsConstructor
public class GuestController {

	private final GuestService service;
	
}
