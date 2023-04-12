package ksmart.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.mapper.MemberMapper;

// @Transactional 클래스 가지고 있는 메소드 전체에 트랜잭션 처리
@Service
@Transactional
public class MemberService {
	// DI 생성자 메소드 주입방식
	private final MemberMapper memberMapper;
	
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	/**
	 * 회원 탈퇴
	 * @param memberId
	 */
	public void removeMember(String memberId) {
		Member memberInfo = memberMapper.getMemberInfoById(memberId);
		if(memberInfo != null) {
			String memberLevel = memberInfo.getMemberLevel();
			switch (memberLevel) {
			case "2":
				// 판매자가 등록한 상품 주문 이력 삭제
				memberMapper.removeOrderBySellerId(memberId);
				// 판매자가 등록한 상품 삭제
				memberMapper.removeGoodsBySellerId(memberId);
				break;
			case "3":
				// 구매자가 주문한 이력 삭제
				memberMapper.removeOrderById(memberId);
				break;
			}
			// 로그인 이력 삭제
			memberMapper.removeLoginById(memberId);
			// 회원 탈퇴
			memberMapper.removeMemberById(memberId);
		}
		
	}
	
	/**
	 * 회원 정보 수정
	 * @param member
	 */
	public void modifyMember(Member member) {
		memberMapper.modifyMember(member);
	}
	
	/**
	 * 특정회원 정보 조회
	 * @param memberId
	 * @return
	 */
	public Member getMemberInfoById(String memberId) {
		Member memberInfo = memberMapper.getMemberInfoById(memberId);
		return memberInfo;
	}
	
	/**
	 * 회원 가입
	 * @param member
	 * @return
	 */
	public int addMember(Member member) {
		int result = 
				memberMapper.addMember(member);
		
		return result;
	}
	
	/**
	 * 회원 등급 조회
	 * @return List<MemberLevel>
	 */
	public List<MemberLevel> getMemberLevelList(){
		
		List<MemberLevel> memberLevelList = memberMapper.getMemberLevelList();
		
		return memberLevelList;
	}
	
	/**
	 * 회원목록 조회
	 * @return List<Member>
	 */
	public List<Member> getMemberList(String searchKey, String searchValue){
		if(searchKey != null) {
			switch (searchKey) {
			case "memberId":
				searchKey = "m.m_id";
				break;
			case "memberName":
				searchKey = "m.m_name";
				break;
			case "memberEmail":
				searchKey = "m.m_email";
				break;
			default:
				searchKey = "ml.level_name";
				break;
			}
		}
		List<Member> memberList = memberMapper.getMemberList(searchKey, searchValue);
		
		/*
		if(memberList != null) {	
			
			for(Member member : memberList) {
				String memberLevel = member.getMemberLevel();
				switch (memberLevel) {
				case "1":
					member.setMemberLevelName("관리자");
					break;
				case "2":
					member.setMemberLevelName("판매자");
					break;
				default:
					member.setMemberLevelName("구매자");
					break;
				}
			}
			memberList.forEach(member -> {
				String memberLevel = member.getMemberLevel();
				switch (memberLevel) {
				case "1":
					member.setMemberLevelName("관리자");
					break;
				case "2":
					member.setMemberLevelName("판매자");
					break;
				default:
					member.setMemberLevelName("구매자");
					break;
				}
			});
		}
		*/
		return memberList;
	}
	/**
	 * 회원정보 확인(로그인)
	 * @param memberId, memberPw
	 * @return loginResultMap (처리결과)
	 */
	public Map<String, Object> loginCheck(String memberId, String memberPw) {
		Map<String, Object> loginResultMap = new HashMap<String, Object>();
		Member member = memberMapper.getMemberInfoById(memberId);
		boolean loginCheck = false;
		if(member != null) {
			String checkPw = member.getMemberPw();
			if(memberPw.equals(checkPw)) {
				loginCheck = true;
				loginResultMap.put("memberInfo", member);
			}
		}
		loginResultMap.put("loginCheck", loginCheck);
		return loginResultMap;
	}


	
	
	
	
	
	
	
	
	
}
