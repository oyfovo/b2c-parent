package com.turing.b2c.sellergoods.service;

import com.turing.b2c.model.pojo.TypeTemplate;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.union.SpecUnion;

import java.util.List;

/**
 * 商品品牌接口,商品类型模板管理
 */
public interface TypeTemplateService {
    TypeTemplate findById(Long id);

    List<TypeTemplate> findAll();

    void save(TypeTemplate entity);

    void update(TypeTemplate entity);

    void delete(Long id);

    void delete(Long[] ids);

    SearchResult<TypeTemplate> findPage(SearchParam searchParam);

    List<SpecUnion> findSpecUnionById(Long id);
}
