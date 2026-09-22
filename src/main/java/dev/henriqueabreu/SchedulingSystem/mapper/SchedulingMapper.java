package dev.henriqueabreu.SchedulingSystem.mapper;

import dev.henriqueabreu.SchedulingSystem.dto.SchedulingCreateRequest;
import dev.henriqueabreu.SchedulingSystem.dto.SchedulingResponse;
import dev.henriqueabreu.SchedulingSystem.dto.SchedulingUpdateRequest;
import dev.henriqueabreu.SchedulingSystem.model.Scheduling;
import dev.henriqueabreu.SchedulingSystem.model.StatusScheduling;

import java.time.LocalDateTime;

public class SchedulingMapper {

    public static Scheduling toEntity(SchedulingCreateRequest req) {
        Scheduling s = new Scheduling();
        s.setTitle(req.title());
        s.setDescription(req.description());
        s.setStartDate(req.startDate());
        s.setEndDate(req.endDate());
        s.setCustomer(req.customer());
        s.setStatus(StatusScheduling.SCHEDULED);
        s.setCreatedIn(LocalDateTime.now());
        s.setUpdatedIn(LocalDateTime.now());
        return s;
    }

    public static void merge(Scheduling s, SchedulingUpdateRequest req){
        if (req.title() != null) {
            s.setTitle(req.title());
        }
        if (req.description() != null) {
            s.setDescription(req.description());
        }
        if (req.startDate() != null) {
            s.setStartDate(req.startDate());
        }
        if (req.endDate() != null) {
            s.setEndDate(req.endDate());
        }
    }

    public static SchedulingResponse toResponse(Scheduling s) {
        return new SchedulingResponse(
                s.getId(),
                s.getTitle(),
                s.getDescription(),
                s.getStartDate(),
                s.getEndDate(),
                s.getStatus(),
                s.getCustomer(),
                s.getCreatedIn(),
                s.getUpdatedIn()
        );
    }

}
