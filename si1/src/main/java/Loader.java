import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {

    static String PROBLEM_NAME;
    static String KNAPSACK_DATA_TYPE;
    int DIMENSIONS;
    int NUMBER_OF_ITEMS;
    int CAPACITY_OF_KNAPSACK;
    float MIN_SPEED;
    float MAX_SPEED;
    float RENTING_RATIO;
    String EDGE_WEIGHT_TYPE;

    String filePath;


    private ArrayList fileData;
    private ArrayList<Double> fileNodeCoords;
    private ArrayList profits;
    private ArrayList weights;
    private ArrayList assigned_node_nums;
    public ArrayList items;
    public ArrayList cities;
    public ArrayList<Integer> genes;

    public Loader(String fp) {
        this.filePath = fp;
        loadData();
    }


    public ArrayList<Double> getFileNodeCoords() {
        return fileNodeCoords;
    }
    public void setFileNodeCoords(ArrayList fileNodeCoords) {
        this.fileNodeCoords = fileNodeCoords;
    }

    public ArrayList getProfits() { return profits; }
    public void setProfits(ArrayList profits) {
        this.profits = profits;
    }

    public ArrayList getWeights() { return weights; }
    public void setWeights(ArrayList weights) {
        this.weights = weights;
    }

    public ArrayList getAssigned_node_nums() { return assigned_node_nums; }
    public void setAssigned_node_nums(ArrayList assigned_node_nums) {
        this.assigned_node_nums = assigned_node_nums;
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Integer> genes) {
        this.genes = genes;
    }

    private void read_file() {

        FileReader fileReader = null;
        String textLine = "";
        fileData = new ArrayList<String>();
        fileNodeCoords = new ArrayList<Double>();
        profits = new ArrayList<Integer>();
        weights = new ArrayList<Integer>();
        assigned_node_nums = new ArrayList<Integer>();

        String[] words;
        int numOfReadLines = 0;

        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("error with opening the file");
            System.exit(1);
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        try {

            fileData.add(bufferedReader.readLine().split("\t")[1]);
            fileData.add(bufferedReader.readLine());
            numOfReadLines = 2;
            while ((textLine = bufferedReader.readLine()) != null) {
                words = textLine.split("\t");

                if (words.length == 2) {
                    fileData.add(words[1]);
                }
                else if (words.length == 3) {
                    fileNodeCoords.add(Double.parseDouble(words[1]));
                    fileNodeCoords.add(Double.parseDouble(words[2]));
                } else {
                    profits.add(Integer.parseInt(words[1]));
                    weights.add(Integer.parseInt(words[2]));
                    assigned_node_nums.add(Integer.parseInt(words[3]));
                }

                numOfReadLines++;
            }
        } catch (IOException e) {
            System.out.println("error with reading the file");
            System.exit(2);
        }

        try {
            fileReader.close();
        } catch (IOException e) {
            System.out.println("error with closing the file");
            System.exit(3);
        }

    }

    private void instantiate_data(ArrayList<String> dataToReadFrom) {

        PROBLEM_NAME = dataToReadFrom.get(0);
        KNAPSACK_DATA_TYPE = dataToReadFrom.get(1);
        DIMENSIONS = Integer.parseInt(dataToReadFrom.get(2));
        NUMBER_OF_ITEMS = Integer.parseInt(dataToReadFrom.get(3));
        CAPACITY_OF_KNAPSACK = Integer.parseInt(dataToReadFrom.get(4));
        MIN_SPEED = Float.parseFloat(dataToReadFrom.get(5));
        MAX_SPEED = Float.parseFloat(dataToReadFrom.get(6));
        RENTING_RATIO = Float.parseFloat(dataToReadFrom.get(7));
        EDGE_WEIGHT_TYPE = dataToReadFrom.get(8);
    }

    private void loadData() {
        read_file();
        instantiate_data(fileData);
        set_items_data();
        set_cities_data();
    }

    private void set_cities_data() {
        cities = new ArrayList<City>();
        genes = new ArrayList<Integer>();
        for(int i = 0; i < (fileNodeCoords.size() / 2); i++) {
            City current_city = new City(i+1, fileNodeCoords.get(2*i), fileNodeCoords.get((2*i)+1));
            genes.add(i+1);
            for(int j = 0; j < items.size(); j++) {
                Item current_item = (Item) items.get(j);
                if(current_item.cityID == i+1)
                    current_city.addItem(current_item);
            }
            cities.add(current_city);
        }

    }

    private void set_items_data() {
        items = new ArrayList<Item>();
        for(int i = 0; i < profits.size(); i++) {
            items.add(new Item(i+1, (Integer) profits.get(i), (Integer) weights.get(i),
                    (Integer) assigned_node_nums.get(i)));
        }
    }

    public void print(ArrayList l){
        for(int i = 0; i < l.size(); i++) {
            System.out.print(l.get(i).toString());
        }
    }

}