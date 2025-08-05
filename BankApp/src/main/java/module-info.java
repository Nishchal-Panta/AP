module com.banking.bankapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.banking.bankapp to javafx.fxml;
    exports com.banking.bankapp;
    exports com.banking.bankapp.controller;
    opens com.banking.bankapp.controller to javafx.fxml;
}