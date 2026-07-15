/**
 * Módulo principal de la aplicación Cincuentazo.
 *
 * <p>Define las dependencias del módulo (JavaFX) y los paquetes
 * que se abren para reflexión (necesario para FXML) y se exportan.</p>
 */
module com.example.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.cincuentazo to javafx.fxml;
    exports com.example.cincuentazo;
}