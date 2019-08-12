package com.diviso.graeshoppe.offer.repository.search;

import com.diviso.graeshoppe.offer.domain.PriceRule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PriceRule entity.
 */
public interface PriceRuleSearchRepository extends ElasticsearchRepository<PriceRule, Long> {
}
