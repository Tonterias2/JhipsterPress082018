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
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "activity_name", length = 40, nullable = false)
    private String activityName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "activity_community",
               joinColumns = @JoinColumn(name = "activities_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "communities_id", referencedColumnName = "id"))
    private Set<Community> communities = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "activity_profile",
               joinColumns = @JoinColumn(name = "activities_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "profiles_id", referencedColumnName = "id"))
    private Set<Profile> profiles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public Activity activityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Set<Community> getCommunities() {
        return communities;
    }

    public Activity communities(Set<Community> communities) {
        this.communities = communities;
        return this;
    }

    public Activity addCommunity(Community community) {
        this.communities.add(community);
        community.getActivities().add(this);
        return this;
    }

    public Activity removeCommunity(Community community) {
        this.communities.remove(community);
        community.getActivities().remove(this);
        return this;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public Activity profiles(Set<Profile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public Activity addProfile(Profile profile) {
        this.profiles.add(profile);
        profile.getActivities().add(this);
        return this;
    }

    public Activity removeProfile(Profile profile) {
        this.profiles.remove(profile);
        profile.getActivities().remove(this);
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
        Activity activity = (Activity) o;
        if (activity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", activityName='" + getActivityName() + "'" +
            "}";
    }
}
