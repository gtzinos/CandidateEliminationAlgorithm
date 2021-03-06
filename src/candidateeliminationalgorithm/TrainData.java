/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package candidateeliminationalgorithm;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author George Tzinos
 */
public class TrainData {
    
    ArrayList<ArrayList<String>>possibleValues;
    ArrayList<String[]> trainingData;
    String[] S;
    ArrayList<String[]> G;
    int attributesLength;
    
    public TrainData(ArrayList<String[]> trainingData, int attributesLength) {
        this.trainingData = trainingData;
        this.attributesLength = attributesLength;
        this.possibleValues = new ArrayList<>();
        this.S = new String[attributesLength];
        this.G = new ArrayList<String[]>();
        
        this.init();
    }
    
    public void init() {
        for(int i = 0; i < this.S.length; i++) {
            this.S[i] = "0";
        }
        
        String gtemp[] = new String[this.attributesLength];
        for(int i = 0; i < gtemp.length; i++) {
            gtemp[i] = "?";
        }
        this.G.add(gtemp);
        
        for(int i = 0; i < this.attributesLength; i++) {
            this.possibleValues.add(i, new ArrayList<String>());
        }
    }
    
    public void train() {
        for(int i = 0; i < this.trainingData.size(); i++) {
            String x[] = this.trainingData.get(i);
            this.savePossibleValues(x);
            
            if(this.isPositive(x)) {
                this.trainAsPositive(x);
            }
            //negative
            else {
                this.trainAsNegative(x);
            }
            
            this.printSGX(i);
        }
    }
    
    public void savePossibleValues(String x[]) {
        for(int i = 0; i < this.attributesLength; i++){
            if(this.possibleValues.get(i).indexOf(x[i]) == -1) {
                this.possibleValues.get(i).add(x[i]);
            }
        }
    }
    
    public void printSGX(int trainIndex) {
        System.out.print("X"  + (trainIndex+1) + ": ");
        System.out.println(Arrays.toString(this.trainingData.get(trainIndex)));
        
        System.out.print("S"  + (trainIndex+1) + ": ");
        System.out.println(Arrays.toString(this.S));

        System.out.print("G" + (trainIndex+1) +": ");
        for(int i = 0; i < this.G.size(); i++) {
            System.out.print(Arrays.toString(this.G.get(i)));
            
            if((i+1) < this.G.size()) {
                System.out.print(", ");
            }
        }
        System.out.println();
        System.out.println();
    }
    
    public boolean isPositive(String[] x) {
        if(String.valueOf(x[x.length - 1]).equals("Y") || String.valueOf(x[x.length - 1]).equals("1")) {
            return true;
        }
        
        return false;
    }
    
    public boolean isNegative(String[] x) {
        if(String.valueOf(x[x.length - 1]).equals("N") || String.valueOf(x[x.length - 1]).equals("0")) {
            return true;
        }
        
        return false;
    }
    
    public void trainAsPositive(String[] x) {
            
        for(int i = 0; i < x.length - 1; i++) {
            if(!this.S[i].equals("0") && !this.S[i].equals(x[i])) {
                this.S[i] = "?";
            }
            else {
                this.S[i] = x[i];
            }
        }
        
       
        
       this.removeFromG();
    }
    
    public void trainAsNegative(String[] x) {
        int xLength = x.length;
        int gLength = this.G.size();
        
            for(int gRowIndex = 0; gRowIndex < gLength; gRowIndex++) {
                for(int xIndex = 0; xIndex < xLength - 1; xIndex++) {
                    if(this.G.size()-1 < gRowIndex) {
                        continue;
                    }
                    String gValue = this.G.get(gRowIndex)[xIndex];
                    if(!gValue.equals("?") && !gValue.equals(x[xIndex])) { 
                        this.G.remove(gRowIndex);
                        break;
                    }
                    else if(gValue.equals("?")){
                        if(!this.S[xIndex].equals("?")) {
                        this.specializeGRow(gRowIndex, x);
                        }
                        break;
                    }
                    
                    //Same value
                }
            }
            
        this.removeFromG();
    }
    
    public void removeFromG() {
        for(int i = 0; i < this.G.size(); i++) {
            for(int j = 0; j < this.G.get(i).length; j++) {
                if(!this.G.get(i)[j].equals("?") && !this.S[j].equals(this.G.get(i)[j])) {
                    this.G.remove(i);
                    break;
                }
            }
        }
    }
    
    public void specializeGRow(int gRowIndex, String x[]) {
        String oldRow[] = Arrays.copyOf(this.G.get(gRowIndex), this.G.get(gRowIndex).length);
        this.G.remove(gRowIndex);
        
        for(int gCellIndex = 0; gCellIndex < oldRow.length; gCellIndex++) {
            String gValue = oldRow[gCellIndex];
            if(gValue.equals("?")) {
                
                String newGRowValue = null;
                for(int i = 0; i < this.possibleValues.get(gCellIndex).size(); i++) {
                    if(!this.possibleValues.get(gCellIndex).get(i).equals(x[gCellIndex])) {
                        newGRowValue = this.possibleValues.get(gCellIndex).get(i);
                        break;
                    }
                }
               
                if(newGRowValue != null) {
                    String newRow[] =  Arrays.copyOf(oldRow, oldRow.length);
                    newRow[gCellIndex] = newGRowValue;
                    this.G.add(this.G.size() > 0 ? this.G.size() - 1 : 0, newRow);
                }
                
               // ?, ?
              //  a, 1
            }
        }
    }
}
