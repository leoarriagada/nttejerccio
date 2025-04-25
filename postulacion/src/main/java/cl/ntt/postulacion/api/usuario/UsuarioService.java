package cl.ntt.postulacion.api.usuario;

import cl.ntt.postulacion.api.usuario.model.*;
import cl.ntt.postulacion.response.GenericResponse;
import cl.ntt.postulacion.utils.UtilsFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsuarioService implements UsuarioOperations{
    private final UsuarioRepository op;
    private static final String COMPLEX_PASSWORD_REGEX =
            "^(?:(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])|" +
                    "(?=.*\\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|" +
                    "(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|" +
                    "(?=.*\\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))(?!.*(.)\\1{2,})" +
                    "[A-Za-z0-9!~<>,;:_=?*+#.\"&§%°()\\|\\[\\]\\-\\$\\^\\@\\/]" +
                    "{8,32}$";
    @Autowired
    public UsuarioService(UsuarioRepository op){
        this.op = op;
    }
    @Override
    public GenericResponse<UsuarioCrea> crear(UsuarioPost usuario) {

        Pattern patternEmail = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcherEmail = patternEmail.matcher(usuario.getCorreo());
        if (!matcherEmail.matches()){
            UtilsFunctions.setStatus(HttpStatus.BAD_REQUEST);
            return new GenericResponse<>(null, false,"correo no valido");
        }
        Pattern patternPassword = Pattern.compile(COMPLEX_PASSWORD_REGEX);
        Matcher matcherPass = patternEmail.matcher(usuario.getCorreo());
        if (!matcherPass.matches()){
            return new GenericResponse<>(null, false,"contraseña no valida");
        }

        UsuarioCrea  usuarioCrea = op.crear(usuario);
        if(usuarioCrea!=null){
            UtilsFunctions.setStatus(HttpStatus.CREATED);

            for(Telefono tel: usuario.getTelefonos()){
                tel.setUsuarioCrea(usuarioCrea);
                op.crearTelefono(tel);
            }
            return new GenericResponse<>(usuarioCrea, true,"Usuario guardado exitosamente");
        }else{
            UtilsFunctions.setStatus(HttpStatus.BAD_REQUEST);
            return new GenericResponse<>(null, false,"Problemas usuario");
        }
    }

    @Override
    public GenericResponse<String> edita(UsuarioPost usuario) {

        Pattern patternEmail = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcherEmail = patternEmail.matcher(usuario.getCorreo());
        if (!matcherEmail.matches()){
            UtilsFunctions.setStatus(HttpStatus.BAD_REQUEST);
            return new GenericResponse<>(null, false,"correo no valido");
        }
        Pattern patternPassword = Pattern.compile(COMPLEX_PASSWORD_REGEX);
        Matcher matcherPass = patternPassword.matcher(usuario.getCorreo());
        if (!matcherPass.matches()){
            return new GenericResponse<>(null, false,"contraseña no valida");
        }
        String  usuarioEdita = op.edita(usuario);
        if(usuarioEdita!=null){
            UtilsFunctions.setStatus(HttpStatus.CREATED);
            op.borrarTelefonos(usuario);
            for(Telefono tel: usuario.getTelefonos()){
                op.crearTelefono(tel);
            }
            return new GenericResponse<>(usuarioEdita, true,"Usuario guardado exitosamente");
        }else{
            UtilsFunctions.setStatus(HttpStatus.BAD_REQUEST);
            return new GenericResponse<>(null, false,"Problemas usuario");
        }
    }

    @Override
    public GenericResponse<String> editapass(UsuarioPost usuario) {


        Pattern patternPassword = Pattern.compile(COMPLEX_PASSWORD_REGEX);
        Matcher matcherPass = patternPassword.matcher(usuario.getCorreo());
        if (!matcherPass.matches()){
            UtilsFunctions.setStatus(HttpStatus.BAD_REQUEST);
            return new GenericResponse<>(null, false,"contraseña no valida");
        }else{
            UtilsFunctions.setStatus(HttpStatus.OK);
            String  usuarioCrea = op.editapass(usuario);
            return new GenericResponse<>(usuarioCrea, true,"contraseña actualizada exitosamente");
        }

    }

    @Override
    public GenericResponse<String> borrar(int idUsuario) {
        UtilsFunctions.setStatus(HttpStatus.BAD_REQUEST);
        String  usuarioCrea = op.borrar(idUsuario);
        UtilsFunctions.setStatus(HttpStatus.OK);
        return new GenericResponse<>(usuarioCrea, true,"registro eliminado exitosamente");

    }

    @Override
    public GenericResponse<UsuarioPost> traer(int idUsuario) {
        UtilsFunctions.setStatus(HttpStatus.BAD_REQUEST);
        UsuarioPost usuario = op.traer(idUsuario);
        UtilsFunctions.setStatus(HttpStatus.OK);
        return new GenericResponse<>(usuario, true,"usuario" + idUsuario);
    }

    @Override
    public GenericResponse<List<UsuarioPost>> todos() {
        UtilsFunctions.setStatus(HttpStatus.BAD_REQUEST);
        List<UsuarioPost> usuarios = op.todos();
        UtilsFunctions.setStatus(HttpStatus.OK);
        return new GenericResponse<>(usuarios, true,"todos los usuarios");
    }

    @Override
    public GenericResponse<String> ingreso(UsuarioAccess usuario) {
        UtilsFunctions.setStatus(HttpStatus.BAD_REQUEST);
        String tokenVerificado = op.verificar(usuario);
        UtilsFunctions.setStatus(HttpStatus.OK);
        return new GenericResponse<>(tokenVerificado, true,"todos los usuarios");
    }
}
