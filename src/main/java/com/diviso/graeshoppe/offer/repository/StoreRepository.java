package com.diviso.graeshoppe.offer.repository;

import com.diviso.graeshoppe.offer.domain.Store;
import com.diviso.graeshoppe.offer.service.dto.OfferDTO;
import com.diviso.graeshoppe.offer.service.dto.StoreDTO;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Store entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	
	 Page<Store> findByStoreId(Pageable pageable,Long storeId);
}
