package project3.com.example.rest_service;

import java.time.OffsetDateTime;

public class ActivityLogRequest {
    private Integer userId;
    private Integer activityTypeId;
    private OffsetDateTime occurredAt; // optional

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getActivityTypeId() { return activityTypeId; }
    public void setActivityTypeId(Integer activityTypeId) { this.activityTypeId = activityTypeId; }

    public OffsetDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(OffsetDateTime occurredAt) { this.occurredAt = occurredAt; }
}