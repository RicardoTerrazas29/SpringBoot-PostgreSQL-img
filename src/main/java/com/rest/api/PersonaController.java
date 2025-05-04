package com.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/persona")
@CrossOrigin(origins = "*") // Permite peticiones desde el frontend
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    // Ruta para servir imágenes
    @GetMapping("/images/{filename}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String filename) {
        Path path = Paths.get("imagenes-subidas", filename);
        File file = path.toFile();

        if (file.exists()) {
            return ResponseEntity.ok(new FileSystemResource(file));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Guardar imagen en el servidor
    public String guardarImagenEnServidor(MultipartFile archivo) throws IOException {
        // Ruta fuera del .jar para guardar las imágenes
        String directorio = System.getProperty("user.dir") + "/imagenes-subidas";
        File carpeta = new File(directorio);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        // Guardar la imagen en esa carpeta
        File destino = new File(carpeta, archivo.getOriginalFilename());
        archivo.transferTo(destino);

        return archivo.getOriginalFilename();  // Retorna solo el nombre del archivo
    }

    // Guardar persona con foto
    @PostMapping("/guardar")
    public Persona guardarPersona(@RequestParam String nombre, @RequestParam MultipartFile archivo) throws IOException {
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setFoto(guardarImagenEnServidor(archivo)); // Guardamos la imagen
        return personaRepository.save(persona);
    }

    // Obtener persona por ID
    @GetMapping("/{id}")
    public Persona obtenerPersona(@PathVariable int id) {
        return personaRepository.findById(id).orElse(null);
    }

    // Listar todas las personas
    @GetMapping("/listar")
    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }

    // Actualizar persona
    @PutMapping("/actualizar/{id}")
    public Persona actualizarPersona(
        @PathVariable int id,
        @RequestParam String nombre,
        @RequestParam(required = false) MultipartFile archivo) throws IOException {
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

    // Eliminar persona
    @DeleteMapping("/eliminar/{id}")
    public void eliminarPersona(@PathVariable int id) {
        personaRepository.deleteById(id);
    }
}

