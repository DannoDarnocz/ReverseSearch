package reversesearch.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import reversesearch.filehandler.FolderLoader;
import reversesearch.filehandler.Loader;
import reversesearch.structure.doublylinkedlist.ImageList;

import java.io.File;

public class DatabaseSelectController {
    // definir controles a accionar desde interfaz grafica
    // preparar eventos a ejecutar

    @FXML private Button btnLoadFolder;
    @FXML private Button btnLoadBinary;

    @FXML
    private void initialize(){
        btnLoadFolder.setOnAction(event->{
            // abrir directorio
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Configuration Folder");

            // abrir dialogo
            File selectedDirectory = directoryChooser.showDialog(((Node)event.getSource()).getScene().getWindow());

            // leer carpeta si se puede y usuario no cancelo
            if(selectedDirectory!=null){
                Loader loader = FolderLoader.getInstance();
                ImageList database = loader.loadHistograms(selectedDirectory.getAbsolutePath());
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
        } catch (Exception e){
            e.printStackTrace(); // imprimir en consola el errorr
        }
    }


}
