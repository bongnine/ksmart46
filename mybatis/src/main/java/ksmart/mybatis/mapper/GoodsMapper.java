package ksmart.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import ksmart.mybatis.dto.Goods;

@Mapper
public interface GoodsMapper {
	// 상품삭제
	public int removeGoodsByGoodsCode(String goodsCode);
	
	// 주문상품삭제
	public int removeOrderByGoodsCode(String goodsCode);
	
	// 상품수정
	public int modifyGoods(Goods goods);
	
	// 특정상품 판매자 여부 조회
	public boolean isSellerByGoodsCode(String memberId, String goodsCode);

	// 특정 상품조회
	public Goods getGoodsInfoByCode(String goodsCode);
	
	// 상품목록조회
	public List<Goods> getGoodsList(Map<String,Object> paramMap);
	
	// 상품등록 
	public int addGoods(Goods goods);



}
