package com.turing.b2c.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.turing.b2c.mapper.BrandMapper;
import com.turing.b2c.model.pojo.Brand;
import com.turing.b2c.model.pojo.BrandExample;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 品牌实现类
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Brand findById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectByExample(null);
    }

    @Override
    public void save(Brand entity) {
        brandMapper.insertSelective(entity);
    }

    @Override
    public void update(Brand entity) {
        brandMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void delete(Long id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        BrandExample example = new BrandExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        brandMapper.deleteByExample(example);
    }

    @Override
    public SearchResult<Brand> findPage(SearchParam searchParam) {
        //开启分页
        PageHelper.startPage(searchParam.getPageNum(), searchParam.getPageSize());
        //获取结果集
        //PageInfo<Brand> pageInfo = new PageInfo<>(findAll());
        Page<Brand> page = (Page<Brand>) findAll();
        //返回结果集
        return new SearchResult<>(page.getTotal(),page.getResult());
    }
}
