module org.example.libraryfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.yaml.snakeyaml;

    opens org.example.libraryfx to javafx.fxml;
    opens org.example.libraryfx.models to com.fasterxml.jackson.databind;

    exports org.example.libraryfx;
    exports org.example.libraryfx.models;
}