package neurons;

public class PhasificationNeurons {
    double x;
    double mean;
    double sigma;

    public PhasificationNeurons(){
        this.x = 0;
        this.mean = 0;
        this.sigma = 0;
    }

    public double getPhasiValue() {
        return (Math.exp(-Math.pow((x-mean)/sigma,2)));
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setC(double c) {
        this.mean = c;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }


}
