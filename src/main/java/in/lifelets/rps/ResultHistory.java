/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.lifelets.rps;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zeroadmin
 */
public class ResultHistory {
    
    private List<Result> results;

    public ResultHistory() {
        this.results = new ArrayList<Result>();
    }
    
    public void addRecord(String winner, String winnerChoice, String loserChoice){
        this.results.add(new Result(winner, winnerChoice, loserChoice));
    }
    
    public void addResult(Result result){
        this.results.add(result);
    }
    
    public List<Result> getResults(){
        return this.results;
    }
    
    public void printRecords(){
        for(Result result : this.results){
            System.out.println(result);
        }
    }

    @Override
    public String toString() {
        String output = "";
        for(Result result : this.results){
            output += result;
        }
        return output;
    }
    
    
}
