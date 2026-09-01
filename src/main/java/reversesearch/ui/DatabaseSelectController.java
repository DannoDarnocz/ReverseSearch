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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import reversesearch.filehandler.BinaryLoader;
import reversesearch.filehandler.FolderLoader;
import reversesearch.filehandler.Loader;
import reversesearch.filehandler.PromptFileExplorer;
import reversesearch.imagehandler.Histogram;
import reversesearch.structure.doublylinkedlist.DoublyLinkedList;

import java.io.File;
import java.util.Optional;

public class DatabaseSelectController {
    // definir controles a accionar desde interfaz grafica
    // preparar eventos a ejecutar

    @FXML private Slider sldBinQuantity;
    @FXML private Button btnLoadFolder;
    @FXML private Button btnLoadBinary;



    @FXML
    private void initialize(){
        btnLoadFolder.setOnAction(event->{


            boolean proceed = true;

            if (sldBinQuantity.getValue()>5){
                Alert warningBins = new Alert(Alert.AlertType.WARNING);;

                warningBins.setWidth(500);
                warningBins.setHeight(500);
                warningBins.setHeaderText("Advertencia");
                warningBins.setContentText("Utilizar la base de datos de imágenes proporcionada por el profesor con una cantidad de bins de 2^6 o más puede dejar sin espacio en heap al programa debido a un crecimiento espacial muy grande (esto se detalla en el informe escrito)");
                 warningBins.showAndWait();
            }

            if(proceed){
                // abrir carpeta por el usuario
                File selectedDirectory = PromptFileExplorer.openDirectoryDialog(event);


                // si es mayor a 5 da problemas por ineficiencia espacial, explicado en el documento escrito
                // es posible seleccionarlo porque lo pide pero es imposible para la base de datos de imagenes proporcionada

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
            }




        });
        btnLoadBinary.setOnAction(    event ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Cargar base de datos como archivo binario");
            File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

            if(selectedFile!=null){
                Loader loader = new BinaryLoader();
                try{
                    // intentar cargar la lista desde el binario
                    DoublyLinkedList<Histogram> histograms = loader.loadHistograms(selectedFile.getAbsolutePath(),LoadedData.binsPerColor);
                    // asignarlo para que la siguiente pantalla conozca los histogramas
                    LoadedData.loadedHistograms = histograms;
                    cambiarPantalla(event, "main.fxml",600,750,false);
                } catch (Exception e) {
                    showAlert("Error","Ha ocurrido un error al cargas el archivo binario: " + e, Alert.AlertType.ERROR);
                }
            }
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
