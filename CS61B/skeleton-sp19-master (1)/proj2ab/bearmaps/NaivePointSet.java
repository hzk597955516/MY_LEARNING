package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> points;

    public NaivePointSet(List<Point> points){
        this.points = points;
    }

    public Point nearest(double x, double y){
        Point targe = new Point(x, y);
        Point nearestpoint = points.get(0);
        double nearestnum = Point.distance(nearestpoint, targe);
        for (int i = 1; i < points.size(); i++){
            Point now = points.get(i);
            double nowdis = Point.distance(now, targe);
            if (nowdis < nearestnum){
                nearestpoint = now;
                nearestnum = nowdis;
            }
        }
        return nearestpoint;
    }

    public static void main(String[] arge){
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        ret.getX(); // evaluates to 3.3
        ret.getY(); // evaluates to 4.4
    }
}
