package com.jhipsterpress.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.jhipsterpress.web.domain.enumeration.Gender;

import com.jhipsterpress.web.domain.enumeration.CivilStatus;

import com.jhipsterpress.web.domain.enumeration.Purpose;

import com.jhipsterpress.web.domain.enumeration.Physical;

import com.jhipsterpress.web.domain.enumeration.Religion;

import com.jhipsterpress.web.domain.enumeration.EthnicGroup;

import com.jhipsterpress.web.domain.enumeration.Studies;

import com.jhipsterpress.web.domain.enumeration.Eyes;

import com.jhipsterpress.web.domain.enumeration.Smoker;

import com.jhipsterpress.web.domain.enumeration.Children;

import com.jhipsterpress.web.domain.enumeration.FutureChildren;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONE)
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Size(max = 20)
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Size(max = 7500)
    @Column(name = "bio", length = 7500)
    private String bio;

    @Column(name = "birthdate")
    private Instant birthdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "civil_status")
    private CivilStatus civilStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "looking_for")
    private Gender lookingFor;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose")
    private Purpose purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "physical")
    private Physical physical;

    @Enumerated(EnumType.STRING)
    @Column(name = "religion")
    private Religion religion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ethnic_group")
    private EthnicGroup ethnicGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "studies")
    private Studies studies;

    @Min(value = -1)
    @Max(value = 20)
    @Column(name = "sibblings")
    private Integer sibblings;

    @Enumerated(EnumType.STRING)
    @Column(name = "eyes")
    private Eyes eyes;

    @Enumerated(EnumType.STRING)
    @Column(name = "smoker")
    private Smoker smoker;

    @Enumerated(EnumType.STRING)
    @Column(name = "children")
    private Children children;

    @Enumerated(EnumType.STRING)
    @Column(name = "future_children")
    private FutureChildren futureChildren;

    @Column(name = "pet")
    private Boolean pet;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "followed")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Follow> followeds = new HashSet<>();

    @OneToMany(mappedBy = "following")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Follow> followings = new HashSet<>();

    @OneToMany(mappedBy = "blockeduser")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Blockuser> blockedusers = new HashSet<>();

    @OneToMany(mappedBy = "blockinguser")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Blockuser> blockingusers = new HashSet<>();

    @ManyToMany(mappedBy = "profiles")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Interest> interests = new HashSet<>();

    @ManyToMany(mappedBy = "profiles")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany(mappedBy = "profiles")
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

    public Profile creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public byte[] getImage() {
        return image;
    }

    public Profile image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Profile imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Gender getGender() {
        return gender;
    }

    public Profile gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public Profile phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public Profile bio(String bio) {
        this.bio = bio;
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Instant getBirthdate() {
        return birthdate;
    }

    public Profile birthdate(Instant birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(Instant birthdate) {
        this.birthdate = birthdate;
    }

    public CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public Profile civilStatus(CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
        return this;
    }

    public void setCivilStatus(CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }

    public Gender getLookingFor() {
        return lookingFor;
    }

    public Profile lookingFor(Gender lookingFor) {
        this.lookingFor = lookingFor;
        return this;
    }

    public void setLookingFor(Gender lookingFor) {
        this.lookingFor = lookingFor;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public Profile purpose(Purpose purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public Physical getPhysical() {
        return physical;
    }

    public Profile physical(Physical physical) {
        this.physical = physical;
        return this;
    }

    public void setPhysical(Physical physical) {
        this.physical = physical;
    }

    public Religion getReligion() {
        return religion;
    }

    public Profile religion(Religion religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public EthnicGroup getEthnicGroup() {
        return ethnicGroup;
    }

    public Profile ethnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
        return this;
    }

    public void setEthnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public Studies getStudies() {
        return studies;
    }

    public Profile studies(Studies studies) {
        this.studies = studies;
        return this;
    }

    public void setStudies(Studies studies) {
        this.studies = studies;
    }

    public Integer getSibblings() {
        return sibblings;
    }

    public Profile sibblings(Integer sibblings) {
        this.sibblings = sibblings;
        return this;
    }

    public void setSibblings(Integer sibblings) {
        this.sibblings = sibblings;
    }

    public Eyes getEyes() {
        return eyes;
    }

    public Profile eyes(Eyes eyes) {
        this.eyes = eyes;
        return this;
    }

    public void setEyes(Eyes eyes) {
        this.eyes = eyes;
    }

    public Smoker getSmoker() {
        return smoker;
    }

    public Profile smoker(Smoker smoker) {
        this.smoker = smoker;
        return this;
    }

    public void setSmoker(Smoker smoker) {
        this.smoker = smoker;
    }

    public Children getChildren() {
        return children;
    }

    public Profile children(Children children) {
        this.children = children;
        return this;
    }

    public void setChildren(Children children) {
        this.children = children;
    }

    public FutureChildren getFutureChildren() {
        return futureChildren;
    }

    public Profile futureChildren(FutureChildren futureChildren) {
        this.futureChildren = futureChildren;
        return this;
    }

    public void setFutureChildren(FutureChildren futureChildren) {
        this.futureChildren = futureChildren;
    }

    public Boolean isPet() {
        return pet;
    }

    public Profile pet(Boolean pet) {
        this.pet = pet;
        return this;
    }

    public void setPet(Boolean pet) {
        this.pet = pet;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Profile comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Profile addComment(Comment comment) {
        this.comments.add(comment);
        comment.setProfile(this);
        return this;
    }

    public Profile removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setProfile(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Profile posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public Profile addPost(Post post) {
        this.posts.add(post);
        post.setProfile(this);
        return this;
    }

    public Profile removePost(Post post) {
        this.posts.remove(post);
        post.setProfile(null);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Profile messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Profile addMessage(Message message) {
        this.messages.add(message);
        message.setProfile(this);
        return this;
    }

    public Profile removeMessage(Message message) {
        this.messages.remove(message);
        message.setProfile(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<Follow> getFolloweds() {
        return followeds;
    }

    public Profile followeds(Set<Follow> follows) {
        this.followeds = follows;
        return this;
    }

    public Profile addFollowed(Follow follow) {
        this.followeds.add(follow);
        follow.setFollowed(this);
        return this;
    }

    public Profile removeFollowed(Follow follow) {
        this.followeds.remove(follow);
        follow.setFollowed(null);
        return this;
    }

    public void setFolloweds(Set<Follow> follows) {
        this.followeds = follows;
    }

    public Set<Follow> getFollowings() {
        return followings;
    }

    public Profile followings(Set<Follow> follows) {
        this.followings = follows;
        return this;
    }

    public Profile addFollowing(Follow follow) {
        this.followings.add(follow);
        follow.setFollowing(this);
        return this;
    }

    public Profile removeFollowing(Follow follow) {
        this.followings.remove(follow);
        follow.setFollowing(null);
        return this;
    }

    public void setFollowings(Set<Follow> follows) {
        this.followings = follows;
    }

    public Set<Blockuser> getBlockedusers() {
        return blockedusers;
    }

    public Profile blockedusers(Set<Blockuser> blockusers) {
        this.blockedusers = blockusers;
        return this;
    }

    public Profile addBlockeduser(Blockuser blockuser) {
        this.blockedusers.add(blockuser);
        blockuser.setBlockeduser(this);
        return this;
    }

    public Profile removeBlockeduser(Blockuser blockuser) {
        this.blockedusers.remove(blockuser);
        blockuser.setBlockeduser(null);
        return this;
    }

    public void setBlockedusers(Set<Blockuser> blockusers) {
        this.blockedusers = blockusers;
    }

    public Set<Blockuser> getBlockingusers() {
        return blockingusers;
    }

    public Profile blockingusers(Set<Blockuser> blockusers) {
        this.blockingusers = blockusers;
        return this;
    }

    public Profile addBlockinguser(Blockuser blockuser) {
        this.blockingusers.add(blockuser);
        blockuser.setBlockinguser(this);
        return this;
    }

    public Profile removeBlockinguser(Blockuser blockuser) {
        this.blockingusers.remove(blockuser);
        blockuser.setBlockinguser(null);
        return this;
    }

    public void setBlockingusers(Set<Blockuser> blockusers) {
        this.blockingusers = blockusers;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public Profile interests(Set<Interest> interests) {
        this.interests = interests;
        return this;
    }

    public Profile addInterest(Interest interest) {
        this.interests.add(interest);
        interest.getProfiles().add(this);
        return this;
    }

    public Profile removeInterest(Interest interest) {
        this.interests.remove(interest);
        interest.getProfiles().remove(this);
        return this;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Profile activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Profile addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getProfiles().add(this);
        return this;
    }

    public Profile removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getProfiles().remove(this);
        return this;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Set<Celeb> getCelebs() {
        return celebs;
    }

    public Profile celebs(Set<Celeb> celebs) {
        this.celebs = celebs;
        return this;
    }

    public Profile addCeleb(Celeb celeb) {
        this.celebs.add(celeb);
        celeb.getProfiles().add(this);
        return this;
    }

    public Profile removeCeleb(Celeb celeb) {
        this.celebs.remove(celeb);
        celeb.getProfiles().remove(this);
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
        Profile profile = (Profile) o;
        if (profile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", gender='" + getGender() + "'" +
            ", phone='" + getPhone() + "'" +
            ", bio='" + getBio() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", civilStatus='" + getCivilStatus() + "'" +
            ", lookingFor='" + getLookingFor() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", physical='" + getPhysical() + "'" +
            ", religion='" + getReligion() + "'" +
            ", ethnicGroup='" + getEthnicGroup() + "'" +
            ", studies='" + getStudies() + "'" +
            ", sibblings=" + getSibblings() +
            ", eyes='" + getEyes() + "'" +
            ", smoker='" + getSmoker() + "'" +
            ", children='" + getChildren() + "'" +
            ", futureChildren='" + getFutureChildren() + "'" +
            ", pet='" + isPet() + "'" +
            "}";
    }
}
