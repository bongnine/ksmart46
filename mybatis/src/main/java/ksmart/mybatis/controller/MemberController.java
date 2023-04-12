package ksmart.mybatis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.mapper.MemberMapper;
import ksmart.mybatis.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@AllArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private final MemberMapper memberMapper;
	
	@GetMapping("/logout")
	public String logout(HttpSession session
						,@CookieValue(value="loginKeepId", required= false) Cookie cookie 
						,HttpServletResponse response) {
		if(cookie !=null) {
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		session.invalidate();
		return "redirect:/member/login";
	}
	
	@PostMapping("/login")
	public String login( @RequestParam(name="memberId") String memberId
			  			,@RequestParam(name="memberPw") String memberPw
			  			,HttpSession session
			  			,RedirectAttributes reAttr
			  			,HttpServletResponse response) {
		String redirect = "redirect:/member/login";
		Map<String, Object> loginResultMap = memberService.loginCheck(memberId, memberPw);
		boolean loginCheck = (boolean) loginResultMap.get("loginCheck");
		if(loginCheck) {
			Member memberInfo = (Member) loginResultMap.get("memberInfo");
			String memberName = memberInfo.getMemberName();
			String memberLevel = memberInfo.getMemberLevel();
			session.setAttribute("SID", 	memberId);
			session.setAttribute("SLEVEL", 	memberLevel);
			session.setAttribute("SNAME", 	memberName);
			
			
			//if(하루동안 유지하는 체크박스 value yes)
			Cookie cookie = new Cookie("loginKeepId", memberId);
			cookie.setPath("/");
			cookie.setMaxAge(60*60*24);// 60초 * 60분 * 24 하루
			response.addCookie(cookie);
			
			redirect = "redirect:/";
		}else {
			reAttr.addAttribute("result", "일치하는 회원의 정보가 없습니다.");
		}
		
		return redirect;
	}
	
	@GetMapping("/login")
	public String login( Model model
						,@RequestParam(name="result", required = false) String result) {
		
		model.addAttribute("title", "로그인");
		if(result != null) model.addAttribute("result", result);
		
		return "login/login";
	}
	
	@PostMapping("/removeMember")
	public String removeMember(@RequestParam(name="memberId") String memberId
							  ,@RequestParam(name="memberPw") String memberPw) {
		
		String redirectURI = "redirect:/member/removeMember?memberId=" + memberId;
		// 비밀번호 확인
		Member member = memberService.getMemberInfoById(memberId);
		if(member != null) {
			String checkPw = member.getMemberPw();
			
			if(checkPw.equals(memberPw)) {
				// 서비스 호출
				memberService.removeMember(memberId);
				redirectURI = "redirect:/member/memberList";
			}
		}
		
		
		return redirectURI;
	}
	
	
	/**
	 * 회원탈퇴화면
	 * @param memberId
	 * @param model
	 * @return
	 */
	@GetMapping("/removeMember")
	public String removeMember(@RequestParam(name="memberId") String memberId
							  ,Model model) {
		
		model.addAttribute("title", "회원탈퇴");
		model.addAttribute("memberId", memberId);
		
		return "member/removeMember";
	}
	
	/**
	 * 회원정보 수정
	 * @param member
	 * @return
	 */
	@PostMapping("/modifyMember")
	public String modifyMember(Member member) {
		
		memberMapper.modifyMember(member);
		
		return "redirect:/member/memberList";
	}
	
	
	/**
	 * 회원수정화면
	 * @param memberId
	 * @param model
	 * @return
	 */
	@GetMapping("/modifyMember")
	public String modifyMember(
				@RequestParam(name="memberId") String memberId
				,Model model) {
		Member memberInfo = memberService.getMemberInfoById(memberId);
		List<MemberLevel> memberLevelList =
					memberService.getMemberLevelList();
		model.addAttribute("title", "회원수정");
		model.addAttribute("memberLevelList", memberLevelList);
		model.addAttribute("memberInfo", memberInfo);
		
		return "member/modifyMember";
	}
	
	/**
	 * 커맨드객체: 클라이언트 쿼리파라미터 요청시 name의 속성이 
	 *             DTO의 멤버변수명과 일치하면 자동 바인딩
	 * @param member <- 커맨드객체 
	 * @return String  redirect 키워드: 리디렉션 요청
	 * 문법 :  redirect:요청주소;
	 */
	@PostMapping("/addMember")
	public String addMember(Member member) {
		log.info("화면에서 전달받은 데이터 : {}", member);
		memberService.addMember(member);
		return "redirect:/member/memberList";
	}
	
	@PostMapping("/idCheck")
	@ResponseBody
	public boolean idCheck(@RequestParam(name="memberId") String memberId) {
		boolean checked = true;
		//아이디 중복체크
		checked = memberMapper.idCheck(memberId);
		
		return checked;
	}
	
	@GetMapping("/addMember")
	public String addMember(Model model) {
		
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("title", "회원가입");
		model.addAttribute("memberLevelList", memberLevelList);
		
		return "member/addMember";
	}
	
	/**
	 * http://localhost/member/memberList
	 * @param model
	 * @return
	 */
	@GetMapping("/memberList")
	public String getMemberList( Model model
								,@RequestParam(name="searchKey", required = false) String searchKey
								,@RequestParam(name="searchValue", required = false) String searchValue) {
		List<Member> memberList = memberService.getMemberList(searchKey, searchValue);
		
		//log.info("memberList: {}", memberList);
		
		model.addAttribute("title", "회원목록조회");
		model.addAttribute("memberList", memberList);
		
		return "member/memberList";
	}
	
	
	
	
	
	
}
