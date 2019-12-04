/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       GESTIÓN DE PROYECTOS DE SOFTWARE
:*
:*                   SEMESTRE: AGO-DIC/2019    HORA: 11-12 HRS
:*
:*                       Clase encargada de la interacción con la base de datos
:*
:*  Archivo     : ContactoSQLiteHelper.java
:*  Autor       : PPS
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Clase encargada de interactuar con la base de datos
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  04/012/2019 Iván García Moreno   Comentarios
:*------------------------------------------------------------------------------------------*/
package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ContactoSQLiteHelper extends SQLiteOpenHelper {

    // Nombre de la base de datos
    private static final String NOMBRE_BD = "DBContacto";
    // Versión de la base de datos (usada para actualización)
    private static final int VERSION_BD = 1;
    // Sentencia de creación de la tabla de contactos
    private static final String TABLA_CONTACTOS = " CREATE TABLE Contactos (Nombre TEXT, Telefono TEXT) ";

    // Constructor de la base de datos
    public ContactoSQLiteHelper(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    // Creación de la base de datos, solo se llama una vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_CONTACTOS);
    }

    // Método encargado de la actualización de las tablas, no en uso actualmente pero requerido
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLA_CONTACTOS);
        db.execSQL(TABLA_CONTACTOS);
    }

    // Método que agrega el contacto a la base de datos
    public void agregarContacto(String nombre, String telefono) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL(" INSERT INTO Contactos VALUES('" + nombre + "', '" + telefono + "') ");
            db.close();
        }
    }

    // Método para actualizar el contacto en la BD
    public void editarContacto(String nombre, String telefono, String nomAnt, String telAnt) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL(" UPDATE Contactos SET Nombre = '" + nombre + "', Telefono = '"+telefono+"' WHERE Nombre = '" + nomAnt + "' AND Telefono = '" + telAnt + "' ");
            db.close();
        }
    }

    // Método para eliminar el contacto en la BD
    public void eliminarContacto(String nombre, String telefono) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL(" DELETE FROM Contactos WHERE Nombre = '" + nombre + "' AND Telefono = '" + telefono + "' ");
            db.close();
        }
    }

    /*
     * Método que obtiene todos los contactos almacenados en la base de datos
     */
    public ArrayList<Contacto> mostrarContactos() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM Contactos ", null);
        ArrayList<Contacto> contactos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                contactos.add(new Contacto(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return contactos;
    }
}
