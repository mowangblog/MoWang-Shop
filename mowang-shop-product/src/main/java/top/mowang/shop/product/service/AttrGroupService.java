package top.mowang.shop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.product.entity.AttrAttrgroupRelationEntity;
import top.mowang.shop.product.entity.AttrEntity;
import top.mowang.shop.product.entity.AttrGroupEntity;
import top.mowang.shop.product.vo.AttrGroupRespVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params,Long catelogId);

    List<AttrEntity> attrGroupRelationList(Long groupId);

    void removeRelationByIds(List<AttrAttrgroupRelationEntity> asList);

    PageUtils relationNoattrlist(Map<String, Object> params, Long groupId);

    List<AttrGroupRespVo> getAttrAttrgroupWithAttr(Long catId);
}

