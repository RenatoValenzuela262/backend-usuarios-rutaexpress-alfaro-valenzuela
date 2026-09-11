package com.rutaexpress.usuarios.entities.dto;

public record AuthResponseDto(
    String accessToken,
    String idToken,
    String refreshToken,
    Integer expiresIn,
    String tokenType
) {}