package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Medicinas_DB_Handler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
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

    private ArrayList<Medicamentos.Medicamento> misMedicamentos;

    public Medicinas_DB_Handler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /* Método que crea la tabla cuando esto se ejecuta por primera vez. Solo se ejecutará una vez
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREAR_TABLA = "CREATE TABLE "+TABLE_NAME+"("+ID_COLUMN+" INTEGER PRIMARY KEY," +NOMBRE_MEDICAMENTO_COLUMN+
                "TEXT, "+TIPO_COLUMN+" TEXT,"+DOSIS_COLUMN+" TEXT, "+HORAS_COLUMN+" TEXT, "+ES_URGENTE_COLUMN+" TEXT,"+
                DURACION_TRATAMIENTO_COLUMN+" TEXT, "+DOSIS_DIA_COLUMN+" TEXT)";
        sqLiteDatabase.execSQL(CREAR_TABLA);
    }

    //Método para actualizar la tabla, en nuestro caso la elimina y la vuelve a creer.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
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
            valores.put(NOMBRE_MEDICAMENTO_COLUMN, medicamento.getNombre());
            valores.put(TIPO_COLUMN, String.valueOf(medicamento.getTipo()));
            valores.put(DOSIS_COLUMN, String.valueOf(medicamento.getDosis()));
            valores.put(HORAS_COLUMN, String.valueOf(medicamento.getHoras()));
            valores.put(ES_URGENTE_COLUMN, medicamento.getPrioridad() ? "1":"0");
            valores.put(DURACION_TRATAMIENTO_COLUMN, String.valueOf(medicamento.getDurTrat()));
            valores.put(DOSIS_DIA_COLUMN, String.valueOf(medicamento.getDosis()));
            medicinasDB.insert(TABLE_NAME, null, valores);
            anadidoExitosamente = true;
        } catch (Exception e) {
            anadidoExitosamente = false;
        } finally {
            medicinasDB.close();
        }
        return  anadidoExitosamente;
    }

    /* Método de consulta de un medicamento, recibe el nombre. Hace la consulta con base en el nombre
     * crea el objeto medicamento y lo regresa en caso de que lo haya encontrado. En caso de que no lo
     * encuentre, retorna null. El método maneja todas las conversiones necesarias.
     */
    public Medicamentos.Medicamento getMedicamento(String nombreMedicamento) {
        String CONSULTA_SQL = "SELECT*FROM " + TABLE_NAME + " WHERE" + NOMBRE_MEDICAMENTO_COLUMN + " = "
                + nombreMedicamento;
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
            String CONSULTA_SQL = "SELECT * FROM "+TABLE_NAME+" WHERE "+NOMBRE_MEDICAMENTO_COLUMN+" = "+
                    medicamento.getNombre();
            Cursor cursor = dbMedicamentos.rawQuery(CONSULTA_SQL, null);
            if(cursor.moveToFirst()) {
                String esUrgente = medicamento.getPrioridad()?"1":"0";
                CONSULTA_SQL = "UPDATE " + TABLE_NAME +
                        "SET " + NOMBRE_MEDICAMENTO_COLUMN +"="+ medicamento.getNombre() +
                        TIPO_COLUMN +"="+ String.valueOf(medicamento.getTipo())+
                        DOSIS_COLUMN +"="+ String.valueOf(medicamento.getDosis())+
                        HORAS_COLUMN +"="+ String.valueOf(medicamento.getHoras())+
                        ES_URGENTE_COLUMN +"="+ esUrgente +
                        DURACION_TRATAMIENTO_COLUMN +"="+ String.valueOf(medicamento.getDurTrat())+
                        DOSIS_COLUMN +"="+ String.valueOf(medicamento.getDosis())+
                        "WHERE "+NOMBRE_MEDICAMENTO_COLUMN+"="+medicamento.getNombre();
                dbMedicamentos.execSQL(CONSULTA_SQL);
                actualizadoExitosamente = true;
            }

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
            String CONSULTA_SQL = "SELECT * FROM "+TABLE_NAME+" WHERE "+NOMBRE_MEDICAMENTO_COLUMN+" = "+
                    nombreMedicamento;
            Cursor cursor = medicamentosBD.rawQuery(CONSULTA_SQL, null);
            if(cursor.moveToFirst()) {
                medicamentosBD.delete(TABLE_NAME, NOMBRE_MEDICAMENTO_COLUMN+" = ", new
                        String[]{String.valueOf(nombreMedicamento)});
            }
            cursor.close();
            eliminadoExitosamente=true;
        } catch (Exception e) {
            eliminadoExitosamente = false;
        } finally {
            medicamentosBD.close();
        }
        return  eliminadoExitosamente;
    }

    /* Método que retorna todos los medicamentos que se tienen almacenados en la base de datos.
     */
    public ArrayList<Medicamentos.Medicamento> getAll() {
        misMedicamentos = null;
        SQLiteDatabase medicamentosDB = this.getWritableDatabase();

        try {
            String CONSULTA_SQL = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = medicamentosDB.rawQuery(CONSULTA_SQL, null);
            if(cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {
                    misMedicamentos = new ArrayList<>();
                    String nombreMedicamento = cursor.getString(1);
                    Medicamentos.Medicamento med = getMedicamento(nombreMedicamento);
                    misMedicamentos.add(med);
                }
                cursor.close();
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
        SQLiteDatabase medicamentosDB = this.getReadableDatabase();;
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
