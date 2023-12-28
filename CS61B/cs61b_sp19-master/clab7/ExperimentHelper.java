/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        int e = 0;
        int sum = 0;
        int flag = 2;
        for (int i = 1; i <= N; i++){
            if (i % flag == 0){
                e += 1;
                flag *= 2;
            }
            sum += e;
        }
        return sum;
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        return (double)optimalIPL(N) / N;
    }

    public static void insertrandomly(BST node){
        int item = RandomGenerator.getRandomInt(10000);
        while (node.contains(item)){
            item = RandomGenerator.getRandomInt(10000);
        }
        node.add(item);
    }

    public static void deleterandomly(BST node){
        node.deleteTakingSuccessor(node.getRandomKey());
    }

    public static void main(String[] arg){
        System.out.println(ExperimentHelper.optimalIPL(8));
        System.out.println(ExperimentHelper.optimalAverageDepth(5000));
    }
}
