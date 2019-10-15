import java.util.ArrayList;

public class Knapsack {

    final int capacity;
    int leftSpace;
    int weight;
    int value;
    double rentingRatio;
    ArrayList<Item> items;

    public Knapsack(int c, double rR){
        this.capacity = c;
        this.rentingRatio = rR;
        this.leftSpace = c;
        this.weight = 0;
        this.value = 0;
        this.items = new ArrayList<Item>();
    }

    public int getCapacity() {
        return capacity;
    }
    public double getRentingRatio() {
        return rentingRatio;
    }
    public int getLeftSpace() {
        return leftSpace;
    }
    public int getWeight() {
        return weight;
    }
    public int getValue() {
        return value;
    }

    public void setLeftSpace() {
        this.leftSpace = capacity - weight;
    }

    public void setWeight(Item i) {
        this.weight = weight + i.getWeight();
    }

    public void setValue(Item i) {
        this.value = value + i.getProfit();
    }

    public void takeItem(Item i){
        items.add(i);
        setWeight(i);
        setLeftSpace();
        setValue(i);
    }
}