package com.jhipsterpress.web.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Interest entity.
 */
public class InterestDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    private String interestName;

    private Set<CommunityDTO> communities = new HashSet<>();

    private Set<ProfileDTO> profiles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public Set<CommunityDTO> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<CommunityDTO> communities) {
        this.communities = communities;
    }

    public Set<ProfileDTO> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<ProfileDTO> profiles) {
        this.profiles = profiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InterestDTO interestDTO = (InterestDTO) o;
        if (interestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), interestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InterestDTO{" +
            "id=" + getId() +
            ", interestName='" + getInterestName() + "'" +
            "}";
    }
}
