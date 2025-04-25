package cl.ntt.postulacion.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorResponse {
    private int codigo;
    private String mensaje;
    private String error;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date fecha = new Date();

    public ErrorResponse(HttpStatus status, String mensaje, String error) {
        this.codigo = status.value();
        this.mensaje = mensaje;
        this.error = error;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getError() {
        return error;
    }

    public Date getFecha() {
        return fecha;
    }
}