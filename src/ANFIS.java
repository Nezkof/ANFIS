import neurons.*;

import java.util.Arrays;

public class ANFIS {
    private final int RULES_NUMBER = 3;
    private double[][] inputData;
    private PhasificationNeurons[] firstLayerNeurons;
    private AggregationNeurons[] fourthLayerNeurons;

    ANFIS(double[][] inputData){
        this.inputData = inputData;
        this.firstLayerNeurons = new PhasificationNeurons[(inputData[0].length - 1) * RULES_NUMBER];
        this.fourthLayerNeurons = new AggregationNeurons[RULES_NUMBER];
        normalizeData();
    }

    private void normalizeData(){
        for (int j = 0; j < inputData[0].length; j++) {
            double max = inputData[0][j];
            double min = inputData[0][j];

            for (double[] row : inputData) {
                max = Math.max(max, row[j]);
                min = Math.min(min, row[j]);
            }

            for (int i = 0; i < inputData.length; i++) {
                if (max != min) {
                    inputData[i][j] = (inputData[i][j] - min) / (max - min);
                } else {
                    inputData[i][j] = 0;
                }
            }
        }
    }
}
