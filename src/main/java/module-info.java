module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    // Consente a JavaFX di accedere al package principale
    opens org.example to javafx.fxml;
    // Specifica che questo modulo contiene il package principale
    exports org.example;
}