package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.DeductionValueType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeductionValueType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeductionValueTypeRepository extends JpaRepository<DeductionValueType, Long> {

}
