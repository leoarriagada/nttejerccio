package cl.ntt.postulacion.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
public class GenericResponse<T> {
    private T data;
    private boolean success;
    private String mensaje;

    public GenericResponse(T data) {
        this.data = data;
        this.success = false;
        this.mensaje = "";
    }

    public GenericResponse(T data, boolean success, String mensaje) {
        this.data = data;
        this.success = success;
        this.mensaje = mensaje;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
