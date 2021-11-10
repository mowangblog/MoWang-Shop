package top.mowang.shop.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.mowang.shop.product.entity.BrandEntity;
import top.mowang.shop.product.entity.CategoryBrandRelationEntity;
import top.mowang.shop.product.service.CategoryBrandRelationService;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.common.utils.R;
import top.mowang.shop.product.vo.BrandsVo;


/**
 * 品牌分类关联
 *
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    //api/product/categorybrandrelation/brands/list?t=1636532964172&catId=165
    @GetMapping("/brands/list")
    public R getBrandsByCatId(@RequestParam("catId")Long catId){
        List<BrandEntity> brands = categoryBrandRelationService.findBrandsByCatId(catId);

        List<BrandsVo> brandsVoList = brands.stream().map(brandEntity -> {
            BrandsVo brandsVo = new BrandsVo();
            brandsVo.setBrandId(brandEntity.getBrandId());
            brandsVo.setBrandName(brandEntity.getName());
            return brandsVo;
        }).collect(Collectors.toList());
        return R.ok().put("data", brandsVoList);
    }

    /**
     * 列表
     */
    @GetMapping("/catelog/list")
    public R list(@RequestParam("brandId")Long brandId){
        List<CategoryBrandRelationEntity> brands = categoryBrandRelationService.
                list(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
        return R.ok().put("data", brands);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
