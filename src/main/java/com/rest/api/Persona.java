package com.rest.api;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String foto;

    public Persona() {}

    public Persona(int id, String nombre, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}
