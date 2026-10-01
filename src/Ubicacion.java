public class Ubicacion {
    private final String codigo;
    private final String nombre;
    private final String direccion;
    private int nivelRiesgo;
    private String estado;

    public Ubicacion(String codigo, String nombre, String direccion, int nivelRiesgo, String estado) {
        this.codigo = textoValido(codigo, "El codigo de la ubicacion es obligatorio");
        this.nombre = textoValido(nombre, "El nombre de la ubicacion es obligatorio");
        this.direccion = textoValido(direccion, "La direccion o descripcion es obligatoria");
        this.estado = textoValido(estado, "El estado es obligatorio");
        validarRiesgo(nivelRiesgo);
        this.nivelRiesgo = nivelRiesgo;
    }

    public void actualizar(int nuevoNivelRiesgo, String nuevoEstado) {
        validarRiesgo(nuevoNivelRiesgo);
        this.estado = textoValido(nuevoEstado, "El estado es obligatorio");
        this.nivelRiesgo = nuevoNivelRiesgo;
    }

    private static void validarRiesgo(int nivelRiesgo) {
        if (nivelRiesgo < 1 || nivelRiesgo > 10) {
            throw new IllegalArgumentException("El nivel de riesgo debe estar entre 1 y 10");
        }
    }

    private static String textoValido(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getNivelRiesgo() {
        return nivelRiesgo;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | riesgo %d | %s", codigo, nombre, direccion, nivelRiesgo, estado);
    }
}
