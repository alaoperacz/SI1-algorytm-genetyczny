import java.util.ArrayList;
import java.util.Collections;

public class Chromosome {

    ArrayList<Integer> genes;
    boolean mutable;
    int pM;

    public Chromosome(ArrayList<Integer> g, boolean child, int pm){
        this.pM = pm;
        if(!child){
            Collections.shuffle(g);
            genes = new ArrayList<Integer>(g);
        }else {
            genes = new ArrayList<Integer>(g);
        }

        if((int)(Math.random()*100)<pM){
            mutable = true;
        }else{
            mutable = false;
        }
    }

    public void mutate(){
        int s = genes.size();
        int g1 = randomGene();
        int g2 = randomGene();
        swap(g1,g2);
    }

    public void swap(int g1, int g2){
        int temp = genes.get(g1);
        genes.set(g1,genes.get(g2));
        genes.set(g2,temp);
    }

    int randomGene() {
        return (int)(Math.random() * genes.size());
    }

    public String toString(){
        String result = "[";
        for(int i=0;i<genes.size();i++){
            result+=genes.get(i)+",";
        }
        result+="]";
        return result;
    }

    public Chromosome clone(){
        return new Chromosome(new ArrayList<Integer>(genes), true, pM);
    }

}
