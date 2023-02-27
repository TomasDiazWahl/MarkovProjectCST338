/*-----------------
Title: Markov
Abstract: This program is the main for Markov.java and runs 10 tests of the markov text generation from the test file.
Author: Tomas Diaz-Wahl
Date: 2023-02-25
--------------------*/

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
