package com.todociber.appbolsadevalores.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table ORDENES.
 */
public class Ordenes {

    private Long id;
    private String idOrden;
    private String correlativo;
    private String fechaDeVigencia;
    private String agenteCorredor;
    private String tipoOrden;
    private String idOrdenPadre;
    private String estadoOrden;
    private String titulo;
    private String TipoEjecucion;
    private String valorMinimo;
    private String valorMaximo;
    private String nombreCasaCorredora;
    private String fechaCreacion;
    private String fechaVigencia;
    private String monto;
    private String tasaDeInterez;
    private String comision;
    private String cuentasCedeval;
    private String emisorNombre;
    private String tipoMercado;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Ordenes() {
    }

    public Ordenes(Long id) {
        this.id = id;
    }

    public Ordenes(Long id, String idOrden, String correlativo, String fechaDeVigencia, String agenteCorredor, String tipoOrden, String idOrdenPadre, String estadoOrden, String titulo, String TipoEjecucion, String valorMinimo, String valorMaximo, String nombreCasaCorredora, String fechaCreacion, String fechaVigencia, String monto, String tasaDeInterez, String comision, String cuentasCedeval, String emisorNombre, String tipoMercado) {
        this.id = id;
        this.idOrden = idOrden;
        this.correlativo = correlativo;
        this.fechaDeVigencia = fechaDeVigencia;
        this.agenteCorredor = agenteCorredor;
        this.tipoOrden = tipoOrden;
        this.idOrdenPadre = idOrdenPadre;
        this.estadoOrden = estadoOrden;
        this.titulo = titulo;
        this.TipoEjecucion = TipoEjecucion;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.nombreCasaCorredora = nombreCasaCorredora;
        this.fechaCreacion = fechaCreacion;
        this.fechaVigencia = fechaVigencia;
        this.monto = monto;
        this.tasaDeInterez = tasaDeInterez;
        this.comision = comision;
        this.cuentasCedeval = cuentasCedeval;
        this.emisorNombre = emisorNombre;
        this.tipoMercado = tipoMercado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public String getFechaDeVigencia() {
        return fechaDeVigencia;
    }

    public void setFechaDeVigencia(String fechaDeVigencia) {
        this.fechaDeVigencia = fechaDeVigencia;
    }

    public String getAgenteCorredor() {
        return agenteCorredor;
    }

    public void setAgenteCorredor(String agenteCorredor) {
        this.agenteCorredor = agenteCorredor;
    }

    public String getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public String getIdOrdenPadre() {
        return idOrdenPadre;
    }

    public void setIdOrdenPadre(String idOrdenPadre) {
        this.idOrdenPadre = idOrdenPadre;
    }

    public String getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(String estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipoEjecucion() {
        return TipoEjecucion;
    }

    public void setTipoEjecucion(String TipoEjecucion) {
        this.TipoEjecucion = TipoEjecucion;
    }

    public String getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(String valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public String getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(String valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public String getNombreCasaCorredora() {
        return nombreCasaCorredora;
    }

    public void setNombreCasaCorredora(String nombreCasaCorredora) {
        this.nombreCasaCorredora = nombreCasaCorredora;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(String fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getTasaDeInterez() {
        return tasaDeInterez;
    }

    public void setTasaDeInterez(String tasaDeInterez) {
        this.tasaDeInterez = tasaDeInterez;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public String getCuentasCedeval() {
        return cuentasCedeval;
    }

    public void setCuentasCedeval(String cuentasCedeval) {
        this.cuentasCedeval = cuentasCedeval;
    }

    public String getEmisorNombre() {
        return emisorNombre;
    }

    public void setEmisorNombre(String emisorNombre) {
        this.emisorNombre = emisorNombre;
    }

    public String getTipoMercado() {
        return tipoMercado;
    }

    public void setTipoMercado(String tipoMercado) {
        this.tipoMercado = tipoMercado;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}