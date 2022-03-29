package com.turing.b2c.sellergoods.service;

import com.turing.b2c.model.pojo.Specification;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.union.SpecUnion;

import java.util.List;

/**
 * 商品规格接口
 */
public interface SpecificationService {
    Specification findById(Long id);

    List<Specification> findAll();

    void save(Specification entity);

    void update(Specification entity);

    void delete(Long id);

    void delete(Long[] ids);

    SearchResult<Specification> findPage(SearchParam searchParam);

    //联合对象的方法
    void saveUnion(SpecUnion specUnion);

    void updateUnion(SpecUnion specUnion);

    SpecUnion findByUnion(Long id);
}
