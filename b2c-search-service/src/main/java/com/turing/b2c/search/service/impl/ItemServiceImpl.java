package com.turing.b2c.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.turing.b2c.mapper.ItemMapper;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Item;
import com.turing.b2c.model.pojo.ItemExample;
import com.turing.b2c.repository.ItemRepository;
import com.turing.b2c.search.service.ItemService;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 搜索功能实现类
 * @author HHF-OVO
 * @date 2021/12/22 9:49
 * @param
 * @return null
 */
@Service

public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    //操作ES，来保持数据
    @Autowired
    private ItemRepository itemRepository;

    //操作ES，用来CRUD
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //设置自定义查询字段范围
    private String[] fieldNames = new String[]{"title","seller","category","brand"};

    @Override
    public Item findById(Long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Item> findAll() {
        return itemMapper.selectByExample(null);
    }

    @Override
    public void save(Item entity) {
        itemMapper.insertSelective(entity);
    }

    @Override
    public void update(Item entity) {
        itemMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void delete(Long id) {
        itemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        ItemExample example = new ItemExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        itemMapper.deleteByExample(example);
    }

    @Override
    public SearchResult<Item> findPage(SearchParam searchParam) {

        SearchResult<Item> result = new SearchResult<>();
        //检索Item
        searchItemList(result,searchParam);
        //返回结果集
        return result;
    }

    private void searchItemList(SearchResult<Item> result, SearchParam searchParam) {
        //创建本地搜索查询
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //创建一个查询构建对象
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        //判断
        if(!Strings.isNullOrEmpty(searchParam.getKeyword())){
            queryBuilder = QueryBuilders.multiMatchQuery(searchParam.getKeyword(),fieldNames);
        }
        /**
         * 设置自定义参数
         * builder.withIndices("b2c") 索引名
         * withTypes("item") 类型名
         * withPageable(PageRequest.of(searchParam.getPageNum()-1, searchParam.getPageSize())); 分页
         * @author HHF-OVO
         * @date 2021/12/22 11:25
         * @param result
         * @param searchParam
         */
        builder.withIndices("b2c").withTypes("item").withPageable(PageRequest.of(searchParam.getPageNum()-1, searchParam.getPageSize()));
        //构建结构
        NativeSearchQuery searchQuery = builder.build();
        //用ES的template来执行查询
        AggregatedPage<Item> items = elasticsearchTemplate.queryForPage(searchQuery,Item.class);
        //封装到Result中
        //总记录数
        result.setTotal(items.getTotalElements());

        result.setList(items.getContent());
    }

    @Override
    public void index() {
        //查询所有商品
        List<Item> items = findAll();
        //遍历所有商品
        items.forEach(item -> {
            //把JSON字段处理
            item.setSprcMap(JSON.parseObject(item.getSpec(), Map.class));
            //使用ES来存储item
            itemRepository.index(item);//使用ES-6.x版本中的方法【7.x没有该方法】
        });
    }
}
