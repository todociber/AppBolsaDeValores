package com.todociber.appbolsadevalores.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table TITULOS.
 */
public class Titulos {

    private Long id;
    private String idTitulo;
    private String nombreTitulo;
    private String tasaDeInteres;
    private String idEmisor;
    private String idTipoTitulo;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Titulos() {
    }

    public Titulos(Long id) {
        this.id = id;
    }

    public Titulos(Long id, String idTitulo, String nombreTitulo, String tasaDeInteres, String idEmisor, String idTipoTitulo) {
        this.id = id;
        this.idTitulo = idTitulo;
        this.nombreTitulo = nombreTitulo;
        this.tasaDeInteres = tasaDeInteres;
        this.idEmisor = idEmisor;
        this.idTipoTitulo = idTipoTitulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdTitulo() {
        return idTitulo;
    }

    public void setIdTitulo(String idTitulo) {
        this.idTitulo = idTitulo;
    }

    public String getNombreTitulo() {
        return nombreTitulo;
    }

    public void setNombreTitulo(String nombreTitulo) {
        this.nombreTitulo = nombreTitulo;
    }

    public String getTasaDeInteres() {
        return tasaDeInteres;
    }

    public void setTasaDeInteres(String tasaDeInteres) {
        this.tasaDeInteres = tasaDeInteres;
    }

    public String getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(String idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getIdTipoTitulo() {
        return idTipoTitulo;
    }

    public void setIdTipoTitulo(String idTipoTitulo) {
        this.idTipoTitulo = idTipoTitulo;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
