package com.diviso.graeshoppe.offer.repository.search;

import com.diviso.graeshoppe.offer.domain.OfferTarget;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OfferTarget entity.
 */
public interface OfferTargetSearchRepository extends ElasticsearchRepository<OfferTarget, Long> {
}
