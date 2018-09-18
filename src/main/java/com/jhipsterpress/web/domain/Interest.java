package com.jhipsterpress.web.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Interest.
 */
@Entity
@Table(name = "interest")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Interest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "interest_name", length = 40, nullable = false)
    private String interestName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "interest_community",
               joinColumns = @JoinColumn(name = "interests_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "communities_id", referencedColumnName = "id"))
    private Set<Community> communities = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "interest_profile",
               joinColumns = @JoinColumn(name = "interests_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "profiles_id", referencedColumnName = "id"))
    private Set<Profile> profiles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInterestName() {
        return interestName;
    }

    public Interest interestName(String interestName) {
        this.interestName = interestName;
        return this;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public Set<Community> getCommunities() {
        return communities;
    }

    public Interest communities(Set<Community> communities) {
        this.communities = communities;
        return this;
    }

    public Interest addCommunity(Community community) {
        this.communities.add(community);
        community.getInterests().add(this);
        return this;
    }

    public Interest removeCommunity(Community community) {
        this.communities.remove(community);
        community.getInterests().remove(this);
        return this;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public Interest profiles(Set<Profile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public Interest addProfile(Profile profile) {
        this.profiles.add(profile);
        profile.getInterests().add(this);
        return this;
    }

    public Interest removeProfile(Profile profile) {
        this.profiles.remove(profile);
        profile.getInterests().remove(this);
        return this;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
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
        Interest interest = (Interest) o;
        if (interest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), interest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Interest{" +
            "id=" + getId() +
            ", interestName='" + getInterestName() + "'" +
            "}";
    }
}
