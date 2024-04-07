package neurons;

import java.util.Random;

public class PhasificationNeurons {
    double x;
    double mean;
    double sigma;

    public PhasificationNeurons(){
        Random random = new Random();

        this.x = 0;
        this.mean = random.nextDouble(10);
        this.sigma = random.nextDouble(10);
    }

    public double getPhasiValue() {
        return (Math.exp(-Math.pow((x-mean)/sigma,2)));
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }


}
