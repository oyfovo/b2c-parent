package com.turing.b2c.seach.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Item;
import com.turing.b2c.search.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 搜索栏目
 * @author HHF-OVO
 * @date 2021/12/29 11:59
 * @param
 * @return null
 */
@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "*") //跨域访问
public class ItemController {

    /**
     * RESTFUL风格：
     * GET：查询
     * POST：添加
     * PUT：修改
     * DELETE：删除
     */

    @Reference
    private ItemService itemService;

    //根据id查询Item
    @GetMapping("/item/{id}")
    public Item findById(@PathVariable("id") Long id){
        return itemService.findById(id);
    }

    //添加item
    @PostMapping("/item")
    public MsgBox save(@RequestBody Item entity){
        try {
            itemService.save(entity);
            return new MsgBox(true, "添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "添加失败！");
        }
    }

    //修改item
    @PutMapping("/item")
    public MsgBox update(@RequestBody Item entity){
        try {
            itemService.update(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    //删除item
    @DeleteMapping("/item/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids){
        try {
            itemService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }

    //分页查询
    @GetMapping("/items")
    public SearchResult<Item> findPage(SearchParam searchParam){
        return itemService.findPage(searchParam);
    }

    //查询所有品牌
    @GetMapping("/item")
    public List<Item> findAll(){
        return itemService.findAll();
    }

}
