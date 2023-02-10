package com.example.firebasead.RecyclerArchivos;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;

public class Archivos implements Serializable {

    public String id;

    public String nombre;
    public String propietario;
    public String extension;
    public String dniCliente;
    public String fechaCreacion;


    public Archivos() {
    }

    public Archivos(String s_nombre, String s_propietario, String s_dni, String s_fecha, String s_extension) {
        this.nombre = s_nombre;
        this.propietario = s_propietario;
        this.extension = s_dni;
        this.dniCliente = s_fecha;
        this.fechaCreacion = s_extension;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
