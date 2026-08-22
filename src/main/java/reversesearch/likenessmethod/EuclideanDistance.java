package reversesearch.likenessmethod;

import reversesearch.imagehandler.Histogram;

import java.security.InvalidParameterException;

public class EuclideanDistance extends LikenessMethod {
    @Override
    public double compare(Histogram histogram1, Histogram histogram2) {
        double result = 0;

        // verificar que tengan la misma cantidad de bins, por si acaso
        if(histogram1.getTotalBins()!=histogram2.getTotalBins()){
            throw new InvalidParameterException("La cantidad de bins de los histogramas es diferente.");
        }

        double sum = 0;

        // sumatoria de (pi-qi) a la 2
        for(int i=0;i<histogram1.getTotalBins();i++){
            double currentBin1 = histogram1.getBin(i);
            double currentBin2 = histogram2.getBin(i);
            sum+=Math.pow(currentBin1-currentBin2,2);
        }

        // raiz cuadrada al final
        sum = Math.sqrt(sum);

        return sum;
    }
}
