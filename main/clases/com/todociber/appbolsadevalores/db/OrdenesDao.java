package com.todociber.appbolsadevalores.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.todociber.appbolsadevalores.db.Ordenes;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ORDENES.
*/
public class OrdenesDao extends AbstractDao<Ordenes, Long> {

    public static final String TABLENAME = "ORDENES";

    /**
     * Properties of entity Ordenes.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdOrden = new Property(1, String.class, "idOrden", false, "ID_ORDEN");
        public final static Property Correlativo = new Property(2, String.class, "correlativo", false, "CORRELATIVO");
        public final static Property FechaDeVigencia = new Property(3, String.class, "fechaDeVigencia", false, "FECHA_DE_VIGENCIA");
        public final static Property AgenteCorredor = new Property(4, String.class, "agenteCorredor", false, "AGENTE_CORREDOR");
        public final static Property TipoOrden = new Property(5, String.class, "tipoOrden", false, "TIPO_ORDEN");
        public final static Property IdOrdenPadre = new Property(6, String.class, "idOrdenPadre", false, "ID_ORDEN_PADRE");
        public final static Property EstadoOrden = new Property(7, String.class, "estadoOrden", false, "ESTADO_ORDEN");
        public final static Property Titulo = new Property(8, String.class, "titulo", false, "TITULO");
        public final static Property TipoEjecucion = new Property(9, String.class, "TipoEjecucion", false, "TIPO_EJECUCION");
        public final static Property ValorMinimo = new Property(10, String.class, "valorMinimo", false, "VALOR_MINIMO");
        public final static Property ValorMaximo = new Property(11, String.class, "valorMaximo", false, "VALOR_MAXIMO");
        public final static Property NombreCasaCorredora = new Property(12, String.class, "nombreCasaCorredora", false, "NOMBRE_CASA_CORREDORA");
        public final static Property FechaCreacion = new Property(13, String.class, "fechaCreacion", false, "FECHA_CREACION");
        public final static Property FechaVigencia = new Property(14, String.class, "fechaVigencia", false, "FECHA_VIGENCIA");
        public final static Property Monto = new Property(15, String.class, "monto", false, "MONTO");
        public final static Property TasaDeInterez = new Property(16, String.class, "tasaDeInterez", false, "TASA_DE_INTEREZ");
        public final static Property Comision = new Property(17, String.class, "comision", false, "COMISION");
        public final static Property CuentasCedeval = new Property(18, String.class, "cuentasCedeval", false, "CUENTAS_CEDEVAL");
        public final static Property EmisorNombre = new Property(19, String.class, "emisorNombre", false, "EMISOR_NOMBRE");
        public final static Property TipoMercado = new Property(20, String.class, "tipoMercado", false, "TIPO_MERCADO");
    };


    public OrdenesDao(DaoConfig config) {
        super(config);
    }
    
    public OrdenesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ORDENES' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ID_ORDEN' TEXT," + // 1: idOrden
                "'CORRELATIVO' TEXT," + // 2: correlativo
                "'FECHA_DE_VIGENCIA' TEXT," + // 3: fechaDeVigencia
                "'AGENTE_CORREDOR' TEXT," + // 4: agenteCorredor
                "'TIPO_ORDEN' TEXT," + // 5: tipoOrden
                "'ID_ORDEN_PADRE' TEXT," + // 6: idOrdenPadre
                "'ESTADO_ORDEN' TEXT," + // 7: estadoOrden
                "'TITULO' TEXT," + // 8: titulo
                "'TIPO_EJECUCION' TEXT," + // 9: TipoEjecucion
                "'VALOR_MINIMO' TEXT," + // 10: valorMinimo
                "'VALOR_MAXIMO' TEXT," + // 11: valorMaximo
                "'NOMBRE_CASA_CORREDORA' TEXT," + // 12: nombreCasaCorredora
                "'FECHA_CREACION' TEXT," + // 13: fechaCreacion
                "'FECHA_VIGENCIA' TEXT," + // 14: fechaVigencia
                "'MONTO' TEXT," + // 15: monto
                "'TASA_DE_INTEREZ' TEXT," + // 16: tasaDeInterez
                "'COMISION' TEXT," + // 17: comision
                "'CUENTAS_CEDEVAL' TEXT," + // 18: cuentasCedeval
                "'EMISOR_NOMBRE' TEXT," + // 19: emisorNombre
                "'TIPO_MERCADO' TEXT);"); // 20: tipoMercado
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ORDENES'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Ordenes entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String idOrden = entity.getIdOrden();
        if (idOrden != null) {
            stmt.bindString(2, idOrden);
        }
 
        String correlativo = entity.getCorrelativo();
        if (correlativo != null) {
            stmt.bindString(3, correlativo);
        }
 
        String fechaDeVigencia = entity.getFechaDeVigencia();
        if (fechaDeVigencia != null) {
            stmt.bindString(4, fechaDeVigencia);
        }
 
        String agenteCorredor = entity.getAgenteCorredor();
        if (agenteCorredor != null) {
            stmt.bindString(5, agenteCorredor);
        }
 
        String tipoOrden = entity.getTipoOrden();
        if (tipoOrden != null) {
            stmt.bindString(6, tipoOrden);
        }
 
        String idOrdenPadre = entity.getIdOrdenPadre();
        if (idOrdenPadre != null) {
            stmt.bindString(7, idOrdenPadre);
        }
 
        String estadoOrden = entity.getEstadoOrden();
        if (estadoOrden != null) {
            stmt.bindString(8, estadoOrden);
        }
 
        String titulo = entity.getTitulo();
        if (titulo != null) {
            stmt.bindString(9, titulo);
        }
 
        String TipoEjecucion = entity.getTipoEjecucion();
        if (TipoEjecucion != null) {
            stmt.bindString(10, TipoEjecucion);
        }
 
        String valorMinimo = entity.getValorMinimo();
        if (valorMinimo != null) {
            stmt.bindString(11, valorMinimo);
        }
 
        String valorMaximo = entity.getValorMaximo();
        if (valorMaximo != null) {
            stmt.bindString(12, valorMaximo);
        }
 
        String nombreCasaCorredora = entity.getNombreCasaCorredora();
        if (nombreCasaCorredora != null) {
            stmt.bindString(13, nombreCasaCorredora);
        }
 
        String fechaCreacion = entity.getFechaCreacion();
        if (fechaCreacion != null) {
            stmt.bindString(14, fechaCreacion);
        }
 
        String fechaVigencia = entity.getFechaVigencia();
        if (fechaVigencia != null) {
            stmt.bindString(15, fechaVigencia);
        }
 
        String monto = entity.getMonto();
        if (monto != null) {
            stmt.bindString(16, monto);
        }
 
        String tasaDeInterez = entity.getTasaDeInterez();
        if (tasaDeInterez != null) {
            stmt.bindString(17, tasaDeInterez);
        }
 
        String comision = entity.getComision();
        if (comision != null) {
            stmt.bindString(18, comision);
        }
 
        String cuentasCedeval = entity.getCuentasCedeval();
        if (cuentasCedeval != null) {
            stmt.bindString(19, cuentasCedeval);
        }
 
        String emisorNombre = entity.getEmisorNombre();
        if (emisorNombre != null) {
            stmt.bindString(20, emisorNombre);
        }
 
        String tipoMercado = entity.getTipoMercado();
        if (tipoMercado != null) {
            stmt.bindString(21, tipoMercado);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Ordenes readEntity(Cursor cursor, int offset) {
        Ordenes entity = new Ordenes( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // idOrden
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // correlativo
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // fechaDeVigencia
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // agenteCorredor
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // tipoOrden
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // idOrdenPadre
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // estadoOrden
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // titulo
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // TipoEjecucion
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // valorMinimo
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // valorMaximo
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // nombreCasaCorredora
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // fechaCreacion
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // fechaVigencia
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // monto
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // tasaDeInterez
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // comision
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // cuentasCedeval
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // emisorNombre
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20) // tipoMercado
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Ordenes entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdOrden(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCorrelativo(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFechaDeVigencia(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAgenteCorredor(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTipoOrden(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIdOrdenPadre(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setEstadoOrden(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTitulo(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTipoEjecucion(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setValorMinimo(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setValorMaximo(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setNombreCasaCorredora(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setFechaCreacion(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setFechaVigencia(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setMonto(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setTasaDeInterez(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setComision(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setCuentasCedeval(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setEmisorNombre(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setTipoMercado(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Ordenes entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Ordenes entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}