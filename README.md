# Participantes
Juan David Vasquez Cañas - 2515123
Juan Camilo Valverde Lopez - 2536682

**BRANCH** → Documentacio
**VIDEO** → https://youtu.be/QCoVoXzPsW4

# Cincuentazo

¡Hola! Este es mi proyecto para el juego de cartas **Cincuentazo**, desarrollado en Java con JavaFX. Es un juego súper entretenido donde compites contra la computadora (máquinas) tratando de no ser el que se pase de 50 en la suma acumulada de las cartas jugadas.

## Introducción
El juego consiste en ir lanzando cartas en la mesa por turnos. Cada carta tiene un valor que se va sumando a un contador general. El límite es **50**. Si en tu turno no tienes ninguna carta que puedas lanzar sin pasarte de 50, ¡quedas eliminado! El último jugador o máquina en pie gana la partida.

Las cartas especiales del juego son:
* **El As (A)**: Vale 1 o 10 puntos (puedes elegir el valor cuando lo lanzas).
* **El Nueve (9)**: Vale 0 puntos (sirve para salvarse cuando la suma está alta).
* **Las letras (J, Q, K)**: Restan 10 puntos a la suma total (súper útiles para bajar la suma acumulada).
* El resto de cartas suman su valor numérico normal.

## Requisitos
Para poder correr y editar este proyecto en tu computadora, vas a necesitar:
* **Java JDK 17** (en este caso usamos **Amazon Corretto 17**).
* **IntelliJ IDEA** (cualquier versión, preferiblemente Community o Ultimate).
* **Maven** (ya viene integrado en el proyecto con el wrapper `mvnw`).
* **JavaFX 21** (las dependencias ya se manejan automáticamente por Maven en el `pom.xml`).

## Conclusiones
* **Uso de Hilos (Threads)**: Este proyecto me sirvió muchísimo para entender cómo funcionan los hilos en Java, especialmente al sincronizarlos con la interfaz gráfica mediante `Platform.runLater()` para que la aplicación no se congele mientras las máquinas "piensan" o mientras corre el temporizador en segundo plano.
* **Patrón MVC**: El proyecto separa muy bien el modelo (`JuegoModelo`) de la vista (`FXML`/`CSS`) y el controlador (`JuegoControlador`), lo que hace que el código sea ordenado y más fácil de mantener.
* **Lógica Orientada a Objetos**: Programar las cartas, el mazo y los turnos me ayudó a reforzar conceptos de colecciones, herencia y enums en Java 17.
