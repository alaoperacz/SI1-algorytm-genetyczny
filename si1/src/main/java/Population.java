import java.util.ArrayList;
import java.util.Collections;

public class Population {

    static ArrayList<City> cityList;
    ArrayList<Thief> thieves = new ArrayList<>();
    int popSize;
    int pX;
    int pM;
    int popID;
    int knapsackCapacity;
    double knapsackRentRatio;
    float maxSpeed;
    float minSpeed;
//    Thief bestThief;

    public Population(ArrayList<Integer> allGenes, ArrayList<City> cl, int id, int pS, int px, int pm, int kc, double krr, float maxS, float minS) {
        cityList = cl;
        Chromosome ch;
        Thief t;
        this.popID = id;
        this.popSize = pS;
        this.pX = px;
        this.pM = pm;
        this.knapsackCapacity = kc;
        this.knapsackRentRatio = krr;
        this.maxSpeed = maxS;
        this.minSpeed = minS;

        for (int i = 0; i <= popSize; i++) {
            ch = new Chromosome(allGenes, false, pM);
            t = new Thief(ch, knapsackCapacity, knapsackRentRatio, maxSpeed, minSpeed, px, pm);
            t.chromosome.randomGene();
            thieves.add(t);
        }
    }

    public Population(ArrayList<Thief> nextGeneration, int id, Population old){
        thieves = new ArrayList<>(nextGeneration);
        cityList = old.cityList;
        this.popID = id;
        this.popSize = old.popSize;
        this.pX = old.pX;
        this.pM = old.pM;
        this.knapsackCapacity = old.knapsackCapacity;
        this.knapsackRentRatio = old.knapsackRentRatio;
        this.maxSpeed = old.maxSpeed;
        this.minSpeed = old.minSpeed;
    }






}