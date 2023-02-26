public class Main {
    public static void printMarkovTests (String filename){
        Markov markov = new Markov();

        markov.addFromFile(filename);
        System.out.println(markov); // DEBUG

        for (int i = 0; i < 10; i ++){
            System.out.println(markov.getSentence());
        }
    }

    public static void main(String[] args) {
        printMarkovTests("spam.txt");
        printMarkovTests("phrases.txt");
        printMarkovTests("hamlet.txt");
    }

}
