package cl.ntt.postulacion.api.usuario;

import cl.ntt.postulacion.api.usuario.model.*;
import cl.ntt.postulacion.utils.UtilsFunctions;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioDao implements UsuarioRepository{
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @PersistenceContext
    private EntityManager em;


    @Override
    public UsuarioCrea crear(UsuarioPost usuario) {
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("crear_usuario");
        query.setParameter("nombre_in", usuario.getNombre());
        query.setParameter("correo_in", usuario.getCorreo());
        query.setParameter("password_in", usuario.getContraseña());
        String token  = getJWToken(usuario.getCorreo(), usuario.getContraseña());
        query.setParameter("token_in", token);
        query.execute();
        int id_usuario = (int) query.getOutputParameterValue("id_user");
        UsuarioCrea usuarioCrea = getUsuarioCreado(id_usuario);

        return usuarioCrea;

    }

    private UsuarioCrea getUsuarioCreado(int idUsuario) {
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("get_usuario_creado");
        query.setParameter("id_user", idUsuario);
        return (UsuarioCrea) query.getResultStream().findFirst().orElse(null);
    }

    private String getJWToken(String correo, String contraseña) {
        int duracion = 86400000;

        String prefix = "nttpost";
        String key = "ntt_clave";
        List<GrantedAuthority> grantedAuthorites = AuthorityUtils.commaSeparatedStringToAuthorityList(contraseña);
        String token = Jwts.builder()
                .setId("nttJWT")
                .setSubject(correo)
                .claim("authorities", grantedAuthorites.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duracion))
                .signWith(SignatureAlgorithm.HS512, key.getBytes())
                .compact();
        return UtilsFunctions.toSha256(prefix) + token;
    }

    @Override
    public String edita(UsuarioPost usuario) {
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("modificar_usuario");
        query.setParameter("correo_in", usuario.getCorreo());
        query.setParameter("password_in", usuario.getContraseña());
        String token  = getJWToken(usuario.getCorreo(), usuario.getContraseña());
        query.setParameter("token_in", token);
        query.execute();
        return "Usuario Editado";
    }

    @Override
    public String editapass(UsuarioPost usuario) {
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("modificar_usuario");
        query.setParameter("correo_in", usuario.getCorreo());
        query.setParameter("password_in", usuario.getContraseña());
        String token  = getJWToken(usuario.getCorreo(), usuario.getContraseña());
        query.setParameter("token_in", token);
        query.execute();
        return "Usuario Editado";
    }

    @Override
    public String borrar(int idUsuario) {
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("borrar_usuario");
        query.setParameter("id_user", idUsuario);
        query.execute();
        return "Usuario Eliminado";
    }

    @Override
    public UsuarioPost traer(int idUsuario) {
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("get_usuario");
        query.setParameter("id_user", idUsuario);
        return (UsuarioPost) query.getResultStream().findFirst().orElse(null);

    }

    @Override
    public List<UsuarioPost> todos() {
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("get_usuarios");
        return (List<UsuarioPost>) query.getResultList().stream().map(o->(UsuarioPost)o).collect(Collectors.toList());
    }

    @Override
    public String crearTelefono(Telefono telefono) {
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("add_telefono");
        query.setParameter("id_user", telefono.getUsuarioCrea().getId());
        query.setParameter("numero_in", telefono.getNumero());
        query.setParameter("codciudad_in", telefono.getCodigoCiudad());
        query.setParameter("codpais_in", telefono.getCodigoPais());
        query.execute();
        return "Telefono creado";
    }

    @Override
    public String borrarTelefonos(UsuarioPost usuario) {
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("borrar_telefonos");
        query.setParameter("id_user", usuario.getId());
        query.execute();
        return "Usuario Eliminado";
    }

    @Override
    public String verificar(UsuarioAccess usuario) {
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("get_validar");
        query.setParameter("correo_in", usuario.getCorreo());
        UsuarioDB usuariopost = (UsuarioDB) query.getResultStream().findFirst().orElse(null);
        if (usuariopost != null){
            if(usuario.getPassword().equals(usuariopost.getPassword())){
                return "Usuario verificado";
            }else{
                return null;
            }
        }else{
            return null;
        }


    }
}
