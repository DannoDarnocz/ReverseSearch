package reversesearch;

import javafx.scene.control.Alert;

public class Utilities {
    public static Alert showAlert(String title, String msg, Alert.AlertType type){
        // configurar alerta
        Alert loadingAlert = new Alert(type);
        loadingAlert.setHeaderText(title);
        loadingAlert.setContentText(msg);

        // mostrarla
        loadingAlert.showAndWait();
        return loadingAlert;
    }

    /// mostrar alerta de cargando
    public static Alert showLoadingAlert(String title, String msg){
        // configurar alerta
        Alert loadingAlert = new Alert(Alert.AlertType.INFORMATION);
        loadingAlert.setHeaderText(title);
        loadingAlert.setContentText(msg);

        loadingAlert.show();

        return loadingAlert;
    }

}
