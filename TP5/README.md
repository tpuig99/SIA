# TP5 - SIA 20202Q - Autoencoders

El fin de este trabajo es resolver dos ejercicios sobre autoencoders. Para dicho fin utilizamos Java como base para los modelos computacionales, y R para el procesamiento gráficos.

##### Autores:

* Tamara Puig
* Luciano Boccardi

#### Restricciones de la solución:

En el primer ejercicio se utiliz aun autoencoder para lograr aprender las representaciones de un conjunto de caracteres expresados en un plano bidimensional de 7x5. Luego, se entrena con un conjunto ruidoso con un conjunto de salida esperado no ruidoso para entrenar al autoencoder en capacidad de eliminar ruido.

En el segundo ejercicio se generan nuevas representaciones dada la capacidad generativa del autoencoder, teniendo en cuenta el plano bidimensional donde se encuentran las letras en el espacio latente.

### Corriendo el proyecto:

Requisito previo: Tener al menos una entorno de Java 8. Puede ser tanto JRE como JDK, dependiendo si se desea correr únicamente el .jar sin un IDE. En el caso de desear realizar los gráficos se debe tener instalado un entorno de R. En particular, algún IDE que lo soporte como RStudio o PyCharm con un complemento adecuado.

1) Navegar hacia la carpeta Executables.

2) Abrir en el directorio una terminal (bash - powershell - cmd) y tipear el comando:
    ```sh
    $ java -jar .\Autoencoder-1.0.jar
    ```

3) A continuación se ejecutará la configuración establecida y se brindará el output correspondiente.

4) Para los ejercicios 1A y 2, se genera un output en .csv que puede ser utilizado por el programa en R provisto para poder observar los plottings.


En caso de querer ejecutarlo con un IDE como IntelliJ, se deja el código fuente y el procedimiento para correrlo debe ser configurando adecuadamente para correr el proyecto desde el archivo "Main" dentro de los recursos del proyecto.


### Librerías y Frameworks externos utiizados:

* Maven: Construcción y administración del proyecto sobre Java. Se añadieron plugins para especificar el compilador, la creación correcta del .jar y administración de dependencias.
* Apache Commons para el procesamiento de archivos en formato CSV, o tipos de datos requeridos.
