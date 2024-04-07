import neurons.*;

import java.util.Arrays;

public class ANFIS {
    private final int RULES_NUMBER = 3;
    private double[][] inputData;
    private int epochsNumber;
    private double learningRate;
    private PhasificationNeurons[] firstLayerNeurons;
    private AggregationNeurons[] fourthLayerNeurons;

    ANFIS(double[][] inputData, int epochsNumber, double learningRate){
        this.inputData = inputData;
        normalizeData();

        this.epochsNumber = epochsNumber;
        this.learningRate = learningRate;

        this.firstLayerNeurons = new PhasificationNeurons[(inputData[0].length - 1) * RULES_NUMBER];
        for (int i = 0; i < firstLayerNeurons.length; ++i)
            this.firstLayerNeurons[i] = new PhasificationNeurons();

        this.fourthLayerNeurons = new AggregationNeurons[RULES_NUMBER];
        for (int i = 0; i < fourthLayerNeurons.length; ++i)
            this.fourthLayerNeurons[i] = new AggregationNeurons((inputData[0].length - 1));
    }

    public void startTraining(){
        for (int epoch = 0; epoch < epochsNumber; ++epoch) {
            double epochError = 0;
            for (double[] row : inputData) {

                //first and seconds layers
                for (int i = 0; i < 3; ++i)
                    firstLayerNeurons[i].setX(row[0]);

                for (int i = 3; i < 6; ++i)
                    firstLayerNeurons[i].setX(row[1]);

                for (int i = 6; i < firstLayerNeurons.length; ++i)
                    firstLayerNeurons[i].setX(row[3]);

                double[] weightsArray = new double[RULES_NUMBER];

                for (int i = 0; i < weightsArray.length; ++i){
                    for (int j = i; j < firstLayerNeurons.length; j += RULES_NUMBER){
                        weightsArray[i] += firstLayerNeurons[j].getPhasiValue();
                    }
                }

                //third layer
                double weightsSum = Arrays.stream(weightsArray).sum();
                for (int i = 0; i < weightsArray.length; ++i){
                    weightsArray[i] /= weightsSum;
                }

                //fourth layer
                double[] qArray = new double[RULES_NUMBER];
                for (int i = 0; i < qArray.length; ++i){
                    fourthLayerNeurons[i].setVariables(Arrays.copyOfRange(row, 0, 3));
                    fourthLayerNeurons[i].setW(weightsArray[i]);
                    qArray[i] = fourthLayerNeurons[i].calculateQ();
                }

                //fifth layer
                double result = Arrays.stream(qArray).sum();

                double error = result - row[3];
                startCorrection(error);
                epochError += error;
            }
            epochError /= 24;
            System.out.println(epochError);
        }
    }

    private void startCorrection(double error){
        //fourth layer
        double denominator = 0;
        for (PhasificationNeurons firstLayerNeuron : firstLayerNeurons) {
            denominator += firstLayerNeuron.getPhasiValue();
        }

        for (int i = 0; i < fourthLayerNeurons.length; ++i){
            double numerator = 1;
            for (int j = i; j < firstLayerNeurons.length; j += RULES_NUMBER){
                numerator *= firstLayerNeurons[j].getPhasiValue();
            }

            fourthLayerNeurons[i].correctConstants(learningRate, error, numerator/denominator);
        }

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
