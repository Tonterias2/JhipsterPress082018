package com.jhipsterpress.web.service.mapper;

import com.jhipsterpress.web.domain.*;
import com.jhipsterpress.web.service.dto.FollowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Follow and its DTO FollowDTO.
 */
@Mapper(componentModel = "spring", uses = {CommunityMapper.class, ProfileMapper.class, UserMapper.class})
public interface FollowMapper extends EntityMapper<FollowDTO, Follow> {

    @Mapping(source = "cfollowed.id", target = "cfollowedId")
    @Mapping(source = "cfollowed.image", target = "cfollowedImage")
    @Mapping(source = "cfollowed.imageContentType", target = "cfollowedImageContentType")
    @Mapping(source = "cfollowed.communityname", target = "cfollowedCommunityname")
    @Mapping(source = "cfollowing.id", target = "cfollowingId")
    @Mapping(source = "cfollowing.image", target = "cfollowingImage")
    @Mapping(source = "cfollowing.imageContentType", target = "cfollowingImageContentType")
    @Mapping(source = "cfollowing.communityname", target = "cfollowingCommunityname")
    @Mapping(source = "followed.id", target = "followedId")
    @Mapping(source = "followed.image", target = "followedImage")
    @Mapping(source = "followed.imageContentType", target = "followedImageContentType")
    @Mapping(source = "followed.user.firstName", target = "followedUserFirstName")
    @Mapping(source = "followed.user.lastName", target = "followedUserLastName")
    @Mapping(source = "following.id", target = "followingId")
    @Mapping(source = "following.image", target = "followingImage")
    @Mapping(source = "following.imageContentType", target = "followingImageContentType")
    @Mapping(source = "following.user.firstName", target = "followingUserFirstName")
    @Mapping(source = "following.user.lastName", target = "followingUserLastName")
    FollowDTO toDto(Follow follow);

    @Mapping(source = "cfollowedId", target = "cfollowed")
    @Mapping(source = "cfollowingId", target = "cfollowing")
    @Mapping(source = "followedId", target = "followed")
    @Mapping(source = "followingId", target = "following")
    Follow toEntity(FollowDTO followDTO);

    default Follow fromId(Long id) {
        if (id == null) {
            return null;
        }
        Follow follow = new Follow();
        follow.setId(id);
        return follow;
    }
}
