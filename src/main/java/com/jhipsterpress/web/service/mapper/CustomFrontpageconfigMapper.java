package com.jhipsterpress.web.service.mapper;

import org.mapstruct.Mapper;

import com.jhipsterpress.web.domain.Frontpageconfig;
import com.jhipsterpress.web.service.dto.CustomFrontpageconfigDTO;

/**
 * Mapper for the entity Frontpageconfig and its DTO FrontpageconfigDTO.
 */
@Mapper(componentModel = "spring", uses = {FrontPagePostMapper.class})
public interface CustomFrontpageconfigMapper extends EntityMapper<CustomFrontpageconfigDTO, Frontpageconfig> {

    default Frontpageconfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        Frontpageconfig frontpageconfig = new Frontpageconfig();
        frontpageconfig.setId(id);
        return frontpageconfig;
    }
}