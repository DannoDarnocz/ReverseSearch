package reversesearch.filehandler;

import reversesearch.imagehandler.Image;
import reversesearch.structure.doublylinkedlist.BufferedImageList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FolderLoader extends Loader {
    // singleton
    private static FolderLoader instance = new FolderLoader();

    private FolderLoader() {}

    public static FolderLoader getInstance() {
        return instance;
    }


    @Override
    public BufferedImageList loadHistograms(String path) {
        BufferedImageList newList = new BufferedImageList();
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

            return new BufferedImageList();
        }
        return null; // no se pudo hacer nada
    }
}
