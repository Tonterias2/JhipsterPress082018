package com.jhipsterpress.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Community.
 */
@Entity
@Table(name = "community")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Community implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "communityname", length = 100, nullable = false)
    private String communityname;

    @NotNull
    @Size(min = 2, max = 7500)
    @Column(name = "communitydescription", length = 7500, nullable = false)
    private String communitydescription;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "community")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Blog> blogs = new HashSet<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "cfollowed", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Follow> cfolloweds = new HashSet<>();

    @OneToMany(mappedBy = "cfollowing", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Follow> cfollowings = new HashSet<>();

    @OneToMany(mappedBy = "cblockeduser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Blockuser> cblockedusers = new HashSet<>();

    @OneToMany(mappedBy = "cblockinguser", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Blockuser> cblockingusers = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User user;

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Calbum> calbums = new HashSet<>();

    @ManyToMany(mappedBy = "communities", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Interest> interests = new HashSet<>();

    @ManyToMany(mappedBy = "communities", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany(mappedBy = "communities", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Celeb> celebs = new HashSet<>();

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

    public Community creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getCommunityname() {
        return communityname;
    }

    public Community communityname(String communityname) {
        this.communityname = communityname;
        return this;
    }

    public void setCommunityname(String communityname) {
        this.communityname = communityname;
    }

    public String getCommunitydescription() {
        return communitydescription;
    }

    public Community communitydescription(String communitydescription) {
        this.communitydescription = communitydescription;
        return this;
    }

    public void setCommunitydescription(String communitydescription) {
        this.communitydescription = communitydescription;
    }

    public byte[] getImage() {
        return image;
    }

    public Community image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Community imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Community isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<Blog> getBlogs() {
        return blogs;
    }

    public Community blogs(Set<Blog> blogs) {
        this.blogs = blogs;
        return this;
    }

    public Community addBlog(Blog blog) {
        this.blogs.add(blog);
        blog.setCommunity(this);
        return this;
    }

    public Community removeBlog(Blog blog) {
        this.blogs.remove(blog);
        blog.setCommunity(null);
        return this;
    }

    public void setBlogs(Set<Blog> blogs) {
        this.blogs = blogs;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Community messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Community addMessage(Message message) {
        this.messages.add(message);
        message.setCommunity(this);
        return this;
    }

    public Community removeMessage(Message message) {
        this.messages.remove(message);
        message.setCommunity(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<Follow> getCfolloweds() {
        return cfolloweds;
    }

    public Community cfolloweds(Set<Follow> follows) {
        this.cfolloweds = follows;
        return this;
    }

    public Community addCfollowed(Follow follow) {
        this.cfolloweds.add(follow);
        follow.setCfollowed(this);
        return this;
    }

    public Community removeCfollowed(Follow follow) {
        this.cfolloweds.remove(follow);
        follow.setCfollowed(null);
        return this;
    }

    public void setCfolloweds(Set<Follow> follows) {
        this.cfolloweds = follows;
    }

    public Set<Follow> getCfollowings() {
        return cfollowings;
    }

    public Community cfollowings(Set<Follow> follows) {
        this.cfollowings = follows;
        return this;
    }

    public Community addCfollowing(Follow follow) {
        this.cfollowings.add(follow);
        follow.setCfollowing(this);
        return this;
    }

    public Community removeCfollowing(Follow follow) {
        this.cfollowings.remove(follow);
        follow.setCfollowing(null);
        return this;
    }

    public void setCfollowings(Set<Follow> follows) {
        this.cfollowings = follows;
    }

    public Set<Blockuser> getCblockedusers() {
        return cblockedusers;
    }

    public Community cblockedusers(Set<Blockuser> blockusers) {
        this.cblockedusers = blockusers;
        return this;
    }

    public Community addCblockeduser(Blockuser blockuser) {
        this.cblockedusers.add(blockuser);
        blockuser.setCblockeduser(this);
        return this;
    }

    public Community removeCblockeduser(Blockuser blockuser) {
        this.cblockedusers.remove(blockuser);
        blockuser.setCblockeduser(null);
        return this;
    }

    public void setCblockedusers(Set<Blockuser> blockusers) {
        this.cblockedusers = blockusers;
    }

    public Set<Blockuser> getCblockingusers() {
        return cblockingusers;
    }

    public Community cblockingusers(Set<Blockuser> blockusers) {
        this.cblockingusers = blockusers;
        return this;
    }

    public Community addCblockinguser(Blockuser blockuser) {
        this.cblockingusers.add(blockuser);
        blockuser.setCblockinguser(this);
        return this;
    }

    public Community removeCblockinguser(Blockuser blockuser) {
        this.cblockingusers.remove(blockuser);
        blockuser.setCblockinguser(null);
        return this;
    }

    public void setCblockingusers(Set<Blockuser> blockusers) {
        this.cblockingusers = blockusers;
    }

    public User getUser() {
        return user;
    }

    public Community user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Calbum> getCalbums() {
        return calbums;
    }

    public Community calbums(Set<Calbum> calbums) {
        this.calbums = calbums;
        return this;
    }

    public Community addCalbum(Calbum calbum) {
        this.calbums.add(calbum);
        calbum.setCommunity(this);
        return this;
    }

    public Community removeCalbum(Calbum calbum) {
        this.calbums.remove(calbum);
        calbum.setCommunity(null);
        return this;
    }

    public void setCalbums(Set<Calbum> calbums) {
        this.calbums = calbums;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public Community interests(Set<Interest> interests) {
        this.interests = interests;
        return this;
    }

    public Community addInterest(Interest interest) {
        this.interests.add(interest);
        interest.getCommunities().add(this);
        return this;
    }

    public Community removeInterest(Interest interest) {
        this.interests.remove(interest);
        interest.getCommunities().remove(this);
        return this;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Community activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Community addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getCommunities().add(this);
        return this;
    }

    public Community removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getCommunities().remove(this);
        return this;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Set<Celeb> getCelebs() {
        return celebs;
    }

    public Community celebs(Set<Celeb> celebs) {
        this.celebs = celebs;
        return this;
    }

    public Community addCeleb(Celeb celeb) {
        this.celebs.add(celeb);
        celeb.getCommunities().add(this);
        return this;
    }

    public Community removeCeleb(Celeb celeb) {
        this.celebs.remove(celeb);
        celeb.getCommunities().remove(this);
        return this;
    }

    public void setCelebs(Set<Celeb> celebs) {
        this.celebs = celebs;
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
        Community community = (Community) o;
        if (community.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), community.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Community{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", communityname='" + getCommunityname() + "'" +
            ", communitydescription='" + getCommunitydescription() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
