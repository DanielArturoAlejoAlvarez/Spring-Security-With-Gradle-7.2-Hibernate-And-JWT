package com.mediasoft.services.demo.Exception;

public class UsernameOrIdNotFound extends Exception {
    private static final long serialVersionUID = 1668398822129870029L;

    public UsernameOrIdNotFound() {
        super("Usuario o Id no encontrado");
    }

    public UsernameOrIdNotFound(String message) {
        super(message);
    }
}
