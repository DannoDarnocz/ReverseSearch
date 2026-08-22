package reversesearch.likenessmethod;

import reversesearch.imagehandler.Histogram;

import java.security.InvalidParameterException;

public class CosineSimilarity extends LikenessMethod {
    @Override
    public double compare(Histogram A, Histogram B) {
        double result = 0;
        double dotProduct = 0;
        double magnitudeA = 0;
        double magnitudeB = 0;
        if(A.getTotalBins()!=B.getTotalBins()){
            throw new InvalidParameterException("La cantidad de bins de los histogramas es diferente.");
        }
        // obtener vectores de cada imagen
        for (int i= 0; i < A.getTotalBins(); i++) {
            double binA = A.getBin(i);
            double binB = B.getBin(i);
            // realizar calculos de similitud coseno
            dotProduct+=( binA * binB);
            magnitudeA+=(binA * binA);
            magnitudeB+=(binB * binB);
            // almacenar o procesar el resultado según sea necesario
        }
        double cosineSimilarity = dotProduct / (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB));
        result=cosineSimilarity;
        return result;
    }
}
