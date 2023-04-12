package ksmart.mybatis.dto;

public class Goods {
	private String goodsCode;
	private String goodsName;
	private String goodsPrice;
	private String goodsSellerId;
	private String goodsRegDate;
	
	private Member sellerInfo;
	
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsSellerId() {
		return goodsSellerId;
	}

	public void setGoodsSellerId(String goodsSellerId) {
		this.goodsSellerId = goodsSellerId;
	}

	public String getGoodsRegDate() {
		return goodsRegDate;
	}

	public void setGoodsRegDate(String goodsRegDate) {
		this.goodsRegDate = goodsRegDate;
	}

	public Member getSellerInfo() {
		return sellerInfo;
	}

	public void setSellerInfo(Member sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Goods [goodsCode=");
		builder.append(goodsCode);
		builder.append(", goodsName=");
		builder.append(goodsName);
		builder.append(", goodsPrice=");
		builder.append(goodsPrice);
		builder.append(", goodsSellerId=");
		builder.append(goodsSellerId);
		builder.append(", goodsRegDate=");
		builder.append(goodsRegDate);
		builder.append(", sellerInfo=");
		builder.append(sellerInfo);
		builder.append("]");
		return builder.toString();
	}
	
	
}
