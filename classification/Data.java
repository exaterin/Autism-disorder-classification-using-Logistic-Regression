package classification;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data {
    public  String[][] strings;
    public double[][] transformedData;
    public int[] classLabels;


    // Read data from a file and store it in a 2D array of strings
    public void readData(String filename){
        List<String[]> data = new ArrayList<>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                line = line.replace("'", "");
                String [] row = line.split(",");
                data.add(row);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the data.");
        }

        strings = data.toArray(new String[data.size()][]);
    }

    // Remove given columns from the data
    public void removeColumns(int[] columns){
        int numCols = strings[0].length;
        String[][] newData = new String[strings.length][numCols - columns.length];

        for (int row = 0; row < strings.length; row++) {
            int newColIndex = 0;
            for (int col = 0; col < numCols; col++) {
                boolean remove = false;
                for (int i = 0; i < columns.length; i++) {
                    if (col == columns[i]) {
                        remove = true;
                        break;
                    }
                }
                if (!remove) {
                    newData[row][newColIndex] = strings[row][col];
                    newColIndex++;
                }
            }
        }
        strings = newData;
    }

    // Extract class lables from the last column of the data
    public void extractLabels(){
        int numRows = strings.length;
        int numCols = strings[0].length;
        int[] labels = new int[numRows];
        for (int row = 0; row < numRows; row++) {
            labels[row] = strings[row][numCols - 1].equals("YES") ? 1 : 0;
        }
        classLabels = labels;
    }

    // Transform given columns into cathegorical features
    public void transformColumns(int[] columns){
        int numRows = this.strings.length;
        int numCols = this.strings[0].length;
        Map<Integer, Map<String, Integer>> columnValues = new HashMap<>();
        for (int col : columns) {
            Map<String, Integer> values = new HashMap<>();
            for (int row = 0; row < numRows; row++) {
                String value = strings[row][col];
                if (!values.containsKey(value)) {
                    values.put(value, values.size());
                    if (value.equals("yes") || value.equals("no")) {
                        values.put(value, value.equals("yes") ? 1 : 0);
                    }
                    if (value.equals("m") || value.equals("f")) {
                        values.put(value, value.equals("m") ? 1 : 0);
                    }
                }
            }
            columnValues.put(col, values);
        }
        String[][] newData = new String[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (columnValues.containsKey(col)) {
                    Map<String, Integer> values = columnValues.get(col);
                    newData[row][col] = Integer.toString(values.get(strings[row][col]));
                } else {
                    newData[row][col] = strings[row][col];
                }
            }
        }
        strings = newData;
    }

    // Convert the data from strings to doubles
    public void convertToDouble(){
        int numRows = strings.length;
        int numCols = strings[0].length;
        double[][] data = new double[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                data[row][col] = Double.parseDouble(strings[row][col]);
            }
        }
        transformedData = data;
    }

}
