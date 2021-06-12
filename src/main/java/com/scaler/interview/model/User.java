package com.scaler.interview.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "email")
    public String emailId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getEmailId(), user.getEmailId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmailId());
    }
}
