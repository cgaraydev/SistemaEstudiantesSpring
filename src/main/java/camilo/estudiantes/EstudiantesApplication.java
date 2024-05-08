package camilo.estudiantes;

import camilo.estudiantes.modelo.Estudiante;
import camilo.estudiantes.servicio.EstudianteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class EstudiantesApplication implements CommandLineRunner {

    @Autowired
    private EstudianteServicio estudianteServicio;

    private static final Logger logger = LoggerFactory.getLogger(EstudiantesApplication.class);

    String newLine = System.lineSeparator();

    public static void main(String[] args) {
        logger.info("Iniciando la aplicacion.. ");
        SpringApplication.run(EstudiantesApplication.class, args);
        logger.info("Aplicacion finalizada.");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("{}Ejecutando metodo run de Spring {}", newLine, newLine);
        var salir = false;
        var sc = new Scanner(System.in);
        while (!salir) {
            mostrarMenu();
            salir = ejecutarOpciones(sc);
            logger.info(newLine);
        }
    }

    private void mostrarMenu() {
        logger.info(newLine);
        logger.info("""
                *** SISTEMA DE ESTUDIANTES ***
                1. Listar estudiantes
                2. Buscar estudiante
                3. Agregar estudiante
                4. Modificar estudiante
                5. Eliminar estudiante
                6. Salir
                Elige una opcion:""");
    }

    private boolean ejecutarOpciones(Scanner sc) {
        var opcion = Integer.parseInt(sc.nextLine());
        var salir = false;
        switch (opcion) {
            case 1 -> {
                logger.info(newLine + "Listando estudiantes: " + newLine);
                List<Estudiante> estudiantes = estudianteServicio.listarEstudiantes();
                estudiantes.forEach((estudiante) -> logger.info(estudiante.toString() + newLine));
            }
            case 2 -> {
                logger.info("Introduce el id del estudiante: ");
                var idEstudiante = Integer.parseInt(sc.nextLine());
                Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
                if (estudiante != null) {
                    logger.info(estudiante.toString() + newLine);
                } else {
                    logger.info("Estudiante no encontrado");
                }
            }
            case 3 -> {
                logger.info("Agregar estudiante: {}", newLine);
                logger.info("Introduce el nombre del estudiante: ");
                var nombre = sc.nextLine();
                logger.info("Introduce el apellido del estudiante: ");
                var apellido = sc.nextLine();
                logger.info("Introduce el telefono del estudiante: ");
                var telefono = sc.nextLine();
                logger.info("Introduce el email del estudiante: ");
                var email = sc.nextLine();
                var estudiante = new Estudiante();
                estudiante.setNombre(nombre);
                estudiante.setApellido(apellido);
                estudiante.setTelefono(telefono);
                estudiante.setEmail(email);
                estudianteServicio.guardarEstudiante(estudiante);
                logger.info("Estudiante guardado" + newLine);
            }
            case 4 -> {
                logger.info("Modificar estudiante: {}", newLine);
                logger.info("Id estudiante: ");
                var idEstudiante = Integer.parseInt(sc.nextLine());
                var estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
                if (estudiante != null) {
                    logger.info("Introduce el nombre del estudiante: ");
                    var nombre = sc.nextLine();
                    logger.info("Introduce el apellido del estudiante: ");
                    var apellido = sc.nextLine();
                    logger.info("Introduce el telefono del estudiante: ");
                    var telefono = sc.nextLine();
                    logger.info("Introduce el email del estudiante: ");
                    var email = sc.nextLine();
                    estudiante.setNombre(nombre);
                    estudiante.setApellido(apellido);
                    estudiante.setTelefono(telefono);
                    estudiante.setEmail(email);
                    estudianteServicio.guardarEstudiante(estudiante);
                    logger.info("Estudiante modificado" + newLine);
                } else {
                    logger.info("Estudiante no encontrado");
                }
            }
            case 5 -> {
                logger.info("Eliminar estudiante: {}", newLine);
                logger.info("Id estudiante a eliminar: ");
                var idEstudiante = Integer.parseInt(sc.nextLine());
                var estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
                if (estudiante != null) {
                    estudianteServicio.eliminarEstudiante(estudiante);
                    logger.info("Estudiante eliminado" + newLine);
                } else logger.info("Estudiante no encontrado");
            }
            case 6 -> {
                logger.info("Saliendo...");
                salir = true;
            }
            default -> logger.info("Opcion no valida");
        }
        return salir;
    }
}
