package reversesearch.ui;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.coobird.thumbnailator.Thumbnails;
import reversesearch.filehandler.BinarySaver;
import reversesearch.filehandler.PromptFileExplorer;
import reversesearch.imagehandler.ImageConvert;
import reversesearch.imagehandler.ImageReference;
import reversesearch.imagehandler.ImageSeeker;
import reversesearch.likenessmethod.SimilarityCalculator;
import reversesearch.likenessmethod.SimilarityResult;
import reversesearch.structure.Clock;
import reversesearch.structure.doublylinkedlist.*;

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

    @FXML
    TilePane tilePaneResults;

    @FXML
    Label lblLoadedImages;

    private ImageReference target; // imagen a buscar



    @FXML
    private void initialize(){
       this.target = null; // no se ha seleccionado imagen

        // poner cantidad de imagenes cargadas de la base de datos
        lblLoadedImages.setText(Integer.toString(LoadedData.loadedHistograms.size()));


        // setear opciones de choiceboxes
        chbSortMethod.getItems().addAll("Bubble","Merge");
        chbLikenessMethod.getItems().addAll("Similitud coseno","Distancia euclidiana","Intersección de histogramas");

        btnSaveBinary.setOnAction(event -> {
            // pedir donde guardar
            FileChooser fileChooser = new FileChooser();

            // poner cual es el tipo de archivo y un nombre generico.
            fileChooser.setInitialFileName("database.bin");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Archivo binario (*.bin)", "*.bin"),
                    new FileChooser.ExtensionFilter("All Files (*.*)", "*.*")
            );

            File selectedDirectory = fileChooser.showSaveDialog(((Node)event.getSource()).getScene().getWindow());


            BinarySaver.saver(
                    LoadedData.loadedHistograms,
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

                    try{

                        // ---- COMPARASION

                        // contar tiempo
                        Clock comparisonClock = new Clock();
                        comparisonClock.start();

                        // obtener los resultados
                        DoublyLinkedList<SimilarityResult> results = SimilarityCalculator.calculate(
                                target,
                                LoadedData.loadedHistograms,
                                likenessMethodStr,
                                LoadedData.binsPerColor
                        );

                        // poner la hora de finalizacion
                        comparisonClock.end();

                        // mostrar en milisegundos
                        txtComparisonTime.setText(Long.toString(comparisonClock.getMilliseconds()));

                        // --- ORDENAMIENTO
                        // ordenar segun metodo

                        SortMethod sort;
                        // todo: arreglar factory para sort?
                        // todo: ARREGLAR ESTA COSAAAA
                        if(sortMethodStr.equals("Merge")){
                            sort = new MergeSort();
                        }else{
                            sort = new BubbleSort();
                        }


                        // contar tiempo
                        Clock sortClock = new Clock();
                        sortClock.start();

                        // ordenar
                        sort.sort(results);

                        // parar contador
                        sortClock.end();

                        // mostrar en milisegundos
                        txtOrderTime.setText(Long.toString(sortClock.getMilliseconds()));


                        tilePaneResults.getChildren().clear(); // limpiar lo que haya en los resultados

                        // mostrar las miniaturas de las imagenes una por una, las primeras 50 únicamente
                        ListIterator<SimilarityResult> it = results.getIterador();
                        for (int i=0;i<50;i++) {
                            if(!it.hasNext()) break; // ya no hay mas imagenes
                            it=it.getNext();
                            SimilarityResult currentResult = it.getContent();
                            ImageReference currentReferences = currentResult.getImageReference();
                            BufferedImage currentThumb = currentReferences.getThumbnail();

                            System.out.println("actual: " + currentResult.getLikenessValue());

                            // convertir thumbnail a Image desde bytes porque es buffered
                            Image thumbImage = ImageConvert.fromBuffered(currentThumb);

                            ImageView currentImageView = new ImageView(thumbImage);
                            // vista de la miniatura para cada uno
                            currentImageView.setFitWidth(120);
                            currentImageView.setFitHeight(120);
                            currentImageView.setPreserveRatio(true);
                            currentImageView.setCursor(Cursor.HAND);

                            // cuando el usuario le da click a la miniatura que estamos construyendo
                            currentImageView.setOnMouseClicked(e -> {
                                try {
                                    // buscar la imagen completa
                                    BufferedImage fullBuffered = ImageSeeker.bufferedFromReference(currentReferences);
                                    // la convierte a Image para poder mostrarse
                                    Image fullImage = ImageConvert.fromBuffered(fullBuffered);
                                    ImageView fullView = new ImageView(fullImage);

                                    // configurar que se vea bien
                                    fullView.setPreserveRatio(true);
                                    fullView.setFitWidth(800);

                                    // montar nueva ventana para verla completa
                                    Stage popup = new Stage();
                                    popup.setScene(new Scene(new StackPane(fullView)));
                                    popup.show();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            });

                            tilePaneResults.getChildren().add(currentImageView);
                        }


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
