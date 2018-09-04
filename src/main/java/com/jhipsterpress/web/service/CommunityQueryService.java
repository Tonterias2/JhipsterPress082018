package com.jhipsterpress.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.jhipsterpress.web.domain.Community;
import com.jhipsterpress.web.domain.*; // for static metamodels
import com.jhipsterpress.web.repository.CommunityRepository;
import com.jhipsterpress.web.service.dto.CommunityCriteria;

import com.jhipsterpress.web.service.dto.CommunityDTO;
import com.jhipsterpress.web.service.mapper.CommunityMapper;

/**
 * Service for executing complex queries for Community entities in the database.
 * The main input is a {@link CommunityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommunityDTO} or a {@link Page} of {@link CommunityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommunityQueryService extends QueryService<Community> {

    private final Logger log = LoggerFactory.getLogger(CommunityQueryService.class);

    private final CommunityRepository communityRepository;

    private final CommunityMapper communityMapper;

    public CommunityQueryService(CommunityRepository communityRepository, CommunityMapper communityMapper) {
        this.communityRepository = communityRepository;
        this.communityMapper = communityMapper;
    }

    /**
     * Return a {@link List} of {@link CommunityDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommunityDTO> findByCriteria(CommunityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Community> specification = createSpecification(criteria);
        return communityMapper.toDto(communityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommunityDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommunityDTO> findByCriteria(CommunityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Community> specification = createSpecification(criteria);
        return communityRepository.findAll(specification, page)
            .map(communityMapper::toDto);
    }

    /**
     * Function to convert CommunityCriteria to a {@link Specification}
     */
    private Specification<Community> createSpecification(CommunityCriteria criteria) {
        Specification<Community> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Community_.id));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Community_.creationDate));
            }
            if (criteria.getCommunityname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommunityname(), Community_.communityname));
            }
            if (criteria.getCommunitydescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommunitydescription(), Community_.communitydescription));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Community_.isActive));
            }
            if (criteria.getBlogId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBlogId(), Community_.blogs, Blog_.id));
            }
            if (criteria.getMessageId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMessageId(), Community_.messages, Message_.id));
            }
            if (criteria.getCfollowedId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCfollowedId(), Community_.cfolloweds, Follow_.id));
            }
            if (criteria.getCfollowingId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCfollowingId(), Community_.cfollowings, Follow_.id));
            }
            if (criteria.getCblockeduserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCblockeduserId(), Community_.cblockedusers, Blockuser_.id));
            }
            if (criteria.getCblockinguserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCblockinguserId(), Community_.cblockingusers, Blockuser_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), Community_.user, User_.id));
            }
            if (criteria.getCalbumId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCalbumId(), Community_.calbums, Calbum_.id));
            }
            if (criteria.getInterestId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getInterestId(), Community_.interests, Interest_.id));
            }
            if (criteria.getActivityId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getActivityId(), Community_.activities, Activity_.id));
            }
            if (criteria.getCelebId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCelebId(), Community_.celebs, Celeb_.id));
            }
        }
        return specification;
    }

}
