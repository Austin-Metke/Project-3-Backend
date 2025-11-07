package project3.com.example.rest_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Challenge")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ChallengeID")
    private Integer id;

    @Column(name = "Name", length = 64, nullable = false)

    private String name;

    @Column(name = "Description", length = 1000)
    private String description;

    @Column(name = "Points", nullable = false)
    private Integer points;

    @Column(name = "iscompleted", nullable = false)
    private Boolean isCompleted = false;

    @Column(name = "UserID", nullable = false)
    private Integer userId;

    public Challenge() {
    }

    public Challenge(Integer id, String name, String description, Integer points, Boolean isCompleted, Integer userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.points = points;
        this.isCompleted = isCompleted;
        this.userId = userId;
    }

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", points=" + points +
                ", isCompleted=" + isCompleted +
                ", userId=" + userId +
                '}';
    }
}