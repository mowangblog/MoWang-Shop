package top.mowang.shop.product.vo;

import lombok.Data;

/**
 * 商品属性
 * 
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
@Data
public class AttrRespVo extends AttrVo {
	private String catelogName;
	private String groupName;
	private Long[] catelogPath;
}
