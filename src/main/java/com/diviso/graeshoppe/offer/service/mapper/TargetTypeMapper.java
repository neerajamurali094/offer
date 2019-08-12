package com.diviso.graeshoppe.offer.service.mapper;

import com.diviso.graeshoppe.offer.domain.*;
import com.diviso.graeshoppe.offer.domain.TargetType;
import com.diviso.graeshoppe.offer.service.dto.TargetTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TargetType and its DTO TargetTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TargetTypeMapper extends EntityMapper<TargetTypeDTO, TargetType> {



    default TargetType fromId(Long id) {
        if (id == null) {
            return null;
        }
        TargetType targetType = new TargetType();
        targetType.setId(id);
        return targetType;
    }
}
