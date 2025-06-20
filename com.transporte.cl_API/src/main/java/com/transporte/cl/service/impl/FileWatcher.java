package com.transporte.cl.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileWatcher {

    public void actualizarRecursosEstaticosCompilados() {
        try {
            // Ejecutar el comando "mvn process-resources" en la raíz del proyecto
            Process process = Runtime.getRuntime().exec("mvn process-resources");

            // Esperar a que el proceso termine
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Recompilación exitosa.");
            } else {
                System.out.println("Error al recompilar.");
            }
        } catch (IOException | InterruptedException ex) {
            System.err.println("Error al ejecutar el comando de recompilación: " + ex.getMessage());
        }
    }

}


