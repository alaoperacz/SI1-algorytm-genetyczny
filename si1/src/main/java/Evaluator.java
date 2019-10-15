import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Evaluator {

    final static int POPULATION_SIZE = 1000;
    final static int TOURNAMENT_SIZE = 25;
    final static int TOURNAMENT_WINNERS = 1;
    final static int POPULATIONS = 70000;
    final static int PX = 80;
    final static int PM = 10;
    Population population;
    Loader loader;
    private String printer;

    public Evaluator(String filename){
        loader = new Loader(filename);

    }

    public void runAlgorithm() throws FileNotFoundException {
        initiatePopulation();
        int i = 0;
        PrintWriter printWriter = new PrintWriter("data.csv");
        do {
            evaluate();
            printWriter.println(printer);
            ArrayList<Thief> strongest = tournament();
            crossover(strongest);
            mutation();
            i++;
        }while(i<POPULATIONS);
        printWriter.close();

    }

    public void notGeneticRandom()throws FileNotFoundException{
        initiatePopulation();
        PrintWriter printWriter = new PrintWriter("dataNGR.csv");
        evaluate();
        printWriter.println(printer);
        ArrayList<Thief> strongest = tournament();
        crossover(strongest);
        mutation();
        printWriter.close();
    }

    public void notGeneticNotGeneticNearestCity()throws FileNotFoundException{
        initiatePopulation();
        PrintWriter printWriter = new PrintWriter("dataNGNC.csv");
        evaluateNotGenetic();
        printWriter.println(printer);
        printWriter.close();
    }

    private void initiatePopulation(){
        population = new Population(loader.genes, loader.cities,1, POPULATION_SIZE, PX, PM, loader.CAPACITY_OF_KNAPSACK, loader.RENTING_RATIO, loader.MAX_SPEED, loader.MIN_SPEED);
    }

    private void evaluate(){
        double best = 0;
        double sum = 0;
        double worst = 0;
        double avg;

        for (Thief t : population.thieves) {
            t.go(loader.cities);
            t.setFitnessVal();
            double score = t.getFitnessVal();

            if (best == 0 || best < score) best = score;
            if (worst == 0 || worst > score) worst = score;
            sum+=score;
        }
        avg = sum / population.thieves.size();
        printer = population.popID+","+(int)best+","+(int)avg+","+(int)worst;
        System.out.println("pop #"+population.popID+":  best one: "+(int)best+", avg one: "+(int)avg+", worst one: "+(int)worst);
    }
    private void evaluateNotGenetic(){
        double best = 0;
        double sum = 0;
        double worst = 0;
        double avg;

        for (Thief t : population.thieves) {
            t.goNearestCityStrategy(loader.cities);
            t.setFitnessVal();
            double score = t.getFitnessVal();

            if (best == 0 || best < score) best = score;
            if (worst == 0 || worst > score) worst = score;
            sum+=score;
        }
        avg = sum / population.thieves.size();
        printer = population.popID+","+(int)best+","+(int)avg+","+(int)worst;
        System.out.println("pop #"+population.popID+":  best one: "+(int)best+", avg one: "+(int)avg+", worst one: "+(int)worst);
    }

    public ArrayList<Thief> tournament(){
        ArrayList<Thief> winners = new ArrayList<>();
        Thief currWinner;
        int currWinnerIndex;
        ArrayList<Thief> tourne = new ArrayList<>();

        for(int i=0;i<POPULATION_SIZE;i++){

            for(int j=0;j<TOURNAMENT_SIZE;j++){
                tourne.add(population.thieves.get((int)(Math.random()*population.thieves.size())));
            }
            for(int h = 0; h<TOURNAMENT_WINNERS; h++){
                currWinner = tourne.get(0);
                currWinnerIndex = 0;
                for(int g=1;g<tourne.size(); g++){
                    if(tourne.get(g).getFitnessVal() > currWinner.getFitnessVal()){
                        currWinner = tourne.get(g);
                        currWinnerIndex = g;
                    }
                }
                winners.add(currWinner);
                tourne.remove(currWinnerIndex);
            }
            tourne.clear();
        }
        return winners;
    }

    public ArrayList<Thief> elite(){
        ArrayList<Thief> winners = new ArrayList<>();
        Thief currWinner;
        int currWinnerIndex;
        ArrayList<Thief> tourne = new ArrayList<>();
        int eliteSize = POPULATION_SIZE/10;
        ArrayList<Thief> sortedPop = new ArrayList<>(population.thieves);
        Collections.sort(sortedPop,Thief::compareTo);

        for(int j = 0; j<eliteSize; j++)
            winners.add(sortedPop.get(j));

        for(int i=0;i<POPULATION_SIZE-eliteSize;i++){

            for(int j=0;j<TOURNAMENT_SIZE;j++){
                tourne.add(population.thieves.get((int)(Math.random()*population.thieves.size())));
            }
            for(int h = 0; h<TOURNAMENT_WINNERS; h++){
                currWinner = tourne.get(0);
                currWinnerIndex = 0;
                for(int g=1;g<tourne.size(); g++){
                    if(tourne.get(g).getFitnessVal() > currWinner.getFitnessVal()){
                        currWinner = tourne.get(g);
                        currWinnerIndex = g;
                    }
                }
                winners.add(currWinner);
                tourne.remove(currWinnerIndex);
            }
            tourne.clear();
        }
        return winners;
    }

    private void crossover(ArrayList<Thief> oldPopulation){
        Population nextPopulation;
        Thief parent1;
        Thief parent2;
        ArrayList<Thief> newThieves = new ArrayList<>();

        for(int i = 0; i<oldPopulation.size(); i+=2){
            parent1 = oldPopulation.get(i);
            parent2 = oldPopulation.get(i+1);
            // System.out.println(parent1.chromosome + " "+parent2.chromosome);
            if(parent1.crossing && parent2.crossing) {
                newThieves.add(cross(parent1, parent2));
                newThieves.add(cross(parent2, parent1));
            }else{
                newThieves.add(parent1.copy());
                newThieves.add(parent2.copy());
            }
        }
        nextPopulation = new Population(newThieves, population.popID+1, population);
        population = nextPopulation;
    }

    private Thief cross(Thief parent1, Thief parent2){

        ArrayList<Integer> check = new ArrayList<>(parent1.chromosome.genes);
        ArrayList<Integer> genes1 = new ArrayList<>(parent1.chromosome.genes);
        ArrayList<Integer> genes2 = new ArrayList<>(parent2.chromosome.genes);
        ArrayList<Integer> genesTemp = new ArrayList<>();
        ArrayList<Integer> childGenes = new ArrayList<>();

        int pivot = check.size()/2;

        for(int i = 0; i<pivot; i++){
            genesTemp.add(genes1.get(i));
        }
        for(int i=pivot; i<check.size(); i++){
          //  for(int j=0; j<check.size(); j++) {
                //if (!genesTemp.contains(genes2.get(j))){
                    genesTemp.add(genes2.get(i));
            //    }
            //}
        }
        //
//        int pivot = check.size()/4;
//
//        for(int i = 0; i<pivot; i++){
//            genesTemp.add(genes1.get(i));
//        }
//
//        for(int i = pivot; i<pivot*3; i++){
//            genesTemp.add(genes2.get(i));
//        }
//
//        for(int i=pivot*3; i<check.size(); i++){
//            genesTemp.add(genes1.get(i));
//        }

        for(int i = 0; i<genesTemp.size(); i++){
            if(childGenes.indexOf(genesTemp.get(i))<0){
                childGenes.add(genesTemp.get(i));
                boolean isRemoved = check.remove(genesTemp.get(i));
            }else{
                childGenes.add(-1);
            }
        }

        for(int i = 0; i<childGenes.size(); i++)
            if(childGenes.get(i)==-1)
                childGenes.set(i, check.remove(0));

        Chromosome ch = new Chromosome(childGenes, true, PM);

        return new Thief(ch, loader.CAPACITY_OF_KNAPSACK, loader.RENTING_RATIO, loader.MAX_SPEED, loader.MIN_SPEED, PX, PM);
    }

    private void mutation(){
        for(Thief t: population.thieves){
            t.chromosome.mutate();
        }
    }

    public ArrayList<Thief> ranking(){
        ArrayList<Thief> winners = new ArrayList<>();
        population.thieves.sort(Thief::compareTo);
        for(int i = 0; i<POPULATION_SIZE; i++){
            for(int j = 0; j<20; j++){
                winners.add(population.thieves.get(j));
            }
        }
        return winners;
    }

    public ArrayList<Thief> roulette(){
        ArrayList<Thief> winners = new ArrayList<>();
        for(int i = 0; i<POPULATION_SIZE; i++){
            winners.add(bestRoulette());
        }
        return winners;
    }

    private Thief bestRoulette(){
        ArrayList<Thief> copy = new ArrayList<>(population.thieves);
        int plus = (-1)*getWorst(copy).getFitnessVal()+1;
        for(Thief t: copy) {
            t.setEnlargedFitnessVal(plus);

        }
        Collections.shuffle(copy);
        boolean found = false;
        Thief t = copy.get(0);
        int bestVal = getBestEnlargedFitnessVal(copy);
        for(int i = 0; i<copy.size() && !found; i++){
            t = copy.get(i);
            int rand = (int) (Math.random()*bestVal);
            //System.out.println("rand: "+rand+" f: "+t.enlargedFitnessVal+"bestval: "+bestVal);
            if(rand<=t.enlargedFitnessVal)
                found = true;
        }
        return t;
    }

    private Thief getWorst(ArrayList<Thief> list){
        ArrayList<Thief> copy = new ArrayList<>(list);
        copy.sort(Thief::compareTo);
        return copy.get(copy.size()-1);
    }

    private int getBestEnlargedFitnessVal(ArrayList<Thief> list){
        ArrayList<Thief> copy = new ArrayList<>(list);
        copy.sort(Thief::compareTo);
        return copy.get(0).enlargedFitnessVal;
    }


}