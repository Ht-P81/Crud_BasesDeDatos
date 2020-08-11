package com.example.crud_basesdedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    //Creamos las variables que harán referencia a los botones
    Button botonInsertar, botonActualizar, botonBorrar, botonBuscar;
    EditText textoId, textoNombre, textoApellido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Vamos a identificar los botones
        botonInsertar = (Button) findViewById(R.id.insertar);
        botonActualizar = (Button) findViewById(R.id.actualizar);
        botonBuscar = (Button) findViewById(R.id.buscar);
        botonBorrar = (Button) findViewById(R.id.borrar);

        //identificamos los EditText
        textoId = (EditText) findViewById(R.id.id);
        textoNombre = (EditText) findViewById(R.id.nombre);
        textoApellido = (EditText) findViewById(R.id.apellidos);

        //Aqui ponemos la instancia que nos dice la API de Android que debemos de poner y de hacer
        //con esta instancia podemos acceder a la base de datos
        final BBDD_Helper helper = new BBDD_Helper(this);

        //Vamos a poner los botones a la escucha, ahora mismo el más importante es el de Insertar
        botonInsertar.setOnClickListener(new View.OnClickListener() {
            //Implementamos los métodos para esta interfaz (onClick)
            @Override
            public void onClick(View v) {
                //Dentro de este método debemos de indicarle qué hacer para crear la base de datos
                //e introducir la información que el usuario ha puesto en los editText, lo vemos en el siguiente

                // Gets the data repository in write mode, variable helper de la linea 40 debe ser marcada como final para quitar el error
                SQLiteDatabase db = helper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                //Con esta instrucción conseguimos que lo que el usuario almacene en el textoId lo almacene en la columna 1
                values.put(Estructura_BBDD.NOMBRE_COLUMNA1, textoId.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA2, textoNombre.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA3, textoApellido.getText().toString());

                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(Estructura_BBDD.TABLE_NAME, null, values);

                //Creamos un toast notificación que nos diga que el registro se ha insertado correctamente
                Toast.makeText(getApplicationContext(), "Se guardó el registro con la clave: " + newRowId, Toast.LENGTH_LONG).show();
            }
        });

        botonBorrar.setOnClickListener(new View.OnClickListener(){
            //Implementamos los métodos para esta interfaz (onClick)
            @Override
            public void onClick (View v){

                SQLiteDatabase db = helper.getWritableDatabase();
                // Define 'where' part of query.
                //Nombre_Columna que es donde especificamos que es el campo del criterio
                String selection = Estructura_BBDD.NOMBRE_COLUMNA1 + " LIKE ?";
// Specify arguments in placeholder order.
                //rescatamos el valor que hay en ese cuadro de texto y lo utilizamos como criterio para que busque en la columna 1 el
                // id que el usuario haya escrito
                String[] selectionArgs = { textoId.getText().toString() };
// Issue SQL statement.
                db.delete(Estructura_BBDD.TABLE_NAME, selection, selectionArgs);

                Toast.makeText(getApplicationContext(), "Se borró el registro número: " + textoId.getText().toString(), Toast.LENGTH_LONG).show();
                //Para vaciar esos campos, es decir pone en blanco los registros del campo que se haya borrado
                textoId.setText("");
                textoApellido.setText("");
                textoNombre.setText("");
            }
        });

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            //Implementamos los métodos para esta interfaz (onClick)
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getReadableDatabase();

// New value for one column
                String title = "MyNewTitle";
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.NOMBRE_COLUMNA2, textoNombre.getText().toString());
                values.put(Estructura_BBDD.NOMBRE_COLUMNA3, textoApellido.getText().toString());

// Which row to update, based on the title
                String selection = Estructura_BBDD.NOMBRE_COLUMNA1 + " LIKE ?";
                String[] selectionArgs = { textoId.getText().toString() };

                int count = db.update(
                        Estructura_BBDD.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                Toast.makeText(getApplicationContext(), "Se actualizó el registro " , Toast.LENGTH_LONG).show();
            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            //Implementamos los métodos para esta interfaz (onClick)
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
                String[] projection = {
                        //BaseColumns._ID,
                        Estructura_BBDD.NOMBRE_COLUMNA2,
                        Estructura_BBDD.NOMBRE_COLUMNA3
                };

// Filter results WHERE "title" = 'My Title'
                String selection = Estructura_BBDD.NOMBRE_COLUMNA1 + " = ?";
                String[] selectionArgs = {textoId.getText().toString()};

// How you want the results sorted in the resulting Cursor
                //String sortOrder =
                // Estructura_BBDD.COLUMN_NAME_SUBTITLE + " DESC";

                //Con un try catch nos aseguramos que si el usuario mete una consulta con un registro que no exista la aplicación no caiga

                try {
                    Cursor c = db.query(
                            Estructura_BBDD.TABLE_NAME,   // The table to query, la tabla de la consulta
                            projection,             // The array of columns to return (pass null to get all)
                            selection,              // The columns for the WHERE clause
                            selectionArgs,          // The values for the WHERE clause
                            null,                   // don't group the rows
                            null,                   // don't filter by row groups
                            null               // The sort order, ningun ordenamiento
                    );

                    c.moveToFirst();
                    textoNombre.setText(c.getString(0));
                    textoApellido.setText(c.getString(1));

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "No se encontró registro alguno", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

