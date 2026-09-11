package com.rutaexpress.usuarios.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rutaexpress.usuarios.entities.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
