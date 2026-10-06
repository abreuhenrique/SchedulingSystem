package dev.henriqueabreu.SchedulingSystem.controller;

import dev.henriqueabreu.SchedulingSystem.dto.SchedulingCreateRequest;
import dev.henriqueabreu.SchedulingSystem.dto.SchedulingResponse;
import dev.henriqueabreu.SchedulingSystem.dto.SchedulingUpdateRequest;
import dev.henriqueabreu.SchedulingSystem.service.SchedulingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {

    private final SchedulingService service;

    public SchedulingController(SchedulingService service) {
        this.service = service;
    }

    @GetMapping
    public List<SchedulingResponse> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public SchedulingResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public SchedulingResponse create(@Valid @RequestBody SchedulingCreateRequest req) {
        return service.create(req);
    }

    @PatchMapping("/{id}")
    public SchedulingResponse update(@PathVariable Long id, @Valid @RequestBody SchedulingUpdateRequest req) {
        return service.update(id, req);
    }

    @PatchMapping("/{id}/complete")
    public SchedulingResponse complete(@PathVariable Long id) {
        return service.complete(id);
    }

    @PatchMapping("/{id}/cancel")
    public SchedulingResponse cancel(@PathVariable Long id) {
        return service.cancel(id);
    }

}
