package com.scaler.interview.repository;

import com.scaler.interview.model.User_Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMeetingRepository extends JpaRepository<User_Meeting, Long> {
    User_Meeting save(User_Meeting user_meeting);

    List<User_Meeting> findAll();
}
