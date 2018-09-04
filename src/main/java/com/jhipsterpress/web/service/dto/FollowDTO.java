package com.jhipsterpress.web.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Follow entity.
 */
public class FollowDTO implements Serializable {

    private Long id;

    private Instant creationDate;

    private Long cfollowedId;

    private Long cfollowingId;

    private Long followedId;

    private Long followingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCfollowedId() {
        return cfollowedId;
    }

    public void setCfollowedId(Long communityId) {
        this.cfollowedId = communityId;
    }

    public Long getCfollowingId() {
        return cfollowingId;
    }

    public void setCfollowingId(Long communityId) {
        this.cfollowingId = communityId;
    }

    public Long getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Long profileId) {
        this.followedId = profileId;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long profileId) {
        this.followingId = profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FollowDTO followDTO = (FollowDTO) o;
        if (followDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), followDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FollowDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", cfollowed=" + getCfollowedId() +
            ", cfollowing=" + getCfollowingId() +
            ", followed=" + getFollowedId() +
            ", following=" + getFollowingId() +
            "}";
    }
}
