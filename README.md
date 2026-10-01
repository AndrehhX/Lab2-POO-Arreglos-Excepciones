# Laboratorio 2 - Arreglos y excepciones

Sistema de consola desarrollado para el laboratorio de Programación Orientada a Objetos. Modela una agencia de detectives que administra cinco ubicaciones fijas y una cantidad dinámica de pistas.

## Requisitos

- JDK 8 o superior.
- Consola de comandos.

## Compilar

Desde la carpeta del proyecto:

En PowerShell de Windows:

```powershell
New-Item -ItemType Directory -Path build -Force | Out-Null
javac -d build (Get-ChildItem src -Filter *.java | ForEach-Object { $_.FullName })
```

## Ejecutar

```text
java -cp build Main
```

El menú incluye las 13 operaciones de la guía: crear caso, registrar y administrar ubicaciones, registrar y administrar pistas, mostrar el reporte y salir.

## Ejecutar las pruebas

```powershell
javac -d build (Get-ChildItem src -Filter *.java | ForEach-Object { $_.FullName }) (Get-ChildItem tests -Filter *.java | ForEach-Object { $_.FullName })
java -cp build CasoTest
java -cp build MainTest
```

Las pruebas cubren las validaciones de dominio, el arreglo de cinco posiciones, los espacios `null`, las operaciones del `ArrayList`, los cálculos del reporte y la recuperación de entradas numéricas incorrectas.

## Estructura

- `src/Caso.java`: coordina las ubicaciones y pistas del caso.
- `src/Ubicacion.java`: entidad de ubicación con validación del riesgo.
- `src/Pista.java`: entidad de pista con validación de importancia y confiabilidad.
- `src/Main.java`: driver program, menú y manejo de excepciones.
- `tests/`: pruebas ejecutables sin dependencias externas.
- `docs/analisis-diseno-lab2.pdf`: análisis, diseño y diagrama UML.

## Correspondencia con la guía

El arreglo `Ubicacion[]` tiene exactamente cinco posiciones y conserva `null` en los espacios disponibles. Las pistas se almacenan directamente en `ArrayList<Pista>`. Los valores inválidos producen `IllegalArgumentException`, las entradas numéricas incorrectas se manejan mediante `InputMismatchException` y el menú continúa después de errores recuperables. El driver incluye un bloque `finally` para informar el cierre de cada operación.

## Entregables

1. `docs/analisis-diseno-lab2.pdf` para el análisis y diseño.
2. Los archivos `.java` de `src/` para ejecutar el programa.
3. Este README con instrucciones de compilación y ejecución.
