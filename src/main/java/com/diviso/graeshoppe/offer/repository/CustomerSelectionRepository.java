package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.CustomerSelection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerSelection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerSelectionRepository extends JpaRepository<CustomerSelection, Long> {

}
