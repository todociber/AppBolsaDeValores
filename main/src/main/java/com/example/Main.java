package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Main {


    public  static void main(String[] args) throws Exception {
        Schema schema = new Schema(3, "com.todociber.appbolsadevalores.db");
        schema.enableKeepSectionsByDefault();
        createDataBase(schema);
        DaoGenerator generador = new DaoGenerator();
        generador.generateAll(schema,args[0]);
    }


    private static void createDataBase(Schema schema) {
        Entity tokenPush =  schema.addEntity("TokenPush");
        tokenPush.addIdProperty();
        tokenPush.addStringProperty("tokenPush");//1

        Entity Emisores = schema.addEntity("Emisores");
        Emisores.addIdProperty();
        Emisores.addStringProperty("idEmisor");//1
        Emisores.addStringProperty("nombreEmisor");//2
        Emisores.addStringProperty("idTitulo");//3


        Entity TipoMercado = schema.addEntity("TipoMercado");
        TipoMercado.addIdProperty();
        TipoMercado.addStringProperty("idTipoMercado");//1
        TipoMercado.addStringProperty("nombreMercado");//2

        Entity Titulos = schema.addEntity("Titulos");
        Titulos.addIdProperty();
        Titulos.addStringProperty("idTitulo");//1
        Titulos.addStringProperty("nombreTitulo");//2
        Titulos.addStringProperty("tasaDeInteres");//3
        Titulos.addStringProperty("idEmisor");//4
        Titulos.addStringProperty("idTipoTitulo");//5


        Entity Cliente = schema.addEntity("Cliente");
        Cliente.addIdProperty();
        Cliente.addIntProperty("idUsuario");//1
        Cliente.addStringProperty("nombre");//2
        Cliente.addStringProperty("apellido");//3
        Cliente.addStringProperty("email");//4
        Cliente.addStringProperty("idCliente");//5
        Cliente.addStringProperty("DUI");//6
        Cliente.addStringProperty("NIT");//7
        Cliente.addStringProperty("FechaDeNacimiento");//8
        Cliente.addStringProperty("token");//9

        Entity Cedeval = schema.addEntity("Cedeval");
        Cedeval.addIdProperty();
        Cedeval.addStringProperty("idCuenta");//1
        Cedeval.addStringProperty("NumeroDeCuenta");//2
        Cedeval.addStringProperty("idCliente");//3

        Entity CasasCorredoras = schema.addEntity("CasasCorredoras");
        CasasCorredoras.addIdProperty();
        CasasCorredoras.addStringProperty("idCasa");//1
        CasasCorredoras.addStringProperty("NombreCasa");//2

        Entity Ordenes = schema.addEntity("Ordenes");
        Ordenes.addIdProperty();
        Ordenes.addStringProperty("idOrden");//1
        Ordenes.addStringProperty("correlativo");//2
        Ordenes.addStringProperty("fechaDeVigencia");//3
        Ordenes.addStringProperty("agenteCorredor");//4
        Ordenes.addStringProperty("tipoOrden");//5
        Ordenes.addStringProperty("idOrdenPadre");//6
        Ordenes.addStringProperty("estadoOrden");//7
        Ordenes.addStringProperty("titulo");//8
        Ordenes.addStringProperty("TipoEjecucion");//9
        Ordenes.addStringProperty("valorMinimo");//10
        Ordenes.addStringProperty("valorMaximo");//11
        Ordenes.addStringProperty("nombreCasaCorredora");//12
        Ordenes.addStringProperty("fechaCreacion");//13
        Ordenes.addStringProperty("fechaVigencia");//14
        Ordenes.addStringProperty("monto");//15
        Ordenes.addStringProperty("tasaDeInterez");//16
        Ordenes.addStringProperty("comision");//17
        Ordenes.addStringProperty("cuentasCedeval");//18
        Ordenes.addStringProperty("emisorNombre");//19
        Ordenes.addStringProperty("tipoMercado");//20


        Entity OrdenesPadre = schema.addEntity("OrdenesPadre");
        OrdenesPadre.addIdProperty();
        OrdenesPadre.addStringProperty("idOrden");//1
        OrdenesPadre.addStringProperty("correlativo");//2
        OrdenesPadre.addStringProperty("fechaDeVigencia");//3
        OrdenesPadre.addStringProperty("agenteCorredor");//4
        OrdenesPadre.addStringProperty("tipoOrden");//5
        OrdenesPadre.addStringProperty("idOrdenPadre");//6
        OrdenesPadre.addStringProperty("estadoOrden");//7
        OrdenesPadre.addStringProperty("titulo");//8
        OrdenesPadre.addStringProperty("TipoEjecucion");//9
        OrdenesPadre.addStringProperty("valorMinimo");//10
        OrdenesPadre.addStringProperty("valorMaximo");//11
        OrdenesPadre.addStringProperty("nombreCasaCorredora");//12
        OrdenesPadre.addStringProperty("fechaCreacion");//13
        OrdenesPadre.addStringProperty("fechaVigencia");//14
        OrdenesPadre.addStringProperty("monto");//15
        OrdenesPadre.addStringProperty("tasaDeInterez");//16
        OrdenesPadre.addStringProperty("comision");//17
        OrdenesPadre.addStringProperty("cuentasCedeval");//18
        OrdenesPadre.addStringProperty("emisorNombre");//19
        OrdenesPadre.addStringProperty("tipoMercado");//20


        Entity Mensajes = schema.addEntity("Mensajes");
        Mensajes.addIdProperty();
        Mensajes.addStringProperty("idMensaje");//1
        Mensajes.addStringProperty("tipoMensaje");//2
        Mensajes.addStringProperty("idUsuario");//3
        Mensajes.addStringProperty("nombreUsuario");//4
        Mensajes.addStringProperty("mensaje");//5
        Mensajes.addStringProperty("idOrden");//6

        Entity OperacionesBolsa = schema.addEntity("OperacionesBolsa");
        OperacionesBolsa.addIdProperty();
        OperacionesBolsa.addStringProperty("idOperacion");//1
        OperacionesBolsa.addStringProperty("montoOperacion");//2
        OperacionesBolsa.addStringProperty("idOrden");//3


        Entity Afiliaciones = schema.addEntity("Afiliaciones");
        Afiliaciones.addIdProperty();
        Afiliaciones.addStringProperty("numeroAfiliacion");//1
        Afiliaciones.addStringProperty("nombreCasa");//2
        Afiliaciones.addStringProperty("estadoAfiliacion");//3








    }

}
