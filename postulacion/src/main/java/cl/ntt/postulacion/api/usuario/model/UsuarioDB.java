package cl.ntt.postulacion.api.usuario.model;
import javax.persistence.*;
@SqlResultSetMappings(
        @SqlResultSetMapping(
                name = "usuarioDBmap",
                entities = {
                        @EntityResult(
                                entityClass = UsuarioDB.class,
                                fields = {
                                        @FieldResult(name = "idUsuarioDb", column = "id"),
                                        @FieldResult(name = "nombre", column = "nombre"),
                                        @FieldResult(name = "correo", column = "correo"),
                                        @FieldResult(name = "password", column = "password"),
                                        @FieldResult(name = "token", column = "token"),
                                        @FieldResult(name = "activo", column = "activo")
                                }
                        )
                }
        )
)
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name="get_usuario", procedureName = "get_usuario",
                parameters = {
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="id_user", type = Integer.class)
                },
                resultSetMappings = {"usuarioDBmap"}
        ),
        @NamedStoredProcedureQuery(name="get_usuarios", procedureName = "get_usuarios",
                resultSetMappings = {"usuarioDBmap"}
        ),
        @NamedStoredProcedureQuery(name="get_validar", procedureName = "get_validar",
                parameters = {
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="correo_in", type = String.class)
                },
                resultSetMappings = {"usuarioDBmap"}
        )}
)
@Entity
public class UsuarioDB {
    @Id
    private int idUsuarioDb;
    private String nombre;
    private String correo;

    private String password;

    private String token;

    private int activo;


    public int getIdUsuarioDb() {
        return idUsuarioDb;
    }

    public void setIdUsuarioDb(int idUsuarioDb) {
        this.idUsuarioDb = idUsuarioDb;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
