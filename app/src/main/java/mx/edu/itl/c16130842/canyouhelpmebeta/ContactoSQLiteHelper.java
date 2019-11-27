package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ContactoSQLiteHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "DBContacto";
    private static final int VERSION_BD = 1;
    private static final String TABLA_CONTACTOS = " CREATE TABLE Contactos (Nombre TEXT, Telefono TEXT) ";

    public ContactoSQLiteHelper(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLA_CONTACTOS);
        db.execSQL(TABLA_CONTACTOS);
    }

    public void agregarContacto(String nombre, String telefono) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL(" INSERT INTO Contactos VALUES('" + nombre + "', '" + telefono + "') ");
            db.close();
        }
    }

    public void editarContacto(String nombre, String telefono, String nomAnt, String telAnt) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL(" UPDATE Contactos SET Nombre = '" + nombre + "', Telefono = '"+telefono+"' WHERE Nombre = '" + nomAnt + "' AND Telefono = '" + telAnt + "' ");
            db.close();
        }
    }

    public void eliminarContacto(String nombre, String telefono) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL(" DELETE FROM Contactos WHERE Nombre = '" + nombre + "' AND Telefono = '" + telefono + "' ");
            db.close();
        }
    }

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
