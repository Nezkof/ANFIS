import java.util.*;

public class EvolutionAlgorithm {
    private final List<ANFIS> population;
    private final int parentsNumber;
    private final int childsPerParent;
    private final int epochsNumber;
    private final double mutationRate;
    private final double sigma; //середньоквадратичне відхилення
    private Random random;
    List<Double> populationScores;
    private double bestPopulationScore;

    private final double[][] data;
    private final int trainTableSize;
    private final int rulesNumber;

    EvolutionAlgorithm(int parentsNumber, int childsPerParent, int epochsNumber,
                       double mutationRate, double sigma,
                       double[][] data, int trainTableSize ,int rulesNumber) {
        this.random = new Random();
        this.population = new ArrayList<>();
        this.parentsNumber = parentsNumber;
        this.childsPerParent = childsPerParent;
        this.epochsNumber = epochsNumber;
        this.mutationRate = mutationRate;
        this.sigma = sigma;
        populationScores = new ArrayList<>();

        double[][] copyOfData = new double[data.length][];
        for (int i = 0; i < data.length; i++)
            copyOfData[i] = Arrays.copyOf(data[i], data[i].length);
        this.data = copyOfData;
        this.trainTableSize = trainTableSize;
        this.rulesNumber = rulesNumber;

        createStartPopulation();
    }

    private void createStartPopulation() {
        for (int i = 0; i < parentsNumber; ++i) {
            double[] inputValues = new double[data[0].length - 1];
            int randomRow = random.nextInt(trainTableSize);
            for (int j = 0; j < data[0].length - 1; ++j){
                inputValues[j] = data[randomRow][j];
            }

            this.population.add(new ANFIS(inputValues, rulesNumber, data[randomRow][3]));
        }
    }

    public void StartEvolution(){
        for (int epoch = 0; epoch < epochsNumber; ++epoch) {
            calculatePopulationScores();
            addChilds();
            calculatePopulationScores();
            formNewPopulation();
            if (epoch >= 60)
                System.out.println("this");
            System.out.println(epoch + " " + this.bestPopulationScore);
            mutateAllIndivids();
        }
    }

    private void addChilds() {
        List<ANFIS> childs = new ArrayList<>();

        //(lambda/ro, mu)//
        for (int i = 0; i < parentsNumber/2; ++i) {
            for (int j = 0; j < childsPerParent; ++j) {
                ANFIS parent1 = tournamentSelection();
                ANFIS parent2 = tournamentSelection();

                double[] parent1CArray = parent1.getCArray();
                double[] parent1SigmaArray = parent1.getSigmaArray();
                double[][] parent1ConstantsArray = parent1.getConstantsArray();

                double[] parent2CArray = parent2.getCArray();
                double[] parent2SigmaArray = parent2.getSigmaArray();
                double[][] parent2ConstantsArray = parent2.getConstantsArray();

                double[] childCArray = new double[parent1CArray.length];
                double[] childSigmaArray = new double[parent1SigmaArray.length];
                double[][] childConstantsArray = new double[parent1ConstantsArray.length][];
                for (int k = 0; k < childConstantsArray.length; ++k){
                    childConstantsArray[k] = new double[parent1ConstantsArray[0].length];
                }

                for (int a = 0; a < childCArray.length; ++a){
                    childCArray[a] = (parent1CArray[a] + parent2CArray[a])/2;
                }

                for (int a = 0; a < childSigmaArray.length; ++a){
                    childSigmaArray[a] = (parent1SigmaArray[a] + parent2SigmaArray[a])/2;
                }

                for (int a = 0; a < childConstantsArray.length; ++a) {
                    for (int b = 0; b < childConstantsArray[0].length; ++b){
                        childConstantsArray[a][b] = (parent1ConstantsArray[a][b]+parent2ConstantsArray[a][b])/2;
                    }
                }

                ANFIS child = new ANFIS(parent1.getValues(),rulesNumber,
                        childCArray, childSigmaArray, childConstantsArray, parent1.getTableResult());

                childs.add(child);
            }
        }

        population.addAll(childs);
    }

    private void mutateAllIndivids() {
        for (ANFIS anfis : population) {
            double[] individCArray = anfis.getCArray();
            double[] individSigmaArray = anfis.getSigmaArray();
            double[][] individConstantsArray = anfis.getConstantsArray();

            for (int a = 0; a < individCArray.length; ++a) {
                individCArray[a] += scaleToRange(random.nextGaussian(), -sigma, sigma);
            }

            for (int a = 0; a < individSigmaArray.length; ++a) {
                individSigmaArray[a] += scaleToRange(random.nextGaussian(), -sigma, sigma);
            }

            for (int a = 0; a < individConstantsArray.length; ++a) {
                for (int b = 0; b < individConstantsArray[0].length; ++b) {
                    individConstantsArray[a][b] += scaleToRange(random.nextGaussian(), -sigma, sigma);
                }
            }

        }
    }
    private double scaleToRange(double value, double min, double max) { return ((value + 1.0) / 2.0) * (max - min) + min; }


    private void formNewPopulation() {
        List<ANFIS> newPopulation = new ArrayList<>(population);

        newPopulation.sort(Comparator.comparingDouble(ANFIS::getError));

        List<ANFIS> selectedPopulation = newPopulation.subList(0, parentsNumber);
        bestPopulationScore = selectedPopulation.get(0).getError();

        population.clear();
        population.addAll(selectedPopulation);
    }



    private ANFIS tournamentSelection() {
        ANFIS bestIndividual = null;
        double bestScore = Double.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(population.size());
            double score = populationScores.get(randomIndex);

            if (score < bestScore) {
                bestIndividual = population.get(randomIndex);
                bestScore = score;
            }
        }
        return bestIndividual;
    }

    private void calculatePopulationScores() {
        if (!populationScores.isEmpty())
            populationScores.clear();

        for (ANFIS individ : population)
        {
            individ.startCalculations();
            populationScores.add(individ.getError());
        }
    }

}
