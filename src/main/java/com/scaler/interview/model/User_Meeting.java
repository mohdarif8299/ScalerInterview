package com.scaler.interview.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Entity(name = "user_meeting")
public class User_Meeting {
    @Id
    @GeneratedValue
    public Long id;
    @Column(name = "meeting_id")
    public Long meetingId;
    @Column(name = "user_email")
    public String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User_Meeting)) return false;
        User_Meeting that = (User_Meeting) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getMeetingId(), that.getMeetingId()) && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMeetingId(), getEmail());
    }
}
