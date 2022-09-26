package com.lim.biz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lim.biz.board.BoardService;
import com.lim.biz.board.BoardVO;
import com.lim.biz.board.impl.BoardDAO;

@Controller
@SessionAttributes("data") //"data"라는 이름의 데이터가 Model객체에 세팅이된다며,그것을 session에 기억시키겠다.
public class BoardController {
	
	@Autowired
	private BoardService boardService; // 비즈니스 컴포넌트. DAO를 직접 이용 xxx
	
	@ModelAttribute("scMap")
	public Map<String,String> searchConditionMap(){
		Map<String,String> scMap=new HashMap<String,String>();
		scMap.put("제목","TITLE");
		scMap.put("작성자","WRITER");
		return scMap;
	}
	@RequestMapping(value="/insert.do",method=RequestMethod.GET)
	public String insert() {
		System.out.println("insert GET방식");
		return "insert.jsp";
	}
	
	@RequestMapping("/main.do")
	   public String main(@RequestParam(value="searchCondition",defaultValue="TITLE",required=false)String searchCondition,@RequestParam(value="searchContent",defaultValue="",required=false)String searchContent,BoardVO bVO,Model model) {
	      System.out.println("검색조건: "+searchCondition);
	      System.out.println("검색어: "+searchContent);
	      
	      List<BoardVO> datas=boardService.selectAllBoard(bVO);
	      
	      model.addAttribute("datas", datas);
	      System.out.println(datas);
	      return "main.jsp";
	   }
		
	@RequestMapping(value="/insert.do" ,method=RequestMethod.POST)
	public String insert(BoardVO vo){
		boardService.insertBoard(vo); 

		return "redirect:main.do";			

	}
	@RequestMapping("/board.do")
	public String board(BoardVO bVO, Model model) {
		bVO=boardService.selectOneBoard(bVO);

		model.addAttribute("data", bVO);
		return "board.jsp";
	}
	@RequestMapping("/updateB.do")
	public String update(@ModelAttribute("data")BoardVO bVO, Model model) {
		
		boardService.updateBoard(bVO);
		
		return "redirect:main.do";
	}
	@RequestMapping("/deleteB.do")
	public String delete(BoardVO bVO, Model model) {
		
		System.out.println(bVO);
		boardService.deleteBoard(bVO);
		return "redirect:main.do";
	}


}
