package com.diviso.graeshoppe.offer.repository.search;

import com.diviso.graeshoppe.offer.domain.CustomerSelection;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerSelection entity.
 */
public interface CustomerSelectionSearchRepository extends ElasticsearchRepository<CustomerSelection, Long> {
}
