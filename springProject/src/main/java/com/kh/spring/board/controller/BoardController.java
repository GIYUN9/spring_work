package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
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
	
	@RequestMapping(value = "/enrollForm.bo")
	public String enrollForm() {
		return "board/boardEnrollForm";
	}
	
	@RequestMapping(value = "/insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session, Model model) {
//		System.out.println(b);
//		System.out.println(upfile);
		
		//전달된 파일이 있을경우 파일명 수정후 서버 업로드 => 원본명, 서버업로드된 경로로 b에 담기(파일이 있을때만)
		if(!upfile.getOriginalFilename().equals("")) {
			
			String changeName = saveFile(upfile, session, "resources/uploadFiles/");
			
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		int result = boardService.insertBoard(b);
		
		if(result > 0) { // 성공 -> 게시글 리스트 페이지
			session.setAttribute("alertMsg", "게시글 작성 완료");
			return "redirect:list.bo";
		}else { //실패 => 에러페이지
			model.addAttribute("errorMsg", "게시글 작성 실패");
			return "common/errorMsg";
		}
	}
	
	public String saveFile(MultipartFile upfile, HttpSession session, String path) {
		//파일명 수정 후 서버 업로드 시키기("음악스트리밍.png" => 20231109102712345.png)
		//년월일시분초 + 랜덤숫자 5개 + 확장자
		
		//원래 파일명
		String originName = upfile.getOriginalFilename();
		
		//시간정보(년월일시분초)
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		//랜덤숫자 5자리
		int ranNum = (int)((Math.random() * 90000) + 10000);
		
		//확장자
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext;
		
		//첨부파일 저장할 폴더의 물리적인 경우
		String savePath = session.getServletContext().getRealPath(path);
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return changeName;
	}
	
	@RequestMapping(value = "detail.bo")
	public String selectBoard(int bno, HttpSession session, Model model) {
		
		int result = boardService.increaseCount(bno);
		
		if(result > 0) { //조회수 증가 성공
			Board b = boardService.selectBoard(bno);
			session.setAttribute("b", b);
			return "board/boardDetailView";
		} else{ //실패
			model.addAttribute("errorMsg", "게시글 조회 실패");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value = "delete.bo")
	public String deleteBoard(int bno, String filePath, HttpSession session, Model model) {
		
		int result = boardService.deleteBoard(bno);
		
		if(result > 0) {
			//첨부파일이있다면 삭제
			if(!filePath.equals("")) {
				//기존에 존재하는 첨부파일 삭제
				// new File(파일물리경로).delete();
				new File(session.getServletContext().getRealPath(filePath)).delete();
			}
			session.setAttribute("alertMsg", "게시글 삭제 완료");
			
			return "redirect:list.bo";
		} else {
			model.addAttribute("errorMsg", "게시글 삭제 실패");
			return "common/errorMsg";
		}
		
		
	}
	
	@RequestMapping(value = "updateForm.bo")
	public String updateForm(int bno, Model model) {
		
		//현재 내가 수정하기를 클릭한 게시글에 대한 정보를 가지고 이동
		model.addAttribute("b", boardService.selectBoard(bno));
		
		
		return "board/boardUpdateForm";
	}
	
	@RequestMapping(value = "update.bo")
	public String updateBoard(Board b, MultipartFile reupfile, HttpSession session, Model model) {
		
//		System.out.println(b);
//		System.out.println(reupfile);
		
		// 새로운 첨부파일 존재유무 확인
		
		if(!reupfile.getOriginalFilename().equals("")) {
			// 새로운 첨부파일 서버 업로드
			String changeName = saveFile(reupfile, session, "resources/uploadFiles/");
			
			// 새로운 첨부파일 있다면 => 기존 첨부파일 삭제
			if(b.getOriginName() != null) {
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
			
			// b객체에 새로운 첨부파일 정보(원본명, 저장경로) 저장
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		// b객체 update
		
		int result = boardService.updateBoard(b);
		
		// 성공유무 확인 후 페이지 리턴
		
		if(result > 0) {
			//성공
			session.setAttribute("alertMsg", "게시글 수정 성공");
			session.setAttribute("b", b);
			return "redirect:detail.bo?bno=" + b.getBoardNo();
		} else {
			//실패
			model.addAttribute("errorMsg", "게시글 수정 실패");
			return "common/errorMsg";
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value = "rlist.bo", produces = "application/json; charset=UTF-8")
	public String ajaxSelectReplyList(int bno, HttpSession session, Model model) {
		ArrayList<Reply> rList = boardService.selectReplyList(bno);
		
		return new Gson().toJson(rList);
	}
	
	@ResponseBody
	@RequestMapping(value = "insertReply.bo")
	public String ajaxInsertReply(Reply r) {
		
//		int result = boardService.insertReply(r);
//			return "success";
//		if(result > 0) {
//			return "fail";
//		}
		return boardService.insertReply(r) > 0 ? "success" : "fail";
	}
	
	@ResponseBody
	@RequestMapping(value = "topList.bo", produces = "application/json; charset=UTF-8")
	public String ajaxTopBoardList() {
		return new Gson().toJson(boardService.selectTopBoardList());
	}
}
