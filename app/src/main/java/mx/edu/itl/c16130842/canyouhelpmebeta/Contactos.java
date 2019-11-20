package mx.edu.itl.c16130842.canyouhelpmebeta;

import java.util.HashMap;
import java.util.Map;

public class Contactos {

    private static Map<Integer, Contacto> contactos = null;

    private Contactos(Contacto contacto) {
        contactos.put(0, new Contacto("Jessica", "8712113710"));
        contactos.put(1, new Contacto("Monica", "8712363248"));
        contactos.put(2, new Contacto("Ivan", "8713698742"));
        contactos.put(3, new Contacto("Emergencia", "911"));
    }

    public static Map<Integer, Contactos.Contacto> getContactos() {
        if(contactos==null) {
            contactos = new HashMap<>();
            contactos.put(0, new Contactos.Contacto("Jessica", "8712113710"));
            contactos.put(1, new Contactos.Contacto("Monica", "8712363248"));
            contactos.put(2, new Contactos.Contacto("Ivan", "8713297388"));
            contactos.put(3, new Contactos.Contacto("Emergencia", "911"));
        }
        return contactos;
    }

    static class Contacto {
        private String nombre;
        private String telefono;

        public Contacto(String nombre, String telefono) {
            this.nombre = nombre;
            this.telefono = telefono;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
    }

}
