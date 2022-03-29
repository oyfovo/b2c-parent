package com.turing.b2c.sellergoods.service;

import com.turing.b2c.model.pojo.Brand;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;

import java.util.List;

/**
 * 品牌接口
 */
public interface BrandService {
    Brand findById(Long id);

    List<Brand> findAll();

    void save(Brand entity);

    void update(Brand entity);

    void delete(Long id);

    void delete(Long[] ids);

    SearchResult<Brand> findPage(SearchParam searchParam);
}
