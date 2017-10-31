/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package candidateeliminationalgorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author George Tzinos
 */
public class CandidateEliminationAlgorithm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("./data2.txt"));
            String s;
            ArrayList<String[]> trainingData = new ArrayList<>(); 
            
            while((s=br.readLine())!=null){
                    StringTokenizer st = new StringTokenizer(s,",");
                    int n = st.countTokens();
                    String[] trainingInstance = new String[n];
                    for(int i = 0; i < n; i++){
                            trainingInstance[i] = st.nextToken();
                    }
                    trainingData.add(trainingInstance);
            }
            
            br.close();
            
            int attributesLength = trainingData.get(0).length - 1;
            TrainData trainData = new TrainData(trainingData, attributesLength);
            trainData.train();
        } catch (Exception ex) {
            Logger.getLogger(CandidateEliminationAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
		
    }
    
}
