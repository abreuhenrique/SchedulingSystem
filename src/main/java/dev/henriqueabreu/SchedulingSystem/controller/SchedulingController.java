package dev.henriqueabreu.SchedulingSystem.controller;

import dev.henriqueabreu.SchedulingSystem.dto.SchedulingCreateRequest;
import dev.henriqueabreu.SchedulingSystem.dto.SchedulingResponse;
import dev.henriqueabreu.SchedulingSystem.dto.SchedulingUpdateRequest;
import dev.henriqueabreu.SchedulingSystem.service.SchedulingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<SchedulingResponse>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if (service.findById(id) != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(service.findById(id));
        } else {
            return ResponseEntity.badRequest().body("Agendamento não encontrado.");
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody SchedulingCreateRequest req) {
        service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body("Agendamento adicionado com sucesso!");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody SchedulingUpdateRequest req) {
        if (service.findById(id) != null) {
            service.update(id, req);
            return ResponseEntity.ok("Agendamento atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Agendamento não encontrado.");
        }
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<String> complete(@PathVariable Long id) {
        service.complete(id);
        return ResponseEntity.ok("Agendamento concluído!");
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<String> cancel(@PathVariable Long id) {
        if (service.findById(id) != null) {
            service.cancel(id);
            return ResponseEntity.ok("Agendamento cancelado.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Agendamento não encontrado.");
        }
    }

}
