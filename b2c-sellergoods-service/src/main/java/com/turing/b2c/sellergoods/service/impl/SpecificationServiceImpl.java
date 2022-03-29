package com.turing.b2c.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.turing.b2c.mapper.SpecificationMapper;
import com.turing.b2c.mapper.SpecificationOptionMapper;
import com.turing.b2c.model.pojo.Specification;
import com.turing.b2c.model.pojo.SpecificationExample;
import com.turing.b2c.model.pojo.SpecificationOption;
import com.turing.b2c.model.pojo.SpecificationOptionExample;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.union.SpecUnion;
import com.turing.b2c.sellergoods.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 规格管理实现类
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public Specification findById(Long id) {
        return specificationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Specification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    @Override
    public void save(Specification entity) {
        specificationMapper.insertSelective(entity);
    }

    @Override
    public void update(Specification entity) {
        specificationMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void delete(Long id) {
        specificationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        //删除规格明细
        SpecificationOptionExample ex = new SpecificationOptionExample();
        ex.createCriteria().andSpecIdIn(Arrays.asList(ids));
        specificationOptionMapper.deleteByExample(ex);
        //删除规格
        SpecificationExample example = new SpecificationExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        specificationMapper.deleteByExample(example);
    }

    @Override
    public SearchResult<Specification> findPage(SearchParam searchParam) {
        //开启分页
        PageHelper.startPage(searchParam.getPageNum(), searchParam.getPageSize());
        //获取结果集
        //PageInfo<Specification> pageInfo = new PageInfo<>(findAll());
        Page<Specification> page = (Page<Specification>) findAll();
//        return new SearchResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new SearchResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public void saveUnion(SpecUnion specUnion) {
        Specification spec = specUnion.getSpec();
        //保存规格
        specificationMapper.insertSelective(spec);
        //保存规格明细，循环遍历
        for (SpecificationOption specOption : specUnion.getSpecOptionList()) {
            //设置保存规格外键编号
            specOption.setSpecId(spec.getId());
            //保存规格明细
            specificationOptionMapper.insertSelective(specOption);
        }
    }

    @Override
    public void updateUnion(SpecUnion specUnion) {
        Specification spec = specUnion.getSpec();
        //修改规格
        specificationMapper.updateByPrimaryKeySelective(spec);
        //删除老的规格明细
        SpecificationOptionExample example = new SpecificationOptionExample();
        example.createCriteria().andSpecIdEqualTo(spec.getId());
        specificationOptionMapper.deleteByExample(example);
        //保存新的规格明细
        for (SpecificationOption specOption : specUnion.getSpecOptionList()) {
            //设置规格外键
            specOption.setSpecId(spec.getId());
            //保存规格明细
            specificationOptionMapper.insertSelective(specOption);
        }
    }

    @Override
    public SpecUnion findByUnion(Long id) {
        //查询规格
        Specification spec = specificationMapper.selectByPrimaryKey(id);
        //查询明细
        SpecificationOptionExample example = new SpecificationOptionExample();
        example.createCriteria().andSpecIdEqualTo(spec.getId());
        //查询全部
        List<SpecificationOption> specOptionList = specificationOptionMapper.selectByExample(example);
        return new SpecUnion(spec, specOptionList);
    }
}
