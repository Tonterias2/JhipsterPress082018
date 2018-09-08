package com.jhipsterpress.web.service.mapper;

import com.jhipsterpress.web.domain.*;
import com.jhipsterpress.web.service.dto.PostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Post and its DTO PostDTO.
 */
@Mapper(componentModel = "spring", uses = {BlogMapper.class, ProfileMapper.class, ProfileMapper.class, CustomTagMapper.class, 
		CommentMapper.class, UserMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "blog.id", target = "blogId")
    @Mapping(source = "blog.title", target = "blogTitle")
    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "profile.bio", target = "profileBio")
    @Mapping(source = "profile.user.id", target = "profileUserId")
    @Mapping(source = "profile.user.firstName", target = "profileUserFirstName")
    @Mapping(source = "profile.user.lastName", target = "profileUserLastName")
    @Mapping(source = "profile.image", target = "profileImage")
    @Mapping(source = "profile.imageContentType", target = "profileImageContentType")
    PostDTO toDto(Post post);

    @Mapping(source = "blogId", target = "blog")
    @Mapping(source = "profileId", target = "profile")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "topics", ignore = true)
    Post toEntity(PostDTO postDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
