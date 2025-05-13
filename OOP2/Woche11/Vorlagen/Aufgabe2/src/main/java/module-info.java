module exercise.template {

    requires javafx.controls;
    requires javafx.fxml;

    exports main;
    opens main to javafx.fxml;

}