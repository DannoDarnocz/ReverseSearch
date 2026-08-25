package reversesearch.ui;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import reversesearch.filehandler.PromptFileExplorer;
import reversesearch.imagehandler.HistogramCalculator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainController {
    @FXML
    ChoiceBox chbSortMethod;

    @FXML
    ChoiceBox chbLikenessMethod;


    @FXML
    Button btnUpload;

    @FXML
    Button btnSearch;
    private  BufferedImage target; // imagen a buscar

    @FXML
    private void initialize(){
       this.target = null; // no se ha seleccionado imagen

        // setear opciones de choiceboxes
        chbSortMethod.getItems().addAll("Bubble","Merge");
        chbLikenessMethod.getItems().addAll("Similitud coseno","Similitud euclidiana","Intersección de histogramas");

        btnUpload.setOnAction(event -> {
            // pedir abrir archivo tipo png
            File selectedFile = PromptFileExplorer.openFileDialog(event,"png");

            if(selectedFile != null){
                try{
                    // convertir a imagen si el usuario no cancelo el dialogo
                    target = ImageIO.read(selectedFile);

                } catch (IOException e) {
                    // todo: ver que hacer con excepciones
                    e.printStackTrace();
                }
            }
        });

        btnSearch.setOnAction(event -> {
            if(target==null){
                // mostrar un mensaje de cargar imagenes
                Alert loadingAlert = new Alert(Alert.AlertType.ERROR);
                loadingAlert.setHeaderText(null);
                loadingAlert.setContentText("Primero debe de subir la imagen a buscar.");

                // mostrar alerta
                loadingAlert.show();
            }else{

                // mostrar un mensaje de cargar imagenes
                Alert loadingAlert = new Alert(Alert.AlertType.INFORMATION);
                loadingAlert.setHeaderText(null);
                loadingAlert.setContentText("Triangulando...");
                loadingAlert.show();
            }
        });
    }


}
