package reversesearch.ui;

import javafx.application.Platform;
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
import reversesearch.imagehandler.Histogram;
import reversesearch.structure.doublylinkedlist.DoublyLinkedList;

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
            LoadedData.binsPerColor = (int)Math.pow(2,sldBinQuantity.getValue());

            if(selectedDirectory!=null){
                // mostrar un mensaje de cargar imagenes
                Alert loadingAlert = new Alert(Alert.AlertType.INFORMATION);
                loadingAlert.setHeaderText("Procesando imágenes...");
                loadingAlert.setContentText("Este proceso puede tardar algunos minutos.");
                loadingAlert.show();

                // cargar las imágenes en paralelo porque sino se congela el sistema y no muestra el cuadro de mensaje
                // de que esta cargando
                Task<DoublyLinkedList<Histogram> > loadTask = new Task<>() {
                    @Override
                    protected DoublyLinkedList<Histogram>  call() throws Exception {
                        Loader loader = FolderLoader.getInstance();
                        return loader.loadHistograms(selectedDirectory.getAbsolutePath(),LoadedData.binsPerColor);
                    }
                };

                // si se carga correctamente entonces avanzar a la siguiente
                loadTask.setOnSucceeded(e -> {
                    loadingAlert.close();

                    // ver si se encontraron imagenes validas
                    if(loadTask!=null){
                        // obtener lista cargada desde el task
                        LoadedData.loadedHistograms = loadTask.getValue();
                        cambiarPantalla(event, "main.fxml",600,750,false);
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
                    loadingAlert.close();
                    // mostrar un mensaje de error
                    Alert loadError = new Alert(Alert.AlertType.INFORMATION);
                    loadError.setHeaderText("Error:");
                    loadError.setContentText("Se ha producido un error al cargar las imágenes: " + loadTask.getException().getMessage());
                    loadError.showAndWait();
                    System.out.println(loadTask.getException().getMessage());
                });

                // ejecutar la tarea de cargar
                try{
                    new Thread(loadTask).start();
                } catch (OutOfMemoryError e) {
                    loadingAlert.close();
                    e.printStackTrace();
                    showAlert("Error","No hay suficiente espacio en memoria para almacenar los histogramas con la cantidad de bins por color especificado.", Alert.AlertType.ERROR);
                }catch (Exception e) {
                    loadingAlert.close();
                    e.printStackTrace();
                    showAlert("Error","Se ha producido un error al cargar las imágenes.", Alert.AlertType.ERROR);
                }

            }




        });
        btnLoadBinary.setOnAction(    event ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Cargar base de datos como archivo binario");
            File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        });
    }

    // mala reutilización de codigo copiando y pegando, lo arreglaremos despues
    public static void cambiarPantalla(ActionEvent evento, String archivoFxml, double width, double height, boolean resizable){
        try{
            // cargar archivo pasado por parametro
            Parent raiz = FXMLLoader.load(DatabaseSelectController.class.getResource(archivoFxml));
            // cambiar el escenario a la siguiente ventana
            Stage stage=(Stage)((Node)evento.getSource()).getScene().getWindow();
            stage.getScene().setRoot(raiz); // devolver a la raiz al cerrarla
            stage.setResizable(resizable);

            stage.setWidth(width);
            stage.setHeight(height);
            stage.setMinWidth(width);
            stage.setMinHeight(height);
        } catch (Exception e){
            e.printStackTrace(); // imprimir en consola el errorr
        }
    }

    public static void showAlert(String title, String msg, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setHeaderText(title);
        alert.setContentText(msg);
        alert.show();
    }


}
