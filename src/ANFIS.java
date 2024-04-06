import neurons.*;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.jar.Manifest;

public class ANFIS {
    private PhasificationNeurons[] firstLayer;
    private SecondLayerNeurons[] secondLayer;
    private ThirdLayerNeurons[] thirdLayer;
    private AggregationNeurons[] fourthLayer;
    private DephasificationNeurons[] fifthLayer;
    private double[] cArray;
    private double[] sigmaArray;
    private double[][] constantsArray;

    ANFIS(double[] inputValues, int rulesNumber){
        this.firstLayer = new PhasificationNeurons[inputValues.length * rulesNumber];
        this.secondLayer = new SecondLayerNeurons[rulesNumber];
        this.thirdLayer = new ThirdLayerNeurons[rulesNumber];
        this.fourthLayer = new AggregationNeurons[rulesNumber];
        this.fifthLayer = new DephasificationNeurons[rulesNumber];
    }

    ANFIS(double[] inputValues, int rulesNumber, double[] cArray, double[] sigmaArray, double[][] constantsArray) {
        this.firstLayer = new PhasificationNeurons[inputValues.length * rulesNumber];
        this.secondLayer = new SecondLayerNeurons[rulesNumber];
        this.thirdLayer = new ThirdLayerNeurons[rulesNumber];
        this.fourthLayer = new AggregationNeurons[rulesNumber];
        this.fifthLayer = new DephasificationNeurons[rulesNumber];

        this.cArray = Arrays.copyOf(cArray, cArray.length);
        this.sigmaArray = Arrays.copyOf(sigmaArray, sigmaArray.length);
        this.constantsArray = Arrays.copyOf(constantsArray, constantsArray.length);
    }

}
