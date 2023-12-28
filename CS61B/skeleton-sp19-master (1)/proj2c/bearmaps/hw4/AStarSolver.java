package bearmaps.hw4;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private LinkedList<Vertex> solution = new LinkedList<>();
    private int numStatesExplored = 0;
    private double timeSpent;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout){
        ArrayHeapMinPQ<Vertex> PQ = new ArrayHeapMinPQ<>();
        Map<Vertex, Double> disto = new HashMap<>();
        Map<Vertex, Vertex> howtogo = new HashMap<>();

        Stopwatch sw = new Stopwatch();

        PQ.add(start, 0);
        disto.put(start, 0.0);

        while (PQ.size() > 0){

            if (PQ.getSmallest().equals(end)){
                outcome = SolverOutcome.SOLVED;
                solutionWeight = disto.get(end);

                Vertex nowpoint = PQ.removeSmallest();
                solution.addFirst(nowpoint);

                while (!nowpoint.equals(start)){
                    solution.addFirst(howtogo.get(nowpoint));
                    nowpoint = howtogo.get(nowpoint);
                }
                timeSpent = sw.elapsedTime();
                return;
            }

            timeSpent = sw.elapsedTime();

            if (timeSpent > timeout){
                outcome = SolverOutcome.TIMEOUT;
                solution = new LinkedList<>();
                solutionWeight = 0;
                return;
            }

            List<WeightedEdge<Vertex>> neighbors = input.neighbors(PQ.removeSmallest());
            numStatesExplored += 1;

            for (WeightedEdge<Vertex> edge: neighbors){
                Vertex p = edge.from();
                Vertex q = edge.to();
                Double w = edge.weight();

                if (!disto.containsKey(q)){
                    Double distoq = disto.get(p) + w;
                    disto.put(q, distoq);
                    howtogo.put(q, p);
                    PQ.add(q, distoq + input.estimatedDistanceToGoal(q, end));
                }
                else if (disto.get(p) + w < disto.get(q)){
                    disto.put(q, disto.get(p) + w);
                    howtogo.put(q, p);
                    if (PQ.contains(q)){
                        PQ.changePriority(q, disto.get(q) + input.estimatedDistanceToGoal(q, end));
                    }
                    else {
                        PQ.add(q, disto.get(q) + input.estimatedDistanceToGoal(q, end));
                    }
                }
            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
        solution = new LinkedList<>();
        solutionWeight = 0;
        timeSpent = sw.elapsedTime();
    }

    public SolverOutcome outcome(){
        return outcome;
    }

    public List<Vertex> solution(){
        return solution;
    }

    public double solutionWeight(){
        return solutionWeight;
    }

    public int numStatesExplored(){
        return numStatesExplored;
    }

    public double explorationTime(){
        return timeSpent;
    }
}
