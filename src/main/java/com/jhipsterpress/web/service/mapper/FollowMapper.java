package com.jhipsterpress.web.service.mapper;

import com.jhipsterpress.web.domain.*;
import com.jhipsterpress.web.service.dto.FollowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Follow and its DTO FollowDTO.
 */
@Mapper(componentModel = "spring", uses = {CommunityMapper.class, ProfileMapper.class})
public interface FollowMapper extends EntityMapper<FollowDTO, Follow> {

    @Mapping(source = "cfollowed.id", target = "cfollowedId")
    @Mapping(source = "cfollowing.id", target = "cfollowingId")
    @Mapping(source = "followed.id", target = "followedId")
    @Mapping(source = "following.id", target = "followingId")
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
