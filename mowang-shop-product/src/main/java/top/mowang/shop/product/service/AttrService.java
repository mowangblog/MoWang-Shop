package top.mowang.shop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.product.entity.AttrEntity;
import top.mowang.shop.product.vo.AttrRespVo;
import top.mowang.shop.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBase(Map<String, Object> params, Long cid,String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateDetail(AttrVo attr);

    List<Long> selectSearchAttrs(List<Long> attrIds);
}

