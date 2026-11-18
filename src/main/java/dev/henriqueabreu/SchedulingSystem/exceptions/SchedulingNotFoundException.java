package dev.henriqueabreu.SchedulingSystem.exceptions;

public class SchedulingNotFoundException extends RuntimeException{

    public SchedulingNotFoundException() {
        super("Agendamento não encontrado");
    }

    public SchedulingNotFoundException(String message) {
        super(message);
    }

}
