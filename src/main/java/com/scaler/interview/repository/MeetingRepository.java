package com.scaler.interview.repository;


import com.scaler.interview.model.Meeting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends CrudRepository<Meeting, Integer> {
    List<Meeting> findAll();

    @Override
    void delete(Meeting meeting);

    Optional<Meeting> findByMeetingId(Long meetingId);

    Meeting save(Meeting meeting);
}

