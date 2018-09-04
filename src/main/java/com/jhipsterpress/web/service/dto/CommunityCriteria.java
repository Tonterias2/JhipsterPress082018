package com.jhipsterpress.web.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the Community entity. This class is used in CommunityResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /communities?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommunityCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter communityname;

    private StringFilter communitydescription;

    private BooleanFilter isActive;

    private LongFilter blogId;

    private LongFilter messageId;

    private LongFilter cfollowedId;

    private LongFilter cfollowingId;

    private LongFilter cblockeduserId;

    private LongFilter cblockinguserId;

    private LongFilter userId;

    private LongFilter calbumId;

    private LongFilter interestId;

    private LongFilter activityId;

    private LongFilter celebId;

    public CommunityCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public StringFilter getCommunityname() {
        return communityname;
    }

    public void setCommunityname(StringFilter communityname) {
        this.communityname = communityname;
    }

    public StringFilter getCommunitydescription() {
        return communitydescription;
    }

    public void setCommunitydescription(StringFilter communitydescription) {
        this.communitydescription = communitydescription;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public LongFilter getBlogId() {
        return blogId;
    }

    public void setBlogId(LongFilter blogId) {
        this.blogId = blogId;
    }

    public LongFilter getMessageId() {
        return messageId;
    }

    public void setMessageId(LongFilter messageId) {
        this.messageId = messageId;
    }

    public LongFilter getCfollowedId() {
        return cfollowedId;
    }

    public void setCfollowedId(LongFilter cfollowedId) {
        this.cfollowedId = cfollowedId;
    }

    public LongFilter getCfollowingId() {
        return cfollowingId;
    }

    public void setCfollowingId(LongFilter cfollowingId) {
        this.cfollowingId = cfollowingId;
    }

    public LongFilter getCblockeduserId() {
        return cblockeduserId;
    }

    public void setCblockeduserId(LongFilter cblockeduserId) {
        this.cblockeduserId = cblockeduserId;
    }

    public LongFilter getCblockinguserId() {
        return cblockinguserId;
    }

    public void setCblockinguserId(LongFilter cblockinguserId) {
        this.cblockinguserId = cblockinguserId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getCalbumId() {
        return calbumId;
    }

    public void setCalbumId(LongFilter calbumId) {
        this.calbumId = calbumId;
    }

    public LongFilter getInterestId() {
        return interestId;
    }

    public void setInterestId(LongFilter interestId) {
        this.interestId = interestId;
    }

    public LongFilter getActivityId() {
        return activityId;
    }

    public void setActivityId(LongFilter activityId) {
        this.activityId = activityId;
    }

    public LongFilter getCelebId() {
        return celebId;
    }

    public void setCelebId(LongFilter celebId) {
        this.celebId = celebId;
    }

    @Override
    public String toString() {
        return "CommunityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (communityname != null ? "communityname=" + communityname + ", " : "") +
                (communitydescription != null ? "communitydescription=" + communitydescription + ", " : "") +
                (isActive != null ? "isActive=" + isActive + ", " : "") +
                (blogId != null ? "blogId=" + blogId + ", " : "") +
                (messageId != null ? "messageId=" + messageId + ", " : "") +
                (cfollowedId != null ? "cfollowedId=" + cfollowedId + ", " : "") +
                (cfollowingId != null ? "cfollowingId=" + cfollowingId + ", " : "") +
                (cblockeduserId != null ? "cblockeduserId=" + cblockeduserId + ", " : "") +
                (cblockinguserId != null ? "cblockinguserId=" + cblockinguserId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (calbumId != null ? "calbumId=" + calbumId + ", " : "") +
                (interestId != null ? "interestId=" + interestId + ", " : "") +
                (activityId != null ? "activityId=" + activityId + ", " : "") +
                (celebId != null ? "celebId=" + celebId + ", " : "") +
            "}";
    }

}
