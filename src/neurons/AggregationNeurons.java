package neurons;

import java.util.Arrays;
import java.util.Random;

public class AggregationNeurons {
    double w;
    double[] constantValues;
    double[] values;

    public AggregationNeurons(int valuesNumber){
        Random random = new Random();

        this.w = 0;

        this.constantValues = new double[valuesNumber];
        for (int i = 0; i < constantValues.length; ++i)
            constantValues[i] = random.nextDouble(10);

        this.values = new  double[valuesNumber];
    }

    public double calculateQ() {
        double linearCombination = 0;

        for (int i = 0; i < values.length; ++i){
            linearCombination += values[i]*constantValues[i];
        }

        return w*linearCombination;
    }

    public void correctConstants(double learningRate, double error, double other){
        for (int i = 0; i < constantValues.length; ++i){
            constantValues[i] = constantValues[i] - learningRate*error*values[i]*other;
        }
    }

    public void setW(double w){
        this.w = w;
    }

    public void setConstants(double[] constants){
        this.constantValues = Arrays.copyOf(constants, constants.length);
    }

    public void setVariables(double[] values){
        this.values = Arrays.copyOf(values, values.length);
    }
}
