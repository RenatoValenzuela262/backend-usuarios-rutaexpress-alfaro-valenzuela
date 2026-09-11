package com.rutaexpress.usuarios.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @Column(name = "cognito_sub", length = 36)
    private String cognitoSub;  

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String rut;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String rol;  

    @Column(name = "creado_en", insertable = false, updatable = false)
    private LocalDateTime creadoEn;
}