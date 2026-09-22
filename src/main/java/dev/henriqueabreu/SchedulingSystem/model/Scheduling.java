package dev.henriqueabreu.SchedulingSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tb_scheduling")
public class Scheduling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusScheduling status;

    @Column(nullable = false, length = 80)
    private String customer;

    @Column(name = "created_in", nullable = false)
    private LocalDateTime createdIn;

    @Column(name = "updated_in", nullable = false)
    private LocalDateTime updatedIn;

}
