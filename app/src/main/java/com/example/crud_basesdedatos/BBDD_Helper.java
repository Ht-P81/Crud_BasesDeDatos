package com.example.crud_basesdedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BBDD_Helper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    //No debemos confundir la constante de clases con el número de serialización
    public static final int DATABASE_VERSION = 2;
    // Esta constante hace referencia al nombre de la base de datos
    public static final String DATABASE_NAME = "Datos_Trabajadores.db";
    //Recordamos que desde la clase Estructura_BBDD hemos establecido el nombre de la tabla (tablas)
    //y de los campos, pero en ningún momento hemos establecido el nombre de la BBDD.


    public BBDD_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        //recordemos que SQL_CREATE_ENTRIES la tenemos en nuestro proyecto clase Estructura_BBDD como
        //una constante de clases es estática, por tanto al ser una constante la debemos llamar con el nombre
        //de la clase por delante. db.execSQL(SQL_CREATE_ENTRIES);
        //Ojo sigue dando problemas pq la tenemos encapsuladas como private en su clase por tanto cambiamos
        //el modificador de acceso por public
        db.execSQL(Estructura_BBDD.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(Estructura_BBDD.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
