package classification;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Questions {
    private ArrayList<String> questions;
    private String[] answers;
    public double[] answersDouble;

    // Read questions from a file
    public Questions(String filename) {
        questions = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                questions.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the questions.");
        }
        answers = new String[questions.size()];
    }

    // Ask questions and store answers
    public void askQuestions() {
        System.out.println("Please answer the following questions: ");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(questions.get(i));
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                answers[i] = reader.readLine();
            } catch (IOException e) {
                System.out.println("An error occurred while reading your answer.");
            }
        }
    }

    // Convert answers to doubles using information about test evaluation
    public void processAnswers(){
        answersDouble = new double[answers.length];
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].equals("yes") || answers[i].equals("no")) {
                if(i == 0 || i == 6 || i == 7 || i == 9 || i == 12 || i == 13 ){
                    answersDouble[i] = answers[i].equals("yes") ? 1 : 0;
                } else if(i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 8){
                    answersDouble[i] = answers[i].equals("yes") ? 0 : 1;
                }
            } else if (answers[i].equals("m")) {
                answersDouble[i] = 1;
            } else if (answers[i].equals("f")){
                answersDouble[i] = 0;
            } else {
                answersDouble[i] = Double.parseDouble(answers[i]);
            }
        }

    }

    
}
