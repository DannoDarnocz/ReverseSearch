package reversesearch.filehandler;

import reversesearch.imagehandler.Image;
import reversesearch.structure.doublylinkedlist.ImageList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

public class FolderLoader extends Loader {
    // singleton
    private static FolderLoader instance = new FolderLoader();

    private FolderLoader() {}

    public static FolderLoader getInstance() {
        return instance;
    }


    @Override
    public ImageList loadHistograms(String path) {
        ImageList newList = new ImageList();
        File directory = new File(path); // abrir directorio

        if (directory.isDirectory()) { // verificar que es directorio
            for (File f : directory.listFiles()) {
                // verificar que sea png
                String currentName = f.getName();
                if(currentName.endsWith(".png")){
                    BufferedImage currentImg = null;

                    try {
                        currentImg = ImageIO.read(f);
                        System.out.println("Leyendo imagen");
                        // poner algo en ui
                        System.out.println("image: " + f.getName());
                        System.out.println(" width : " + currentImg.getWidth());
                        System.out.println(" height: " + currentImg.getHeight());
                        System.out.println(" size  : " + f.length());

                        // imagen leida con exito, ingresar a la lista
                        Image imgObject = new Image(currentImg);
                        newList.addStart(imgObject);
                    } catch (final IOException e) {
                        // handle errors here
                    }
                }else{
                    System.out.println("Archivo no valido, se ha saltado.");
                }

            }

            return new ImageList();
        }
        return null; // no se pudo hacer nada
    }
}
