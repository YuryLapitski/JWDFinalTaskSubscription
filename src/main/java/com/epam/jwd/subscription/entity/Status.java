package com.epam.jwd.subscription.entity;

import java.util.Objects;

public class Status implements Entity {

    private static final long serialVersionUID = 1376900759940314447L;
    private final Long id;
    private final String status;

    public Status(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status1 = (Status) o;
        return Objects.equals(id, status1.id) &&
                Objects.equals(status, status1.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
