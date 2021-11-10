package top.mowang.shop.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.common.utils.Query;

import top.mowang.shop.product.dao.AttrAttrgroupRelationDao;
import top.mowang.shop.product.dao.AttrDao;
import top.mowang.shop.product.dao.AttrGroupDao;
import top.mowang.shop.product.dao.CategoryDao;
import top.mowang.shop.product.entity.AttrAttrgroupRelationEntity;
import top.mowang.shop.product.entity.AttrEntity;
import top.mowang.shop.product.entity.AttrGroupEntity;
import top.mowang.shop.product.entity.CategoryEntity;
import top.mowang.shop.product.service.AttrService;
import top.mowang.shop.product.vo.AttrRespVo;
import top.mowang.shop.product.vo.AttrVo;

import javax.annotation.Resource;
import javax.management.relation.Relation;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    AttrAttrgroupRelationDao relationDao;

    @Resource
    CategoryDao categoryDao;

    @Resource
    AttrGroupDao attrGroupDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtil.copyProperties(attr,attrEntity);
        //保存基本数据
        this.save(attrEntity);
        //保存关联关系
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attr.getAttrGroupId());
        relationEntity.setAttrId(attrEntity.getAttrId());

        relationDao.insert(relationEntity);
    }

    @Override
    public PageUtils queryBase(Map<String, Object> params, Long cid) {
        //查询参数规格
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        String key = (String)params.get("key");
        if(StrUtil.isNotEmpty(key)){
            queryWrapper.eq("attr_id",key).or().like("attr_name",key);
        }
        if (cid != 0) {
            queryWrapper.eq("catelog_id",cid);
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> list = records.stream().map(attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtil.copyProperties(attrEntity, attrRespVo);
            //查询参数分组id来获取分组名
            Long attrId = attrEntity.getAttrId();
            AttrAttrgroupRelationEntity relationEntity = relationDao
                    .selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                            .eq("attr_id", attrId));
            Long attrGroupId = relationEntity.getAttrGroupId();
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
            attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            //获取分类id查询分类名并设置进去
            Long catelogId = attrEntity.getCatelogId();
            CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
            attrRespVo.setCatelogName(categoryEntity.getName());
            return attrRespVo;
        }).collect(Collectors.toList());
        PageUtils pageUtils = new PageUtils(page);

        pageUtils.setList(list);
        return pageUtils;
    }

}