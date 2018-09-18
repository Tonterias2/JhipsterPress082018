package com.jhipsterpress.web.service.mapper;

import com.jhipsterpress.web.domain.*;
import com.jhipsterpress.web.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class, ProfileMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "profile.id", target = "profileId")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "postId", target = "post")
    @Mapping(source = "profileId", target = "profile")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
