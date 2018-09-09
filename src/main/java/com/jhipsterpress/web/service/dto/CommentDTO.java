package com.jhipsterpress.web.service.dto;

import java.time.Instant;

import javax.persistence.Lob;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * A DTO for the Comment entity.
 */
public class CommentDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 65000)
    private String commentText;

    private Boolean isOffensive;

    private Long postId;

    private Long userId;
    
    private Long profileId;
    
    private String commentProfileUserFirstName;
    
    private String commentProfileUserLastName;
    
    @Lob
    private byte[] profileImage;
    private String profileImageContentType;

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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Boolean isIsOffensive() {
        return isOffensive;
    }

    public void setIsOffensive(Boolean isOffensive) {
        this.isOffensive = isOffensive;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getCommentProfileUserFirstName() {
		return commentProfileUserFirstName;
	}

	public void setCommentProfileUserFirstName(String commentProfileUserFirstName) {
		this.commentProfileUserFirstName = commentProfileUserFirstName;
	}

	public String getCommentProfileUserLastName() {
		return commentProfileUserLastName;
	}

	public void setCommentProfileUserLastName(String commentProfileUserLastName) {
		this.commentProfileUserLastName = commentProfileUserLastName;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}

	public String getProfileImageContentType() {
		return profileImageContentType;
	}

	public void setProfileImageContentType(String profileImageContentType) {
		this.profileImageContentType = profileImageContentType;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentDTO commentDTO = (CommentDTO) o;
        if (commentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "CommentDTO [id=" + id + ", creationDate=" + creationDate + ", commentText=" + commentText
				+ ", isOffensive=" + isOffensive + ", postId=" + postId + ", userId=" + userId + ", profileId="
				+ profileId + ", commentProfileUserFirstName=" + commentProfileUserFirstName
				+ ", commentProfileUserLastName=" + commentProfileUserLastName + ", profileImage="
				+ Arrays.toString(profileImage) + ", profileImageContentType=" + profileImageContentType + "]";
	}

}
