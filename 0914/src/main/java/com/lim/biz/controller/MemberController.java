package com.lim.biz.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lim.biz.member.MemberService;
import com.lim.biz.member.MemberVO;
import com.lim.biz.member.impl.MemberDAO;

@Controller
@SessionAttributes("data")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/login.do",method=RequestMethod.GET)
	public String login() {
		return "login.jsp";
	}
	
	@RequestMapping(value="/signin.do",method=RequestMethod.GET)
	public String signin() {
		System.out.println("signin GET방식");
		return "signin.jsp";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String selectOne(Model model,MemberVO mVO,HttpSession session){

		mVO=memberService.selectOne(mVO);
		if(mVO==null) {
			return "login.jsp";
		}
		else {
			session.setAttribute("member", mVO.getMid());
			model.addAttribute("data", mVO);
			return "redirect:main.do";
		}

	}
	@RequestMapping(value="/logout.do", method=RequestMethod.GET)
	public String logout(@ModelAttribute("data")MemberVO vo,HttpSession session,Model model) {
		System.out.println("1");
		model.addAttribute("data", vo);
		session.invalidate();
		System.out.println(vo);
		return "redirect:main.do";

	}
	
	
	@RequestMapping(value="/signin.do",method=RequestMethod.POST)
	public String signin(MemberVO mVO) {
		memberService.insert(mVO);
		
		return "redirect:main.do";

	}
	@RequestMapping(value="/update.do",method=RequestMethod.POST)
	public String update(Model model,@ModelAttribute("data")MemberVO vo,HttpSession session){
		if(vo != null) {
			
			memberService.update(vo);
			
//			model.addAttribute("data", vo);
			
			
		}
		else {
			return"signin.jsp";
		}
		return "redirect:main.do";
	}
	@RequestMapping(value="/delete.do",method=RequestMethod.GET)
	public String delete(@ModelAttribute("data")MemberVO vo,HttpSession session ) {

//		vo = (MemberVO)session.getAttribute("member"); 
//		System.out.println("3"+vo);
//		vo = memberService.selectOne(vo);

		if(vo != null) {
			
			memberService.delete(vo);
			session.invalidate();
			System.out.println("만료된회원: "+vo);
		}
		return "redirect:main.do";
	}
	@RequestMapping("/mypage.do")
	public String mypage(MemberVO vo,MemberDAO dao,HttpSession session) {
		
		if(vo != null) {
			return "mypage.jsp";
		}
		else {
			return "main.do";
		}	
		
	}
	
	

}
