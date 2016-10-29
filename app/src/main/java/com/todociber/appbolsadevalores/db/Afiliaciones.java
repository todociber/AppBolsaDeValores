package com.todociber.appbolsadevalores.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table AFILIACIONES.
 */
public class Afiliaciones {

    private Long id;
    private String numeroAfiliacion;
    private String nombreCasa;
    private String estadoAfiliacion;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Afiliaciones() {
    }

    public Afiliaciones(Long id) {
        this.id = id;
    }

    public Afiliaciones(Long id, String numeroAfiliacion, String nombreCasa, String estadoAfiliacion) {
        this.id = id;
        this.numeroAfiliacion = numeroAfiliacion;
        this.nombreCasa = nombreCasa;
        this.estadoAfiliacion = estadoAfiliacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroAfiliacion() {
        return numeroAfiliacion;
    }

    public void setNumeroAfiliacion(String numeroAfiliacion) {
        this.numeroAfiliacion = numeroAfiliacion;
    }

    public String getNombreCasa() {
        return nombreCasa;
    }

    public void setNombreCasa(String nombreCasa) {
        this.nombreCasa = nombreCasa;
    }

    public String getEstadoAfiliacion() {
        return estadoAfiliacion;
    }

    public void setEstadoAfiliacion(String estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
