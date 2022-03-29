package com.turing.b2c.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.turing.b2c.mapper.ItemCatMapper;
import com.turing.b2c.model.pojo.ItemCat;
import com.turing.b2c.model.pojo.ItemCatExample;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.sellergoods.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 品牌实现类
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public ItemCat findById(Long id) {
        return itemCatMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return itemCatMapper.selectByExample(null);
    }

    @Override
    public void save(ItemCat entity) {
        itemCatMapper.insertSelective(entity);
    }

    @Override
    public void update(ItemCat entity) {
        itemCatMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void delete(Long id) {
        itemCatMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        ItemCatExample example = new ItemCatExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        itemCatMapper.deleteByExample(example);
    }

    @Override
    public SearchResult<ItemCat> findPage(SearchParam searchParam) {
        //开启分页
        PageHelper.startPage(searchParam.getPageNum(), searchParam.getPageSize());
        //获取结果集
        //PageInfo<ItemCat> pageInfo = new PageInfo<>(findAll());
        Page<ItemCat> page = (Page<ItemCat>) findAll();
        //返回结果集
        return new SearchResult<>(page.getTotal(),page.getResult());
    }

    /**
     * 查询三级分类列表
     * @param parentId
     * @return
     */
    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        ItemCatExample example = new ItemCatExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        return itemCatMapper.selectByExample(example);
    }
}
