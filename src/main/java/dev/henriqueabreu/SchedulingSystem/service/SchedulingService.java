package dev.henriqueabreu.SchedulingSystem.service;

import dev.henriqueabreu.SchedulingSystem.dto.SchedulingCreateRequest;
import dev.henriqueabreu.SchedulingSystem.dto.SchedulingResponse;
import dev.henriqueabreu.SchedulingSystem.dto.SchedulingUpdateRequest;
import dev.henriqueabreu.SchedulingSystem.mapper.SchedulingMapper;
import dev.henriqueabreu.SchedulingSystem.model.Scheduling;
import dev.henriqueabreu.SchedulingSystem.model.StatusScheduling;
import dev.henriqueabreu.SchedulingSystem.repository.SchedulingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SchedulingService {

    private final SchedulingRepository repository;

    public SchedulingService(SchedulingRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public SchedulingResponse create(@Valid SchedulingCreateRequest req) {

        validadeInterval(req.startDate(), req.endDate());
        checkConflict(req.customer(), req.startDate(), req.endDate(), null);

        Scheduling scheduling = SchedulingMapper.toEntity(req);
        repository.save(scheduling);
        return SchedulingMapper.toResponse(scheduling);
    }

    @Transactional
    public SchedulingResponse update(Long id, @Valid SchedulingUpdateRequest req) {
        Scheduling s = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException ("Agendamento não encontrado"));
        SchedulingMapper.merge(s, req);
        validadeInterval(req.startDate(), req.endDate());
        checkConflict(s.getCustomer(), req.startDate(), req.endDate(), s.getId());
        s = repository.save(s);
        return SchedulingMapper.toResponse(s);
    }

    @Transactional
    public SchedulingResponse cancel(Long id) {
        Scheduling s = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
        s.setStatus(StatusScheduling.CANCELED);
        return SchedulingMapper.toResponse(s);
    }

    @Transactional
    public SchedulingResponse complete(Long id) {
        Scheduling s = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
        s.setStatus(StatusScheduling.COMPLETED);
        return SchedulingMapper.toResponse(s);
    }

    @Transactional
    public SchedulingResponse findById(Long id) {
        Scheduling s = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
        return SchedulingMapper.toResponse(s);
    }

    private void validadeInterval(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Intervalo inválido: A data de Início deve ser anterior a data de Fim");
        }
    }

    private void checkConflict(String customer, LocalDateTime startDate, LocalDateTime endDate, Long id) {
        if (repository.existsConflict(customer, startDate, endDate, id)) {
            throw new IllegalArgumentException("Conflito na agenda: já existe um agendamento nesse período");
        }
    }
}
