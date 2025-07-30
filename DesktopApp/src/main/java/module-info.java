module com.example.nepaltourism.desktopapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.nepaltourism.desktopapp to javafx.fxml;
    exports com.example.nepaltourism.desktopapp;
}