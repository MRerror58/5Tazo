ENUNCIADO MINI PROYECTO #3
Descripción: En este mini proyecto se desarrollará el juego llamado "Cincuentazo". Es un
juego de cartas de Poker donde los jugadores (humano y maquina) deben sobrevivir
utilizando sus cartas. Se jugará contra 1, 2 o 3 jugadores máquina; donde cada jugador
siempre debe tener una mano de 4 cartas que podrá jugar en su turno, siguiendo las
siguientes reglas del juego:
• Regla principal: En la mesa existe una suma que no debe exceder el 50 (>50).
• Preparación: Del mazo de cartas se reparten 4 cartas aleatorias a cada jugador,
luego se colocará una carta aleatoria en la mesa boca arriba (esta carta inicia la suma
de la mesa) para así el jugador humano empezar a jugar; el resto de las cartas se
quedan en el mazo boca abajo para luego ser tomadas por un jugador.
• Turno de juego: El juego se desarrolla por turnos. En su turno, el jugador debe
seleccionar una carta de su mano teniendo en cuenta la regla principal y que:
o Todas las cartas con numeros del 2 al 8 y el 10 suman su número.
o Todas las cartas con numero 9 ni suman ni restan.
o Todas las cartas con letras J, Q, K restan 10.
o Todas las cartas con letra A suman 1 o 10, según convenga.
La carta seleccionada por el jugador quedará boca arriba en la mesa encima de la
carta anterior. Esta carta debe ser jugada para sumar o restar con el fin de no exceder
la suma de 50 en la mesa, por lo tanto, la suma de la mesa será modificada con el
valor de la carta. Luego, el mismo jugador deberá tomar una carta del mazo para que
siempre cada jugador tenga 4 cartas en su mano. En caso contrario, que el jugador
no pueda jugar ninguna carta de su mano porque excede la suma de 50 en la mesa,
este quedará eliminado.
• Fin del juego: El objetivo del juego es ser el último jugador en quedar en juego.
• Otras consideraciones:
o Debido a que el juego se inicia con una carta aleatoria del mazo, la suma de
la mesa puede iniciar en 0 (Carta con numero 9), 1 (Carta con la letra A) o -10
(Cartas con las letras J, Q, K).
o Si las cartas del mazo se terminan se deben tomar las cartas de la mesa
excepto la última jugada, barajarlas y dejarlas disponibles en el mazo. La suma
de la mesa no se modifica.
o Las cartas del jugador eliminado deben enviarse al final del mazo y quedan
disponibles para ser tomadas por un jugador.
Entregables:
• Crear una interfaz gráfica de usuario intuitiva con aspectos básicos de UX y UI.
• Emplear la arquitectura Modelo-Vista-Controlador en el código fuente.
• Documentar el código fuente usando Javadoc.
• Gestionar el proyecto por medio de git y alojarlo en GitHub.
• Implementar excepciones de Java marcadas, no marcadas y propias.
• Implementar dos hilos.
750014C FUNDAMENTOS DE PROGRAMACIÓN ORIENTADA A EVENTOS - 2026-1
• Implementar tres clases de pruebas unitarias.
Tecnologías y herramientas para desarrollar el sol eclipsado: Java, JavaFX, Scene
Builder, IntelliJ IDEA, Git y GitHub.
Cantidad de integrantes por grupo: Tres estudiantes.
Condiciones no negociables:
• No se aceptarán entregas de proyectos individuales.
• No se aceptarán entregas después de la fecha acordada de entrega.
• La sustentación consiste en realizar un cambio al proyecto y contestar las preguntas
realizadas por el profesor sobre el código fuente del proyecto, el cambio a realizar
será asignado el día de la sustentación.
• La sustentación se realiza en los computadores de la sala de cómputo asignada para
el curso no en los computadores personales.
• Si el estudiante no realiza la sustentación la nota del proyecto será 0 (cero).
• Si la sustentación no es válida, es decir, el estudiante no logra comprobar que realizó
el proyecto, se disminuirá 1.0 en la nota del proyecto por cada uno de los siguientes
ítems:
 No realiza el cambio dentro del tiempo establecido para la sustentación. Serán
30 min.
 No responde las preguntas de profesor sobre el código fuente.
 Se evidencia copia de código fuente de otros proyectos.
 Se evidencia copia de código fuente generado por la IA sin saber qué hace.
Historias de usuario: A continuación, se presentan las funcionalidades del mini proyecto.
HU-1 Inicio del juego
Como jugador, quiero seleccionar con cuántos usuarios máquina (1, 2 o 3) voy a jugar.
Criterios de aceptación:
• El jugador humano puede seleccionar si desea jugar con 1, 2 o 3 jugadores
máquina.
• Una vez seleccionada la cantidad de usuarios máquina se podrá iniciar el juego.
Definición de hecho:
• El jugador humano podrá seleccionar si desea jugar con 1, 2 o 3 jugadores máquina.
• El jugador humano podrá iniciar el juego.
HU-2 Preparación del juego
Como jugador, quiero recibir automáticamente 4 cartas al inicio del juego y ver una carta
en la mesa para poder comenzar a jugar y saber en cuánto inicia la suma de la mesa.
Criterios de aceptación:
• El jugador humano y los jugadores máquina reciben 4 cartas cada uno.
750014C FUNDAMENTOS DE PROGRAMACIÓN ORIENTADA A EVENTOS - 2026-1
• Una carta se coloca boca arriba en la mesa como carta inicial.
• El resto de las cartas quedan en el mazo boca abajo.
Definición de hecho:
• El juego muestra la mano inicial de cada jugador y la carta en la mesa.
• Las cartas del jugador humano se muestran boca arriba.
• Las cartas de los jugadores maquina se muestran boca abajo.
• El mazo restante está disponible para que los jugadores tomen cartas.
HU-3 Jugar una carta
Como jugador, quiero poder seleccionar una carta de mi mano y jugarla si cumple con la
regla de no exceder el 50 la suma de la mesa para poder avanzar en el juego.
Criterios de aceptación:
• El jugador humano puede seleccionar una carta que cumple con la regla de no
exceder el 50 la suma de la mesa.
• El jugador maquina puede seleccionar una carta en un tiempo entre 2 a 4 segundos
que cumple con la regla de no exceder el 50 la suma de la mesa.
• La carta jugada se coloca encima de la carta anterior en la mesa.
Definición de hecho:
• La carta seleccionada se mueve de la mano del jugador a la mesa.
• Se actualiza la carta visible en la mesa.
HU-4 Tomar una carta del mazo
Como jugador, quiero tomar una carta del mazo después de jugar una en la mesa para
tener siempre 4 cuartas en la mano durante el juego y terminar mi turno.
Criterios de aceptación:
• El jugador humano puede tomar una carta del mazo después de jugar una en la
mesa.
• El jugador maquina puede tomar una carta del mazo en un tiempo entre 2 a 4
segundos después de jugar una en la mesa.
• La carta tomada se agrega a la mano del jugador.
Definición de hecho:
• La mano del jugador se actualiza con la nueva carta.
• El turno pasa al siguiente jugador.
HU-5 Eliminación de un jugador
Como juego, quiero eliminar un jugador cuando en su mano no tenga una carta para jugar.
Criterios de aceptación:
• Un jugador será eliminado cuando en su mano no tenga una carta para jugar.
• Las cartas del jugador eliminado son enviadas al mazo y quedan disponibles para
ser tomadas por un jugador.
Definición de hecho:
• Un jugador es eliminado cuando en su mano no tenga una carta para jugar.
• Las cartas del jugador eliminado son enviadas al mazo y quedan disponibles para
ser tomadas por un jugador.
750014C FUNDAMENTOS DE PROGRAMACIÓN ORIENTADA A EVENTOS - 2026-1
HU-6 Fin del juego
Como juego quiero finalizar el juego cuando el penúltimo jugador haya sido eliminado y
declarar ganador al único jugador que quedó en juego.
Criterios de aceptación:
• Se finalizará el juego cuando solo queda un jugador en juego.
• Declara ganador al único jugador en juego.
Definición de hecho:
• Se finaliza el juego cuando solo queda un jugador en juego.
• Declara ganador al único jugador en juego