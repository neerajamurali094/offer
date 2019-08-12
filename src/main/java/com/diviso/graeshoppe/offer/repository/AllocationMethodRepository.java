package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.AllocationMethod;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AllocationMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllocationMethodRepository extends JpaRepository<AllocationMethod, Long> {

}
