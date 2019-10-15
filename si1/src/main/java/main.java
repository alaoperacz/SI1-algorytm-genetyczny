import java.io.FileNotFoundException;

public class main {

    public static void main(String[] args) throws FileNotFoundException {

/*
       Evaluator evaluator = new Evaluator("C:\\Users\\alicj\\Desktop\\pwr6\\SI\\cw1\\hard_4.ttp");
       evaluator.runAlgorithm();
*/

        System.out.println("plik hard_0");
        Evaluator evaluator1 = new Evaluator("C:\\Users\\alicj\\Desktop\\pwr6\\SI\\cw1\\easy_0.ttp");
        evaluator1.runAlgorithm();
       /* System.out.println("plik hard_1");
        Evaluator evaluator2 = new Evaluator("C:\\Users\\alicj\\Desktop\\pwr6\\SI\\cw1\\hard_4.ttp");
        evaluator2.runAlgorithm();
        System.out.println("plik hard_2");
        Evaluator evaluator3 = new Evaluator("C:\\Users\\alicj\\Desktop\\pwr6\\SI\\cw1\\hard_2.ttp");
        evaluator3.runAlgorithm();
        System.out.println("plik hard_3");
        Evaluator evaluator4 = new Evaluator("C:\\Users\\alicj\\Desktop\\pwr6\\SI\\cw1\\hard_3.ttp");
        evaluator4.runAlgorithm();
        System.out.println("plik hard_4");
        Evaluator evaluator5 = new Evaluator("C:\\Users\\alicj\\Desktop\\pwr6\\SI\\cw1\\hard_4.ttp");
        evaluator5.runAlgorithm();*/
        Evaluator e = new Evaluator("C:\\Users\\alicj\\Desktop\\pwr6\\SI\\cw1\\hard_4.ttp");

        e.notGeneticNotGeneticNearestCity();
    }
}
