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
/*                {12, 21, 34, 0},
                {14, 14, 14, 0},
                {20, 20, 20, 0}*/
        };

        /*double[][] data = {
                {1, 2, 3, 6},    // x1 = 1, x2 = 2, x3 = 3, y = 6
                {4, 5, 6, 15},   // x1 = 4, x2 = 5, x3 = 6, y = 15
                {7, 8, 9, 24},   // x1 = 7, x2 = 8, x3 = 9, y = 24
                {10, 11, 12, 33},// x1 = 10, x2 = 11, x3 = 12, y = 33
                {13, 14, 15, 42},// x1 = 13, x2 = 14, x3 = 15, y = 42
                {16, 17, 18, 51},// x1 = 16, x2 = 17, x3 = 18, y = 51
                {19, 20, 21, 60},// x1 = 19, x2 = 20, x3 = 21, y = 60
                {22, 23, 24, 69},// x1 = 22, x2 = 23, x3 = 24, y = 69
                {25, 26, 27, 78},// x1 = 25, x2 = 26, x3 = 27, y = 78
                {28, 29, 30, 87},// x1 = 28, x2 = 29, x3 = 30, y = 87
                {31, 32, 33, 96},// x1 = 31, x2 = 32, x3 = 33, y = 96
                {34, 35, 36, 105},// x1 = 34, x2 = 35, x3 = 36, y = 105
                {37, 38, 39, 114},// x1 = 37, x2 = 38, x3 = 39, y = 114
                {40, 41, 42, 123},// x1 = 40, x2 = 41, x3 = 42, y = 123
                {43, 44, 45, 132},// x1 = 43, x2 = 44, x3 = 45, y = 132
                {46, 47, 48, 141},// x1 = 46, x2 = 47, x3 = 48, y = 141
                {49, 50, 51, 150},// x1 = 49, x2 = 50, x3 = 51, y = 150
                {52, 53, 54, 159},// x1 = 52, x2 = 53, x3 = 54, y = 159
        };*/

        ANFIS anfis = new ANFIS(data, 1000, 0.3);

        anfis.startTraining();


    }
}