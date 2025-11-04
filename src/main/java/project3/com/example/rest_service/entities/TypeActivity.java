package project3.com.example.rest_service.entities;

import java.util.Objects;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "activity_types")
public class TypeActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_type_id")
    private Integer id;

    @Column(nullable = false, length = 125, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer points;

    @Column(name = "co2g_saved", nullable = false, precision = 8, scale = 2)
    private BigDecimal co2gSaved;

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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public BigDecimal getCo2gSaved() {
        return co2gSaved;
    }

    public void setCo2gSaved(BigDecimal co2gSaved) {
        this.co2gSaved = co2gSaved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeActivity)) return false;
        TypeActivity that = (TypeActivity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TypeActivity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", co2gSaved=" + co2gSaved +
                '}';
    }
}
