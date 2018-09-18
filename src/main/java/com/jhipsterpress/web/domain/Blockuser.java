package com.jhipsterpress.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Blockuser.
 */
@Entity
@Table(name = "blockuser")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Blockuser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "creation_date")
    private Instant creationDate;

    @ManyToOne
    @JsonIgnoreProperties("cblockedusers")
    private Community cblockeduser;

    @ManyToOne
    @JsonIgnoreProperties("cblockingusers")
    private Community cblockinguser;

    @ManyToOne
    @JsonIgnoreProperties("blockedusers")
    private Profile blockeduser;

    @ManyToOne
    @JsonIgnoreProperties("blockingusers")
    private Profile blockinguser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Blockuser creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Community getCblockeduser() {
        return cblockeduser;
    }

    public Blockuser cblockeduser(Community community) {
        this.cblockeduser = community;
        return this;
    }

    public void setCblockeduser(Community community) {
        this.cblockeduser = community;
    }

    public Community getCblockinguser() {
        return cblockinguser;
    }

    public Blockuser cblockinguser(Community community) {
        this.cblockinguser = community;
        return this;
    }

    public void setCblockinguser(Community community) {
        this.cblockinguser = community;
    }

    public Profile getBlockeduser() {
        return blockeduser;
    }

    public Blockuser blockeduser(Profile profile) {
        this.blockeduser = profile;
        return this;
    }

    public void setBlockeduser(Profile profile) {
        this.blockeduser = profile;
    }

    public Profile getBlockinguser() {
        return blockinguser;
    }

    public Blockuser blockinguser(Profile profile) {
        this.blockinguser = profile;
        return this;
    }

    public void setBlockinguser(Profile profile) {
        this.blockinguser = profile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Blockuser blockuser = (Blockuser) o;
        if (blockuser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blockuser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Blockuser{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
