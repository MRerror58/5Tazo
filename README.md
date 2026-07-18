# Cincuentazo 🎮

¡Hola! Este es mi proyecto para el juego de cartas **Cincuentazo**, desarrollado en Java con JavaFX. Es un juego súper entretenido donde compites contra la computadora (máquinas) tratando de no ser el que se pase de 50 en la suma acumulada de las cartas jugadas.

## 📝 Introducción
El juego consiste en ir lanzando cartas en la mesa por turnos. Cada carta tiene un valor que se va sumando a un contador general. El límite es **50**. Si en tu turno no tienes ninguna carta que puedas lanzar sin pasarte de 50, ¡quedas eliminado! El último jugador o máquina en pie gana la partida.

Las cartas especiales del juego son:
* **El As (A)**: Vale 1 o 10 puntos (puedes elegir el valor cuando lo lanzas).
* **El Nueve (9)**: Vale 0 puntos (sirve para salvarse cuando la suma está alta).
* **Las letras (J, Q, K)**: Restan 10 puntos a la suma total (súper útiles para bajar la suma acumulada).
* El resto de cartas suman su valor numérico normal.

## 🛠️ Requisitos
Para poder correr y editar este proyecto en tu computadora, vas a necesitar:
* **Java JDK 17** (en este caso usamos **Amazon Corretto 17**).
* **IntelliJ IDEA** (cualquier versión, preferiblemente Community o Ultimate).
* **Maven** (ya viene integrado en el proyecto con el wrapper `mvnw`).
* **JavaFX 21** (las dependencias ya se manejan automáticamente por Maven en el `pom.xml`).

## ⚙️ Instalación
Para abrir el proyecto en IntelliJ:
1. Descarga o clona este repositorio en tu computadora.
2. Abre **IntelliJ IDEA**.
3. Selecciona la opción **Open** (Abrir) y busca la carpeta raíz del proyecto (donde está el archivo `pom.xml`).
4. Deja que IntelliJ cargue el proyecto y descargue las dependencias de Maven (tarda un par de minutos la primera vez).

## 🚀 Ejecución
Para correr el juego desde la terminal del sistema o de IntelliJ, solo debes ejecutar el siguiente comando:
```bash
./mvnw clean javafx:run
```
*(Si estás en Windows y usas CMD o PowerShell, puedes usar `mvnw.cmd clean javafx:run`)*.

Una vez ejecutes el comando, se abrirá la ventana de inicio donde podrás elegir jugar contra 1, 2 o 3 máquinas. ¡Y listo, a jugar!

## 📁 Estructura del proyecto
El código está organizado de la siguiente manera dentro de la carpeta `src/main`:
* **`java/com/example/cincuentazo/`**: Aquí está toda la lógica en Java:
  * `Carta.java`: Clase que define qué es una carta (su palo, valor y símbolo).
  * `Mazo.java`: Maneja la pila de cartas, cómo barajar y cómo reciclar la mesa cuando nos quedamos sin cartas.
  * `Jugador.java`: Representa a los jugadores (tanto el humano como los rivales virtuales).
  * `JuegoModelo.java`: Lleva la lógica del juego (la suma, el turno actual, quién es eliminado, etc.).
  * `CincuentazoApp.java`: Es la clase principal que arranca la interfaz gráfica.
  * `InicioControlador.java`: Controla la pantalla donde seleccionamos el número de oponentes.
  * `JuegoControlador.java`: Maneja el flujo de la interfaz del tablero del juego, los eventos de los clics y los hilos para el temporizador y la simulación del turno de las máquinas.
* **`resources/com/example/cincuentazo/`**: Contiene la parte gráfica del juego:
  * `inicio-view.fxml` y `juego-view.fxml`: Diseños de las pantallas hechos en Scene Builder.
  * `estilos.css`: Hoja de estilos CSS para que el juego se vea moderno y organizado.

## 🎓 Conclusiones
* **Uso de Hilos (Threads)**: Este proyecto me sirvió muchísimo para entender cómo funcionan los hilos en Java, especialmente al sincronizarlos con la interfaz gráfica mediante `Platform.runLater()` para que la aplicación no se congele mientras las máquinas "piensan" o mientras corre el temporizador en segundo plano.
* **Patrón MVC**: El proyecto separa muy bien el modelo (`JuegoModelo`) de la vista (`FXML`/`CSS`) y el controlador (`JuegoControlador`), lo que hace que el código sea ordenado y más fácil de mantener.
* **Lógica Orientada a Objetos**: Programar las cartas, el mazo y los turnos me ayudó a reforzar conceptos de colecciones, herencia y enums en Java 17.
