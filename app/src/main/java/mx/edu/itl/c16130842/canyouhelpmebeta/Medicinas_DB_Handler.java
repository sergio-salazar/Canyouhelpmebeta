package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class Medicinas_DB_Handler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "medicinas.db";
    private static final String TABLE_NAME = "medicinas";

    //NOMBRES DE LAS TABLAS
    private static final String ID_COLUMN = "_id";
    private static final String NOMBRE_MEDICAMENTO_COLUMN = "nombre_medicamento";
    private static final String TIPO_COLUMN = "tipo";
    private static final String DOSIS_COLUMN = "dosis";
    private static final String HORAS_COLUMN = "horas";
    private static final String ES_URGENTE_COLUMN = "urgencia";
    private static final String DURACION_TRATAMIENTO_COLUMN = "duracion";
    private static final String DOSIS_DIA_COLUMN = "dosisdia";
    private final String comillas = "\"";

    private ArrayList<Medicamentos.Medicamento> misMedicamentos;

    public Medicinas_DB_Handler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Método que crea la tabla cuando esto se ejecuta por primera vez. Solo se ejecutará una vez
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREAR_TABLA = "CREATE TABLE " + TABLE_NAME + "(" + ID_COLUMN + " TEXT PRIMARY KEY," + NOMBRE_MEDICAMENTO_COLUMN +
                " TEXT, " + TIPO_COLUMN + " TEXT, " + DOSIS_COLUMN + " TEXT, " + HORAS_COLUMN + " TEXT, " + ES_URGENTE_COLUMN + " TEXT, " +
                DURACION_TRATAMIENTO_COLUMN + " TEXT, " + DOSIS_DIA_COLUMN + " TEXT)";
        //CREAR TABLA = CREATE TABLE medicinas(_id TEXT PRIMARY KEY, nombre_medicamento TEXT, tipo TEXT, dosis TEXT,
        //horas TEXT, ES_URGENTE_COLUMN TEXT, duracion TEXT, dosis TEXT
        sqLiteDatabase.execSQL(CREAR_TABLA);
    }

    //Método para actualizar la tabla, en nuestro caso la elimina y la vuelve a creer.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /* Método para añadir un objeto medicamento que recibe. Retorna un booleano para saber si fue
     * exitoso o no. Almacena todos los valores como String, para simplificar el proceso. El
     * método se encarga de las conversiones necesarias.
     */
    public boolean anadeMedicamento(Medicamentos.Medicamento medicamento) {
        boolean anadidoExitosamente = false;
        SQLiteDatabase medicinasDB = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        try {
            valores.put(ID_COLUMN, medicamento.getNombre());
            valores.put(NOMBRE_MEDICAMENTO_COLUMN, medicamento.getNombre());
            valores.put(TIPO_COLUMN, String.valueOf(medicamento.getTipo()));
            valores.put(DOSIS_COLUMN, String.valueOf(medicamento.getDosis()));
            valores.put(HORAS_COLUMN, String.valueOf(medicamento.getHoras()));
            valores.put(ES_URGENTE_COLUMN, medicamento.getPrioridad() ? "1" : "0");
            valores.put(DURACION_TRATAMIENTO_COLUMN, String.valueOf(medicamento.getDurTrat()));
            valores.put(DOSIS_DIA_COLUMN, String.valueOf(medicamento.getDosis()));
            medicinasDB.insert(TABLE_NAME, null, valores);
            anadidoExitosamente = true;
        } catch (Exception e) {
            anadidoExitosamente = false;
        } finally {
            medicinasDB.close();
        }
        return anadidoExitosamente;
    }

    /* Método de consulta de un medicamento, recibe el nombre. Hace la consulta con base en el nombre
     * crea el objeto medicamento y lo regresa en caso de que lo haya encontrado. En caso de que no lo
     * encuentre, retorna null. El método maneja todas las conversiones necesarias.
     */
    public Medicamentos.Medicamento getMedicamento(String nombreMedicamento) {
        String CONSULTA_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + NOMBRE_MEDICAMENTO_COLUMN + " = "
                + comillas + nombreMedicamento + comillas;
        //SELECT FROM medicinas WHHERE nombre_medicamento = "Loratadina"
        SQLiteDatabase dbMedicamentos = this.getWritableDatabase();

        try {
            Cursor cursor = dbMedicamentos.rawQuery(CONSULTA_SQL, null);
            Medicamentos.Medicamento medicamento = new Medicamentos.Medicamento();
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                medicamento.setNombre(cursor.getString(1));
                medicamento.setTipo(Integer.parseInt(cursor.getString(2)));
                medicamento.setDosis(Float.valueOf(cursor.getString(3)));
                medicamento.setHoras(Integer.parseInt(cursor.getString(4)));
                medicamento.setPrioridad(cursor.getString(5) == "1" ? true : false);
                medicamento.setDurTrat(Integer.parseInt(cursor.getString(6)));
                medicamento.setDosis(Float.valueOf(cursor.getString(7)));
            }
            cursor.close();
            return medicamento;
        } catch (Exception e) {
            return null;
        } finally {
            dbMedicamentos.close();
        }
    }

    /* Método para actualizar un medicamento. Recibe un objeto de este tipo y lo busca por nombre.
     * Si lo encuentra, lo actualiza. Si no, no. Retorna un booleano para indicar si fue exitoso o no.
     */
    public boolean actualizaMedicamento(Medicamentos.Medicamento medicamento) {
        boolean actualizadoExitosamente = false;
        SQLiteDatabase dbMedicamentos = this.getWritableDatabase();

        try {
            String CONSULTA_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + NOMBRE_MEDICAMENTO_COLUMN + " = " +
                    comillas + medicamento.getNombre() + comillas;
            //SELECT * FROM TABLE_NAME WHERE NOMBRE_MED = "medicamento"
            Cursor cursor = dbMedicamentos.rawQuery(CONSULTA_SQL, null);
            if (cursor.moveToFirst()) {
                String esUrgente = medicamento.getPrioridad() ? "1" : "0";
                CONSULTA_SQL = "UPDATE " + TABLE_NAME +
                        " SET " + NOMBRE_MEDICAMENTO_COLUMN + " = " + comillas + medicamento.getNombre()+ comillas  +","+
                        TIPO_COLUMN + " = " + comillas + String.valueOf(medicamento.getTipo()) + comillas +","+
                        DOSIS_COLUMN + " = " + comillas + String.valueOf(medicamento.getDosis()) + comillas +","+
                        HORAS_COLUMN + " = " + comillas + String.valueOf(medicamento.getHoras()) + comillas +","+
                        ES_URGENTE_COLUMN + " = " + comillas + esUrgente + comillas +","+
                        DURACION_TRATAMIENTO_COLUMN + " = " + comillas + String.valueOf(medicamento.getDurTrat()) + comillas +","+
                        DOSIS_COLUMN + " = " + comillas + String.valueOf(medicamento.getDosis()) + comillas +
                        " WHERE " + NOMBRE_MEDICAMENTO_COLUMN + " = " +  comillas +medicamento.getNombre()+ comillas;
                dbMedicamentos.execSQL(CONSULTA_SQL);
                actualizadoExitosamente = true;
            }
            cursor.close();
        } catch (Exception e) {
            actualizadoExitosamente = false;
        } finally {
            dbMedicamentos.close();
        }
        return actualizadoExitosamente;
    }

    /* Método que elimina un medicamento mediante el nombre. Revisa que exista y en caso afirmativo
     * lo elimina. Regresa un booleano dependiendo de si tuvo exito o no.
     */
    public boolean eliminaMedicamento(String nombreMedicamento) {
        boolean eliminadoExitosamente = false;
        SQLiteDatabase medicamentosBD = this.getWritableDatabase();

        try {
            String CONSULTA_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + NOMBRE_MEDICAMENTO_COLUMN + " = " +
                    comillas + nombreMedicamento + comillas;
            Cursor cursor = medicamentosBD.rawQuery(CONSULTA_SQL, null);
            if (cursor.moveToFirst()) {
                medicamentosBD.delete(TABLE_NAME, NOMBRE_MEDICAMENTO_COLUMN + " = ", new
                        String[]{String.valueOf(nombreMedicamento)});
            }
            cursor.close();
            eliminadoExitosamente = true;
        } catch (Exception e) {
            eliminadoExitosamente = false;
        } finally {
            medicamentosBD.close();
        }
        return eliminadoExitosamente;
    }

    /* Método que retorna todos los medicamentos que se tienen almacenados en la base de datos.
     */
    public ArrayList<Medicamentos.Medicamento> getAll() {
        misMedicamentos = null;
        SQLiteDatabase medicamentosDB = this.getWritableDatabase();

        try {
            String CONSULTA_SQL = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = medicamentosDB.rawQuery(CONSULTA_SQL, null);
            Log.i("REGBD", "Leyendo todos los registros");
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                misMedicamentos = new ArrayList<>();
                do {
                    Medicamentos.Medicamento medicamento = new Medicamentos.Medicamento();
                    medicamento.setNombre(cursor.getString(1));
                    medicamento.setTipo(Integer.parseInt(cursor.getString(2)));
                    medicamento.setDosis(Float.valueOf(cursor.getString(3)));
                    medicamento.setHoras(Integer.parseInt(cursor.getString(4)));
                    medicamento.setPrioridad(cursor.getString(5) == "1" ? true : false);
                    medicamento.setDurTrat(Integer.parseInt(cursor.getString(6)));
                    medicamento.setDosis(Float.valueOf(cursor.getString(7)));
                    misMedicamentos.add(medicamento);
                    Log.i("REGBD", "Leida de un elemento exitosamente");
                } while(cursor.moveToNext());
                Log.i("REGBD", "Todos los elementos leidos exitosamente");
                cursor.close();
            } else {
                misMedicamentos = null;
            }
        } catch (Exception e) {
            misMedicamentos = null;
        } finally {
            medicamentosDB.close();
        }
        return misMedicamentos;
    }

    public long contadorMedicamentos() {
        long contador = 0;
        SQLiteDatabase medicamentosDB = this.getReadableDatabase();
        ;
        try {
            contador = DatabaseUtils.queryNumEntries(medicamentosDB, TABLE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            contador = 0;
        } finally {
            medicamentosDB.close();
        }
        return contador;
    }

}
