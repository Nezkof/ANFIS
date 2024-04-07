import neurons.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ANFIS {
    private final int RULES_NUMBER = 3;
    private double[][] inputData;
    private int epochsNumber;
    private double learningRate;
    private double eps;
    private PhasificationNeurons[] firstLayerNeurons;
    private AggregationNeurons[] fourthLayerNeurons;

    ANFIS(double[][] inputData, int epochsNumber, double learningRate, double eps){
        this.inputData = inputData;
        normalizeData();

        this.epochsNumber = epochsNumber;
        this.learningRate = learningRate;
        this.eps = eps;

        this.firstLayerNeurons = new PhasificationNeurons[(inputData[0].length - 1) * RULES_NUMBER];
        for (int i = 0; i < firstLayerNeurons.length; ++i)
            this.firstLayerNeurons[i] = new PhasificationNeurons();

        this.fourthLayerNeurons = new AggregationNeurons[RULES_NUMBER];
        for (int i = 0; i < fourthLayerNeurons.length; ++i)
            this.fourthLayerNeurons[i] = new AggregationNeurons((inputData[0].length - 1));

        double[] means = {0.6926283485363582, 0.903776922809556, 7.791696452023861, 5.312331027797374, 0.8959532021754602, 1.5547227718655, 0.6032034719546031, 6.613343432068357, 2.546878801117952};
        double[] sigmas = {2.88215010756516, 3.1559766501624207, 9.799563183713435, 0.1720626857534724, 2.2251951987724263, 7.047313705236673, 9.647698658407682, 3.9495180904460767, 2.5936910659289527};

        for (int i = 0; i < firstLayerNeurons.length && i < means.length; i++) {
            firstLayerNeurons[i].setMean(means[i]);
            firstLayerNeurons[i].setSigma(sigmas[i]);
        }
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
                    firstLayerNeurons[i].setX(row[2]);

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
            System.out.println("#" + (epoch+1) + " Похибка " + epochError);
            if (epochError < eps)
                break;
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

    public void startTesting(double[][] dataForTests){
/*        System.out.println("================================================");
        for (double[] row : inputData) {

            //first and seconds layers
            for (int i = 0; i < 3; ++i)
                firstLayerNeurons[i].setX(row[0]);

            for (int i = 3; i < 6; ++i)
                firstLayerNeurons[i].setX(row[1]);

            for (int i = 6; i < firstLayerNeurons.length; ++i)
                firstLayerNeurons[i].setX(row[2]);

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

            System.out.println(result - row[3]);
        }*/


        System.out.println("================================================");
        for (double[] row : dataForTests) {
            //first and seconds layers
            for (int i = 0; i < 3; ++i)
                firstLayerNeurons[i].setX(row[0]);

            for (int i = 3; i < 6; ++i)
                firstLayerNeurons[i].setX(row[1]);

            for (int i = 6; i < firstLayerNeurons.length; ++i)
                firstLayerNeurons[i].setX(row[2]);

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
            System.out.println(Arrays.toString(row));
            System.out.println("Result: " + result);
            System.out.println();

            String filePath = "numbers.txt";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

                // Запись каждого числа из массива в файл
                for (int i = 0; i < firstLayerNeurons.length; ++i){
                    writer.write(Integer.toString(i));
                    writer.newLine();
                    writer.write(Double.toString(firstLayerNeurons[i].getMean()));
                    writer.newLine();
                    writer.write(Double.toString(firstLayerNeurons[i].getSigma()));
                    writer.newLine();
                    writer.newLine();
                }

                writer.close(); // закрываем поток
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
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
