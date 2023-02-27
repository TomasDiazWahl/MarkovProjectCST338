/*-----------------
Title: Markov
Abstract: This program reads a text file and generates a series words and words that follow those words
Author: Tomas Diaz-Wahl
Date: 2023-02-25
--------------------*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.exit;

public class Markov {
    private static final boolean DEBUG = false;
    private static final String PUNCTUATION_MARKS = ".!?$";
    private static final String ENDS_IN_PUNCTUATION = "__$";
    private String prevWord;
    private HashMap <String, ArrayList<String>> words = new HashMap<String, ArrayList<String>>();

    //constructor
    public Markov(){
        this.words.put(ENDS_IN_PUNCTUATION, new ArrayList<String>());
        this.prevWord = ENDS_IN_PUNCTUATION;
    }

    // endsWithPunctuation takes a string, converts it to a regular expression and
    // checks to see if it ends with punctuation
    static boolean endsWithPunctuation (String word){
        boolean test;
        int lastIndex = word.length() - 1;
        String lastChar = word.substring(lastIndex);
        String punctuation = "[" + PUNCTUATION_MARKS +"]";
        try {
            test = lastChar.matches(punctuation);
        } catch (Exception e) {
            System.out.println("Unknown error in endsWithPunctuation");
        }
        test = lastChar.matches(punctuation);
        return test;
    }

    //addFromFile takes in the filename as a string and attempts to open and parse the file
    public void addFromFile (String filename){
        String holdLine;
        //open file
        File file = null;
        Scanner readFile = null;
        try{
            file = new File(filename);
        }
        catch (NullPointerException e){
            System.out.println("File " + filename +  " does not exist");
        }
        try{
            readFile = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file "+ filename);;
        }

        //call addLine to parse individual lines
        while(readFile.hasNextLine()){
            holdLine = readFile.nextLine();
            addLine(holdLine);
        }
    }

    // addLine takes in an untrimmed line and removes whitespace and check to make sure the
    // line is not empty
    void addLine(String untrimmedLine){
        String trimmedLine = untrimmedLine.trim();
        String[] words;
        if (trimmedLine.length() < 1){
            if (DEBUG){
                System.out.println("There are no words in this line.");
            }
            return;
        }
        else {
            words = trimmedLine.split("\\s+");
            for (String word: words){
                addWord(word);
            }
        }
    }

    // addWord checks the previous word to decide which key in the hashmap the current word should be stored
    // under. If no key exists the current word is added as a key
    void addWord(String currentWord) {
        ArrayList<String> tempList;
        if (endsWithPunctuation(prevWord)){
            tempList = words.get(ENDS_IN_PUNCTUATION);
            tempList.add(currentWord);
            words.put(ENDS_IN_PUNCTUATION, tempList);
        }
        else if (words.containsKey(prevWord)){
            tempList = words.get(prevWord);
            tempList.add(currentWord);
            words.put(prevWord, tempList);
        }
        else{
            tempList = new ArrayList<String>();
            tempList.add(currentWord);
            words.put(prevWord, tempList);
        }
        prevWord = currentWord;
    }

    // randomWord selects a random word from the HashMap whose key is the passed in String
    String randomWord (String word){
        ArrayList<String> tempList;
        Random pickWord = new Random();
        int index;
        try {
            tempList = words.get(word);
        } catch (Exception e) {
            System.out.println("Key does not exist in HashMap");
        }
        tempList = words.get(word);
        if (tempList.isEmpty()){
            System.out.println("randomWord: no words to return for this key");
            exit(-1);
        }
        index = pickWord.nextInt(tempList.size());
        return tempList.get(index); // return variable instead
    }

    // getSentence constructs a sentence using the HashMap key value pair logic
    // to randomly select words in the sentence
    public String getSentence (){
        String currentWord;
        String sentence = "";
        currentWord = randomWord(ENDS_IN_PUNCTUATION);
        while (!endsWithPunctuation(currentWord)){
            sentence = sentence + currentWord + " ";
            currentWord = randomWord(currentWord);
        }
        sentence = sentence + currentWord;
        return sentence;
    }

    // getWords returns the HashMap words
    HashMap<String, ArrayList<String>> getWords (){
        return words;
    }

    // toString is the toString method for Markov.java    public String toString() {
        String tempString = words.toString();
        return tempString;
    }
}
// ----- End of program
