package mx.edu.itl.c16130842.canyouhelpmebeta;

import java.util.HashMap;
import java.util.Map;

public class Medicamentos {
    private boolean creado=false;
    private Medicamentos med;
    private static Map<Integer, Medicamento> medicinas = null;


    //Datos de prueba
    private Medicamentos() {
        medicinas.put(0, new Medicamento(1, "Loperamida", 1, 8, false));
        medicinas.put(1, new Medicamento(2, "Ranitidina", 1, 8, false));
        medicinas.put(2, new Medicamento(3, "Ambroxol", 1, 5, false));
    }

    public static void AnadeMedicamento(int tipo, String nombre, int dosis, int horas, boolean urgente) {
        Medicamento m = new Medicamento(tipo, nombre, dosis, horas, urgente);
        int id=medicinas.size();
        medicinas.put(id, m);
    }

    public static void AnadeMedicamento(Medicamento med) {

    }

    public static Map<Integer, Medicamento> getMedicinas() {
        if(medicinas==null) {
             medicinas = new HashMap<>();
            medicinas.put(0, new Medicamento(1, "Loperamida", 1, 8, false));
            medicinas.put(1, new Medicamento(2, "Ranitidina", 1, 8, false));
            medicinas.put(2, new Medicamento(3, "Ambroxol", 1, 5, false));
        }
        return medicinas;
    }

    public static void ActualizarMedicamento(int pos, String nombre, int dosis, int horas, boolean urgente) {
        if(medicinas.containsKey(pos)) {
            medicinas.get(pos).setNombre(nombre);
            medicinas.get(pos).setDosis(dosis);
            medicinas.get(pos).setHoras(horas);
            medicinas.get(pos).setUrgente(urgente);
        }
    }

    static class Medicamento {
        //1: Tableta/comprimido
        //2: Inyección
        //3: Suspensión
        private int tipo;
        private String nombre;
        private int dosis;
        private String dosisCad;
        private int horas;
        private boolean urgente;

        public Medicamento() {
            tipo=1;
            nombre = "Nombre";
            dosis = 0;
            dosisCad = "Error: objeto vacío";
            horas = 0;
            urgente = false;
        }

        public Medicamento(int tipo, String nombre, int dosis, int horas, boolean urgente) {
            this.tipo=tipo;
            this.nombre=nombre;
            this.dosis=dosis;
            this.horas=horas;
            this.urgente=urgente;
            if(tipo==1) {
                this.dosisCad=""+dosis+" tabletas cada "+horas+" horas";
            } else if(tipo==2) {
                this.dosisCad=""+dosis+" inyeccion cada "+horas+" horas";
            } else {
                this.dosisCad=""+dosis+" toma cada "+horas+" horas";
            }
        }

        public int getTipo() {
            return tipo;
        }

        public void setTipo(int tipo) {
            this.tipo = tipo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getDosis() {
            return dosis;
        }

        public void setDosis(int dosis) {
            this.dosis = dosis;
        }

        public int getHoras() {
            return horas;
        }

        public void setHoras(int horas) {
            this.horas = horas;
        }

        public boolean isUrgente() {
            return urgente;
        }

        public void setUrgente(boolean urgente) {
            this.urgente = urgente;
        }

        public String getDosisCad() {
            return dosisCad;
        }

        public void setDosisCad(String dosisCad) {
            this.dosisCad = dosisCad;
        }
    }
}
