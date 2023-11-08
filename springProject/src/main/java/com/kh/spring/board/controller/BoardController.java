package com.kh.spring.board.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagenation;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/list.bo")
	public ModelAndView selectList(@RequestParam(value = "cpage", defaultValue = "1") int currentPage,
			ModelAndView mv) {
		int boardCount = boardService.selectListCount();
		
		PageInfo pi = Pagenation.getPageInfo(boardCount, currentPage, 10, 5);
		
		mv.addObject("pi",pi)
		  .addObject("list", boardService.selectList(pi))
		  .setViewName("board/boardListView");
		
		return mv;
	}
}
