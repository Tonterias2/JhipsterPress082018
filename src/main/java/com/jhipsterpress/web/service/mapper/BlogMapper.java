package com.jhipsterpress.web.service.mapper;

import com.jhipsterpress.web.domain.*;
import com.jhipsterpress.web.service.dto.BlogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Blog and its DTO BlogDTO.
 */
@Mapper(componentModel = "spring", uses = {CommunityMapper.class, UserMapper.class})
public interface BlogMapper extends EntityMapper<BlogDTO, Blog> {

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "community.id", target = "communityId")
    BlogDTO toDto(Blog blog);

    @Mapping(source = "userId", target = "id")
    @Mapping(source = "communityId", target = "community")
    @Mapping(target = "posts", ignore = true)
//    @Mapping(target = "community", ignore = true)
    Blog toEntity(BlogDTO blogDTO);

    default Blog fromId(Long id) {
        if (id == null) {
            return null;
        }
        Blog blog = new Blog();
        blog.setId(id);
        return blog;
    }
}
