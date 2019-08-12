package com.diviso.graeshoppe.offer.repository.search;

import com.diviso.graeshoppe.offer.domain.PaymentRule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PaymentRule entity.
 */
public interface PaymentRuleSearchRepository extends ElasticsearchRepository<PaymentRule, Long> {
}
