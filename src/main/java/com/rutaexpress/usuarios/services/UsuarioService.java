package com.rutaexpress.usuarios.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rutaexpress.usuarios.entities.Usuario;
import com.rutaexpress.usuarios.entities.dto.UsuarioDto;
import com.rutaexpress.usuarios.repositories.UsuarioRepository;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import java.util.*;

@Service 
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${aws.cognito.user-pool-id}")
    private String userPoolId;

    // Inyectamos cognitoClient definido en AwsConfig
    public UsuarioService(UsuarioRepository usuarioRepository, CognitoIdentityProviderClient cognitoClient) {
        this.usuarioRepository = usuarioRepository;
        this.cognitoClient = cognitoClient;
    }

    @Transactional
    public Usuario crearUsuario(UsuarioDto usuariodto) {
 
        //Creamos usuario en AWS Cognito mediante AdminCreateUser
        AdminCreateUserRequest cognitoRequest = AdminCreateUserRequest.builder()
                .userPoolId(userPoolId)
                .username(usuariodto.getEmail())
                .userAttributes(
                        AttributeType.builder().name("email").value(usuariodto.getEmail()).build(),
                        AttributeType.builder().name("email_verified").value("true").build()
                )
                .desiredDeliveryMediums(DeliveryMediumType.EMAIL)
                .build();

        AdminCreateUserResponse cognitoResponse = cognitoClient.adminCreateUser(cognitoRequest);
        UserType cognitoUser = cognitoResponse.user();

        //Extraemos el campo 'sub' (UUID generado por Cognito)
        String sub = cognitoUser.attributes().stream()
                .filter(attr -> attr.name().equals("sub"))
                .findFirst()
                .map(AttributeType::value)
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el sub de Cognito"));

        //Asignamos usuario al Grupo correspondiente en Cognito
        if (usuariodto.getRol() != null && !usuariodto.getRol().isEmpty()) {
            AdminAddUserToGroupRequest groupRequest = AdminAddUserToGroupRequest.builder()
                    .userPoolId(userPoolId)
                    .username(usuariodto.getEmail())
                    .groupName(usuariodto.getRol())
                    .build();
            cognitoClient.adminAddUserToGroup(groupRequest);
        }

        //Almacenamos entidad de negocio en PostgreSQL RDS vinculando el cognitoSub
        Usuario nuevoUsuario = Usuario.builder()
                .cognitoSub(sub)
                .email(usuariodto.getEmail())
                .rut(usuariodto.getRut())
                .nombre(usuariodto.getNombre())
                .apellido(usuariodto.getApellido())
                .rol(usuariodto.getRol())
                .build();

        return usuarioRepository.save(nuevoUsuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorSub(String sub) {
        return usuarioRepository.findById(sub)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con sub: " + sub));
    }


}
