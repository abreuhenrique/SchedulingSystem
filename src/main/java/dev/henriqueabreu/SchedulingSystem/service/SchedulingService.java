package dev.henriqueabreu.SchedulingSystem.service;

import dev.henriqueabreu.SchedulingSystem.dto.SchedulingCreateRequest;
import dev.henriqueabreu.SchedulingSystem.dto.SchedulingResponse;
import dev.henriqueabreu.SchedulingSystem.dto.SchedulingUpdateRequest;
import dev.henriqueabreu.SchedulingSystem.exceptions.IntervalInvalidException;
import dev.henriqueabreu.SchedulingSystem.exceptions.SchedulingNotFoundException;
import dev.henriqueabreu.SchedulingSystem.mapper.SchedulingMapper;
import dev.henriqueabreu.SchedulingSystem.model.Scheduling;
import dev.henriqueabreu.SchedulingSystem.model.StatusScheduling;
import dev.henriqueabreu.SchedulingSystem.repository.SchedulingRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulingService {

    private final SchedulingRepository repository;

    public SchedulingService(SchedulingRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public SchedulingResponse create(@Valid SchedulingCreateRequest req) {

        validadeInterval(req.startDate(), req.endDate());
        checkConflict(req.startDate(), req.endDate(), null);

        Scheduling scheduling = SchedulingMapper.toEntity(req);
        repository.save(scheduling);
        return SchedulingMapper.toResponse(scheduling);
    }

    @Transactional
    public SchedulingResponse update(Long id, @Valid SchedulingUpdateRequest req) {
        Scheduling s = repository.findById(id)
                .orElseThrow(SchedulingNotFoundException::new);
        SchedulingMapper.merge(s, req);
        validadeInterval(s.getStartDate(), s.getEndDate());
        checkConflict(s.getStartDate(), s.getEndDate(), s.getId());
        s = repository.save(s);
        return SchedulingMapper.toResponse(s);
    }

    @Transactional
    public SchedulingResponse cancel(Long id) {
        Scheduling s = repository.findById(id)
                .orElseThrow(SchedulingNotFoundException::new);
        s.setStatus(StatusScheduling.CANCELED);
        return SchedulingMapper.toResponse(s);
    }

    @Transactional
    public SchedulingResponse complete(Long id) {
        Scheduling s = repository.findById(id)
                .orElseThrow(SchedulingNotFoundException::new);
        s.setStatus(StatusScheduling.COMPLETED);
        return SchedulingMapper.toResponse(s);
    }

    @Transactional
    public List<SchedulingResponse> listAll() {
        List<Scheduling> s = repository.findAll();
        return s.stream()
                .map(SchedulingMapper::toResponse)
                .toList();
    }

    @Transactional
    public SchedulingResponse findById(Long id) {
        Scheduling s = repository.findById(id)
                .orElseThrow(SchedulingNotFoundException::new);
        return SchedulingMapper.toResponse(s);
    }

    private void validadeInterval(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
            throw new IntervalInvalidException();
        }
    }

    private void checkConflict(LocalDateTime startDate, LocalDateTime endDate, Long id) {
        if (repository.existsConflict(startDate, endDate, id)) {
            throw new IntervalInvalidException();
        }
    }
}
