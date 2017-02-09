/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.lifelets.rps;

/**
 *
 * @author zeroadmin
 */
public class Result {
    
    private String winner;
    private String winnerChoice;
    private String loserChoice;

    public Result(String winner, String winnerChoice, String loserChoice) {
        this.winner = winner;
        this.winnerChoice = winnerChoice;
        this.loserChoice = loserChoice;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinnerChoice() {
        return winnerChoice;
    }

    public void setWinnerChoice(String winnerChoice) {
        this.winnerChoice = winnerChoice;
    }

    public String getLoserChoice() {
        return loserChoice;
    }

    public void setLoserChoice(String loserChoice) {
        this.loserChoice = loserChoice;
    }

    @Override
    public String toString() {
        return "Winner is [ "+this.winner+" ]"+"user choice : "+this.winnerChoice+
                "__"+"systems Choice : "+this.loserChoice+"-->";
    }
    
}
