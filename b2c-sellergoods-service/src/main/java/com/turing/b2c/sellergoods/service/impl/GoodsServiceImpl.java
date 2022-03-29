package com.turing.b2c.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.turing.b2c.mapper.GoodsMapper;
import com.turing.b2c.model.pojo.Goods;
import com.turing.b2c.model.pojo.GoodsExample;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 品牌实现类
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Goods findById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Goods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    @Override
    public void save(Goods entity) {
        goodsMapper.insertSelective(entity);
    }

    @Override
    public void update(Goods entity) {
        goodsMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void delete(Long id) {
        goodsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        GoodsExample example = new GoodsExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        goodsMapper.deleteByExample(example);
    }

    @Override
    public SearchResult<Goods> findPage(SearchParam searchParam) {
        //开启分页
        PageHelper.startPage(searchParam.getPageNum(), searchParam.getPageSize());
        //获取结果集
        //PageInfo<Goods> pageInfo = new PageInfo<>(findAll());
        Page<Goods> page = (Page<Goods>) findAll();
//        return new SearchResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new SearchResult<>(page.getTotal(),page.getResult());
    }
}
