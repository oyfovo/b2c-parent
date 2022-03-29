package com.turing.b2c.seller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.model.pojo.Goods;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.seller.utils.FastDFSClient;
import com.turing.b2c.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author H ovo
 * @date 2021年12月10日 11:15
 */
@RestController
@RequestMapping("/seller")
@CrossOrigin(origins = "*") //跨域访问
public class GoodsCatController {

    /**
     * RESTFUL风格：
     * GET：查询
     * POST：添加
     * PUT：修改
     * DELETE：删除
     */
    @Reference
    private GoodsService goodsService;

    @Value("${file.server.path}")
    private String fileServicePath;

    //根据id查询Goods
    @GetMapping("/goods/{id}")
    public Goods findById(@PathVariable("id") Long id){
        return goodsService.findById(id);
    }

    //添加Goods
    @PostMapping("/goods")
    public MsgBox save(@RequestBody Goods entity){
        try {
            goodsService.save(entity);
            return new MsgBox(true, "添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "添加失败！");
        }
    }

    //修改Goods
    @PutMapping("/goods")
    public MsgBox update(@RequestBody Goods entity){
        try {
            goodsService.update(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    //删除Goods
    @DeleteMapping("/goods/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids){
        try {
            goodsService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }

    //分页查询
    @GetMapping("/goodss")
    public SearchResult<Goods> findPage(SearchParam searchParam){
        return goodsService.findPage(searchParam);
    }

    //查询所有品牌
    @GetMapping("/goods")
    public List<Goods> findAll(){
        return goodsService.findAll();
    }

    /**
     * 上传文件
     * @author HHF-OVO
     * @date 2021/12/15 17:34
     * @param pic
     * @return com.turing.b2c.model.dto.MsgBox
     */
    @PostMapping("/upload")
    public MsgBox upload(@RequestParam("pic") MultipartFile pic){
        try {
            //创建文件上传工具类
            FastDFSClient client = new FastDFSClient("fdfs_client.conf");
            //使用文件上传方法
            String url = fileServicePath + client.uploadFile(pic.getBytes(), "jpg");
            //返回结果
            return new MsgBox(true,url);
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false,"上床文件失败！");
        }
    }
}
