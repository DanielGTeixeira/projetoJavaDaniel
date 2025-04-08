module com.projeto {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.projeto to javafx.fxml;
    exports com.projeto;
}