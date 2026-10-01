import java.util.ArrayList;
import java.util.Locale;

public class Caso {
    private static final int MAX_UBICACIONES = 5;
    private final String nombre;
    private final String codigo;
    private final String detectiveResponsable;
    private final Ubicacion[] ubicaciones;
    private final ArrayList<Pista> pistas;

    public Caso(String nombre, String codigo, String detectiveResponsable) {
        this.nombre = textoValido(nombre, "El nombre del caso es obligatorio");
        this.codigo = textoValido(codigo, "El codigo del caso es obligatorio");
        this.detectiveResponsable = textoValido(detectiveResponsable, "El detective responsable es obligatorio");
        this.ubicaciones = new Ubicacion[MAX_UBICACIONES];
        this.pistas = new ArrayList<Pista>();
    }

    public void registrarUbicacion(int posicion, Ubicacion ubicacion) {
        validarPosicion(posicion);
        if (ubicacion == null) {
            throw new IllegalArgumentException("La ubicacion no puede ser null");
        }
        if (ubicaciones[posicion] != null) {
            throw new IllegalArgumentException("La posicion seleccionada ya esta ocupada");
        }
        ubicaciones[posicion] = ubicacion;
    }

    public String consultarUbicaciones() {
        StringBuilder resultado = new StringBuilder();
        for (int posicion = 0; posicion < ubicaciones.length; posicion++) {
            if (ubicaciones[posicion] != null) {
                resultado.append("[").append(posicion).append("] ").append(ubicaciones[posicion]).append(System.lineSeparator());
            }
        }
        return resultado.length() == 0 ? "No hay ubicaciones registradas." : resultado.toString();
    }

    public Ubicacion consultarUbicacion(int posicion) {
        validarPosicion(posicion);
        if (ubicaciones[posicion] == null) {
            throw new IllegalArgumentException("La posicion esta vacia");
        }
        return ubicaciones[posicion];
    }

    public void modificarUbicacion(int posicion, int nuevoRiesgo, String nuevoEstado) {
        consultarUbicacion(posicion).actualizar(nuevoRiesgo, nuevoEstado);
    }

    public void descartarUbicacion(int posicion) {
        consultarUbicacion(posicion);
        ubicaciones[posicion] = null;
    }

    public void registrarPista(Pista pista) {
        if (pista == null) {
            throw new IllegalArgumentException("La pista no puede ser null");
        }
        if (buscarPista(pista.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una pista con ese codigo");
        }
        pistas.add(pista);
    }

    public String consultarPistas() {
        if (pistas.isEmpty()) {
            return "No hay pistas registradas.";
        }
        StringBuilder resultado = new StringBuilder();
        for (Pista pista : pistas) {
            resultado.append(pista).append(System.lineSeparator());
        }
        return resultado.toString();
    }

    public Pista buscarPista(String codigoPista) {
        if (codigoPista == null) {
            return null;
        }
        for (Pista pista : pistas) {
            if (pista.getCodigo().equalsIgnoreCase(codigoPista.trim())) {
                return pista;
            }
        }
        return null;
    }

    public void modificarPista(String codigoPista, String descripcion, String tipoEvidencia, int importancia, int confiabilidad) {
        Pista pista = buscarPista(codigoPista);
        if (pista == null) {
            throw new IllegalArgumentException("No existe una pista con ese codigo");
        }
        pista.actualizar(descripcion, tipoEvidencia, importancia, confiabilidad);
    }

    public void eliminarPista(String codigoPista) {
        Pista pista = buscarPista(codigoPista);
        if (pista == null) {
            throw new IllegalArgumentException("No existe una pista con ese codigo");
        }
        pistas.remove(pista);
    }

    public String generarReporte() {
        int ubicacionesRegistradas = 0;
        Ubicacion mayorRiesgo = null;
        for (Ubicacion ubicacion : ubicaciones) {
            if (ubicacion != null) {
                ubicacionesRegistradas++;
                if (mayorRiesgo == null || ubicacion.getNivelRiesgo() > mayorRiesgo.getNivelRiesgo()) {
                    mayorRiesgo = ubicacion;
                }
            }
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("Caso: ").append(nombre).append(" (").append(codigo).append(")").append(System.lineSeparator());
        reporte.append("Detective responsable: ").append(detectiveResponsable).append(System.lineSeparator());
        reporte.append("Ubicaciones registradas: ").append(ubicacionesRegistradas).append(System.lineSeparator());
        reporte.append("Espacios disponibles: ").append(MAX_UBICACIONES - ubicacionesRegistradas).append(System.lineSeparator());
        reporte.append("Ubicacion con mayor riesgo: ").append(mayorRiesgo == null ? "Ninguna" : mayorRiesgo).append(System.lineSeparator());
        reporte.append("Pistas registradas: ").append(pistas.size()).append(System.lineSeparator());

        if (pistas.isEmpty()) {
            reporte.append("No hay pistas para calcular maximos o promedio.");
            return reporte.toString();
        }

        Pista mayorImportancia = pistas.get(0);
        Pista mayorConfiabilidad = pistas.get(0);
        int sumaImportancia = 0;
        for (Pista pista : pistas) {
            sumaImportancia += pista.getNivelImportancia();
            if (pista.getNivelImportancia() > mayorImportancia.getNivelImportancia()) {
                mayorImportancia = pista;
            }
            if (pista.getNivelConfiabilidad() > mayorConfiabilidad.getNivelConfiabilidad()) {
                mayorConfiabilidad = pista;
            }
        }
        double promedio = (double) sumaImportancia / pistas.size();
        reporte.append("Pista con mayor importancia: ").append(mayorImportancia).append(System.lineSeparator());
        reporte.append("Pista con mayor confiabilidad: ").append(mayorConfiabilidad).append(System.lineSeparator());
        reporte.append("Promedio de importancia: ").append(String.format(Locale.US, "%.2f", promedio));
        return reporte.toString();
    }

    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= ubicaciones.length) {
            throw new IllegalArgumentException("La posicion debe estar entre 0 y 4");
        }
    }

    private static String textoValido(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDetectiveResponsable() {
        return detectiveResponsable;
    }

    public Ubicacion[] getUbicaciones() {
        return ubicaciones;
    }

    public ArrayList<Pista> getPistas() {
        return new ArrayList<Pista>(pistas);
    }
}
