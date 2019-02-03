package net.hydrogen2oxygen.biblestudycenter.dao;

import net.hydrogen2oxygen.biblestudycenter.domain.Timeline;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimelineRepository extends CrudRepository<Timeline, Long> {
}
