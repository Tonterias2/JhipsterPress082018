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
 * Criteria class for the Blockuser entity. This class is used in BlockuserResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /blockusers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BlockuserCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter creationDate;

    private LongFilter cblockeduserId;

    private LongFilter cblockinguserId;

    private LongFilter blockeduserId;

    private LongFilter blockinguserId;

    public BlockuserCriteria() {
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

    public LongFilter getBlockeduserId() {
        return blockeduserId;
    }

    public void setBlockeduserId(LongFilter blockeduserId) {
        this.blockeduserId = blockeduserId;
    }

    public LongFilter getBlockinguserId() {
        return blockinguserId;
    }

    public void setBlockinguserId(LongFilter blockinguserId) {
        this.blockinguserId = blockinguserId;
    }

    @Override
    public String toString() {
        return "BlockuserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (cblockeduserId != null ? "cblockeduserId=" + cblockeduserId + ", " : "") +
                (cblockinguserId != null ? "cblockinguserId=" + cblockinguserId + ", " : "") +
                (blockeduserId != null ? "blockeduserId=" + blockeduserId + ", " : "") +
                (blockinguserId != null ? "blockinguserId=" + blockinguserId + ", " : "") +
            "}";
    }

}
