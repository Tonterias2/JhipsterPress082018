package com.jhipsterpress.web.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Lob;

/**
 * A DTO for the Blockuser entity.
 */
public class BlockuserDTO implements Serializable {

    private Long id;

    private Instant creationDate;

    
    private Long cblockeduserId;
    
    @Lob
    private byte[] cblockeduserImage;
    private String cblockeduserImageContentType;

    private String cblockeduserCommunityname;


    private Long cblockinguserId;

    @Lob
    private byte[] cblockinguserImage;
    private String cblockinguserImageContentType;

    private String cblockinguserCommunityname;


    private Long blockeduserId;

    @Lob
    private byte[] blockeduserImage;
    private String blockeduserImageContentType;

    private String blockeduserUserFirstName;

    private String blockeduserUserLastName;


    private Long blockinguserId;

    @Lob
    private byte[] blockinguserImage;
    private String blockinguserImageContentType;

    private String blockinguserUserFirstName;

    private String blockinguserUserLastName;


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

    public Long getCblockeduserId() {
        return cblockeduserId;
    }

    public void setCblockeduserId(Long communityId) {
        this.cblockeduserId = communityId;
    }

    public Long getCblockinguserId() {
        return cblockinguserId;
    }

    public void setCblockinguserId(Long communityId) {
        this.cblockinguserId = communityId;
    }

    public Long getBlockeduserId() {
        return blockeduserId;
    }

    public void setBlockeduserId(Long profileId) {
        this.blockeduserId = profileId;
    }

    public Long getBlockinguserId() {
        return blockinguserId;
    }

    public void setBlockinguserId(Long profileId) {
        this.blockinguserId = profileId;
    }

    public byte[] getCblockinguserImage() {
		return cblockinguserImage;
	}

	public void setCblockinguserImage(byte[] cblockinguserImage) {
		this.cblockinguserImage = cblockinguserImage;
	}

	public byte[] getBlockinguserImage() {
		return blockinguserImage;
	}

	public void setBlockinguserImage(byte[] blockinguserImage) {
		this.blockinguserImage = blockinguserImage;
	}

	public byte[] getCblockeduserImage() {
		return cblockeduserImage;
	}

	public void setCblockeduserImage(byte[] cblockeduserImage) {
		this.cblockeduserImage = cblockeduserImage;
	}

	public String getCblockeduserImageContentType() {
		return cblockeduserImageContentType;
	}

	public void setCblockeduserImageContentType(String cblockeduserImageContentType) {
		this.cblockeduserImageContentType = cblockeduserImageContentType;
	}

	public String getCblockeduserCommunityname() {
		return cblockeduserCommunityname;
	}

	public void setCblockeduserCommunityname(String cblockeduserCommunityname) {
		this.cblockeduserCommunityname = cblockeduserCommunityname;
	}

	public String getCblockinguserImageContentType() {
		return cblockinguserImageContentType;
	}

	public void setCblockinguserImageContentType(String cblockinguserImageContentType) {
		this.cblockinguserImageContentType = cblockinguserImageContentType;
	}

	public String getCblockinguserCommunityname() {
		return cblockinguserCommunityname;
	}

	public void setCblockinguserCommunityname(String cblockinguserCommunityname) {
		this.cblockinguserCommunityname = cblockinguserCommunityname;
	}

	public byte[] getBlockeduserImage() {
		return blockeduserImage;
	}

	public void setBlockeduserImage(byte[] blockeduserImage) {
		this.blockeduserImage = blockeduserImage;
	}

	public String getBlockeduserImageContentType() {
		return blockeduserImageContentType;
	}

	public void setBlockeduserImageContentType(String blockeduserImageContentType) {
		this.blockeduserImageContentType = blockeduserImageContentType;
	}

	public String getBlockeduserUserFirstName() {
		return blockeduserUserFirstName;
	}

	public void setBlockeduserUserFirstName(String blockeduserUserFirstName) {
		this.blockeduserUserFirstName = blockeduserUserFirstName;
	}

	public String getBlockeduserUserLastName() {
		return blockeduserUserLastName;
	}

	public void setBlockeduserUserLastName(String blockeduserUserLastName) {
		this.blockeduserUserLastName = blockeduserUserLastName;
	}

	public String getBlockinguserImageContentType() {
		return blockinguserImageContentType;
	}

	public void setBlockinguserImageContentType(String blockinguserImageContentType) {
		this.blockinguserImageContentType = blockinguserImageContentType;
	}

	public String getBlockinguserUserFirstName() {
		return blockinguserUserFirstName;
	}

	public void setBlockinguserUserFirstName(String blockinguserUserFirstName) {
		this.blockinguserUserFirstName = blockinguserUserFirstName;
	}

	public String getBlockinguserUserLastName() {
		return blockinguserUserLastName;
	}

	public void setBlockinguserUserLastName(String blockinguserUserLastName) {
		this.blockinguserUserLastName = blockinguserUserLastName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlockuserDTO blockuserDTO = (BlockuserDTO) o;
        if (blockuserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blockuserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "BlockuserDTO [id=" + id + ", creationDate=" + creationDate + ", cblockeduserId=" + cblockeduserId
				+ ", cblockeduserImage=" + Arrays.toString(cblockeduserImage) + ", cblockeduserImageContentType="
				+ cblockeduserImageContentType + ", cblockeduserCommunityname=" + cblockeduserCommunityname
				+ ", cblockinguserId=" + cblockinguserId + ", cblockinguserImage=" + Arrays.toString(cblockinguserImage)
				+ ", cblockinguserImageContentType=" + cblockinguserImageContentType + ", cblockinguserCommunityname="
				+ cblockinguserCommunityname + ", blockeduserId=" + blockeduserId + ", blockeduserImage="
				+ Arrays.toString(blockeduserImage) + ", blockeduserImageContentType=" + blockeduserImageContentType
				+ ", blockeduserUserFirstName=" + blockeduserUserFirstName + ", blockeduserUserLastName="
				+ blockeduserUserLastName + ", blockinguserId=" + blockinguserId + ", blockinguserImage="
				+ Arrays.toString(blockinguserImage) + ", blockinguserImageContentType=" + blockinguserImageContentType
				+ ", blockinguserUserFirstName=" + blockinguserUserFirstName + ", blockinguserUserLastName="
				+ blockinguserUserLastName + "]";
	}
}
