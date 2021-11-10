package top.mowang.shop.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.common.utils.Query;

import top.mowang.shop.product.dao.BrandDao;
import top.mowang.shop.product.dao.CategoryBrandRelationDao;
import top.mowang.shop.product.dao.CategoryDao;
import top.mowang.shop.product.entity.BrandEntity;
import top.mowang.shop.product.entity.CategoryBrandRelationEntity;
import top.mowang.shop.product.entity.CategoryEntity;
import top.mowang.shop.product.service.CategoryBrandRelationService;

import javax.annotation.Resource;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Resource
    BrandDao brandDao;

    @Resource
    CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        //查询名字
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        //设置进去
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandId(brandId);
        categoryBrandRelationEntity.setBrandName(name);
        this.update(categoryBrandRelationEntity,
                new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
    }


    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId,name);
    }


}