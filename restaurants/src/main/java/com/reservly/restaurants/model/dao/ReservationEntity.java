package com.reservly.restaurants.model.dao;

import com.reservly.restaurants.util.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation")
@Entity
public class ReservationEntity extends AuditDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_no", nullable = false)
    private String referenceNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private RestaurantTableEntity table;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime ;

    @Column(name = "duration_minutes", nullable = false)
    private Long durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @PrePersist
    public void prePersist() {
        this.endDateTime = this.dateTime.plusMinutes(this.durationMinutes);
    }
}
