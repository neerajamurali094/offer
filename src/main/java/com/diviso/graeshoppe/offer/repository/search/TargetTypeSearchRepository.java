package com.diviso.graeshoppe.offer.repository.search;

import com.diviso.graeshoppe.offer.domain.TargetType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TargetType entity.
 */
public interface TargetTypeSearchRepository extends ElasticsearchRepository<TargetType, Long> {
}
