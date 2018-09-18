package com.jhipsterpress.web.web.rest;

import com.jhipsterpress.web.JhipsterPress08App;

import com.jhipsterpress.web.domain.Community;
import com.jhipsterpress.web.domain.Blog;
import com.jhipsterpress.web.domain.Message;
import com.jhipsterpress.web.domain.Follow;
import com.jhipsterpress.web.domain.Follow;
import com.jhipsterpress.web.domain.Blockuser;
import com.jhipsterpress.web.domain.Blockuser;
import com.jhipsterpress.web.domain.User;
import com.jhipsterpress.web.domain.Calbum;
import com.jhipsterpress.web.domain.Interest;
import com.jhipsterpress.web.domain.Activity;
import com.jhipsterpress.web.domain.Celeb;
import com.jhipsterpress.web.repository.CommunityRepository;
import com.jhipsterpress.web.service.CommunityService;
import com.jhipsterpress.web.service.dto.CommunityDTO;
import com.jhipsterpress.web.service.mapper.CommunityMapper;
import com.jhipsterpress.web.web.rest.errors.ExceptionTranslator;
import com.jhipsterpress.web.service.dto.CommunityCriteria;
import com.jhipsterpress.web.service.CommunityQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.jhipsterpress.web.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommunityResource REST controller.
 *
 * @see CommunityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterPress08App.class)
public class CommunityResourceIntTest {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMUNITYNAME = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNITYNAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMUNITYDESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNITYDESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private CommunityMapper communityMapper;
    
    @Autowired
    private CommunityService communityService;

    @Autowired
    private CommunityQueryService communityQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommunityMockMvc;

    private Community community;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommunityResource communityResource = new CommunityResource(communityService, communityQueryService);
        this.restCommunityMockMvc = MockMvcBuilders.standaloneSetup(communityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Community createEntity(EntityManager em) {
        Community community = new Community()
            .creationDate(DEFAULT_CREATION_DATE)
            .communityname(DEFAULT_COMMUNITYNAME)
            .communitydescription(DEFAULT_COMMUNITYDESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .isActive(DEFAULT_IS_ACTIVE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        community.setUser(user);
        // Add required entity
        Calbum calbum = CalbumResourceIntTest.createEntity(em);
        em.persist(calbum);
        em.flush();
        community.getCalbums().add(calbum);
        return community;
    }

    @Before
    public void initTest() {
        community = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommunity() throws Exception {
        int databaseSizeBeforeCreate = communityRepository.findAll().size();

        // Create the Community
        CommunityDTO communityDTO = communityMapper.toDto(community);
        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isCreated());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeCreate + 1);
        Community testCommunity = communityList.get(communityList.size() - 1);
        assertThat(testCommunity.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCommunity.getCommunityname()).isEqualTo(DEFAULT_COMMUNITYNAME);
        assertThat(testCommunity.getCommunitydescription()).isEqualTo(DEFAULT_COMMUNITYDESCRIPTION);
        assertThat(testCommunity.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCommunity.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testCommunity.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createCommunityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communityRepository.findAll().size();

        // Create the Community with an existing ID
        community.setId(1L);
        CommunityDTO communityDTO = communityMapper.toDto(community);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = communityRepository.findAll().size();
        // set the field null
        community.setCreationDate(null);

        // Create the Community, which fails.
        CommunityDTO communityDTO = communityMapper.toDto(community);

        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommunitynameIsRequired() throws Exception {
        int databaseSizeBeforeTest = communityRepository.findAll().size();
        // set the field null
        community.setCommunityname(null);

        // Create the Community, which fails.
        CommunityDTO communityDTO = communityMapper.toDto(community);

        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommunitydescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = communityRepository.findAll().size();
        // set the field null
        community.setCommunitydescription(null);

        // Create the Community, which fails.
        CommunityDTO communityDTO = communityMapper.toDto(community);

        restCommunityMockMvc.perform(post("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommunities() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList
        restCommunityMockMvc.perform(get("/api/communities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(community.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].communityname").value(hasItem(DEFAULT_COMMUNITYNAME.toString())))
            .andExpect(jsonPath("$.[*].communitydescription").value(hasItem(DEFAULT_COMMUNITYDESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCommunity() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get the community
        restCommunityMockMvc.perform(get("/api/communities/{id}", community.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(community.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.communityname").value(DEFAULT_COMMUNITYNAME.toString()))
            .andExpect(jsonPath("$.communitydescription").value(DEFAULT_COMMUNITYDESCRIPTION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where creationDate equals to DEFAULT_CREATION_DATE
        defaultCommunityShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the communityList where creationDate equals to UPDATED_CREATION_DATE
        defaultCommunityShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultCommunityShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the communityList where creationDate equals to UPDATED_CREATION_DATE
        defaultCommunityShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where creationDate is not null
        defaultCommunityShouldBeFound("creationDate.specified=true");

        // Get all the communityList where creationDate is null
        defaultCommunityShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunitynameIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityname equals to DEFAULT_COMMUNITYNAME
        defaultCommunityShouldBeFound("communityname.equals=" + DEFAULT_COMMUNITYNAME);

        // Get all the communityList where communityname equals to UPDATED_COMMUNITYNAME
        defaultCommunityShouldNotBeFound("communityname.equals=" + UPDATED_COMMUNITYNAME);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunitynameIsInShouldWork() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityname in DEFAULT_COMMUNITYNAME or UPDATED_COMMUNITYNAME
        defaultCommunityShouldBeFound("communityname.in=" + DEFAULT_COMMUNITYNAME + "," + UPDATED_COMMUNITYNAME);

        // Get all the communityList where communityname equals to UPDATED_COMMUNITYNAME
        defaultCommunityShouldNotBeFound("communityname.in=" + UPDATED_COMMUNITYNAME);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunitynameIsNullOrNotNull() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communityname is not null
        defaultCommunityShouldBeFound("communityname.specified=true");

        // Get all the communityList where communityname is null
        defaultCommunityShouldNotBeFound("communityname.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunitydescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communitydescription equals to DEFAULT_COMMUNITYDESCRIPTION
        defaultCommunityShouldBeFound("communitydescription.equals=" + DEFAULT_COMMUNITYDESCRIPTION);

        // Get all the communityList where communitydescription equals to UPDATED_COMMUNITYDESCRIPTION
        defaultCommunityShouldNotBeFound("communitydescription.equals=" + UPDATED_COMMUNITYDESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunitydescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communitydescription in DEFAULT_COMMUNITYDESCRIPTION or UPDATED_COMMUNITYDESCRIPTION
        defaultCommunityShouldBeFound("communitydescription.in=" + DEFAULT_COMMUNITYDESCRIPTION + "," + UPDATED_COMMUNITYDESCRIPTION);

        // Get all the communityList where communitydescription equals to UPDATED_COMMUNITYDESCRIPTION
        defaultCommunityShouldNotBeFound("communitydescription.in=" + UPDATED_COMMUNITYDESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByCommunitydescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where communitydescription is not null
        defaultCommunityShouldBeFound("communitydescription.specified=true");

        // Get all the communityList where communitydescription is null
        defaultCommunityShouldNotBeFound("communitydescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunitiesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where isActive equals to DEFAULT_IS_ACTIVE
        defaultCommunityShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the communityList where isActive equals to UPDATED_IS_ACTIVE
        defaultCommunityShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultCommunityShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the communityList where isActive equals to UPDATED_IS_ACTIVE
        defaultCommunityShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCommunitiesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        // Get all the communityList where isActive is not null
        defaultCommunityShouldBeFound("isActive.specified=true");

        // Get all the communityList where isActive is null
        defaultCommunityShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommunitiesByBlogIsEqualToSomething() throws Exception {
        // Initialize the database
        Blog blog = BlogResourceIntTest.createEntity(em);
        em.persist(blog);
        em.flush();
        community.addBlog(blog);
        communityRepository.saveAndFlush(community);
        Long blogId = blog.getId();

        // Get all the communityList where blog equals to blogId
        defaultCommunityShouldBeFound("blogId.equals=" + blogId);

        // Get all the communityList where blog equals to blogId + 1
        defaultCommunityShouldNotBeFound("blogId.equals=" + (blogId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        Message message = MessageResourceIntTest.createEntity(em);
        em.persist(message);
        em.flush();
        community.addMessage(message);
        communityRepository.saveAndFlush(community);
        Long messageId = message.getId();

        // Get all the communityList where message equals to messageId
        defaultCommunityShouldBeFound("messageId.equals=" + messageId);

        // Get all the communityList where message equals to messageId + 1
        defaultCommunityShouldNotBeFound("messageId.equals=" + (messageId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCfollowedIsEqualToSomething() throws Exception {
        // Initialize the database
        Follow cfollowed = FollowResourceIntTest.createEntity(em);
        em.persist(cfollowed);
        em.flush();
        community.addCfollowed(cfollowed);
        communityRepository.saveAndFlush(community);
        Long cfollowedId = cfollowed.getId();

        // Get all the communityList where cfollowed equals to cfollowedId
        defaultCommunityShouldBeFound("cfollowedId.equals=" + cfollowedId);

        // Get all the communityList where cfollowed equals to cfollowedId + 1
        defaultCommunityShouldNotBeFound("cfollowedId.equals=" + (cfollowedId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCfollowingIsEqualToSomething() throws Exception {
        // Initialize the database
        Follow cfollowing = FollowResourceIntTest.createEntity(em);
        em.persist(cfollowing);
        em.flush();
        community.addCfollowing(cfollowing);
        communityRepository.saveAndFlush(community);
        Long cfollowingId = cfollowing.getId();

        // Get all the communityList where cfollowing equals to cfollowingId
        defaultCommunityShouldBeFound("cfollowingId.equals=" + cfollowingId);

        // Get all the communityList where cfollowing equals to cfollowingId + 1
        defaultCommunityShouldNotBeFound("cfollowingId.equals=" + (cfollowingId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCblockeduserIsEqualToSomething() throws Exception {
        // Initialize the database
        Blockuser cblockeduser = BlockuserResourceIntTest.createEntity(em);
        em.persist(cblockeduser);
        em.flush();
        community.addCblockeduser(cblockeduser);
        communityRepository.saveAndFlush(community);
        Long cblockeduserId = cblockeduser.getId();

        // Get all the communityList where cblockeduser equals to cblockeduserId
        defaultCommunityShouldBeFound("cblockeduserId.equals=" + cblockeduserId);

        // Get all the communityList where cblockeduser equals to cblockeduserId + 1
        defaultCommunityShouldNotBeFound("cblockeduserId.equals=" + (cblockeduserId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCblockinguserIsEqualToSomething() throws Exception {
        // Initialize the database
        Blockuser cblockinguser = BlockuserResourceIntTest.createEntity(em);
        em.persist(cblockinguser);
        em.flush();
        community.addCblockinguser(cblockinguser);
        communityRepository.saveAndFlush(community);
        Long cblockinguserId = cblockinguser.getId();

        // Get all the communityList where cblockinguser equals to cblockinguserId
        defaultCommunityShouldBeFound("cblockinguserId.equals=" + cblockinguserId);

        // Get all the communityList where cblockinguser equals to cblockinguserId + 1
        defaultCommunityShouldNotBeFound("cblockinguserId.equals=" + (cblockinguserId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        community.setUser(user);
        communityRepository.saveAndFlush(community);
        Long userId = user.getId();

        // Get all the communityList where user equals to userId
        defaultCommunityShouldBeFound("userId.equals=" + userId);

        // Get all the communityList where user equals to userId + 1
        defaultCommunityShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCalbumIsEqualToSomething() throws Exception {
        // Initialize the database
        Calbum calbum = CalbumResourceIntTest.createEntity(em);
        em.persist(calbum);
        em.flush();
        community.addCalbum(calbum);
        communityRepository.saveAndFlush(community);
        Long calbumId = calbum.getId();

        // Get all the communityList where calbum equals to calbumId
        defaultCommunityShouldBeFound("calbumId.equals=" + calbumId);

        // Get all the communityList where calbum equals to calbumId + 1
        defaultCommunityShouldNotBeFound("calbumId.equals=" + (calbumId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        Interest interest = InterestResourceIntTest.createEntity(em);
        em.persist(interest);
        em.flush();
        community.addInterest(interest);
        communityRepository.saveAndFlush(community);
        Long interestId = interest.getId();

        // Get all the communityList where interest equals to interestId
        defaultCommunityShouldBeFound("interestId.equals=" + interestId);

        // Get all the communityList where interest equals to interestId + 1
        defaultCommunityShouldNotBeFound("interestId.equals=" + (interestId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        Activity activity = ActivityResourceIntTest.createEntity(em);
        em.persist(activity);
        em.flush();
        community.addActivity(activity);
        communityRepository.saveAndFlush(community);
        Long activityId = activity.getId();

        // Get all the communityList where activity equals to activityId
        defaultCommunityShouldBeFound("activityId.equals=" + activityId);

        // Get all the communityList where activity equals to activityId + 1
        defaultCommunityShouldNotBeFound("activityId.equals=" + (activityId + 1));
    }


    @Test
    @Transactional
    public void getAllCommunitiesByCelebIsEqualToSomething() throws Exception {
        // Initialize the database
        Celeb celeb = CelebResourceIntTest.createEntity(em);
        em.persist(celeb);
        em.flush();
        community.addCeleb(celeb);
        communityRepository.saveAndFlush(community);
        Long celebId = celeb.getId();

        // Get all the communityList where celeb equals to celebId
        defaultCommunityShouldBeFound("celebId.equals=" + celebId);

        // Get all the communityList where celeb equals to celebId + 1
        defaultCommunityShouldNotBeFound("celebId.equals=" + (celebId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommunityShouldBeFound(String filter) throws Exception {
        restCommunityMockMvc.perform(get("/api/communities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(community.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].communityname").value(hasItem(DEFAULT_COMMUNITYNAME.toString())))
            .andExpect(jsonPath("$.[*].communitydescription").value(hasItem(DEFAULT_COMMUNITYDESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommunityShouldNotBeFound(String filter) throws Exception {
        restCommunityMockMvc.perform(get("/api/communities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCommunity() throws Exception {
        // Get the community
        restCommunityMockMvc.perform(get("/api/communities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommunity() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        int databaseSizeBeforeUpdate = communityRepository.findAll().size();

        // Update the community
        Community updatedCommunity = communityRepository.findById(community.getId()).get();
        // Disconnect from session so that the updates on updatedCommunity are not directly saved in db
        em.detach(updatedCommunity);
        updatedCommunity
            .creationDate(UPDATED_CREATION_DATE)
            .communityname(UPDATED_COMMUNITYNAME)
            .communitydescription(UPDATED_COMMUNITYDESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE);
        CommunityDTO communityDTO = communityMapper.toDto(updatedCommunity);

        restCommunityMockMvc.perform(put("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isOk());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeUpdate);
        Community testCommunity = communityList.get(communityList.size() - 1);
        assertThat(testCommunity.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCommunity.getCommunityname()).isEqualTo(UPDATED_COMMUNITYNAME);
        assertThat(testCommunity.getCommunitydescription()).isEqualTo(UPDATED_COMMUNITYDESCRIPTION);
        assertThat(testCommunity.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCommunity.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testCommunity.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommunity() throws Exception {
        int databaseSizeBeforeUpdate = communityRepository.findAll().size();

        // Create the Community
        CommunityDTO communityDTO = communityMapper.toDto(community);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunityMockMvc.perform(put("/api/communities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Community in the database
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommunity() throws Exception {
        // Initialize the database
        communityRepository.saveAndFlush(community);

        int databaseSizeBeforeDelete = communityRepository.findAll().size();

        // Get the community
        restCommunityMockMvc.perform(delete("/api/communities/{id}", community.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Community> communityList = communityRepository.findAll();
        assertThat(communityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Community.class);
        Community community1 = new Community();
        community1.setId(1L);
        Community community2 = new Community();
        community2.setId(community1.getId());
        assertThat(community1).isEqualTo(community2);
        community2.setId(2L);
        assertThat(community1).isNotEqualTo(community2);
        community1.setId(null);
        assertThat(community1).isNotEqualTo(community2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunityDTO.class);
        CommunityDTO communityDTO1 = new CommunityDTO();
        communityDTO1.setId(1L);
        CommunityDTO communityDTO2 = new CommunityDTO();
        assertThat(communityDTO1).isNotEqualTo(communityDTO2);
        communityDTO2.setId(communityDTO1.getId());
        assertThat(communityDTO1).isEqualTo(communityDTO2);
        communityDTO2.setId(2L);
        assertThat(communityDTO1).isNotEqualTo(communityDTO2);
        communityDTO1.setId(null);
        assertThat(communityDTO1).isNotEqualTo(communityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(communityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(communityMapper.fromId(null)).isNull();
    }
}
