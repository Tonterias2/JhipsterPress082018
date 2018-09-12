package com.jhipsterpress.web.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Lob;

/**
 * A DTO for the Community entity.
 */
public class CommunityDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 100)
    private String communityname;

    @NotNull
    @Size(min = 2, max = 7500)
    private String communitydescription;

    @Lob
    private byte[] image;
    private String imageContentType;

    private Boolean isActive;

    private Long userId;
    
    private Set<CustomActivityDTO> activities;
    
    private Set<CustomCelebDTO> celebs;
    
    private Set<CustomInterestDTO> interests;

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

    public String getCommunityname() {
        return communityname;
    }

    public void setCommunityname(String communityname) {
        this.communityname = communityname;
    }

    public String getCommunitydescription() {
        return communitydescription;
    }

    public void setCommunitydescription(String communitydescription) {
        this.communitydescription = communitydescription;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<CustomActivityDTO> getActivities() {
		return activities;
	}

	public void setActivities(Set<CustomActivityDTO> activities) {
		this.activities = activities;
	}

	public Set<CustomCelebDTO> getCelebs() {
		return celebs;
	}

	public void setCelebs(Set<CustomCelebDTO> celebs) {
		this.celebs = celebs;
	}

	public Set<CustomInterestDTO> getInterests() {
		return interests;
	}

	public void setInterests(Set<CustomInterestDTO> interests) {
		this.interests = interests;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommunityDTO communityDTO = (CommunityDTO) o;
        if (communityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), communityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "CommunityDTO [id=" + id + ", creationDate=" + creationDate + ", communityname=" + communityname
				+ ", communitydescription=" + communitydescription + ", image=" + Arrays.toString(image)
				+ ", imageContentType=" + imageContentType + ", isActive=" + isActive + ", userId=" + userId
				+ ", activities=" + activities + ", celebs=" + celebs + ", interests=" + interests + "]";
	}
}
