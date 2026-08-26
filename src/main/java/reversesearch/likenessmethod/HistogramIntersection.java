package reversesearch.likenessmethod;

import reversesearch.imagehandler.Histogram;

import java.security.InvalidParameterException;

public class HistogramIntersection implements LikenessMethod{
    @Override
    public double compare(Histogram A, Histogram B) {
        double minSum = 0;
        if(A.getTotalBins()!=B.getTotalBins()){
            throw new InvalidParameterException("La cantidad de bins de los histogramas es diferente:"+A.getTotalBins()+", "+B.getTotalBins());
        }
        for (int i=0; i < A.getTotalBins(); i++) {
            double binA = A.getBin(i);
            double binB = B.getBin(i);
            minSum += Math.min(binA, binB);
        }
        return minSum;
    }

}
