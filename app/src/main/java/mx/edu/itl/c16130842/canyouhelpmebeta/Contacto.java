/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       GESTIÓN DE PROYECTOS DE SOFTWARE
:*
:*                   SEMESTRE: AGO-DIC/2019    HORA: 11-12 HRS
:*
:*                       Modelo del Contacto para su posterior almacenamiento
:*
:*  Archivo     : Contacto.java
:*  Autor       : PPS
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Clase "modelo" del esquema de los contactos almacenados
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  04/012/2019 Iván García Moreno   Comentarios
:*------------------------------------------------------------------------------------------*/
package mx.edu.itl.c16130842.canyouhelpmebeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Contacto {
    // Datos que nos interesan del contacto
    private String nombre;
    private String telefono;

    // Constructor de la clase
    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Getter del nombre
    public String getNombre() {
        return nombre;
    }

    // Setter del nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter del telefono
    public String getTelefono() {
        return telefono;
    }

    // Setter del teléfono
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}

