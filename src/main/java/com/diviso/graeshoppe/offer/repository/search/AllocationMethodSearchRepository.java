package com.diviso.graeshoppe.offer.repository.search;

import com.diviso.graeshoppe.offer.domain.AllocationMethod;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AllocationMethod entity.
 */
public interface AllocationMethodSearchRepository extends ElasticsearchRepository<AllocationMethod, Long> {
}
