package com.diviso.graeshoppe.offer.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of OfferTargetSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class OfferTargetSearchRepositoryMockConfiguration {

    @MockBean
    private OfferTargetSearchRepository mockOfferTargetSearchRepository;

}
