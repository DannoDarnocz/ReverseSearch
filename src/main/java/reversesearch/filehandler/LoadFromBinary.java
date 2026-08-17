package reversesearch.filehandler;

import reversesearch.structure.doublylinkedlist.ImageList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadFromBinary {
    // singleton
    private static LoadFromBinary instance = new LoadFromBinary();

    // Private constructor stops external instantiation
    private LoadFromBinary() {}

    // Global access point
    public static LoadFromBinary getInstance() {
        return instance;
    }

    ImageList loadHistograms(String path){

        File inputFile = new File(path); // abrir imagen
        try(Scanner myReader = new Scanner(inputFile)){
            ImageList newList = new ImageList();
            newList.addStart(current); // añadir al inicio porque es mas rapido
        }catch (FileNotFoundException e) {
            System.out.println("Ha ocurrido un error al abrir el archivo binario de histogramas.");
            e.printStackTrace();
        }
    }

}
