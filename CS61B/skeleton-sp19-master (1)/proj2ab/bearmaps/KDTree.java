package bearmaps;

import java.util.Collections;
import java.util.List;

public class KDTree implements PointSet {
    private Node root;

    public KDTree(List<Point> points){
        for (Point point: points){
            root = insert(point, root, 0);
        }
    }

    public Point nearest(double x, double y){
        Point goal = new Point(x, y);
        return nearest(root, goal, root).getPoint();
    }

    public Node nearest(Node n, Point goal, Node best){
        Node bestside;
        Node badside;
        double gooddis = Point.distance(best.getPoint(), goal);
        if (n == null){
            return best;
        }
        if (Double.compare(Point.distance(n.getPoint(), goal), gooddis) < 0){
            best = n;
        }
        if (gorightorup(goal, n)){
            bestside = n.getRight();
            badside = n.getLeft();
        }
        else {
            bestside = n.getLeft();
            badside = n.getRight();
        }
        best = nearest(bestside, goal, best);
        if (gobad(n, goal, gooddis)){
            best = nearest(badside, goal, best);
        }
        return best;
    }

    private boolean gobad(Node n, Point goal, double nowgood){
        double badis;
        if (n.getLable() == 0){
            badis = Math.pow(n.getPoint().getX() - goal.getX(), 2);
        }
        else {
            badis = Math.pow(n.getPoint().getY() - goal.getY(), 2);
        }
        if (Double.compare(badis, nowgood) < 0){
            return true;
        }
        return false;
    }

    private boolean gorightorup(Point goal, Node n){
        if (n.getLable() == 0){
            if (n.getPoint().getX() <= goal.getX()){
                return true;
            }
        }
        else {
            if (n.getPoint().getY() <= goal.getY()){
                return true;
            }
        }
        return false;
    }

    private Node insert(Point p, Node n, int k){
        if (n == null){
            return new Node(p, k);
        }
        if (p.equals(n.getPoint())){
            return n;
        }
        if (n.lable == 0){
            if (Double.compare(p.getX(), n.getPoint().getX()) >= 0){
                n.right = insert(p, n.right, 1);
            }
            else {
                n.left = insert(p, n.left, 1);
            }
        }
        else {
            if (Double.compare(p.getY(), n.getPoint().getY()) >= 0){
                n.right = insert(p, n.right, 0);
            }
            else {
                n.left = insert(p, n.left, 0);
            }
        }
        return n;
    }

    private class Node {
        private Point item;
        private int lable;
        private Node left;
        private Node right;

        public Node(Point point, int num){
            item = point;
            lable = num;
        }

        public Point getPoint(){
            return item;
        }

        public Node getLeft(){
            return left;
        }

        public Node getRight(){
            return right;
        }

        public int getLable(){
            return lable;
        }
    }
}
