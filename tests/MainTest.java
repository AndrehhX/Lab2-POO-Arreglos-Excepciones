import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class MainTest {
    public static void main(String[] args) {
        String entrada = String.join("\n",
                "1", "Caso de prueba", "CAS-100", "Detective Uno",
                "2", "no-es-numero", "0", "UB-100", "Bodega", "Zona 1", "6", "Activa",
                "2", "5", "UB-101", "Laboratorio", "Zona 2", "4", "Pendiente",
                "13", "");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Main.ejecutar(new ByteArrayInputStream(entrada.getBytes(StandardCharsets.UTF_8)), new PrintStream(buffer));
        String salida = buffer.toString();
        assertContains(salida, "Entrada numerica invalida", "Debe informar el error numerico");
        assertContains(salida, "Ubicacion registrada", "Debe continuar y registrar despues del error");
        assertContains(salida, "La posicion debe estar entre 0 y 4", "Debe manejar una posicion fuera del arreglo");
        assertContains(salida, "Saliendo del sistema", "Debe permitir salir normalmente");
        assertContains(salida, "Operacion finalizada", "Debe ejecutar la accion final del finally");
        System.out.println("MainTest: todas las pruebas pasaron");
    }

    private static void assertContains(String texto, String fragmento, String mensaje) {
        if (!texto.contains(fragmento)) {
            throw new AssertionError(mensaje + " | no aparece: " + fragmento + "\nSalida:\n" + texto);
        }
    }
}
