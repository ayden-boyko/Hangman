import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backtracker.Backtracker;
import backtracker.Configuration;

public class Hangsolver implements Configuration{
    private Set<Character> movelist;
    private List<String> lexi;

    private Hangman game;

    public Hangsolver(Hangman game){
        this.game = game;
        this.movelist = new HashSet<>();
        this.lexi = new ArrayList<>(
            Arrays.asList("E", "A", "R" ,"I",
                                "I", "O", "T", "N",
                                "S", "L", "C", "U",
                                "D", "P", "M", "H", 
                                "G", "B", "F", "Y", 
                                "W", "K", "V", "X", 
                                "Z", "J", "Q"));//most common to least common letters, makes it faster
    }

    public Hangsolver(Hangman game, Set<Character> movelist){
        this.game = game;
        this.movelist = movelist;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        
        List<Configuration> successors = new ArrayList<>();

        for (String let : lexi){
            if (game.getStatus() == Hangman.Status.IN_PROGRESS){
                Hangman copy = new Hangman(this.game);
                Set<Character> movelistcopy = new HashSet<>();
                for (Character letter : movelist){
                    movelistcopy.add(letter);
                }
                movelistcopy.add(Character.valueOf(let.charAt(0)));

                copy.guess(Character.valueOf(let.charAt(0)));
                Hangsolver successor = new Hangsolver(copy, movelistcopy);
                successors.add(successor);
            }
        }
        return successors;
    }

    @Override
    public boolean isValid() {
        return game.getStatus() != Hangman.Status.LOST;
    }

    public Set<Character> getMoveList(){
        return movelist;
    }

    @Override
    public boolean isGoal() {
        return game.getStatus() == Hangman.Status.WON;
    }

    @Override
    public String toString(){
        return movelist + "\n" + game;
    }

    public static Hangsolver solver(Hangman game){
        Hangsolver finder = new Hangsolver(game);
        Backtracker backtracker = new Backtracker(true);
        if (backtracker.solve(finder) != null){
            System.out.println(backtracker.solve(finder));
        }
        else{
            System.out.println("no solution");
        }
        return finder;
    }
    
    public static void main(String[] args) {
        Hangman game = new Hangman("Never gonna give you up");
        Hangsolver result = new Hangsolver(game);  
        Hangsolver.solver(game);
    }
}