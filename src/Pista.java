public class Pista {
    private final String codigo;
    private String descripcion;
    private String tipoEvidencia;
    private int nivelImportancia;
    private int nivelConfiabilidad;

    public Pista(String codigo, String descripcion, String tipoEvidencia, int nivelImportancia, int nivelConfiabilidad) {
        this.codigo = textoValido(codigo, "El codigo de la pista es obligatorio");
        this.descripcion = textoValido(descripcion, "La descripcion es obligatoria");
        this.tipoEvidencia = textoValido(tipoEvidencia, "El tipo de evidencia es obligatorio");
        actualizar(descripcion, tipoEvidencia, nivelImportancia, nivelConfiabilidad);
    }

    public void actualizar(String nuevaDescripcion, String nuevoTipoEvidencia, int nuevaImportancia, int nuevaConfiabilidad) {
        validarImportancia(nuevaImportancia);
        validarConfiabilidad(nuevaConfiabilidad);
        this.descripcion = textoValido(nuevaDescripcion, "La descripcion es obligatoria");
        this.tipoEvidencia = textoValido(nuevoTipoEvidencia, "El tipo de evidencia es obligatorio");
        this.nivelImportancia = nuevaImportancia;
        this.nivelConfiabilidad = nuevaConfiabilidad;
    }

    private static void validarImportancia(int nivelImportancia) {
        if (nivelImportancia < 1 || nivelImportancia > 10) {
            throw new IllegalArgumentException("El nivel de importancia debe estar entre 1 y 10");
        }
    }

    private static void validarConfiabilidad(int nivelConfiabilidad) {
        if (nivelConfiabilidad < 0 || nivelConfiabilidad > 100) {
            throw new IllegalArgumentException("El nivel de confiabilidad debe estar entre 0 y 100");
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

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public int getNivelImportancia() {
        return nivelImportancia;
    }

    public int getNivelConfiabilidad() {
        return nivelConfiabilidad;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | importancia %d | confiabilidad %d%%", codigo, descripcion, tipoEvidencia, nivelImportancia, nivelConfiabilidad);
    }
}
