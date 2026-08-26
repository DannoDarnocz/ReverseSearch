package reversesearch.filehandler;

import javafx.scene.Node;
import javafx.stage.DirectoryChooser;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class PromptFileExplorer {
    // pedirle al usuario que abra una carpeta
    public static File openDirectoryDialog(ActionEvent event){
        // abrir directorio
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Abrir carpeta de imágenes");

        // abrir dialogo y retornar resultado (null si canceló)
        return directoryChooser.showDialog(((Node)event.getSource()).getScene().getWindow());
    }

    // pedirle al usuario que abra un archivo
    public static File openFileDialog(ActionEvent event, String type){
        // abrir directorio
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir archivo");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*."+type)
        );

        // abrir dialogo y retornar resultado (null si canceló)
        return fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
    }
}
