package top.mowang.shop.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mowang.shop.common.constant.ProductConstant;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.common.utils.Query;

import top.mowang.shop.product.dao.AttrAttrgroupRelationDao;
import top.mowang.shop.product.dao.AttrDao;
import top.mowang.shop.product.dao.AttrGroupDao;
import top.mowang.shop.product.entity.AttrAttrgroupRelationEntity;
import top.mowang.shop.product.entity.AttrEntity;
import top.mowang.shop.product.entity.AttrGroupEntity;
import top.mowang.shop.product.service.AttrGroupService;
import top.mowang.shop.product.service.AttrService;
import top.mowang.shop.product.vo.AttrGroupRespVo;
import top.mowang.shop.product.vo.AttrRespVo;

import javax.annotation.Resource;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Resource
    AttrAttrgroupRelationDao relationDao;

    @Resource
    AttrDao AttrDao;

    @Resource
    AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override // AttrGroupServiceImpl.java
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        // key?????????
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) ->
                    obj.eq("attr_group_id", key).or().like("attr_group_name", key)
            );
        }
        if (catelogId == 0) {
            // Query?????????map?????????IPage
            IPage<AttrGroupEntity> page =
                    this.page(new Query<AttrGroupEntity>().getPage(params),
                            wrapper);
            return new PageUtils(page);
        } else {
            // ??????id??????
            wrapper.eq("catelog_id", catelogId);
            IPage<AttrGroupEntity> page =
                    this.page(new Query<AttrGroupEntity>().getPage(params),
                            wrapper);
            return new PageUtils(page);
        }
    }

    /**
     * @description: ????????????id?????????????????????????????????attr
     * @author: Xuan Li<mowangblog@qq.com>
     * @date: 2021/11/10 17:37
    */
    @Override
    public List<AttrEntity> attrGroupRelationList(Long groupId) {
        List<AttrAttrgroupRelationEntity> attrGroupList = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", groupId));
        List<Long> attrIdList = attrGroupList.stream()
                .map(AttrAttrgroupRelationEntity::getAttrId)
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(attrIdList)) {
            return null;
        }
        return AttrDao.selectBatchIds(attrIdList);
    }

    @Override
    public void removeRelationByIds(List<AttrAttrgroupRelationEntity> asList) {
        List<Long> ids = asList.stream().map(relationEntity -> {
            AttrAttrgroupRelationEntity attrgroupRelationEntity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", relationEntity.getAttrId())
                    .eq("attr_group_id", relationEntity.getAttrGroupId()));
            return attrgroupRelationEntity;
        }).map(AttrAttrgroupRelationEntity::getId).collect(Collectors.toList());
        relationDao.deleteBatchIds(ids);
    }

    @Override
    public PageUtils relationNoattrlist(Map<String, Object> params, Long groupId) {
        //??????????????????????????????attr
        List<Long> usedIdList = relationDao.selectList(null).stream()
                .map(AttrAttrgroupRelationEntity::getAttrId)
                .collect(Collectors.toList());

        //?????????????????????????????????attr????????????????????????
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());

        //?????????????????????attr
        if(CollectionUtil.isNotEmpty(usedIdList)){
           queryWrapper.notIn("attr_id", usedIdList);
        }
        //??????????????????????????????
        String key = (String) params.get("key");
        if(StrUtil.isNotEmpty(key)){
            queryWrapper.eq("attr_id",key).or().like("attr_name",key);
        }
        return new PageUtils(attrService.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        ));
    }

    /**
     * @description: ??????catId???????????????????????????????????????
     * @author: Xuan Li<mowangblog@qq.com>
     * @date: 2021/11/10 17:21
    */
    @Override
    public List<AttrGroupRespVo> getAttrAttrgroupWithAttr(Long catId) {
        //??????catId????????????????????????????????????
        List<AttrGroupEntity> groupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId));
        List<AttrGroupRespVo> respVoList = groupEntities.stream().map(attrGroupEntity -> {
            AttrGroupRespVo attrRespVo = new AttrGroupRespVo();
            BeanUtil.copyProperties(attrGroupEntity, attrRespVo);
            return attrRespVo;
        }).peek(attrGroupRespVo -> {
            Long attrGroupId = attrGroupRespVo.getAttrGroupId();
            //????????????id?????????????????????????????????attr
            List<AttrEntity> attrEntityList = this.attrGroupRelationList(attrGroupId);
            attrGroupRespVo.setAttrs(attrEntityList);
        }).collect(Collectors.toList());

        return respVoList;
    }

}