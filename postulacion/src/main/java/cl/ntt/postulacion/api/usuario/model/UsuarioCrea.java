package cl.ntt.postulacion.api.usuario.model;

import org.hibernate.cfg.annotations.QueryBinder;

import javax.persistence.*;
import java.util.Date;



@SqlResultSetMappings(
        @SqlResultSetMapping(
            name = "usuariocreamap",
            entities = {
                @EntityResult(
                        entityClass = UsuarioCrea.class,
                            fields = {
                                    @FieldResult(name = "id", column = "id"),
                                    @FieldResult(name = "creado", column = "creado"),
                                    @FieldResult(name = "modificado", column = "modificado"),
                                    @FieldResult(name = "ultimoLogin", column = "last_login"),
                                    @FieldResult(name = "token", column = "token"),
                                    @FieldResult(name = "activo", column = "activo")
                            }
                        )
                }
            )
)
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name="crear_usuario", procedureName = "crear_usuario",
                parameters = {
                    @StoredProcedureParameter(mode=ParameterMode.OUT, name="id_user", type = Integer.class),
                    @StoredProcedureParameter(mode=ParameterMode.IN, name="nombre_in", type = String.class),
                    @StoredProcedureParameter(mode=ParameterMode.IN, name="correo_in", type = String.class),
                    @StoredProcedureParameter(mode=ParameterMode.IN, name="password_in", type = String.class),
                    @StoredProcedureParameter(mode=ParameterMode.IN, name="token_in", type = String.class)
                }
        ),
        @NamedStoredProcedureQuery(name="borrar_usuario", procedureName = "borrar_usuario",
                parameters = {
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="id_user", type = Integer.class)
                }
        ),
        @NamedStoredProcedureQuery(name="get_usuario_creado", procedureName = "get_usuario_creado",
                parameters = {
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="id_user", type = Integer.class)
                },
                resultSetMappings = {"usuariocreamap"}
        ),
        @NamedStoredProcedureQuery(name="add_telefono", procedureName = "add_telefono",
                parameters = {
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="id_user", type = Integer.class),
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="numero_in", type = String.class),
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="codciudad_in", type = String.class),
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="codpais_in", type = String.class)

                }
        ),
        @NamedStoredProcedureQuery(name="borrar_telefonos", procedureName = "borrar_telefonos",
                parameters = {
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="id_user", type = Integer.class)
                }
        ),
        @NamedStoredProcedureQuery(name="modificar_usuario", procedureName = "modificar_usuario",
                parameters = {
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="correo_in", type = String.class),
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="password_in", type = String.class),
                        @StoredProcedureParameter(mode=ParameterMode.IN, name="token_in", type = String.class)
                }
        )}
)
@Entity
public class UsuarioCrea {
    @Id
    private int id;
    private Date creado;
    private Date modificado;

    private Date ultimoLogin;
    private String token;
    private String activo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    public Date getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(Date ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
