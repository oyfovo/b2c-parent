package com.turing.b2c.seller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.model.pojo.TypeTemplate;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.union.SpecUnion;
import com.turing.b2c.sellergoods.service.TypeTemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author H ovo
 * @date 2021年12月10日 11:17
 */
@RestController
@RequestMapping("/seller")
@CrossOrigin(origins = "*") //跨域访问
public class TypeTemplateController {

    /**
     * RESTFUL风格：
     * GET：查询
     * POST：添加
     * PUT：修改
     * DELETE：删除
     */

    @Reference
    private TypeTemplateService typeTemplateService;

    //根据id查询TypeTemplate
    @GetMapping("/typeTemplate/{id}")
    public TypeTemplate findById(@PathVariable("id") Long id){
        return typeTemplateService.findById(id);
    }

    /**
     * 根据ID查询SpecUnion列表
     * @param id
     * @return
     * http://localhost:9002/seller/typeTemplate/specUnions/35
     */
    @GetMapping("/typeTemplate/specUnions/{id}")
    public List<SpecUnion> findSpecUnionById(@PathVariable("id") Long id){
        return typeTemplateService.findSpecUnionById(id);
    }
    //添加typeTemplate
    @PostMapping("/typeTemplate")
    public MsgBox save(@RequestBody TypeTemplate entity){
        try {
            typeTemplateService.save(entity);
            return new MsgBox(true, "添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "添加失败！");
        }
    }

    //修改typeTemplate
    @PutMapping("/typeTemplate")
    public MsgBox update(@RequestBody TypeTemplate entity){
        try {
            typeTemplateService.update(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    //删除typeTemplate
    @DeleteMapping("/typeTemplate/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids){
        try {
            typeTemplateService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }

    //分页查询
    @GetMapping("/typeTemplates")
    public SearchResult<TypeTemplate> findPage(SearchParam searchParam){
        return typeTemplateService.findPage(searchParam);
    }

    //查询所有品牌
    @GetMapping("/typeTemplate")
    public List<TypeTemplate> findAll(){
        return typeTemplateService.findAll();
    }

}
