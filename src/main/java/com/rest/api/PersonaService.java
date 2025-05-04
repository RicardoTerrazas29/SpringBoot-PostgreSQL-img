package com.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public String guardarImagenEnServidor(MultipartFile archivo) throws IOException {
        // Ruta absoluta fuera del proyecto (ej: /imagenes-subidas/)
        String directorio = System.getProperty("user.dir") + "/imagenes-subidas";
        File carpeta = new File(directorio);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        // Guardar imagen
        File destino = new File(carpeta, archivo.getOriginalFilename());
        archivo.transferTo(destino);

        // Retornar nombre del archivo para construir la URL luego
        return archivo.getOriginalFilename();
    }


    public Persona guardarPersonaConFoto(String nombre, MultipartFile archivo) throws IOException {
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setFoto(guardarImagenEnServidor(archivo));
        return personaRepository.save(persona);
    }

    public Persona obtenerPersonaPorId(int id) {
        return personaRepository.findById(id).orElse(null);
    }

    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }

    public Persona actualizarPersona(int id, String nombre, MultipartFile archivo) throws IOException {
        Persona persona = personaRepository.findById(id).orElse(null);
        if (persona != null) {
            persona.setNombre(nombre);
            if (archivo != null && !archivo.isEmpty()) {
                persona.setFoto(guardarImagenEnServidor(archivo));
            }
            return personaRepository.save(persona);
        }
        return null;
    }

    public void eliminarPersona(int id) {
        personaRepository.deleteById(id);
    }
}
