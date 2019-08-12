package com.diviso.graeshoppe.offer.repository.search;

import com.diviso.graeshoppe.offer.domain.OrderRule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderRule entity.
 */
public interface OrderRuleSearchRepository extends ElasticsearchRepository<OrderRule, Long> {
}
