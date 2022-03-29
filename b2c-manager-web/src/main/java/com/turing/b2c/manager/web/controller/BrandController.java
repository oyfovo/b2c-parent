package com.turing.b2c.manager.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.model.pojo.Brand;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin(origins = "*") //跨域访问
public class BrandController {

    /**
     * RESTFUL风格：
     * GET：查询
     * POST：添加
     * PUT：修改
     * DELETE：删除
     */

    /**
     * 依赖Service层
     */
    @Reference
    private BrandService brandService;

    //根据id查询Brand
    @GetMapping("/brand/{id}")
    public Brand findById(@PathVariable("id") Long id){
        return brandService.findById(id);
    }

    //添加brand
    @PostMapping("/brand")
    public MsgBox save(@RequestBody Brand entity){
        try {
            brandService.save(entity);
            return new MsgBox(true, "添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "添加失败！");
        }
    }

    //修改brand
    @PutMapping("/brand")
    public MsgBox update(@RequestBody Brand entity){
        try {
            brandService.update(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    //删除brand
    @DeleteMapping("/brand/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids){
        try {
            brandService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }

    //分页查询
    @GetMapping("/brands")
    public SearchResult<Brand> findPage(SearchParam searchParam){
        return brandService.findPage(searchParam);
    }

    //查询所有品牌
    @GetMapping("/brand")
    public List<Brand> findAll(){
        return brandService.findAll();
    }
}
