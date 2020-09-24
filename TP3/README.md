# TP3 - SIA 20202Q - Perceptrones

El fin de este trabajo es resolver una serie de Ejercicios propuestos sobre perceptrones en Java.

##### Autores:

* Tamara Puig
* Luciano Boccardi

#### Restricciones de la solución:

La solución es parametizable, cuya configuración se detalla en la próxima sección.

En el primer ejercicio, se detalla la comparación de un Perceptrón de Función Escalón para resolver los problemas de AND y XOR. Se analiza la cantida de fallos a lo largo de una serie de iteraciones.

En el segundo ejercicio, se compara la capacidad de un Peceptrón Lineal contra uno No Lineal para aproximar una función a raíz de un conjunto de datos y otro de resultados esperados. Se detalla el error cuadrático medio obtenido en cada algoritmo.

En el tercer ejercicio, en ambas partes se utilizan perceptrones multicapas, en primer lugar para resolver el problema del primer ejericicio. Y, en el segundo caso, para analizar el comportamiento de la red para identificar números pares a raíz de una serie de píxeles.

### Configuración previa:

Cada uno de los ejericico cuenta con un archivo ```config.json```. En el mismo se pueden parametrizar los valores iniciales para cada problema. Al no ser demasiados ni tan variantes entre sí, decidimos no detallarlos en este espacio.

Destacamos que tanto en el Ejercicio 3, hay un atributo en el archivo llamado:

```JSON
  "neuronsByLayer": [3, 3]
```

Que funciona de la siguiente manera. Cada elemento del array representa la cantidad de perceptrones en la capa correspondiente. En el caso del ejemplo, la primera capa oculta tendrá 3 perceptrones, y la segunda, tendrá otros 3.

El archivo de configuración del Ejercicio 3 posee arriba los datos del primer inciso, y abajo, los del segundo.

### Corriendo el proyecto:

Requisito previo: Tener al menos una entorno de Java 8. Puede ser tanto JRE como JDK, dependiendo si se desea correr únicamente el .jar sin un IDE.

1) Navegar hacia la carpeta Executable, y entrar al ejercicio deseado. Editar el archivo ```config.json``` correspondiente al ejercicio de la manera deseada según los nombres de los parámetros.

2) Abrir en el directorio una terminal (bash - powershell - cmd) y tipear el comando:
    ```sh
    $ java -jar .\TP?-1.0.jar //  (Reemplazar "?" por el número del ejercicio).
    ```

3) A continuación se ejecutará la configuración establecida con los parámetros dados para el ejercicio y se brindará el output correspondiente.

En caso de querer ejecutarlo con un IDE como IntelliJ, se deja el código fuente y el procedimiento para correrlo debe ser configurando adecuadamente para correr el proyecto desde el archivo "Main" dentro de los recursos del proyecto.

### Librerías y Frameworks externos utiizados:

* Maven: Construcción y administración del proyecto sobre Java. Se añadieron plugins para especificar el compilador, la creación correcta del .jar y administración de dependencias.
* JSON.simple (https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple): Para el manejo archivos tipo JSON.
