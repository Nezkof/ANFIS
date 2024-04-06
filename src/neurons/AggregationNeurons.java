package neurons;

import java.util.Arrays;

public class AggregationNeurons {
    double w;
    double[] constantsValues;
    double[] values;

    public AggregationNeurons(int valuesNumber){
        this.w = 0;
        this.constantsValues = new double[valuesNumber];
        this.values = new  double[valuesNumber];
    }

    public double calculateQ() {
        double linearCombination = 0;

        for (int i = 0; i < values.length; ++i){
            linearCombination += values[i]*constantsValues[i];
        }

        return w*linearCombination;
    }

    public void setW(double w){
        this.w = w;
    }

    public void setConstants(double[] constants){
        this.constantsValues = Arrays.copyOf(constants, constants.length);
    }

    public void setVariables(double[] values){
        this.values = Arrays.copyOf(values, values.length);
    }
}
