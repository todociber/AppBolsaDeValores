package com.todociber.appbolsadevalores.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table TIPO_MERCADO.
 */
public class TipoMercado {

    private Long id;
    private String idTipoMercado;
    private String nombreMercado;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public TipoMercado() {
    }

    public TipoMercado(Long id) {
        this.id = id;
    }

    public TipoMercado(Long id, String idTipoMercado, String nombreMercado) {
        this.id = id;
        this.idTipoMercado = idTipoMercado;
        this.nombreMercado = nombreMercado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdTipoMercado() {
        return idTipoMercado;
    }

    public void setIdTipoMercado(String idTipoMercado) {
        this.idTipoMercado = idTipoMercado;
    }

    public String getNombreMercado() {
        return nombreMercado;
    }

    public void setNombreMercado(String nombreMercado) {
        this.nombreMercado = nombreMercado;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
