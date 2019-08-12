package com.diviso.graeshoppe.offer.repository.search;

import com.diviso.graeshoppe.offer.domain.DeductionValueType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DeductionValueType entity.
 */
public interface DeductionValueTypeSearchRepository extends ElasticsearchRepository<DeductionValueType, Long> {
}
