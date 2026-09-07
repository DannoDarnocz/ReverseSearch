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
import reversesearch.Utilities;
import reversesearch.filehandler.BinarySaver;
import reversesearch.filehandler.PromptFileExplorer;
import reversesearch.imagehandler.ImageConvert;
import reversesearch.imagehandler.ImageReference;
import reversesearch.imagehandler.ImageSeeker;
import reversesearch.similarity.SimilarityCalculator;
import reversesearch.similarity.SimilarityResult;
import reversesearch.similarity.families.SimilarityFamilyFactory;
import reversesearch.similarity.likenessmethods.LikenessMethod;
import reversesearch.structure.Clock;
import reversesearch.structure.doublylinkedlist.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

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

        /*
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

            if(selectedDirectory!=null){
                Alert alert = Utilities.showAlert("Guardando a archivo binario","Este proceso puede tardar varios minutos.", Alert.AlertType.INFORMATION);

                // hacer una funcion lambda que se puede ejecutar en paralelo porque sino el programa se congela y no
                // muestra cuadro de infromacion
                Runnable saveBinary = () -> {
                    BinarySaver.saver(
                            LoadedData.loadedHistograms,
                            selectedDirectory.getAbsolutePath());
                };


                // crear el executor, y asignarle la tarea saveBinary
                ExecutorService executor = Executors.newSingleThreadExecutor();

                // completablefuture permite ejecutar algo despues de que se completa la tarea de forma paralela
                CompletableFuture.runAsync(saveBinary, executor)
                        .thenRun(() -> {
                            alert.hide(); // cerrar ventana automaticamente
                        });

                executor.shutdown(); // una vez que termine, apagar el executor
            }


        });*/
        btnSearch.setDisable(true);

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

                    btnSearch.setDisable(false); // ya hay imagen con la cual comparar
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSearch.setOnAction(event -> {
            if(target==null){
                Utilities.showAlert("Error","Primero debe de subir la imagen a buscar", Alert.AlertType.ERROR);
            }else{
                String likenessMethodStr = chbLikenessMethod.getValue().toString();
                String sortMethodStr = chbSortMethod.getValue().toString();

                    try{
                        SimilarityFamilyFactory family = SimilarityFamilyFactory.getFactory(likenessMethodStr);
                        LikenessMethod likenessMethod = family.createLikenessMethod(); // metodo de comparacion
                        Comparator<SimilarityResult> comparator = family.createComparator(); // ordenamiento descendente o ascendente

                        // ---- COMPARASION

                        // contar tiempo
                        Clock comparisonClock = new Clock();
                        comparisonClock.start();

                        // obtener los resultados
                        DoublyLinkedList<SimilarityResult> results = SimilarityCalculator.calculate(
                                target,
                                LoadedData.loadedHistograms,
                                likenessMethod,
                                LoadedData.binsPerColor
                        );

                        // poner la hora de finalizacion
                        comparisonClock.end();

                        // mostrar en milisegundos
                        txtComparisonTime.setText(Long.toString(comparisonClock.getMilliseconds()));

                        // --- ORDENAMIENTO
                        // ordenar segun metodo

                        SortMethod sort;
                        if(sortMethodStr.equals("Merge")){
                            sort = new MergeSort();
                        }else{
                            sort = new BubbleSort();
                        }


                        // contar tiempo
                        Clock sortClock = new Clock();
                        sortClock.start();

                        // ordenar
                        //ordenar de acuerdo que significa ser mas similar en el likeness method
                        // el de distancia euclidiana es de menor a mayor pero el resto es de mayor a menor, de eso se encarga el factory
                        sort.sort(results, comparator);

                        // parar contador
                        sortClock.end();

                        // mostrar en milisegundos
                        txtOrderTime.setText(Long.toString(sortClock.getMilliseconds()));


                        tilePaneResults.getChildren().clear(); // limpiar lo que haya en los resultados

                        // mostrar las miniaturas de las imagenes una por una, las primeras 50 únicamente
                        ListIterator<SimilarityResult> it = results.getIterador();
                        for (int i=0;i<50;i++) {
                            if(it==null) break;
                            SimilarityResult currentResult = it.getContent();
                            ImageReference currentReferences = currentResult.getImageReference();
                            BufferedImage currentThumb = currentReferences.getThumbnail();

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


                            it=it.getNext();
                        }


                    } catch (Exception e) {
                        Utilities.showAlert("Error","Ha ocurrido un error durante la búsqueda de imágenes similares: " + e.getMessage(), Alert.AlertType.ERROR);
                        e.printStackTrace();
                    }
            }
        });


    }


}
