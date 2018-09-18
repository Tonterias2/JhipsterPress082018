package com.jhipsterpress.web.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Interest entity. This class is used in InterestResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /interests?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InterestCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter interestName;

    private LongFilter communityId;

    private LongFilter profileId;

    public InterestCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInterestName() {
        return interestName;
    }

    public void setInterestName(StringFilter interestName) {
        this.interestName = interestName;
    }

    public LongFilter getCommunityId() {
        return communityId;
    }

    public void setCommunityId(LongFilter communityId) {
        this.communityId = communityId;
    }

    public LongFilter getProfileId() {
        return profileId;
    }

    public void setProfileId(LongFilter profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toString() {
        return "InterestCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (interestName != null ? "interestName=" + interestName + ", " : "") +
                (communityId != null ? "communityId=" + communityId + ", " : "") +
                (profileId != null ? "profileId=" + profileId + ", " : "") +
            "}";
    }

}
