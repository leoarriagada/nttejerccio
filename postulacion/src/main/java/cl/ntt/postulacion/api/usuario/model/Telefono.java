package cl.ntt.postulacion.api.usuario.model;

public class Telefono {

    private UsuarioCrea usuarioCrea;
    private String numero;
    private String codigoCiudad;

    private String codigoPais;

    public Telefono(String numero, String codigoCiudad, String codigoPais) {
        this.numero = numero;
        this.codigoCiudad = codigoCiudad;
        this.codigoPais = codigoPais;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(String codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public UsuarioCrea getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(UsuarioCrea usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }
}
