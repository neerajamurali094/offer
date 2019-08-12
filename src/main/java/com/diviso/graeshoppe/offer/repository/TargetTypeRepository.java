package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.TargetType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TargetType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TargetTypeRepository extends JpaRepository<TargetType, Long> {

}
