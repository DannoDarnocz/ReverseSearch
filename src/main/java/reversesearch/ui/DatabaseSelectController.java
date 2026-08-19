package reversesearch.ui;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import reversesearch.filehandler.FolderLoader;
import reversesearch.filehandler.Loader;
import reversesearch.filehandler.PromptFileExplorer;
import reversesearch.structure.doublylinkedlist.HistogramList;
import reversesearch.structure.doublylinkedlist.ImageReferenceList;

import java.io.File;

public class DatabaseSelectController {
    // definir controles a accionar desde interfaz grafica
    // preparar eventos a ejecutar

    @FXML private Slider sldBinQuantity;
    @FXML private Button btnLoadFolder;
    @FXML private Button btnLoadBinary;

    @FXML
    private void initialize(){
        btnLoadFolder.setOnAction(event->{
            // abrir carpeta por el usuario
            File selectedDirectory = PromptFileExplorer.openDirectoryDialog(event);

            // obtener el valor que el usuario dio para el bin quantity para cada coor
            int binsPerColor = (int)Math.pow(2,sldBinQuantity.getValue());
            System.out.println(binsPerColor);

            if(selectedDirectory!=null){
                // mostrar un mensaje de cargar imagenes
                Alert loadingAlert = new Alert(Alert.AlertType.INFORMATION);
                loadingAlert.setHeaderText("Cargando imágenes...");
                loadingAlert.setContentText("Este proceso puede durar algunos minutos.");
                loadingAlert.show();

                // cargar las imágenes en paralelo porque sino se congela el sistema y no muestra el cuadro de mensaje
                // de que esta cargando
                Task<HistogramList> loadTask = new Task<>() {
                    @Override
                    protected HistogramList call() throws Exception {
                        Loader loader = FolderLoader.getInstance();
                        return loader.loadHistograms(selectedDirectory.getAbsolutePath(),binsPerColor);
                    }
                };

                // si se carga correctamente entonces avanzar a la siguiente
                loadTask.setOnSucceeded(e -> {
                    loadingAlert.hide();
                    cambiarPantalla(event, "main.fxml");

                    // ver si se encontraron imagenes validas
                    if(loadTask!=null){
                        cambiarPantalla(event, "main.fxml");
                    }
                    else{

                        // mostrar un mensaje de error
                        Alert noImageAlert = new Alert(Alert.AlertType.ERROR);
                        loadingAlert.setHeaderText("Error:");
                        loadingAlert.setContentText("No hay imágenes .png en el directorio seleccionado");
                    }
                });

                // sino mostrar error y no avanzar
                loadTask.setOnFailed(e -> {
                    loadingAlert.hide();
                    // mostrar un mensaje de error
                    Alert loadError = new Alert(Alert.AlertType.INFORMATION);
                    loadError.setHeaderText("Error:");
                    loadError.setContentText("Se ha producido un error al cargar las imágenes: " + loadTask.getException().getMessage());
                    loadError.showAndWait();
                    System.out.println(loadTask.getException().getMessage());
                });

                // ejecutar la tarea de cargar
                new Thread(loadTask).start();


            }




        });
        btnLoadBinary.setOnAction(    event ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Cargar base de datos como archivo binario");
            File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        });
    }

    // mala reutilización de codigo copiando y pegando, lo arreglaremos despues
    private void cambiarPantalla(ActionEvent evento, String archivoFxml){
        try{
            // cargar archivo pasado por parametro
            Parent raiz = FXMLLoader.load(getClass().getResource(archivoFxml));
            // cambiar el escenario a la siguiente ventana
            Stage stage=(Stage)((Node)evento.getSource()).getScene().getWindow();
            stage.getScene().setRoot(raiz); // devolver a la raiz al cerrarla
            stage.sizeToScene();
        } catch (Exception e){
            e.printStackTrace(); // imprimir en consola el errorr
        }
    }


}
