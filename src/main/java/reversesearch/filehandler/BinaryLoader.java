/*package reversesearch.filehandler;

import reversesearch.structure.doublylinkedlist.ImageReferenceList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BinaryLoader extends Loader {
    // singleton
    private static BinaryLoader instance = new BinaryLoader();

    private BinaryLoader() {}

    public static BinaryLoader getInstance() {
        return instance;
    }

    @Override
    public HistogramList loadHistograms(String path) {

        File inputFile = new File(path); // abrir imagen
        try (Scanner myReader = new Scanner(inputFile)) {
            ImageReferenceList newList = new ImageReferenceList();
            //newList.addStart(current); // añadir al inicio porque es mas rapido
        } catch (FileNotFoundException e) {
            System.out.println("Ha ocurrido un error al abrir el archivo binario de histogramas.");
            e.printStackTrace();
        }

        return new ImageReferenceList();
    }
}
*/