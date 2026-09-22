package dev.henriqueabreu.SchedulingSystem.repository;

import dev.henriqueabreu.SchedulingSystem.model.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
            FROM Scheduling s
            WHERE s.customer = :customer
                AND s.status = dev.henriqueabreu.SchedulingSystem.model.StatusScheduling.SCHEDULED
                AND (s.startDate < :end AND s.endDate > :start)
                AND (:ignoreId is NULL OR s.id <> : ignoreId)
    """)
    boolean existsConflict(@Param("customer") String customer,
                           @Param("start")LocalDateTime start,
                           @Param("end")LocalDateTime end,
                           @Param("ignoreId")Long ignoreId);

}
