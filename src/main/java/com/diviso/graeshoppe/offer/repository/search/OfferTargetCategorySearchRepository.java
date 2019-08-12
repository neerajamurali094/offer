package com.diviso.graeshoppe.offer.repository.search;

import com.diviso.graeshoppe.offer.domain.OfferTargetCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OfferTargetCategory entity.
 */
public interface OfferTargetCategorySearchRepository extends ElasticsearchRepository<OfferTargetCategory, Long> {
}
