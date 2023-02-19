package classification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A Logistic Regression model for binary classification.
 */
public class LogisticRegression {
    private final int numFeatures;
    private double[] weights;
    private final double learningRate;
    private int maxIterations;

    /**
     * Constructs a new LogisticRegression object
     *
     * @param numFeatures   the number of features in the dataset
     * @param learningRate  the learning rate for gradient descent
     * @param maxIterations the maximum number of iterations for training the model
     */
    public LogisticRegression(int numFeatures, double learningRate, int maxIterations){
        this.numFeatures = numFeatures;
        this.weights = new double[numFeatures];
        this.learningRate = learningRate;
        this.maxIterations = maxIterations;
    }

    /**
     * Sigmoid function
     *
     * @param x the input value
     * @return  the output of the sigmoid function
     */
    private double sigmoid(double x){
        return 1.0 / (1 + Math.exp(-x));
    }

    /**
     * Dot product of two vectors
     *
     * @param a the first vector
     * @param b the second vector
     * @return  the dot product of the two vectors
     */
    public double dotProduct(double[] a, double[] b){
        double sum = 0;
        for (int i = 0; i < a.length; i++)
            sum += a[i] * b[i];
        
        return sum;
    }

    /**
     * Train the model.
     *
     * @param X the feature matrix of the training dataset
     * @param y the target vector of the training dataset
     */
    public void fit(double [][] X, int[] y){
        for(int m = 0; m < maxIterations; m++){
            for (int i = 0; i < X.length; i++){
                double[] x = X[i];
                double predicted = predict(x);
                int label = y[i];
                for (int j = 0; j < numFeatures; j++)
                    weights[j] = weights[j] + learningRate * (label - predicted) * x[j];
            }
        }
    }

    /**
     * Predict the label of a single sample.
     *
     * @param x the input sample
     * @return  the predicted label (0 or 1)
     */
    public int predict(double[] x){
        double z = 0.0;
        for (int i = 0; i < numFeatures; i++) 
            z += weights[i] * x[i];

        return sigmoid(z) >= 0.5 ? 1 : 0;
    }

    /**
     * Save the model weights to a file weights.txt
     */
    public void saveModel(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("weights.txt"));
            for (int i = 0; i < weights.length; i++) {
                writer.write(Double.toString(weights[i]));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e){
            System.out.println("An error occurred while saving the weights to file.");
        }
    }


    /**
     * Load the model from the file weights.txt
     */
    public void loadModel(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("weights.txt"));
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                weights[i] = Double.parseDouble(line);
                line = reader.readLine();
                i++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while loading the weights from file.");
        }
    }
}
