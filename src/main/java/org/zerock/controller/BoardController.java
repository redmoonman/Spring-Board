package org.zerock.controller;

import javax.management.AttributeValueExp;

import org.apache.ibatis.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
@Log4j
public class BoardController {

		private final BoardService service;
	
//		@GetMapping("/list")
//		public void list(Model model) {
//			log.info("list.............");
//			//Model.addAttribute("list", service.getList());
//		
//			model.addAttribute("list", service.getList());
//		}
		
		@GetMapping("/list")
		public void list(Criteria cri, Model model) {
			
			log.info("--------------------------");
			log.info("list.............");
			//Model.addAttribute("list", service.getList());
		
			model.addAttribute("list", service.getList(cri));
			model.addAttribute("pageMaker", new PageDTO(cri, 123));
		}
		
		
		@GetMapping("/register")
		public void registerGET() {
			
		}
		
		@PostMapping("/register")
		public String register(BoardVO board, RedirectAttributes rttr) {
			
			log.info("board: " + board);
			
			Long bno = service.register(board);

			log.info("BNO: " + bno);
			
			rttr.addFlashAttribute("result", bno);
			
			return "redirect:/board/list";
		}
		
		
		@GetMapping({"/get", "/modify"})
		public void get(@RequestParam("bno") long bno, Model model) {
			
			model.addAttribute("board", service.get(bno));
		}
		
		
		
//		@GetMapping("/modify")
//		public void modify(@RequestParam("bno") long bno, Model model) {
//			model.addAttribute("board", service.get(bno));
//		}
		
		
		
		@PostMapping("/modify")
		public String modify(BoardVO board, RedirectAttributes rttr) {
			
			int count = service.modify(board);
			if(count==1) {rttr.addFlashAttribute("result", "success");
			}
			return "redirect:/board/list";
		}
		
		
		@PostMapping("/remove")
		public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
			
			int count = service.remove(bno);
			if(count==1) {rttr.addFlashAttribute("result", "success");
			}
			return "redirect:/board/list";
		}	
		
		
		
	}
