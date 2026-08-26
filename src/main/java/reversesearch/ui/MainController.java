package reversesearch.ui;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import net.coobird.thumbnailator.Thumbnails;
import reversesearch.filehandler.BinarySaver;
import reversesearch.filehandler.PromptFileExplorer;
import reversesearch.imagehandler.ImageReference;
import reversesearch.likenessmethod.SimilarityCalculator;
import reversesearch.likenessmethod.SimilarityResult;
import reversesearch.structure.doublylinkedlist.BubbleSort;
import reversesearch.structure.doublylinkedlist.DoublyLinkedList;
import reversesearch.structure.doublylinkedlist.MergeSort;
import reversesearch.structure.doublylinkedlist.SortMethod;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class MainController {
    @FXML
    ChoiceBox chbSortMethod;

    @FXML
    ChoiceBox chbLikenessMethod;

    @FXML
    Button btnSaveBinary;

    @FXML
    Button btnUpload;

    @FXML
    Button btnSearch;

    @FXML
    TextField txtOrderTime;
    @FXML
    TextField txtComparisonTime;
    private ImageReference target; // imagen a buscar


    @FXML
    private void initialize(){
       this.target = null; // no se ha seleccionado imagen

        // setear opciones de choiceboxes
        chbSortMethod.getItems().addAll("Bubble","Merge");
        chbLikenessMethod.getItems().addAll("Similitud coseno","Similitud euclidiana","Intersección de histogramas");

        btnSaveBinary.setOnAction(event -> {
            // pedir donde guardar
            FileChooser fileChooser = new FileChooser();
            File selectedDirectory = fileChooser.showSaveDialog(((Node)event.getSource()).getScene().getWindow());

            BinarySaver.saver(
                    DatabaseSelectController.loadedHistograms,
                    selectedDirectory.getAbsolutePath()
            );
        });

        btnUpload.setOnAction(event -> {
            // pedir abrir archivo tipo png
            File selectedFile = PromptFileExplorer.openFileDialog(event,"png");

            if(selectedFile != null){
                try{
                    // convertir a imagen si el usuario no cancelo el dialogo
                    BufferedImage thumb = Thumbnails.of(selectedFile).size(160, 160).asBufferedImage();
                    target = new ImageReference(selectedFile.getAbsolutePath(), thumb);

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
                String likenessMethodStr = chbLikenessMethod.getValue().toString();
                String sortMethodStr = chbSortMethod.getValue().toString();

                // mostrar un mensaje de cargar imagenes
                Alert loadingAlert = new Alert(Alert.AlertType.INFORMATION);
                loadingAlert.setHeaderText(null);
                loadingAlert.setContentText("Triangulando...");
                loadingAlert.show();

                    try{

                        // ---- COMPARASION

                        // contar tiempo
                        Instant startComparison = Instant.now();

                        // obtener los resultados
                        DoublyLinkedList<SimilarityResult> results = SimilarityCalculator.calculate(
                                target,
                                DatabaseSelectController.loadedHistograms,
                                likenessMethodStr,
                                DatabaseSelectController.binsPerColor
                        );

                        // parar contador
                        Instant finishComparison = Instant.now();

                        // Calcular tiempo de duracion
                        long timeElapsed = Duration.between(startComparison, finishComparison).toSeconds();

                        // mostrar
                        txtComparisonTime.setText(Long.toString(timeElapsed));

                        // --- ORDENAMIENTO
                        // ordenar segun metodo

                        SortMethod sort;
                        // todo: arreglar factory para sort?
                        if(sortMethodStr=="Merge"){
                            sort = new MergeSort();
                        }else{
                            sort = new BubbleSort();
                        }

                        // iniciar contador
                        Instant startSort = Instant.now();

                        // ordenar
                        sort.sort(results);

                        // parar contador
                        Instant finishSort = Instant.now();

                        // Calcular tiempo de duracion
                        long timeElapsedSort = Duration.between(startSort, finishSort).toSeconds();

                        // mostrar
                        txtOrderTime.setText(Long.toString(timeElapsedSort));

                        loadingAlert.hide();


                    } catch (Exception e) {
                        showAlert("Error","Ha ocurrido un error durante la búsqueda de imágenes similares", Alert.AlertType.ERROR);
                        e.printStackTrace();
                    }
            }
        });


    }
    private void showAlert(String title, String msg, Alert.AlertType type){
        // configurar alerta
        Alert loadingAlert = new Alert(type);
        loadingAlert.setHeaderText(title);
        loadingAlert.setContentText(msg);

        // mostrarla
        loadingAlert.show();
    }

}
