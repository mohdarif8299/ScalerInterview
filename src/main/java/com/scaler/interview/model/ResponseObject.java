package com.scaler.interview.model;

import lombok.Data;

import java.util.Objects;

@Data
public class ResponseObject {

    public String message;

    public ResponseObject(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseObject)) return false;
        ResponseObject that = (ResponseObject) o;
        return Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage());
    }
}
