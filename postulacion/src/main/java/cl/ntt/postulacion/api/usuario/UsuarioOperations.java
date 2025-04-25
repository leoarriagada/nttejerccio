package cl.ntt.postulacion.api.usuario;

import cl.ntt.postulacion.api.usuario.model.Usuario;
import cl.ntt.postulacion.api.usuario.model.UsuarioAccess;
import cl.ntt.postulacion.api.usuario.model.UsuarioCrea;
import cl.ntt.postulacion.api.usuario.model.UsuarioPost;
import cl.ntt.postulacion.response.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UsuarioOperations {

    GenericResponse<UsuarioCrea> crear(UsuarioPost usuario);

    GenericResponse<String> edita(UsuarioPost usuario);

    GenericResponse<String> editapass(UsuarioPost usuario);

    GenericResponse<String> borrar(int idUsuario);

    GenericResponse<UsuarioPost> traer(int idUsuario);
    GenericResponse<List<UsuarioPost>> todos();

    GenericResponse<String> ingreso(UsuarioAccess usuario);
}
