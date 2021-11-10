package top.mowang.shop.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.common.utils.Query;

import top.mowang.shop.product.dao.BrandDao;
import top.mowang.shop.product.entity.BrandEntity;
import top.mowang.shop.product.service.BrandService;
import top.mowang.shop.product.service.CategoryBrandRelationService;

import javax.annotation.Resource;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {
    @Resource
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(key)){
            wrapper.eq("brand_id",key).or().like("name",key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        this.updateById(brand);
        if(StrUtil.isNotEmpty(brand.getName())){
            //同步更新其他关联的冗余字段
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());
            // TODO 更新其他关联
        }
    }

}