package com.turing.b2c.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.turing.b2c.mapper.SpecificationOptionMapper;
import com.turing.b2c.mapper.TypeTemplateMapper;
import com.turing.b2c.model.pojo.Specification;
import com.turing.b2c.model.pojo.SpecificationOption;
import com.turing.b2c.model.pojo.SpecificationOptionExample;
import com.turing.b2c.model.pojo.TypeTemplate;
import com.turing.b2c.model.pojo.TypeTemplateExample;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.union.SpecUnion;
import com.turing.b2c.sellergoods.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 品牌接口实现类
 */

@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public TypeTemplate findById(Long id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TypeTemplate> findAll() {
        return typeTemplateMapper.selectByExample(null);
    }

    @Override
    public void save(TypeTemplate entity) {
        typeTemplateMapper.insertSelective(entity);
    }

    @Override
    public void update(TypeTemplate entity) {
        typeTemplateMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void delete(Long id) {
        typeTemplateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        TypeTemplateExample example = new TypeTemplateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        typeTemplateMapper.deleteByExample(example);
    }

    @Override
    public SearchResult<TypeTemplate> findPage(SearchParam searchParam) {
        //开启分页
        PageHelper.startPage(searchParam.getPageNum(), searchParam.getPageSize());
        //获取结果集
        Page<TypeTemplate> page = (Page<TypeTemplate>) findAll();
        return new SearchResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public List<SpecUnion> findSpecUnionById(Long id) {
        //根据接口创建规格联合对象列表
        List<SpecUnion> specUnions = new ArrayList<>();
        //根据id查询TypeTemplate
        TypeTemplate typeTemplate =findById(id);
        //获取typeTemplate对象中的SpecIds
        List<Specification> specifications = JSON.parseArray(typeTemplate.getSpecIds(),Specification.class);
        //遍历specifications
        specifications.forEach(spec ->{
            //根据Spec的id查询specOption
            SpecificationOptionExample example = new SpecificationOptionExample();
            example.createCriteria().andSpecIdEqualTo(spec.getId());
            List<SpecificationOption> specOptionList = specificationOptionMapper.selectByExample(example);
            //封装到联合对象中
            specUnions.add(new SpecUnion(spec,specOptionList));
        });
        return specUnions;
    }
}
