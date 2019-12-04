/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       GESTIÓN DE PROYECTOS DE SOFTWARE
:*
:*                   SEMESTRE: AGO-DIC/2019    HORA: 11-12 HRS
:*
:*                      Clase manejadora de los medicamentos
:*
:*  Archivo     : Medicamentos.java
:*  Autor       : PPS
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Clase que funciona como intermediaria entre la clase manejadora de bases
:*  de datos y la aplicación. Cuando la aplicación está funcionando, almacena los medicamentos
:* en memoria mediante el uso de un Map. 
:* Engloba a la clase Medicamento, que sirve como el modelo en memoria del esquema de la BD.
:*
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  04/012/2019 Iván García Moreno   Comentarios
:*------------------------------------------------------------------------------------------*/
package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Clase encargada de almacenar los medicamentos en memoria y de interactuar
 * con el manejador de BD.
 */
public class Medicamentos {
    // Mapa almacenador de los Medicamentos
    private static Map<Integer, Medicamento> medicinas = null;
    // Bandera para ver si los medicamentos están cargados
    private static boolean medicamentosCargados = false;
    // Referencia al manejador de la BD.
    private static Medicinas_DB_Handler medicinas_db_handler;

    // Carga de los medicamentos mediante una llamada al manejador. Obtiene una
    // lista de los medicamentos y los carga en el Map local.
    private Medicamentos(Context context) {
        medicinas_db_handler = new Medicinas_DB_Handler(context, null, null, 1);
        medicinas = new HashMap<>();
        medicamentosCargados = true;
        ArrayList<Medicamento> medGuardados = medicinas_db_handler.getAll();
        if(medGuardados != null) {
            for (int i = 0; i < medGuardados.size(); i++) {
                medicinas.put(i, medGuardados.get(i));
            }
        }
    }

    //Método para ver si los medicamentos están cargados o no.
    public static void setMedicamentosCargados(boolean estadoMedicamentos) {
        medicamentosCargados = estadoMedicamentos;
    }

    // Cargador de medicamentos
    public static void cargarMedicamentos(Context context) {
        if(!medicamentosCargados) {
            new Medicamentos(context);
        }
    }

    /*
     * Añade un medicamento llamando al método correspondiente. Regresa
     * true o false dependiendo del éxito o fallo en la operación.
     */
    public static boolean AnadeMedicamento(int tipo, String nombre, int dosis, int horas, boolean urgente, int durTrat) {
        Medicamento m = new Medicamento(tipo, nombre, dosis, horas, urgente, durTrat);
        if (medicinas_db_handler.anadeMedicamento(m)) {
            medicinas.put(medicinas.size(), m);
            return true;
        }
        return false;
    }

    /*
     * Añade un medicamento llamando al método correspondiente. Regresa
     * true o false dependiendo del éxito o fallo en la operación.
     */
    public static boolean AnadeMedicamento(Medicamento med) {
        if(medicinas_db_handler.anadeMedicamento(med)) {
            medicinas.put(medicinas.size(), med);
            return true;
        }
        return false;
    }

    // Regresa el Mapa de medicamentos
    public static Map<Integer, Medicamento> getMedicinas() {
        return medicinas;
    }

    // Regresa la cantidad de medicamentos almacenados
    public static long ContadorMedicamentos() {
        if(medicamentosCargados) {
            return medicinas_db_handler.contadorMedicamentos();
        } else {
            return 0;
        }
    }
 
    /*
     * Método encargado de actualizar las entradas en la BD. Llama al 
     * método correspondiente en la base de datos y solo después de que esta
     * actualización haya tenido éxito es que actualiza el mapa local.
     */ 
    public static boolean ActualizarMedicamento(int pos, int tipo, String nombre, int dosis, int horas, boolean urgente, int durTrat) {
        Medicamento med = new Medicamento(tipo, nombre, dosis, horas, urgente, durTrat);
        if(medicinas_db_handler.actualizaMedicamento(med)) {
            if (medicinas.containsKey(pos)) {
                medicinas.get(pos).setNombre(nombre);
                medicinas.get(pos).setDosis(dosis);
                medicinas.get(pos).setHoras(horas);
                medicinas.get(pos).setPrioridad(urgente);
                medicinas.get(pos).setDurTrat(durTrat);
                return true;
            }
        }
        return  false;
    }

    // Clase interna que nos sirve para representar el modelo de los Medicamentos
    static class Medicamento {
        
        // Nombre del medicamento
        private String nombre;
        // 1: Tableta/Comprimido
        // 2: Inyección
        // 3: Suspensión
        private int tipo;
        // Dosis del medicamento
        private float dosis;
        // Cada cuantas horas
        private int horas;
        // Si es urgente, notificará siempre
        private boolean esUrgente;
        // Cadena cuyo único fin es el despliegue de info, no se almacena
        private String dosisCad;
        // Tiempo de toma del tratamiento en días (un -1 indica permanente)
        private int durTrat;

        // Constructor por defecto
        public Medicamento() {
            tipo = 1;
            nombre = "Nombre";
            dosis = 0;
            dosisCad = "Error: objeto vacío";
            horas = 0;
            esUrgente = false;
            durTrat = 10;
        }

        // Constructor del medicamento en caso de que se capturen todos sus campos
        public Medicamento(int tipo, String nombre, int dosis, int horas, boolean urgente, int durTrat) {
            this.tipo = tipo;
            this.nombre = nombre;
            this.dosis = dosis;
            this.horas = horas;
            this.esUrgente = urgente;
            this.durTrat = durTrat;
            if (tipo == 1) {
                this.dosisCad = "" + dosis + " tabletas cada " + horas + " horas";
            } else if (tipo == 2) {
                this.dosisCad = "" + dosis + " inyeccion cada " + horas + " horas";
            } else {
                this.dosisCad = "" + dosis + " toma cada " + horas + " horas";
            }
        }

        // Setter del nombre
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        // Setter del tipo
        public void setTipo(int tipo) {
            this.tipo = tipo;
        }

        // Setter de la dosis
        public void setDosis(float dosis) {
            this.dosis = dosis;
        }

        // Setter de cada cuantas horas se debe tomar
        public void setHoras(int horas) {
            this.horas = horas;
        }

        // Setter de la bandera prioridad
        public void setPrioridad(boolean esUrgente) {
            this.esUrgente = esUrgente;
        }

        // Setter de cuantos días debe durar el tratamiento
        public void setDurTrat(int durTrat) {
            this.durTrat = durTrat;
        }

        // Getter del nombre
        public String getNombre() {
            return nombre;
        }

        // Getter del tipo
        public int getTipo() {
            return tipo;
        }
        
        // Getter de la dosis
        public float getDosis() {
            return dosis;
        }

        // Getter de las horas
        public int getHoras() {
            return horas;
        }

        // Getter de la bandera prioridad
        public boolean getPrioridad() {
            return esUrgente;
        }

        // Generador de la cadena de despliegue para el usuario
        public String getDosisCad() {
            if (tipo == 1) {
                this.dosisCad = "" + dosis + " tabletas cada " + horas + " horas";
            } else if (tipo == 2) {
                this.dosisCad = "" + dosis + " inyeccion cada " + horas + " horas";
            } else {
                this.dosisCad = "" + dosis + " toma cada " + horas + " horas";
            }
            return dosisCad;
        }

        // Getter de la duración del tratamiento
        public int getDurTrat() {
            return durTrat;
        }

    }
}

