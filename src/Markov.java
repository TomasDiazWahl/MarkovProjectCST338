import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Markov {
    private static final String PUNCTUATION_MARKS = " $";
    private static final String ENDS_IN_PUNCTUATION = ".?!$";
    private String prevWord;
    private HashMap <String, ArrayList<String>> words;

    //constructor
    public Markov(){
        this.words = new HashMap<>();
        prevWord = ENDS_IN_PUNCTUATION;
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
        holdLine = readFile.nextLine();
        addLine(holdLine);
    }

    void addLine(String line){
        String[] word;
        if (line.length() < 1){
            System.out.println("Could not read line");
        }
        else {
            word = line.split("\\s+");
            addWord(word);
        }

    }

    void addWord(String[] word) {
    }
//Hello

}
