public class Item implements Comparable {

    int iD;
    int cityID;
    final int weight;
    final int profit;

    public Item(int id, int p, int w, int cID){
        this.iD = id;
        this.cityID = cID;
        this.weight = w;
        this.profit = p;
    }

    public int getProfit() {
        return profit;
    }

    public int getWeight() {
        return weight;
    }

    public int getCityID() {
        return cityID;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + iD +
                ", value=" + profit +
                ", weight=" + weight +
                '}'+
                "\n";
    }

    @Override
    public int compareTo(Object o) {
        Item it2 = (Item) o;
        //return profit>it2.profit?-1:(profit==it2.profit?0:1);
        return profit/weight>it2.profit/it2.weight?-1:(profit/weight==it2.profit/it2.weight?0:1);
        //return weight<it2.weight?-1:(weight==it2.weight?0:1);
    }

}
