# TP1 - SIA 20202Q - Solucionador de Sokoban

El fin de este trabajo es crear un solucionador del juego Sokoban, desarrollado en Java.

##### Autores:

* Tamara Puig
* Luciano Boccardi

#### Restricciones de la solución:
En primer lugar, el programa cuenta con solo 3 (tres) mapas disponibles, identificados por niveles. Easy - Medium y Hard. La solución de Medium resulta la más interesante para datos analíticos ya que Hard al emplear gran cantidad de recursos en algunos métodos de búsqueda puede llegar a arrojar excepciones.

En segundo lugar, los métodos de búsqueda empelados fueron: BFS, DFS, IDDFS, Global Greedy, A* y IDA*. Estos últimos tres cuentan con tres posibles heurísticas llamadas: H1, H2 y H3. Las tres son implementaciones distintas utilizando el cáculo de distancias Manhattan, con un agregado que se encuentra explicado a detalle en la presentación.

### Configuración previa:

El programa cuenta con un archivo ``` config.json ```. El mismo tiene el siguiente formato: (No es Case Sensitive para el value).
```sh
{
  "map": 3,
  "searchMethod": "dfs",
  "heuristic": "h2"
}
```
* ``` "map" ```: número del 1 al 3 indicando el mapa según dificultad. Siendo 1 el nivel Easy, 2 el Medium y 3 el Hard.
* ``` "searchMethod" ```: Puede tener los valores: "bfs", "dfs", "iddfs", "g_greedy", "a_star", "ida_star". También tiene un valor extra: "all". Si se utiliza este valor se realizará un algoritmo tras otro en el orden que fueron enumerados. Es imprescindible definir la heurística para los últimos 3 métodos de búsqueda.
* ``` "heuristic" ```: Puede tener los valores: "h1", "h2" o "h2". También tiene un valor extra: "all". Se comporta de igual manera que el anterior. Si se selecciona un método de búsqueda entre "bfs", "dfs" o "iddfs", se ignorará este valor.

Por ejemplo: si se elije: {2, "all", "all"}. Se ejecutará: bfs, dfs, iddfs, g_greedy (h1), g_greedy (h2), g_greedy (h3), a_star (h1), a_star(h2), a_star(h3), ida_star(h1), ida_star(h2) y finalmente, ida_star(h3).   

### Corriendo el proyecto:

Requisito previo: Tener al menos una entorno de Java 8. Puede ser tanto JRE como JDK, dependiendo si se desea correr únicamente el .jar sin un IDE.
1) Navegar hacia la carpeta SokobanSolver donde se encuentra el ```TP1-1.0.jar``` a su vez que el archivo ```config.json```.
2) Editar el archivo ```config.json``` de la manera deseada siguiendo los lineamientos anteriormente mencionados.
3) Abrir en el directorio una terminal (bash - powershell - cmd) y tipear el comando:
    ```sh
    $ java -jar .\TP1-1.0.jar
    ```
4) A continuación se ejecutará el/los algoritmo/s y brindará el output en dos formatos. En primer lugar mediante la consola, y luego lo enviará a un archivo ```results.txt``` ubicado en la misma carpeta.
5) También se abrirá una ventana de Java. Maximizarla y aparecerá un sketch del mapa seleccionado. Al hacer click izquierda dentro de la ventana y presionar la ```tecla Enter``` se ejecutará la solución paso a paso si fue encontrada. Si no fue encontrada, al presionar la tecla de nuevo se seguirá a la próxima solución si más de un algoritmo o heurística fue seleccionado, hasta que se vacíe.

En caso de querer ejecutarlo con un IDE como IntelliJ, se deja el código fuente y el procedimiento para correrlo debe ser configurando adecuadamente para correr el proyecto desde el archivo "ui.Main" dentro de los recursos del proyecto.

### Librerías y Frameworks externos utiizados:

* Maven: Construcción y administración del proyecto sobre Java. Se añadieron plugins para especificar el compilador, la creación correcta del .jar y administración de dependencias.
* JSON.simple (https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple): Para el manejo archivos tipo JSON.
