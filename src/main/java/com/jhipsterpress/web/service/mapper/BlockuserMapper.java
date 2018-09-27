package com.jhipsterpress.web.service.mapper;

import com.jhipsterpress.web.domain.*;
import com.jhipsterpress.web.service.dto.BlockuserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Blockuser and its DTO BlockuserDTO.
 */
@Mapper(componentModel = "spring", uses = {CommunityMapper.class, ProfileMapper.class, UserMapper.class})
public interface BlockuserMapper extends EntityMapper<BlockuserDTO, Blockuser> {

    @Mapping(source = "cblockeduser.id", target = "cblockeduserId")
    @Mapping(source = "cblockeduser.image", target = "cblockeduserImage")
    @Mapping(source = "cblockeduser.imageContentType", target = "cblockeduserImageContentType")
    @Mapping(source = "cblockeduser.communityname", target = "cblockeduserCommunityname")
    @Mapping(source = "cblockinguser.id", target = "cblockinguserId")
    @Mapping(source = "cblockinguser.image", target = "cblockinguserImage")
    @Mapping(source = "cblockinguser.imageContentType", target = "cblockinguserImageContentType")
    @Mapping(source = "cblockinguser.communityname", target = "cblockinguserCommunityname")
    @Mapping(source = "blockeduser.id", target = "blockeduserId")
    @Mapping(source = "blockeduser.image", target = "blockeduserImage")
    @Mapping(source = "blockeduser.imageContentType", target = "blockeduserImageContentType")
    @Mapping(source = "blockeduser.user.firstName", target = "blockeduserUserFirstName")
    @Mapping(source = "blockeduser.user.lastName", target = "blockeduserUserLastName")
    @Mapping(source = "blockinguser.id", target = "blockinguserId")
    @Mapping(source = "blockinguser.image", target = "blockinguserImage")
    @Mapping(source = "blockinguser.imageContentType", target = "blockinguserImageContentType")
    @Mapping(source = "blockinguser.user.firstName", target = "blockinguserUserFirstName")
    @Mapping(source = "blockinguser.user.lastName", target = "blockinguserUserLastName")
    BlockuserDTO toDto(Blockuser blockuser);

    @Mapping(source = "cblockeduserId", target = "cblockeduser")
    @Mapping(source = "cblockinguserId", target = "cblockinguser")
    @Mapping(source = "blockeduserId", target = "blockeduser")
    @Mapping(source = "blockinguserId", target = "blockinguser")
    Blockuser toEntity(BlockuserDTO blockuserDTO);

    default Blockuser fromId(Long id) {
        if (id == null) {
            return null;
        }
        Blockuser blockuser = new Blockuser();
        blockuser.setId(id);
        return blockuser;
    }
}
