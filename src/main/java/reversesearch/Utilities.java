package reversesearch;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.Objects;

public class Utilities {
    public static Alert showAlert(String title, String msg, Alert.AlertType type){
        // configurar alerta
        Alert loadingAlert = new Alert(type);
        loadingAlert.setHeaderText(title);
        loadingAlert.setContentText(msg);

        // mostrarla
        loadingAlert.show();
        return loadingAlert;
    }



}
