package com.turing.b2c.search.service;


import com.turing.b2c.model.pojo.Item;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;

import java.util.List;

public interface ItemService {
    Item findById(Long id);

    List<Item> findAll();

    void save(Item entity);

    void update(Item entity);

    void delete(Long id);

    void delete(Long[] ids);

    SearchResult<Item> findPage(SearchParam searchParam);

    /**
     * 用ES存储所有Item
     * @author HHF-OVO
     * @date 2021/12/21 11:40
     * @param
     * @return null
     */
    void index();
}
