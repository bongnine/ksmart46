package ksmart.mybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.mapper.GoodsMapper;
import ksmart.mybatis.mapper.MemberMapper;
import ksmart.mybatis.service.GoodsService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

	private final GoodsService goodsService;
	private final MemberMapper memberMapper;
	private final GoodsMapper goodsMapper;
	
	public GoodsController(GoodsService goodsService, MemberMapper memberMapper, GoodsMapper goodsMapper) {
		this.goodsService = goodsService;
		this.goodsMapper = goodsMapper;
		this.memberMapper = memberMapper;
	}
	/**
	 *@PostMapping("/removeGoods"): HTTP POST 요청을 /removeGoods 경로로 처리합니다.
	 *@RequestParam(name="goodsCode") String goodsCode: 요청 파라미터 중 
	 *goodsCode 값을 String 타입의 goodsCode 변수에 매핑합니다.
	 *@RequestParam(name="memberId") String memberId: 요청 파라미터 중 
	 *memberId 값을 String 타입의 memberId 변수에 매핑합니다.
	 *@RequestParam(name="memberPw") String memberPw: 요청 파라미터 중 
	 *memberPw 값을 String 타입의 memberPw 변수에 매핑합니다.
	 *HttpSession session: HttpSession 객체를 받아옵니다. (로그인할때 session에 값을 저장해놨음)
	 *HttpSession controller에서만 사용가능하고 memberController에서 저장한것을 goodsController에서 사용가능한다.
	 *RedirectAttributes reAttr: RedirectAttributes 객체를 받아옵니다.
	 */
	@PostMapping("/removeGoods")
	public String removeGoods(@RequestParam(name="goodsCode") String goodsCode
							 ,@RequestParam(name="memberId") String memberId
							 ,@RequestParam(name="memberPw") String memberPw
							 ,HttpSession session
							 ,RedirectAttributes reAttr) {
		String memberLevel = (String) session.getAttribute("SLEVEL");
		boolean isDelete = true;
		String msg = "";
		if(memberLevel != null && "2".equals(memberLevel)) {
			isDelete = goodsMapper.isSellerByGoodsCode(memberId, goodsCode);
			//값이 있어서 너는 true
		}
		
		Member member = memberMapper.getMemberInfoById(memberId);
		if(member != null) {
			String checkPw = member.getMemberPw();
			if(!checkPw.equals(memberPw)) isDelete = false;
			//비밀번호가 일치해서 isdelete는 아직 true
		}
		if(isDelete) {
			goodsService.removeGoods(goodsCode);
			msg = "상품코드: "+ goodsCode + " 가 삭제되었습니다.";
		}else {
			msg = "상품코드: "+ goodsCode + " 가 삭제할 수 없습니다.";			
		}
		reAttr.addAttribute("msg", msg);
		
		return "redirect:/goods/goodsList";
	}
	
	@GetMapping("/removeGoods")
	public String removeGoods(Model model
							 ,@RequestParam(name="goodsCode") String goodsCode) {
		model.addAttribute("title", "상품삭제");
		model.addAttribute("goodsCode", goodsCode);
		return "goods/removeGoods";
	}
	/**
	 * 포스트방식으로 URL "/modifyGoods" 요청받았을때 메서드 실행한다.
	 * @param goods
	 * @return 경로 "/goods/goodsList" 로 리다이렉트 한다.
	 */
	@PostMapping("/modifyGoods")
	public String modifyGoods(Goods goods) {
		goodsService.modifyGoods(goods);
		return "redirect:/goods/goodsList";
	}
	
	/**
	 *	get방식으로 ("/modifyGoods") URL을 요청했을때 메서드 실행
	 *
	 * @param model 
	 * @param goodsCode @RequestParam 으로 name 이 "goodsCode"인 전달받은 변숙의 값을 
	 * String dataType인 goodsCode에 담는다.
	 * goods 타입인 goodsInfo변수에 goodsCode를 매개변수로 넣은 goodsService에 있는 getGoodsInfoByCode메서드의
	 * 리턴값을 담는다.
	 * model객체에 goodsInfo를 담는다.
	 * @return 경로로 "goods/modifyGoods" 포워딩한다.
	 */
	@GetMapping("/modifyGoods")
	public String modifyGoods(Model model
							 ,@RequestParam(name="goodsCode") String goodsCode) {
		
		Goods goodsInfo = goodsService.getGoodsInfoByCode(goodsCode);
		
		model.addAttribute("title", "상품수정");
		model.addAttribute("goodsInfo", goodsInfo);
		
		return "goods/modifyGoods";
	}
	
	@PostMapping("/addGoods")
	public String addGoods(Goods goods) {
		goodsService.addGoods(goods);
		return "redirect:/goods/goodsList";
	}
	
	@PostMapping("/sellersInfo")
	@ResponseBody
	public List<Member> sellersInfo(){
		String searchKey = "m.m_level";
		String searchValue = "2";
		List<Member> memberList = memberMapper.getMemberList(searchKey, searchValue);
		memberList.forEach(seller -> seller.setMemberPw(""));
		return memberList;
	}
	
	@GetMapping("/addGoods")
	public String addGoods(Model model) {
		
		model.addAttribute("title", "상품등록");
		
		return "goods/addGoods";
	}
		
	@GetMapping("/sellerList")
	public String getGoodsListBySeller( Model model
									   ,@RequestParam(name="checkSearch", required = false) String[] checkArr
									   ,@RequestParam(name="searchValue", required = false) String searchValue) {
		
		List<Member> goodsListBySeller = goodsService.getGoodsListBySeller(checkArr, searchValue);
		model.addAttribute("title", "판매자별상품조회");
		model.addAttribute("goodsListBySeller", goodsListBySeller);
		
		return "goods/sellerList";
	}
	
	@GetMapping("/goodsList")
	public String getGoodsList(Model model
			   				  ,HttpSession session
			   				  ,@RequestParam(name="msg", required = false) String msg) {
		String memberLevel = (String) session.getAttribute("SLEVEL");
		Map<String, Object> paramMap = null;
		if(memberLevel != null && "2".equals(memberLevel)) {
			String sellerId = (String)session.getAttribute("SID");
			
			paramMap = new HashMap<String, Object>();
			paramMap.put("searchKey", "g_seller_id");
			paramMap.put("searchValue", sellerId);
		}
		
		List<Goods> goodsList = goodsService.getGoodsList(paramMap);
		
		model.addAttribute("title", "상품조회");
		model.addAttribute("goodsList", goodsList);
		if(msg != null) model.addAttribute("msg", msg);
		
		return "goods/goodsList";
	}
}
