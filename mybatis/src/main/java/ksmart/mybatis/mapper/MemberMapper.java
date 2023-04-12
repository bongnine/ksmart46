package ksmart.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;

@Mapper
public interface MemberMapper {
	// 판매자별 상품조회
	public List<Member> goodsListBySeller(Map<String, Object> searchMap);
	
	// 판매자가 등록한 상품 주문 이력 삭제
	public int removeOrderBySellerId(String memberId);
	// 판매자가 등록한 상품 삭제
	public int removeGoodsBySellerId(String memberId);
	// 구매자가 주문한 이력 삭제
	public int removeOrderById(String memberId);
	// 로그인 이력 삭제
	public int removeLoginById(String memberId);
	// 회원 탈퇴
	public int removeMemberById(String memberId);
	// 회원수정
	public int modifyMember(Member member);
	// 특정회원조회
	public Member getMemberInfoById(String memberId);
	// 회원가입
	public int addMember(Member member);
	// 회원아이디 중복체크
	public boolean idCheck(String memberId);
	// 회원의 목록 조회
	public List<Member> getMemberList(String searchKey, String searchValue);
	// 회원등급 조회
	public List<MemberLevel> getMemberLevelList();
}
