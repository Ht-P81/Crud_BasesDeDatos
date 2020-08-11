package com.example.crud_basesdedatos;

public class Estructura_BBDD {

    //El constructor tiene que tener el mismo nombre que la clase
        private Estructura_BBDD() {}

             /* Inner class that defines the table contents */
         // public static class FeedEntry implements BaseColumns {
         //Constantes de clases para poner el nombre que mas se adapte a tu proyecto
         //si necesitamos más tablas pues la agregamos TABLE_NAME, si lo que necesitamos es más columnas lo agregamos NOMBRE_COLUMNA1
        //mucho ojo con los espacios, NO deben tener espacios, fue un error que me llevó tiempo solucionar
            public static final String TABLE_NAME = "DatosPersonales"; //COMO QUEREMOS QUE SE LLAME NUESTRA TABLA
            public static final String NOMBRE_COLUMNA1 = "Id";
            public static final String NOMBRE_COLUMNA2 = "Nombre";
            public static final String NOMBRE_COLUMNA3 = "Apellido";
        //}

    //Una vez que hayas definido el aspecto de tu base de datos, debes implementar métodos
    // que creen y mantengan la base de datos y las tablas
        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME + " (" +
                    Estructura_BBDD.NOMBRE_COLUMNA1 + " INTEGER PRIMARY KEY," +
                    Estructura_BBDD.NOMBRE_COLUMNA2 + " TEXT," +
                    Estructura_BBDD.NOMBRE_COLUMNA3 + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME;
    }

