package com.example.firebasead.RecyclerArchivos;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;

public class Archivos implements Serializable {

    public String id;
    public String idCarpeta;
    public String nombre;
    public String propietario;
    public String extension;
    public String dniCliente;
    public String fechaCreacion;


    public Archivos(String id, String idCarpeta, String nombre, String propietario, String extension, String dniCliente, String fechaCreacion) {
        this.id = id;
        this.idCarpeta = idCarpeta;
        this.nombre = nombre;
        this.propietario = propietario;
        this.extension = extension;
        this.dniCliente = dniCliente;
        this.fechaCreacion = fechaCreacion;
    }

    public Archivos() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCarpeta() {
        return idCarpeta;
    }

    public void setIdCarpeta(String idCarpeta) {
        this.idCarpeta = idCarpeta;
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
