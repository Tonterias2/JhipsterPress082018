package com.jhipsterpress.web.repository;

import com.jhipsterpress.web.domain.Follow;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Follow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>, JpaSpecificationExecutor<Follow> {

}
