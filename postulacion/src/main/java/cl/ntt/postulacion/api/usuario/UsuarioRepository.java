package cl.ntt.postulacion.api.usuario;

import cl.ntt.postulacion.api.usuario.model.*;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsuarioRepository {
    UsuarioCrea crear(UsuarioPost usuario);

    String edita(UsuarioPost usuario);

    String editapass(UsuarioPost usuario);

    String borrar(int idUsuario);

    UsuarioPost traer(int idUsuario);
    List<UsuarioPost> todos();

    String crearTelefono(Telefono telefono);

    String borrarTelefonos(UsuarioPost usuario);

    String verificar(UsuarioAccess usuario);
}