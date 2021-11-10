package top.mowang.shop.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.mowang.shop.product.entity.AttrAttrgroupRelationEntity;
import top.mowang.shop.product.entity.AttrEntity;
import top.mowang.shop.product.entity.AttrGroupEntity;
import top.mowang.shop.product.service.AttrAttrgroupRelationService;
import top.mowang.shop.product.service.AttrGroupService;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.common.utils.R;
import top.mowang.shop.product.service.CategoryService;
import top.mowang.shop.product.vo.AttrGroupRespVo;

import javax.annotation.Resource;


/**
 * 属性分组
 *
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
@RestController
@RequestMapping("product/attrgroup")
@SuppressWarnings("all")
public class AttrGroupController {
    @Resource
    private AttrAttrgroupRelationService attrAttrgroupRelationService;
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;

    //api/product/attrgroup/225/withattr?t=1636535677077
    @GetMapping("/{catId}/withattr")
    public R getAttrAttrgroupWithAttr(@PathVariable("catId") Long catId) {
        List<AttrGroupRespVo> list = attrGroupService.getAttrAttrgroupWithAttr(catId);
        return R.ok().put("data",list);
    }

    //product/attrgroup/attr/relation
    @PostMapping("/attr/relation")
    public R batchAdd(@RequestBody List<AttrAttrgroupRelationEntity> relationEntities) {
        boolean b = attrAttrgroupRelationService.saveBatch(relationEntities);

        return R.ok().put("status", b);
    }

    //product/attrgroup/1/noattr/relation 查出还没有和任何属性关联的属性列表
    @GetMapping("{groupId}/noattr/relation")
    public R relationNoattrlist(@RequestParam Map<String, Object> params, @PathVariable("groupId") Long groupId) {
        PageUtils page = attrGroupService.relationNoattrlist(params, groupId);

        return R.ok().put("page", page);
    }

    //api/product/attrgroup/attr/relation/delete
    @PostMapping("/attr/relation/delete")
    public R delete(@RequestBody AttrAttrgroupRelationEntity[] relationEntities) {
        attrGroupService.removeRelationByIds(Arrays.asList(relationEntities));

        return R.ok();
    }

    //api/product/attrgroup/1/attr/relation
    @GetMapping("{groupId}/attr/relation")
    public R list(@PathVariable("groupId") Long groupId) {
        List<AttrEntity> list = attrGroupService.attrGroupRelationList(groupId);

        return R.ok().put("data", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     *
     * @param catelogId 0的话查所有
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable Long catelogId) {
        //        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params, catelogId);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        // 用当前当前分类id查询完整路径并写入 attrGroup
        attrGroup.setCatelogPath(categoryService.findCateLogPath(attrGroup.getCatelogId()));
        return R.ok().put("attrGroup", attrGroup);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
