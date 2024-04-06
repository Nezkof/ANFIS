public class Main {
    public static void main(String[] args) {
        double[][] data = {
                {1, 32, 3, 3.141362},
                {2, 32, 2, 6.29715},
                {3, 45, 3, 0.316198},
                {4, 65, 4, 2.229754},
                {5, 3, 53, 12.56472},
                {6, 2, 23, 0.973952},
                {7, 1, 12, 1.339259},
                {8, 5, 6, 46.39461},
                {9, 4, 5, 1.551375},
                {5, 3, 7, 12.8396},
                {4, 7, 6, 1.986992},
                {3, 5, 4, 0.673534},
                {2, 2, 11, 5.947558},
                {1, 8, 21, 3.146682},
                {2, 2, 21, 5.64757},
                {6, 11, 76, 0.405182},
                {7, 24, 34, 1.219278},
                {8, 56, 54, 47.27631},
                {9, 75, 23, 1.770304},
                {23, 43, 76, 3.150859},
                {43, 32, 32, 3.245165},
                {23, 21, 16, 2.905126},
                {54, 43, 38, 0.849992},
                {34, 21, 43, 1.380608},
                {12, 21, 34, 0},
                {14, 14, 14, 0},
                {20, 20, 20, 0}
        };

        EvolutionAlgorithm evolutionAlgorithm = new EvolutionAlgorithm(
                200, 4, 100000,
                0.05, 0.05, data, 24,2);

        evolutionAlgorithm.StartEvolution();
//        ANFIS anfis = new ANFIS(new double[] {1,2,3}, 2);
//
//        anfis.startCalculations();
//        System.out.println(anfis.getResult());
    }
}