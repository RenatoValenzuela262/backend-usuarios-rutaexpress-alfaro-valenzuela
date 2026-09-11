package com.rutaexpress.usuarios.entities.dto;

public record LoginRequestDto(
    String email,
    String password
) {
    
}
