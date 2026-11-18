package dev.henriqueabreu.SchedulingSystem.exceptions;

public class IntervalInvalidException extends RuntimeException{

    public IntervalInvalidException() {
        super("Intervalo inválido!");
    }

    public IntervalInvalidException(String message) {
        super(message);
    }

}
