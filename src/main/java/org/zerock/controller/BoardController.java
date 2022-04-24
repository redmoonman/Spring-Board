package org.zerock.controller;

import java.io.File;

import javax.management.AttributeValueExp;

import org.apache.ibatis.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
@Log4j
public class BoardController {

		private final BoardService service;
		private String uploadFolder;
	
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
			model.addAttribute("pageMaker", new PageDTO(cri, service.getTotal(cri)));
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
		public void get(@RequestParam("bno") long bno, @ModelAttribute("cri") Criteria cri, Model model) {
			
			model.addAttribute("board", service.get(bno));
		}
		
		
		
//		@GetMapping("/modify")
//		public void modify(@RequestParam("bno") long bno, Model model) {
//			model.addAttribute("board", service.get(bno));
//		}
		
		
	
		@PostMapping("/modify")
		public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
			
			int count = service.modify(board);
			if(count==1) {rttr.addFlashAttribute("result", "success");
			}
			rttr.addAttribute("pageNum", cri.getPageNum());
			rttr.addAttribute("amount", cri.getAmount());			
			rttr.addAttribute("type", cri.getType());	
			rttr.addAttribute("keyword", cri.getKeyword());				
			return "redirect:/board/list";
		}
		
		
		@PostMapping("/remove")
		public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr) {
			
			int count = service.remove(bno);
			if(count==1) {rttr.addFlashAttribute("result", "success");
			}
			
			rttr.addAttribute("pageNum", cri.getPageNum());
			rttr.addAttribute("amount", cri.getAmount());		
			
			return "redirect:/board/list";
		}	
		
		@GetMapping("/uploadForm")
		public void uploadForm() {
			log.info("upload form");
		}
		
		@PostMapping("/uploadFormAction")
		public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
			for (MultipartFile multipartFile : uploadFile) {
			
				log.info("--------------------------------------");
				log.info("Upload File Nmae: "+multipartFile.getOriginalFilename());
				log.info("Upload File Size: "+multipartFile.getSize());
		 
		File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
		
		try {
			multipartFile.transferTo(saveFile);
		} catch (Exception e) {
			log.error(e.getMessage());
		} //end catch
			
		} //end for
		}
		
		
		@GetMapping("/uploadAjax")
		public void uploadAjax() {
			log.info("upload ajax");
		}
		
		@PostMapping("/uploadAjaxAction")
		public void uploadAjaxPost(MultipartFile[] uploadFile) {
		
			log.info("upload ajax ..post........");
			String uploadFolder = "C://upload";
			
			for (MultipartFile multipartFile : uploadFile) {
			
				log.info("--------------------------------------");
				log.info("Upload File Nmae: "+multipartFile.getOriginalFilename());
				log.info("Upload File Size: "+multipartFile.getSize());
		 
			String uploadFileName = multipartFile.getOriginalFilename();	
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("//")+1);
			log.info("only file name: "+uploadFileName);
			
		File saveFile = new File(uploadFolder, uploadFileName);
		
		try {
			multipartFile.transferTo(saveFile);
		} catch (Exception e) {
			log.error(e.getMessage());
		} //end catch
			
		} //end for
		}
		
	}
