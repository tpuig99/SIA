# TP4 - SIA 20202Q - Aprendizaje No Supervisado

El fin de este trabajo es resolver dos ejercicios sobre aprendizajo no supervisado. Para dicho fin utilizamos Java como base para los modelos computacionales, y R para el procesamiento de datos crudos y gráficos.

##### Autores:

* Tamara Puig
* Luciano Boccardi

#### Restricciones de la solución:

La solución es parametizable, cuya configuración se detalla en la próxima sección.

En el primer ejercicio, a partir de un dataset normalizado con R, se calcula en primer lugar mediante una red de Kohonen asociaciones entre países de características similares. En segundo lugar, se aplica un perceptrón utilizando la Regla de Oja con el objetivo de obtener la primer componente principal y analizarla.

En el segundo ejercicio, se realiza un modelo de Hopfield para poder entrenar con una serie de patrones (letras) y poder analizar el comportamiento al reconocer dicha letra con ruido, y también identificar estados espúreos.


### Configuración previa:

La misma se puede realizar dentro de la carpeta Executables.

El primer ejercicio cuenta con un archivo ```config_ej1.json```. En el mismo se pueden parametrizar los valores tanto para la Red de Kohonen como para la Regla de Oja. El valor "initialNeighbourhoodValue" se corresponde solo al número por el cual se modifica la función exponencial negativa correspondiente al entorno, se recomienda ampliamente no colocar valores negativos. Las dimensiones "dim_x" y "dim_y" se corresponden a las del mapa de Kohonen, no necesitan ser iguales.

```JSON
    {
    "eta_a": 0.1,
    "epochs_a": 1000,
    "initialNeighbourhoodValue": 3.0,
    "dim_x": 5,
    "dim_y": 5,

    "eta_b": 0.5,
    "epochs_b": 10000
    }
```

Para el segundo ejercicio, existe un archivo ```config_ej1.json```. El mismo tiene tres atributos, uno debe ser un String de 4 caracteres que indica las letras a almacenar como patrones, el segundo es la letra a utilizar para testeo, y en tercer lugar el nivel correspondiente a la cantidad de ruido correspondiente a la letra, el cual es un valor que se encuentra entre 1 y 3.

```JSON
{
  "letters": "HVCK",
  "testingLetter": "C",
  "testingLevel": 3
}
```


### Corriendo el proyecto:

Requisito previo: Tener al menos una entorno de Java 8. Puede ser tanto JRE como JDK, dependiendo si se desea correr únicamente el .jar sin un IDE. En el caso de desear realizar los gráficos se debe tener instalado un entorno de R. En particular, algún IDE que lo soporte como RStudio o PyCharm con un complemento adecuado. El programa sólo funcionará si se encuentra el archivo ```europe.csv``` tal como lo ofreció la cátedra, y un archivo auxiliar ```normalized_data.csv``` que corresponde a la estandarización de los datos realizada con un programa en R, sin headers y separado por el delimitador ';'. Si alguno de estos dos archivos falta en el directorio, o difiere el formato, el proyecto NO SE PODRÁ CORRER o su funcionamiento no está determinado.

1) Navegar hacia la carpeta Executables. Editar el archivo ```.json``` correspondiente al ejercicio de la manera deseada según los nombres de los parámetros.

2) Abrir en el directorio una terminal (bash - powershell - cmd) y tipear el comando:
    ```sh
    $ java -jar .\TP4-EJ?-1.0.SNAPSHOT.jar //  (Reemplazar "?" por el número del ejercicio).
    ```

3) A continuación se ejecutará la configuración establecida con los parámetros dados para el ejercicio y se brindará el output correspondiente.

4) Solo para el primer ejercicio, una vez ejecutado el mismo en el directorio ```./data/``` se encontrarán los outputs correspondientes al ejercicio, ya preparados para ser procesados por los dos scripts en R ```EJ1_A.R``` y ```EJ1_B.R```. Estos mismos muestran gráficamente cómo funciona el proyecto.

En caso de querer ejecutarlo con un IDE como IntelliJ, se deja el código fuente y el procedimiento para correrlo debe ser configurando adecuadamente para correr el proyecto desde el archivo "Main" dentro de los recursos del proyecto.


### Librerías y Frameworks externos utiizados:

* Maven: Construcción y administración del proyecto sobre Java. Se añadieron plugins para especificar el compilador, la creación correcta del .jar y administración de dependencias.
* JSON.simple (https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple): Para el manejo archivos tipo JSON.
* Apache Commons para el procesamiento de archivos en formato CSV, o tipos de datos requeridos.
