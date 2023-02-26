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

    //methods
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

    HashMap<String, ArrayList<String>> getWords (){
        return words;
    }

    public String toString() {
        String tempString = words.toString();
        return tempString;
    }
}
// ----- End of program
