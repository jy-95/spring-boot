package net.dsa.web5.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.repository.BoardRepository;
import net.dsa.web5.repository.MemberRepository;
import net.dsa.web5.repository.ReplyRepository;
import net.dsa.web5.util.FileManager;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service

public class BoardService {

	
	private final MemberRepository mr;
	private final BoardRepository br;
	private final ReplyRepository rr;
	private final FileManager fileManager;
	
	
}
