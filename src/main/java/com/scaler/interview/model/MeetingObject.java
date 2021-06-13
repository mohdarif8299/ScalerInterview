package com.scaler.interview.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Data
public class MeetingObject {
    MultipartFile file;
    Meeting meeting;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeetingObject)) return false;
        MeetingObject that = (MeetingObject) o;
        return Objects.equals(getFile(), that.getFile()) && Objects.equals(getMeeting(), that.getMeeting());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFile(), getMeeting());
    }
}
