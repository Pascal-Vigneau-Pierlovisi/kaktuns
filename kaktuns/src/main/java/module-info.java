module com.example.kaktuns_project_media {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.kaktuns_project_media to javafx.fxml;
    exports com.example.kaktuns_project_media;
}