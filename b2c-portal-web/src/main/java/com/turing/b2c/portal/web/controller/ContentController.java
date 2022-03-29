package com.turing.b2c.portal.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.content.service.impl.ContentService;
import com.turing.b2c.model.pojo.Content;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HHF-QAQ
 * @date 2021年12月16日 16:12
 * 广告主页控制器
 */
@RestController
@RequestMapping("/portal")
@CrossOrigin(origins = "*") //跨域访问
public class ContentController {

    /**
     * RESTFUL风格：
     * GET：查询
     * POST：添加
     * PUT：修改
     * DELETE：删除
     */
    //依赖业务
    @Reference
    private ContentService contentService;

    //根据id查询Content
    @GetMapping("/content/{id}")
    public Content findById(@PathVariable("id") Long id){
        return contentService.findById(id);
    }

    //根据categoryId查询Content
    @GetMapping("/contents/{categoryId}")
    public List<Content> findByCategoryId(@PathVariable("categoryId") Long categoryId){
        return contentService.findByCategoryId(categoryId);
    }

    //添加content
    @PostMapping("/content")
    public MsgBox save(@RequestBody Content entity){
        try {
            contentService.save(entity);
            return new MsgBox(true, "添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "添加失败！");
        }
    }

    //修改content
    @PutMapping("/content")
    public MsgBox update(@RequestBody Content entity){
        try {
            contentService.update(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    //删除content
    @DeleteMapping("/content/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids){
        try {
            contentService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }

    //分页查询
    @GetMapping("/contents")
    public SearchResult<Content> findPage(SearchParam searchParam){
        return contentService.findPage(searchParam);
    }

    //查询所有品牌
    @GetMapping("/content")
    public List<Content> findAll(){
        return contentService.findAll();
    }
}
