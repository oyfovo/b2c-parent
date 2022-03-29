package com.turing.b2c.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.turing.b2c.mapper.ContentMapper;
import com.turing.b2c.model.pojo.Content;
import com.turing.b2c.model.pojo.ContentExample;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author HHF-QAQ
 * @date 2021年12月16日 15:26
 */
@Service
public class ContentServiceImpl implements ContentService{
    //操作数据库
    @Autowired
    private ContentMapper contentMapper;
    //操作缓存
    @Autowired
    private RedisTemplate<Object,Object> template; //操作缓存

    @Override
    public Content findById(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Content> findAll() {
        return contentMapper.selectByExample(null);
    }

    /**
     * 输入的第一个值:contentList，就是大key，
     * 小key就是entity，你加进来的编号，它的getCategoryId.
     * 不适合使用注解，要使用RedistTemplate
     * @author HHF-OVO
     * @date 2021/12/17 21:28
     * @param entity
     */
    @Override
    public void save(Content entity) {
        //数据库添加数据要做的操作
        contentMapper.insertSelective(entity);
        //缓存做删除操作
        template.opsForHash().delete("contentList",entity.getCategoryId());
    }

    /**
     * 修改之前的缓存也要删除掉，修改完成后的缓存也要删除掉
     * @author HHF-OVO
     * @date 2021/12/17 21:36
     * @param entity
     */
    @Override
    public void update(Content entity) {
        //删除缓存的操作，通过主键查询对象
        Content content = contentMapper.selectByPrimaryKey(entity.getId());
        template.opsForHash().delete("contentList",content.getCategoryId());
        //数据库修改操作,做修改之前缓存得删除，所以缓存优先
        contentMapper.updateByPrimaryKeySelective(entity);
        //删除缓存后
        if(!content.getCategoryId().equals(entity.getCategoryId())){
            template.opsForHash().delete("contentList",entity.getCategoryId());
        }
    }

    @Override
    public void delete(Long id) {
        //数据库做的删除操作。。
        contentMapper.deleteByPrimaryKey(id);
        //缓存做的删除
        Content content = contentMapper.selectByPrimaryKey(id);
        template.opsForHash().delete("contentList",content.getCategoryId());
    }

    /**
     * 删除缓存前就得操作，优先级，如果删除缓存放在后面，数据库已经删除了，那么缓存查不到任何数据
     * @author HHF-OVO
     * @date 2021/12/17 21:51
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        //循环遍历
        for(long id:ids){
            //查询
            Content content = contentMapper.selectByPrimaryKey(id);
            //执行删除
            template.opsForHash().delete("contentList",content.getCategoryId());

        }
        //数据库执行删除
        ContentExample example = new ContentExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        contentMapper.deleteByExample(example);
    }

    @Override
    public SearchResult<Content> findPage(SearchParam searchParam) {
        //开启分页
        PageHelper.startPage(searchParam.getPageNum(), searchParam.getPageSize());
        //获取结果集
        Page<Content> page = (Page<Content>) findAll();
        //返回结果集
        return new SearchResult<>(page.getTotal(),page.getResult());
    }

    /**
     * 轮流展现广告轮播图实现类，使用Redis缓存进行改造后
     * 大KEY就是contentList，小KEY就是categoryId。
     * 大key 方法的返回值
     * 小key 方法的参数
     * @author HHF-OVO
     * @date 2021/12/17 21:16
     * @param categoryId 广告图ID分类编号
     * @return java.util.List<com.turing.b2c.model.pojo.Content>
     */
    @Cacheable(cacheNames = "contentList")
    @Override
    public List<Content> findByCategoryId(Long categoryId) {
        ContentExample example = new ContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId).andStatusEqualTo("1");
        example.setOrderByClause("sort_order");
        return contentMapper.selectByExample(example);
    }
}
