import java.util.ArrayList;

public class City {

    int iD;
    double coorX;
    double coorY;
    ArrayList<Item> items = new ArrayList<Item>();

    public City(int id, double x, double y){
        this.iD = id;
        this.coorX = x;
        this.coorY = y;
    }

    public void addItem(Item i){
        items.add(i);
    }

    public double getCoorX() {
        return coorX;
    }

    public double getCoorY() {
        return coorY;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getiD() {
        return iD;
    }

    @Override
    public String toString() {
        return "city nr "+iD+" "+"coorX:"+coorX+", coorY:"+coorY+"; ITEMS: " + items+ " ";
    }
}
