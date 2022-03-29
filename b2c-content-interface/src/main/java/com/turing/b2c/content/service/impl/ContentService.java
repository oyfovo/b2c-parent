package com.turing.b2c.content.service.impl;

import com.turing.b2c.model.pojo.Content;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;

import java.util.List;

/**
 * @author HHF-QAQ
 * 广告服务接口
 * @date 2021年12月16日 11:16
 */
public interface ContentService {

    Content findById(Long id);

    List<Content> findAll();

    void save(Content entity);

    void update(Content entity);

    void delete(Long id);

    void delete(Long[] ids);

    SearchResult<Content> findPage(SearchParam searchParam);

    /**
     * 根据categoryId查找Content
     * @author HHF-OVO
     * @date 2021/12/16 11:17
     * @param categoryId 分类编号
     * @return java.util.List<com.turing.b2c.model.pojo.Content> 返回广告内容对象
     */
    List<Content> findByCategoryId(Long categoryId);
}
