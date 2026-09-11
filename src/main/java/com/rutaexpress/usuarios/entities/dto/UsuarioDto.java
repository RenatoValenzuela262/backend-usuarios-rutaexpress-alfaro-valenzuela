package com.rutaexpress.usuarios.entities.dto;

public class UsuarioDto {

    private String email;
    private String rut;
    private String nombre;
    private String apellido;
    private String rol;

    public UsuarioDto(String email, String rut, String nombre, String apellido, String rol) {
        this.email = email;
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
    }

    public UsuarioDto() {
         
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRut() {
        return rut;
    }
    public void setRut(String rut) {
        this.rut = rut;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    

}
