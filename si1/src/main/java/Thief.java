import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Thief {

    private int fitnessVal;
    private Knapsack knapsack;
    double time;
    double distance;
    float maxSpeed;
    float minSpeed;
    int pX;
    int pM;
    boolean crossing;
    public int enlargedFitnessVal;
    Chromosome chromosome;
    Item noItem = new Item(0,0,0, 0);

    public Thief(Chromosome ch, int c, double rR, float maxS, float minS, int px, int pm){
        this.chromosome = ch;
        this.knapsack = new Knapsack(c, rR);
        this.maxSpeed = maxS;
        this.minSpeed = minS;
        this.time = 0;
        this.pX = px;
        this.pM = pm;
        if((int)(Math.random()*100) < 100-pX){
            this.crossing = false;
        }else{
            this.crossing = true;
        }
    }
    public Thief(Thief thief){
        this.chromosome = new Chromosome(new ArrayList<>(thief.chromosome.genes), true, pM);
        knapsack = new Knapsack(thief.getKnapsackCapacity(), thief.getKnapsackRentingRatio());
        this.maxSpeed = thief.maxSpeed;
        this.minSpeed = thief.minSpeed;
        this.time = 0;
        this.pX = thief.pX;
        this.pM = pM;
        if((int)(Math.random()*100) < 100-pX){
            this.crossing = false;
        }else{
            this.crossing = true;
        }
    }

    public void go(ArrayList<City> allCities){
        double speed;
        double currTime = 0;
        double currDistance;
        double currSpeed = maxSpeed;
        distance = 0;
        int currCityId;
        int nextCityId;
        City currCity;
        City nextCity;

        for(int i = 0; i < chromosome.genes.size(); i++) {
            currCityId = chromosome.genes.get(i);
            currCity = allCities.get(currCityId - 1);
            if (i == chromosome.genes.size() - 1){
                nextCityId = chromosome.genes.get(0);
                nextCity = allCities.get(nextCityId - 1);
            }else {
                nextCityId = chromosome.genes.get(i + 1);
                nextCity = allCities.get(nextCityId - 1);
            }
            currDistance = countDistance(currCity.getCoorX(), currCity.getCoorY(), nextCity.getCoorX(), nextCity.getCoorY());
            distance = distance + currDistance;
            knapsack.takeItem(chooseItem(currCity.items));
            currSpeed = maxSpeed - (knapsack.getWeight()*((maxSpeed-minSpeed)/knapsack.getCapacity()));
            if(currSpeed > minSpeed){
                currTime = currDistance/currSpeed;
            }else{
                currTime = currDistance/minSpeed;
            }
            time = time + currTime;

//            System.out.println(currCity);
//            System.out.println(time);
//            System.out.println(knapsack.getValue());
        }
    }

    public void goNearestCityStrategy(ArrayList<City> allCities){
        double speed;
        double currTime = 0;
        double currDistance;
        double currSpeed = maxSpeed;
        distance = 0;
        int currCityId;
        City nearestCity;
        int nearestCityId;
        City currCity;
        City start = allCities.get(chromosome.genes.get(0)-1);
        currCity = start;
        currCityId = start.iD-1;
        ArrayList<City> toGo = new ArrayList<>(allCities);
        toGo.remove(currCityId);

        for(int i = 0; i < toGo.size(); i++) {
            nearestCity = toGo.get(i);
            nearestCityId = i;
            for(int j=1;j<toGo.size();j++){
                if((countDistance(allCities.get(j).coorX,allCities.get(j).coorY,currCity.coorX,currCity.coorY)<=countDistance(nearestCity.coorX,nearestCity.coorY,currCity.coorX,currCity.coorY))){
                    nearestCity = allCities.get(j);
                    nearestCityId = j;
                }
            }
            currDistance = countDistance(currCity.getCoorX(), currCity.getCoorY(), nearestCity.getCoorX(), nearestCity.getCoorY());
            distance = distance + currDistance;
            knapsack.takeItem(chooseItem(currCity.items));
            currSpeed = maxSpeed - (knapsack.getWeight()*((maxSpeed-minSpeed)/knapsack.getCapacity()));
            if(currSpeed > minSpeed){
                currTime = currDistance/currSpeed;
            }else{
                currTime = currDistance/minSpeed;
            }
            time = time + currTime;
            currCity = nearestCity;
            currCityId = nearestCityId;
            toGo.remove(nearestCityId);

        }
        currDistance = countDistance(currCity.coorX,currCity.coorY,start.coorX,start.coorY);
        distance = distance + currDistance;
        knapsack.takeItem(chooseItem(currCity.items));
        currSpeed = maxSpeed - (knapsack.getWeight()*((maxSpeed-minSpeed)/knapsack.getCapacity()));
        if(currSpeed > minSpeed){
            currTime = currDistance/currSpeed;
        }else{
            currTime = currDistance/minSpeed;
        }
        time = time + currTime;
    }


    public Item chooseItem(ArrayList<Item> list){
        sortItems(list);
        Item bestItem = noItem ;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getWeight()<=knapsack.getLeftSpace()){
                bestItem = list.get(i);
                return bestItem;
            }
        }
        return bestItem;
    }
    private void sortItems(ArrayList<Item> list){
        list.sort(Item::compareTo);
    }

    public void setFitnessVal(){
        fitnessVal = knapsack.getValue() - (int)(time * knapsack.rentingRatio);
    }

    public int countDistance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }else {
            return (int)(Math.ceil(Math.sqrt((Math.pow((lat2-lat1),2)+Math.pow((lon2-lon1),2)))));
        }
    }

    public void setTime(double time) {
        this.time = time;

    }

    public int getFitnessVal() {
        return fitnessVal;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    public void setEnlargedFitnessVal(int plus){
        enlargedFitnessVal= (fitnessVal+plus)*1000;
    }

    public void crossing(Thief other){
        ArrayList<Integer> allGensCheck1 = (ArrayList)chromosome.genes.clone();
        ArrayList<Integer> allGensCheck2 = (ArrayList)allGensCheck1.clone();
        ArrayList<Integer> selfGens = chromosome.genes;
        ArrayList<Integer> otherGens =other.chromosome.genes;
        int pivot = allGensCheck1.size()/2;


        ArrayList<Integer> cloneSelfGens = (ArrayList)selfGens.clone();
        for (int i=allGensCheck1.size()-1; i>=pivot; i-- ){
            selfGens.set(i,otherGens.get(i));
            otherGens.set(i,cloneSelfGens.get(i));
        }

        ArrayList<Integer> finalSelf = new ArrayList<>();
        ArrayList<Integer> finalOther = new ArrayList<>();
        Collections.sort(allGensCheck1);
        for(int i=0; i<selfGens.size(); i++) {
            if(finalSelf.indexOf(selfGens.get(i))<0){
                finalSelf.add(selfGens.get(i));
                boolean isRemoved = allGensCheck1.remove(selfGens.get(i));
            }
            else{
                finalSelf.add(-1);
            }
        }
        for(int i = 0; i<finalSelf.size(); i++){
            if(finalSelf.get(i)==-1){
                finalSelf.set(i, allGensCheck1.remove(0));
            }
        }


        //other:
        Collections.sort(allGensCheck2);
        for(int i=0; i<otherGens.size(); i++) {
            if(finalOther.indexOf(otherGens.get(i))<0){
                finalOther.add(otherGens.get(i));
                boolean isRemoved = allGensCheck2.remove(otherGens.get(i));
            }
            else{
                finalOther.add(-1);
            }
        }

        for(int i = 0; i<finalOther.size(); i++){
            if(finalOther.get(i)==-1){
                finalOther.set(i, allGensCheck2.remove(0));
            }
        }

        chromosome.genes = finalSelf;
        other.chromosome.genes = finalOther;
    }

    public Thief copy(){
        return new Thief(this);
    }

    public int compareTo(Object o) {
        Thief other = (Thief)o;
        return this.fitnessVal<other.fitnessVal?1:this.fitnessVal==other.fitnessVal?0:-1;
    }

    private int getKnapsackCapacity(){
        return knapsack.capacity;
    }
    private double getKnapsackRentingRatio(){
        return knapsack.rentingRatio;
    }



}
