package bearmaps.hw4.integerhoppuzzle;

import bearmaps.hw4.AStarGraph;
import bearmaps.hw4.WeightedEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * The Integer Hop puzzle implemented as a graph.
 * Created by hug.
 */
public class IntegerHopGraph implements AStarGraph<Integer> {

    @Override
    public List<WeightedEdge<Integer>> neighbors(Integer v) {
        ArrayList<WeightedEdge<Integer>> neighbors = new ArrayList<>();
        neighbors.add(new WeightedEdge<>(v, v * v, 10));
        neighbors.add(new WeightedEdge<>(v, v * 2, 5));
        neighbors.add(new WeightedEdge<>(v, v / 2, 5));
        neighbors.add(new WeightedEdge<>(v, v - 1, 1));
        neighbors.add(new WeightedEdge<>(v, v + 1, 1));
        return neighbors;
    }

    @Override
    public double estimatedDistanceToGoal(Integer s, Integer goal) {
        // possibly fun challenge: Try to find an admissible heuristic that
        // speeds up your search. This is tough!
        int a = Math.abs(s - 1 - goal);
        int e = Math.abs(s + 1 - goal);
        int b = Math.abs(s * 2 - goal);
        int c = Math.abs(s * s - goal);
        int d = Math.abs((s / 2) - goal);
        int[] number = new int[]{a, b, c, d, e};

        int min = a;
        for (int i = 0; i < 4; i += 1){
            if (number[i] < min){
                min = number[i];
            }
        }
        if (min == a || min == e){
            return 1;
        }
        else if (min == b || min == d){
            return 5;
        }
        else {
            return 10;
        }
    }
}
