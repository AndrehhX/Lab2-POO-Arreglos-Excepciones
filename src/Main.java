import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ejecutar(System.in, System.out);
    }

    public static void ejecutar(InputStream entrada, PrintStream salida) {
        Scanner scanner = new Scanner(entrada);
        Caso caso = new Caso("Caso inicial", "SIN-CODIGO", "Sin detective");
        boolean continuar = true;

        while (continuar && scanner.hasNextLine()) {
            mostrarMenu(salida, caso);
            int opcion = leerEntero(scanner, salida, "Seleccione una opcion: ");
            if (opcion == 13) {
                salida.println("Saliendo del sistema.");
                break;
            }
            try {
                caso = ejecutarOpcion(opcion, caso, scanner, salida);
            } catch (IllegalArgumentException excepcion) {
                salida.println("Error: " + excepcion.getMessage());
            } finally {
                salida.println("Operacion finalizada.");
            }
            continuar = scanner.hasNextLine();
        }
    }

    private static Caso ejecutarOpcion(int opcion, Caso caso, Scanner scanner, PrintStream salida) {
        switch (opcion) {
            case 1:
                salida.println("Nombre del caso: ");
                String nombre = leerTexto(scanner);
                salida.println("Codigo del caso: ");
                String codigo = leerTexto(scanner);
                salida.println("Detective responsable: ");
                String detective = leerTexto(scanner);
                Caso nuevoCaso = new Caso(nombre, codigo, detective);
                salida.println("Nuevo caso creado.");
                return nuevoCaso;
            case 2:
                int posicionRegistro = leerEntero(scanner, salida, "Posicion (0-4): ");
                salida.println("Codigo de ubicacion: ");
                String codigoUbicacion = leerTexto(scanner);
                salida.println("Nombre de ubicacion: ");
                String nombreUbicacion = leerTexto(scanner);
                salida.println("Direccion o descripcion: ");
                String direccion = leerTexto(scanner);
                int riesgo = leerEntero(scanner, salida, "Nivel de riesgo (1-10): ");
                salida.println("Estado: ");
                String estado = leerTexto(scanner);
                caso.registrarUbicacion(posicionRegistro, new Ubicacion(codigoUbicacion, nombreUbicacion, direccion, riesgo, estado));
                salida.println("Ubicacion registrada.");
                return caso;
            case 3:
                salida.print(caso.consultarUbicaciones());
                return caso;
            case 4:
                salida.println(caso.consultarUbicacion(leerEntero(scanner, salida, "Posicion (0-4): ")));
                return caso;
            case 5:
                int posicionModificar = leerEntero(scanner, salida, "Posicion (0-4): ");
                int nuevoRiesgo = leerEntero(scanner, salida, "Nuevo nivel de riesgo (1-10): ");
                salida.println("Nuevo estado: ");
                caso.modificarUbicacion(posicionModificar, nuevoRiesgo, leerTexto(scanner));
                salida.println("Ubicacion modificada.");
                return caso;
            case 6:
                caso.descartarUbicacion(leerEntero(scanner, salida, "Posicion (0-4): "));
                salida.println("Ubicacion descartada.");
                return caso;
            case 7:
                salida.println("Codigo de pista: ");
                String codigoPista = leerTexto(scanner);
                salida.println("Descripcion: ");
                String descripcion = leerTexto(scanner);
                salida.println("Tipo de evidencia: ");
                String tipo = leerTexto(scanner);
                int importancia = leerEntero(scanner, salida, "Nivel de importancia (1-10): ");
                int confiabilidad = leerEntero(scanner, salida, "Nivel de confiabilidad (0-100): ");
                caso.registrarPista(new Pista(codigoPista, descripcion, tipo, importancia, confiabilidad));
                salida.println("Pista registrada.");
                return caso;
            case 8:
                salida.print(caso.consultarPistas());
                return caso;
            case 9:
                salida.println("Codigo de pista: ");
                Pista encontrada = caso.buscarPista(leerTexto(scanner));
                salida.println(encontrada == null ? "No se encontro la pista." : encontrada);
                return caso;
            case 10:
                salida.println("Codigo de pista: ");
                String codigoModificar = leerTexto(scanner);
                salida.println("Nueva descripcion: ");
                String nuevaDescripcion = leerTexto(scanner);
                salida.println("Nuevo tipo de evidencia: ");
                String nuevoTipo = leerTexto(scanner);
                int nuevaImportancia = leerEntero(scanner, salida, "Nuevo nivel de importancia (1-10): ");
                int nuevaConfiabilidad = leerEntero(scanner, salida, "Nuevo nivel de confiabilidad (0-100): ");
                caso.modificarPista(codigoModificar, nuevaDescripcion, nuevoTipo, nuevaImportancia, nuevaConfiabilidad);
                salida.println("Pista modificada.");
                return caso;
            case 11:
                salida.println("Codigo de pista: ");
                caso.eliminarPista(leerTexto(scanner));
                salida.println("Pista eliminada.");
                return caso;
            case 12:
                salida.println(caso.generarReporte());
                return caso;
            default:
                throw new IllegalArgumentException("La opcion debe estar entre 1 y 13");
        }
    }

    private static int leerEntero(Scanner scanner, PrintStream salida, String mensaje) {
        while (true) {
            salida.print(mensaje);
            if (!scanner.hasNext()) {
                return 13;
            }
            try {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException excepcion) {
                salida.println("Entrada numerica invalida. Intente de nuevo.");
                scanner.nextLine();
            }
        }
    }

    private static String leerTexto(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return "";
        }
        return scanner.nextLine().trim();
    }

    private static void mostrarMenu(PrintStream salida, Caso caso) {
        salida.println();
        salida.println("=== AGENCIA DE DETECTIVES ===");
        salida.println("Caso actual: " + caso.getNombre());
        salida.println("1. Nuevo caso");
        salida.println("2. Registrar ubicacion");
        salida.println("3. Consultar ubicaciones");
        salida.println("4. Consultar una ubicacion");
        salida.println("5. Modificar ubicacion");
        salida.println("6. Descartar ubicacion");
        salida.println("7. Registrar pista");
        salida.println("8. Consultar pistas");
        salida.println("9. Buscar pista");
        salida.println("10. Modificar pista");
        salida.println("11. Eliminar pista");
        salida.println("12. Mostrar reporte de investigacion");
        salida.println("13. Salir");
    }
}
