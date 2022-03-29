package com.turing.b2c.manager.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.model.pojo.Specification;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.union.SpecUnion;
import com.turing.b2c.sellergoods.service.SpecificationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin(origins = "*") //跨域访问
public class SpecificationController {

    /**
     * RESTFUL风格：
     * GET：查询
     * POST：添加
     * PUT：修改
     * DELETE：删除
     */

    @Reference
    private SpecificationService specificationService;

    //根据id查询Specification
    @GetMapping("/specification/{id}")
    public SpecUnion findById(@PathVariable("id") Long id){
        return specificationService.findByUnion(id);
    }

    //添加specification
    @PostMapping("/specification")
    public MsgBox save(@RequestBody SpecUnion entity){
        try {
            specificationService.saveUnion(entity);
            return new MsgBox(true, "添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "添加失败！");
        }
    }

    //修改specification
    @PutMapping("/specification")
    public MsgBox update(@RequestBody SpecUnion entity){
        try {
            specificationService.updateUnion(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    //删除specification
    @DeleteMapping("/specification/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids){
        try {
            specificationService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }

    //分页查询
    @GetMapping("/specifications")
    public SearchResult<Specification> findPage(SearchParam searchParam){
        return specificationService.findPage(searchParam);
    }

    //查询所有规格
    @GetMapping("/specification")
    public List<Specification>findAll(){
        return specificationService.findAll();
    }
}
