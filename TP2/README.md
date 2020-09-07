# TP2 - SIA 20202Q - Algoritmos Genéticos para un Juego de Rol

El fin de este trabajo es, mediante algoritmos genéticos, conseguir maximizar el desempaño de una población de sujetos que varían su desempeño en función de equipamiento y atibutos, desarrollado en Java.

##### Autores:

* Tamara Puig
* Luciano Boccardi

#### Restricciones de la solución:

La solución es completamente parametizable, cuya configuración se detalla en la próxima sección. Sin embargo, hay algunas restricciones a tener en cuenta.

En primer lugar, en el caso de los cuatro criterios de selección pedidos, si en una misma etapa (Selección de Padres o Selección de la próxima generación), se desea usar el mismo criterio de selección, los parámetros del mismo en caso de ser parametizable, no varían. Esto ocurre simplemente por una simplificación del código y no supone una alteración grande en cuanto a los resultados.

En segundo lugar, mediante el output de la consola donde se ejecuta, se dan detalles sobre cada una de las generaciones. Esto se complementa con un gráfico en tiempo real que muestra el mínimo, el máximo y el fitness promedio de cada generación. Cuando se alcanza el criterio de corte adecuado, termina la visualización gráfica y se muestran datos sobre el mejor y el peor sujeto, además de las condiciones finales del problema.

### Configuración previa:

Valores que pueden tomar los parámetros:

Selection: {"boltzmann", "dTournament", "pTournament", "universal", "elite", "roulette", "ranking"}
Crossover: {"uniform", "single", "double", "anular"}
Mutation: {"limited", "uniform", "complete", "gen"}
FinishCriteria: {"time", "solution", "generations", "content", "structure"}

El programa cuenta con un archivo ``` config.json ```. El mismo tiene el siguiente formato: (Es Case Sensitive).

```sh
{
    "role": "archer", -> Puede tomar los valores "archer", "warrior", "defender" e "infiltrate"
    "population": 3000, -> Tamaño de la población inicial
    "parentSize": 1800, -> Tamaño de la selección de padres
    "path": "../dataset",	-> Path de la carpeta contenedora de los .tsv, SI ES RELATIVO DEBE SER CON RESPECTO AL DIRECTORIO "Executable"
    "selectionSize": 1200 -> Tamaño de la selección de la nueva generación

    "chosenParentSelection": { -> Campo para los métodos selección de padres
      "parent1": "universal", -> Criterio de selección 1. Toma los valores de Selection
      "parent2": "universal", -> Criterio de selección 2
      "a": 0.5, -> Probabilidad "a" entre [0,1]
    },
    "chosenCrossover": {
      "name": "uniform" -> Criterio de cruce. Toma los valores de Crossover
    },
    "chosenMutation": {
      "name": "complete", -> Criterio de mutación. Toma los valores de Mutation
      "p_m": 0.6, -> Probabilidad de mutar.
    },
    "chosenSubjectSelection": {
      "subject1": "boltzmann", -> Criterio de reemplazo 1. Toma los valores de Selection
      "subject2": "boltzmann", -> Criterio de reemplazo 2.
      "b": 0.5, -> probabilidad "b" entre [0,1]
      "fill": "fillAll" -> Criterio de Implementación, puede tomar los valores "fillAll" o "fillParent"
    },
    "chosenFinishCriteria": {
      "name": "generations", Criterio de Corte, puede tomar los valores de FinishCriteria
    },

    "parentSelection": { -> Parámetros para parentSelection, solo modificar los que se contemplen en "chosenParentSelection"
      "boltzmann": {
          "t0": 10, -> Parámetro 1
          "tc": 2 -> Parámetro 2
      },
      "pTournament": {
          "threshold": 0.6, -> Probabilidad (0.5, 1)
      },
      "dTournament": {
          "threshold": 60, -> Tamaño de los torneos
      },
    },

    "subjectSelection": { -> Parámetros de subjectSelection, mismo criterio que parentSelection
      "boltzmann": {
          "t0": 10,
          "tc": 2
      },
      "pTournament": {
          "threshold": 0.5,
      },
      "dTournament": {
          "threshold": 200,
      },
    },

    "finishCriteria": { -> Parámetros de finishCriteria
      "time": {
          "seconds": 30, -> Límite en segundos del algoritmo
      },
      "content": {
          "generationsLimit": 30, -> Límite de generaciones
          "epsilon": 0.01 -> Valor épsilon que determina si el incremento con respecto al máximo fue significativo
      },
      "structure": {
          "generationsLimit": 10, -> Límite de generaciones
          "percentage": 0.5 -> Valor por encima del que deben estar con respecto al promedio
      },
      "generations": {
          "generationsLimit": 20 -> Generaciones hasta cortar
      },
      "solution": {
          "fitness": 20.0 -> Mínimo fitness aceptable
      },
    },
}

### Corriendo el proyecto:

Requisito previo: Tener al menos una entorno de Java 8. Puede ser tanto JRE como JDK, dependiendo si se desea correr únicamente el .jar sin un IDE.
1) Editar el archivo ```config.json``` de la manera deseada siguiendo los lineamientos anteriormente mencionados. Es importante declarar el "path" del archivo donde se encuentran los ```.tsv```. De no declararlo de manera correcta, el programa lanzará una Excepción.
2) Navegar hacia la carpeta Executable donde se encuentra el ```TP2-1.0-SNAPSHOT.jar```.
3) Abrir en el directorio una terminal (bash - powershell - cmd) y tipear el comando:
    ```sh
    $ java -jar .\TP1-2.0-SNAPSHOT.jar
    ```
4) A continuación se ejecutará la configuración establecida con los parámetros dados. Demora unos segundos hasta leer y cargar todos los datos, y a continuación comenzará la ejecución del algoritmo genético y con el output correspondiente tanto por terminal como visualmente mediante un gráfico en una ventana emergente.

En caso de querer ejecutarlo con un IDE como IntelliJ, se deja el código fuente y el procedimiento para correrlo debe ser configurando adecuadamente para correr el proyecto desde el archivo "Main" dentro de los recursos del proyecto.

### Librerías y Frameworks externos utiizados:

* Maven: Construcción y administración del proyecto sobre Java. Se añadieron plugins para especificar el compilador, la creación correcta del .jar y administración de dependencias.
* JavaFX: Para generar los gráficos en tiempo real de manera dinámica.
* JSON.simple (https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple): Para el manejo archivos tipo JSON.
