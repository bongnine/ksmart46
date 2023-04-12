package ksmart.mybatis.dto;

import java.util.List;

public class Member {
	private String memberId;
	private String memberPw;
	private String memberLevel;
	private String memberLevelName;
	private String memberName;
	private String memberAddr;
	private String memberEmail;
	private String memberRegDate;
	
	private List<Goods> sellerList;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getMemberLevelName() {
		return memberLevelName;
	}

	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberAddr() {
		return memberAddr;
	}

	public void setMemberAddr(String memberAddr) {
		this.memberAddr = memberAddr;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberRegDate() {
		return memberRegDate;
	}

	public void setMemberRegDate(String memberRegDate) {
		this.memberRegDate = memberRegDate;
	}

	public List<Goods> getSellerList() {
		return sellerList;
	}

	public void setSellerList(List<Goods> sellerList) {
		this.sellerList = sellerList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Member [memberId=");
		builder.append(memberId);
		builder.append(", memberPw=");
		builder.append(memberPw);
		builder.append(", memberLevel=");
		builder.append(memberLevel);
		builder.append(", memberLevelName=");
		builder.append(memberLevelName);
		builder.append(", memberName=");
		builder.append(memberName);
		builder.append(", memberAddr=");
		builder.append(memberAddr);
		builder.append(", memberEmail=");
		builder.append(memberEmail);
		builder.append(", memberRegDate=");
		builder.append(memberRegDate);
		builder.append(", sellerList=");
		builder.append(sellerList);
		builder.append("]");
		return builder.toString();
	}
	
}
