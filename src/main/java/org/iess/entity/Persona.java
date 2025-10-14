package org.iess.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@MappedSuperclass
public abstract class Persona extends PanacheEntityBase {

    @Column(nullable = false)
    private String nombre;

    private String genero;

    private int edad;

    @Column(unique = true, nullable = false)
    private String identificacion;

    private String direccion;

    private String telefono;

    // Getters / Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
