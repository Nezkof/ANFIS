package neurons;

public class PhasificationNeurons {
    double x;
    double c;
    double sigma;

    public PhasificationNeurons(){
        this.x = 0;
        this.c = 0;
        this.sigma = 0;
    }

    public double getPhasiValue() {
        return (Math.exp(-Math.pow((x-c)/sigma,2)));
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setC(double c) {
        this.c = c;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }


}
