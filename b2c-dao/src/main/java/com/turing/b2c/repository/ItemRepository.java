package com.turing.b2c.repository;

import com.turing.b2c.model.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author HHF-QAQ
 * @date 2021年12月22日 9:53
 */
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

}
