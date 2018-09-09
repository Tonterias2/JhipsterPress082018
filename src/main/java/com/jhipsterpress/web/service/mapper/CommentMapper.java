package com.jhipsterpress.web.service.mapper;

import com.jhipsterpress.web.domain.*;
import com.jhipsterpress.web.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class, ProfileMapper.class, UserMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "profile.user.id", target = "userId")
    @Mapping(source = "profile.user.firstName", target = "commentProfileUserFirstName")
    @Mapping(source = "profile.user.lastName", target = "commentProfileUserLastName")
    @Mapping(source = "profile.image", target = "profileImage")
    @Mapping(source = "profile.imageContentType", target = "profileImageContentType")
    @Mapping(source = "post.id", target = "postId")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "profileId", target = "profile.id")
    @Mapping(source = "postId", target = "post")
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
