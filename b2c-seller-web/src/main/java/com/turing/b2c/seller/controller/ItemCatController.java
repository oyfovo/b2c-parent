package com.turing.b2c.seller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.model.pojo.ItemCat;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.sellergoods.service.ItemCatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author H ovo
 * @date 2021年12月10日 11:15
 */
@RestController
@RequestMapping("/seller")
@CrossOrigin(origins = "*") //跨域访问
public class ItemCatController {

    /**
     * RESTFUL风格：
     * GET：查询
     * POST：添加
     * PUT：修改
     * DELETE：删除
     */
    @Reference
    private ItemCatService itemCatService;

    //根据id查询ItemCat
    @GetMapping("/itemCat/{id}")
    public ItemCat findById(@PathVariable("id") Long id){
        return itemCatService.findById(id);
    }

    //根据parentId查询ItemCat
    @GetMapping("/itemCats/{parentId}")
    public List<ItemCat> findByParentId(@PathVariable("parentId") Long parentId){
        return itemCatService.findByParentId(parentId);
    }

    //添加itemCat
    @PostMapping("/itemCat")
    public MsgBox save(@RequestBody ItemCat entity){
        try {
            itemCatService.save(entity);
            return new MsgBox(true, "添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "添加失败！");
        }
    }

    //修改itemCat
    @PutMapping("/itemCat")
    public MsgBox update(@RequestBody ItemCat entity){
        try {
            itemCatService.update(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    //删除itemCat
    @DeleteMapping("/itemCat/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids){
        try {
            itemCatService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }

    //分页查询
    @GetMapping("/itemCats")
    public SearchResult<ItemCat> findPage(SearchParam searchParam){
        return itemCatService.findPage(searchParam);
    }

    //查询所有品牌
    @GetMapping("/itemCat")
    public List<ItemCat> findAll(){
        return itemCatService.findAll();
    }

}
