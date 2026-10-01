public class CasoTest {
    public static void main(String[] args) {
        pruebaUbicacionesYPosiciones();
        pruebaPistasYDuplicados();
        pruebaReporteVacioYSinVacio();
        System.out.println("CasoTest: todas las pruebas pasaron");
    }

    private static void pruebaUbicacionesYPosiciones() {
        Caso caso = new Caso("La llave perdida", "CAS-001", "Ana Perez");
        assertEquals(5, caso.getUbicaciones().length, "El arreglo debe tener cinco posiciones");
        assertNull(caso.getUbicaciones()[0], "Una posicion nueva debe iniciar en null");

        Ubicacion ubicacion = new Ubicacion("UB-01", "Bodega", "Zona 1", 4, "Pendiente");
        caso.registrarUbicacion(0, ubicacion);
        assertSame(ubicacion, caso.getUbicaciones()[0], "La ubicacion debe ocupar la posicion indicada");
        assertThrows(() -> caso.registrarUbicacion(0, ubicacion), "No se debe sobrescribir una posicion ocupada");
        assertThrows(() -> caso.registrarUbicacion(5, ubicacion), "Una posicion fuera del arreglo debe rechazarse");

        caso.modificarUbicacion(0, 9, "Vigilada");
        assertEquals(9, caso.getUbicaciones()[0].getNivelRiesgo(), "Debe actualizar el riesgo");
        assertEquals("Vigilada", caso.getUbicaciones()[0].getEstado(), "Debe actualizar el estado");
        caso.descartarUbicacion(0);
        assertNull(caso.getUbicaciones()[0], "Descartar debe dejar la posicion en null");
    }

    private static void pruebaPistasYDuplicados() {
        Caso caso = new Caso("La llave perdida", "CAS-001", "Ana Perez");
        Pista pista = new Pista("PI-01", "Huella en la puerta", "Fisica", 8, 90);
        caso.registrarPista(pista);
        assertSame(pista, caso.buscarPista("PI-01"), "La busqueda debe recorrer el ArrayList");
        assertThrows(() -> caso.registrarPista(new Pista("PI-01", "Otra", "Digital", 5, 50)), "No se permiten codigos repetidos");
        caso.modificarPista("PI-01", "Huella ampliada", "Fisica", 10, 95);
        assertEquals(10, caso.buscarPista("PI-01").getNivelImportancia(), "Debe modificar la pista localizada");
        caso.eliminarPista("PI-01");
        assertNull(caso.buscarPista("PI-01"), "Eliminar debe quitar la pista del ArrayList");
        assertThrows(() -> new Pista("PI-02", "Invalida", "Otra", 11, 50), "La importancia debe estar entre 1 y 10");
        assertThrows(() -> new Pista("PI-03", "Invalida", "Otra", 5, 101), "La confiabilidad debe estar entre 0 y 100");
    }

    private static void pruebaReporteVacioYSinVacio() {
        Caso caso = new Caso("Reporte", "CAS-002", "Luis Gomez");
        String vacio = caso.generarReporte();
        assertContains(vacio, "Pistas registradas: 0", "El reporte vacio debe indicar cero pistas");
        caso.registrarUbicacion(2, new Ubicacion("UB-02", "Parque", "Zona 10", 7, "Activa"));
        caso.registrarPista(new Pista("PI-02", "Camara", "Digital", 6, 80));
        caso.registrarPista(new Pista("PI-03", "Nota", "Documental", 9, 70));
        String reporte = caso.generarReporte();
        assertContains(reporte, "Ubicaciones registradas: 1", "El reporte debe contar ubicaciones");
        assertContains(reporte, "Pistas registradas: 2", "El reporte debe contar pistas");
        assertContains(reporte, "Promedio de importancia: 7.50", "El reporte debe calcular el promedio");
    }

    private static void assertThrows(Runnable accion, String mensaje) {
        try {
            accion.run();
            throw new AssertionError(mensaje);
        } catch (IllegalArgumentException esperado) {
            // La validacion esperada ocurrio.
        }
    }

    private static void assertEquals(Object esperado, Object actual, String mensaje) {
        if (esperado == null ? actual != null : !esperado.equals(actual)) {
            throw new AssertionError(mensaje + " | esperado=" + esperado + ", actual=" + actual);
        }
    }

    private static void assertNull(Object valor, String mensaje) {
        if (valor != null) {
            throw new AssertionError(mensaje);
        }
    }

    private static void assertSame(Object esperado, Object actual, String mensaje) {
        if (esperado != actual) {
            throw new AssertionError(mensaje);
        }
    }

    private static void assertContains(String texto, String fragmento, String mensaje) {
        if (!texto.contains(fragmento)) {
            throw new AssertionError(mensaje + " | no aparece: " + fragmento);
        }
    }
}
