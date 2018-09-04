package com.jhipsterpress.web.service.mapper;

import com.jhipsterpress.web.domain.*;
import com.jhipsterpress.web.service.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Profile and its DTO ProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

    @Mapping(source = "user.id", target = "userId")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "followeds", ignore = true)
    @Mapping(target = "followings", ignore = true)
    @Mapping(target = "blockedusers", ignore = true)
    @Mapping(target = "blockingusers", ignore = true)
    @Mapping(target = "interests", ignore = true)
    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "celebs", ignore = true)
    Profile toEntity(ProfileDTO profileDTO);

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}
