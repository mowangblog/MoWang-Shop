package top.mowang.shop.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.mowang.shop.product.entity.AttrEntity;
import top.mowang.shop.product.service.AttrService;
import top.mowang.shop.common.utils.PageUtils;
import top.mowang.shop.common.utils.R;
import top.mowang.shop.product.vo.AttrRespVo;
import top.mowang.shop.product.vo.AttrVo;


/**
 * 商品属性
 *
 * @author mowang
 * @email mowangblog@qq.com
 * @date 2021-11-06 23:40:27
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 列表api/product/attr/base/list/基本属性
     * /api/product/attr/sale/list/销售属性
     */
    @GetMapping("/{type}/list/{cid}")
    public R listBase(@RequestParam Map<String, Object> params,
                      @PathVariable("cid") Long cid,
                      @PathVariable("type") String type){
        PageUtils page = attrService.queryBase(params,cid,type);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		AttrRespVo attr = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateDetail(attr);;

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
