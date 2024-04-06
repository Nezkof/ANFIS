import neurons.*;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.jar.Manifest;

public class ANFIS {
    private double[] values;
    private int rulesNumber;
    private PhasificationNeurons[] firstLayer;
    private ThirdLayerNeurons[] thirdLayer;
    private AggregationNeurons[] fourthLayer;
    private DephasificationNeurons fifthLayer;
    private double[] cArray;
    private double[] sigmaArray;
    private double[][] constantsArray;
    Random random;

    ANFIS(double[] inputValues, int rulesNumber){
        random = new Random();

        this.values = Arrays.copyOf(inputValues, inputValues.length);
        this.rulesNumber = rulesNumber;

        this.firstLayer = new PhasificationNeurons[inputValues.length * rulesNumber];
            for (int i = 0; i < firstLayer.length; ++i)
                firstLayer[i] = new PhasificationNeurons();

        this.thirdLayer = new ThirdLayerNeurons[rulesNumber];
            for (int i = 0; i < thirdLayer.length; ++i)
                thirdLayer[i] = new ThirdLayerNeurons();

        this.fourthLayer = new AggregationNeurons[rulesNumber];
            for (int i = 0; i < fourthLayer.length; ++i)
                fourthLayer[i] = new AggregationNeurons();

        this.fifthLayer = new DephasificationNeurons();
        this.cArray = new double[inputValues.length * rulesNumber];
        this.sigmaArray = new double[inputValues.length * rulesNumber];
        this.constantsArray = new double[rulesNumber][inputValues.length];

        randomFill(cArray);
        randomFill(sigmaArray);
        for (int i = 0; i < constantsArray.length; ++i)
            this.constantsArray[i] = randomFill(constantsArray[i]);
    }

    /*ANFIS(double[] inputValues, int rulesNumber, double[] cArray, double[] sigmaArray, double[][] constantsArray) {
        random = new Random();

        this.values = Arrays.copyOf(inputValues, inputValues.length);
        this.rulesNumber = rulesNumber;

        this.firstLayer = new PhasificationNeurons[inputValues.length * rulesNumber];
        this.secondLayer = new SecondLayerNeurons[rulesNumber];
        this.thirdLayer = new ThirdLayerNeurons[rulesNumber];
        this.fourthLayer = new AggregationNeurons[rulesNumber];
        this.fifthLayer = new DephasificationNeurons();

        this.cArray = Arrays.copyOf(cArray, cArray.length);
        this.sigmaArray = Arrays.copyOf(sigmaArray, sigmaArray.length);
        this.constantsArray = Arrays.copyOf(constantsArray, constantsArray.length);
    }*/

    public void startCalculations() {
        inputValues();
        double[] wValues = calculateWValues();

    }

    private void inputValues() {
        int index = 0;
        for (double value : values) {
            for (int j = 0; j < rulesNumber; j++) {
                firstLayer[index].setX(value);
                firstLayer[index].setC(cArray[index]);
                firstLayer[index].setSigma(sigmaArray[index]);
                index++;
            }
        }
    }

    double[] calculateWValues(){
        double[] wArray = new double[rulesNumber];

        for (int i = 0; i < wArray.length; ++i) {
            for (int j = i; j < firstLayer.length; j += rulesNumber) {
                wArray[i] += firstLayer[j].getPhasiValue();
            }
        }

        return wArray;
    }

    private double[] randomFill(double[] arrayToFill) {
        for (int i = 0; i < arrayToFill.length; ++i)
            arrayToFill[i] = random.nextInt(999);
        return arrayToFill;
    }

}
