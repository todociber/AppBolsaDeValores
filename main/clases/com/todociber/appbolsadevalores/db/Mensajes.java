package com.todociber.appbolsadevalores.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table MENSAJES.
 */
public class Mensajes {

    private Long id;
    private String idMensaje;
    private String tipoMensaje;
    private String idUsuario;
    private String nombreUsuario;
    private String mensaje;
    private String idOrden;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Mensajes() {
    }

    public Mensajes(Long id) {
        this.id = id;
    }

    public Mensajes(Long id, String idMensaje, String tipoMensaje, String idUsuario, String nombreUsuario, String mensaje, String idOrden) {
        this.id = id;
        this.idMensaje = idMensaje;
        this.tipoMensaje = tipoMensaje;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.mensaje = mensaje;
        this.idOrden = idOrden;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
