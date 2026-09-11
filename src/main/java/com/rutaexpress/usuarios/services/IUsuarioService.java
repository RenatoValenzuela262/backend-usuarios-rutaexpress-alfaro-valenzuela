package com.rutaexpress.usuarios.services;

import com.rutaexpress.usuarios.entities.Usuario;
import com.rutaexpress.usuarios.entities.dto.UsuarioDto;
import java.util.*;

public interface IUsuarioService {

    Usuario crearUsuario(UsuarioDto usuarioDto);

    List<Usuario> obtenerTodos();

    Usuario obtenerPorSub(String sub);

}