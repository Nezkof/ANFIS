import neurons.*;

import java.util.Arrays;
import java.util.Random;

public class ANFIS {
    private double[] values;
    private int rulesNumber;
    private double result;
    private PhasificationNeurons[] firstLayer;
    private AggregationNeurons[] fourthLayer;
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

        this.fourthLayer = new AggregationNeurons[rulesNumber];
            for (int i = 0; i < fourthLayer.length; ++i)
                fourthLayer[i] = new AggregationNeurons(inputValues.length);

        this.cArray = new double[inputValues.length * rulesNumber];
        this.sigmaArray = new double[inputValues.length * rulesNumber];
        this.constantsArray = new double[rulesNumber][inputValues.length];

        randomFill(cArray);
        randomFill(sigmaArray);
        for (int i = 0; i < constantsArray.length; ++i)
            this.constantsArray[i] = randomFill(constantsArray[i]);
    }

    public void startCalculations() {
        inputValues(); //first layer
        double[] wValues = calculateWValues(); //second layer
        normalizeWValues(wValues); //third layer
        double[] qValues = calculateAggregatedValues(wValues); //fourthLayer

        for (double qValue : qValues)
            this.result += qValue;
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

    private double[] calculateWValues(){
        double[] wArray = new double[rulesNumber];

        for (int i = 0; i < wArray.length; ++i)
            for (int j = i; j < firstLayer.length; j += rulesNumber)
                wArray[i] += firstLayer[j].getPhasiValue();

        return wArray;
    }

    private void normalizeWValues(double[] wValues) {
        double wValuesSum = 0;
        for (double wValue : wValues)
            wValuesSum += wValue;

        for (int i = 0; i < wValues.length; ++i)
            wValues[i] = wValues[i]/wValuesSum;
    }

    private double[] calculateAggregatedValues(double[] wValues) {
        double[] qValues = new double[rulesNumber];

        for (int i = 0; i < fourthLayer.length; ++i){
            fourthLayer[i].setW(wValues[i]);
            fourthLayer[i].setConstants(this.constantsArray[i]);
            fourthLayer[i].setVariables(this.values);
            qValues[i] = fourthLayer[i].calculateQ();
        }
        return qValues;
    }

    private double[] randomFill(double[] arrayToFill) {
        for (int i = 0; i < arrayToFill.length; ++i)
            arrayToFill[i] = random.nextInt(999);
        return arrayToFill;
    }

    public double getResult(){
        return this.result;
    }

}
