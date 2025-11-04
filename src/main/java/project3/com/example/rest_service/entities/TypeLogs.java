package project3.com.example.rest_service.entities;

import java.time.OffsetDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "activity_logs",
       indexes = {
           @Index(name = "idx_activity_logs_user_id", columnList = "user_id"),
           @Index(name = "idx_activity_logs_activity_type_id", columnList = "activity_type_id")
       })
public class TypeLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Integer activityId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) 
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "activity_type_id", nullable = false)
    private TypeActivity activityType;

    @Column(name = "occurred_at", nullable = false)
    private OffsetDateTime occurredAt = OffsetDateTime.now();

    public TypeLogs() {}

    public TypeLogs(User user, TypeActivity activityType) {
        this.user = user;
        this.activityType = activityType;
        this.occurredAt = OffsetDateTime.now();
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TypeActivity getActivityType() {
        return activityType;
    }

    public void setActivityType(TypeActivity activityType) {
        this.activityType = activityType;
    }

    public OffsetDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(OffsetDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeLogs)) return false;
        TypeLogs that = (TypeLogs) o;
        return Objects.equals(activityId, that.activityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityId);
    }

    @Override
    public String toString() {
        return "TypeLogs{" +
                "activityId=" + activityId +
                ", user=" + (user != null ? user.getId() : null) +
                ", activityType=" + (activityType != null ? activityType.getId() : null) +
                ", occurredAt=" + occurredAt +
                '}';
    }
}