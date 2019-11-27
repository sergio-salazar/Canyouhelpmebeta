package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Medicamentos {
    private static Map<Integer, Medicamento> medicinas = null;
    private static boolean medicamentosCargados = false;
    private static Medicinas_DB_Handler medicinas_db_handler;

    //Carga de los medicamentos
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

    public static void cargarMedicamentos(Context context) {
        if(!medicamentosCargados) {
            new Medicamentos(context);
        }
    }

    public static boolean AnadeMedicamento(int tipo, String nombre, int dosis, int horas, boolean urgente, int durTrat) {
        Medicamento m = new Medicamento(tipo, nombre, dosis, horas, urgente, durTrat);
        if (medicinas_db_handler.anadeMedicamento(m)) {
            medicinas.put(medicinas.size(), m);
            return true;
        }
        return false;
    }

    public static boolean AnadeMedicamento(Medicamento med) {
        if(medicinas_db_handler.anadeMedicamento(med)) {
            medicinas.put(medicinas.size(), med);
            return true;
        }
        return false;
    }

    public static Map<Integer, Medicamento> getMedicinas() {
        return medicinas;
    }

    public static long ContadorMedicamentos() {
        if(medicamentosCargados) {
            return medicinas_db_handler.contadorMedicamentos();
        } else {
            return 0;
        }
    }

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

    static class Medicamento {
        //Nombre del medicamento
        private String nombre;
        //1: Tableta/Comprimido
        //2: Inyección
        //3: Suspensión
        private int tipo;
        //Dosis del medicamento
        private float dosis;
        //Cada cuantas horas
        private int horas;
        //Si es urgente, notificará siempre
        private boolean esUrgente;
        //Cadena cuyo único fin es el despliegue de info
        private String dosisCad;
        //Tiempo de toma del tratamiento en días (un -1 indica permanente)
        private int durTrat;

        public Medicamento() {
            tipo = 1;
            nombre = "Nombre";
            dosis = 0;
            dosisCad = "Error: objeto vacío";
            horas = 0;
            esUrgente = false;
            durTrat = 10;
        }

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

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setTipo(int tipo) {
            this.tipo = tipo;
        }

        public void setDosis(float dosis) {
            this.dosis = dosis;
        }

        public void setHoras(int horas) {
            this.horas = horas;
        }

        public void setPrioridad(boolean esUrgente) {
            this.esUrgente = esUrgente;
        }

        public void setDurTrat(int durTrat) {
            this.durTrat = durTrat;
        }

        public String getNombre() {
            return nombre;
        }

        public int getTipo() {
            return tipo;
        }

        public float getDosis() {
            return dosis;
        }

        public int getHoras() {
            return horas;
        }

        public boolean getPrioridad() {
            return esUrgente;
        }

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

        public int getDurTrat() {
            return durTrat;
        }

    }
}

