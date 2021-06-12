package com.scaler.interview.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;


@Entity(name = "meeting")
@Data
public class Meeting {
    @Id
    @Column(name = "meeting_id")
    @GeneratedValue
    public Long meetingId;

    @Column(name = "meeting_agenda")
    public String agenda;

    @Column(name = "meeting_start_time")
    public String startTime;

    @Column(name = "meeting_end_time")
    public String endTime;

    @Column(name = "participantOne")
    public String participantOne;

    @Column(name = "participantTwo")
    public String participantTwo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(getMeetingId(), meeting.getMeetingId()) && Objects.equals(getAgenda(), meeting.getAgenda()) && Objects.equals(getStartTime(), meeting.getStartTime()) && Objects.equals(getEndTime(), meeting.getEndTime()) && Objects.equals(getParticipantOne(), meeting.getParticipantOne()) && Objects.equals(getParticipantTwo(), meeting.getParticipantTwo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMeetingId(), getAgenda(), getStartTime(), getEndTime(), getParticipantOne(), getParticipantTwo());
    }
}
