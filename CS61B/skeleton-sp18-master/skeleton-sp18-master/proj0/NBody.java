public class NBody
{
    public static double readRadius(String args)
    {
        In in = new In(args);
        in.readInt();
        return in.readDouble();
    }

    public  static Planet[] readPlanets(String file)
    {
        In in = new In(file);
        int nums = in.readInt();
        in.readDouble();
        Planet[] p = new Planet[nums];
        int i = 0;
        while (i < nums)
        {
            Double xxPos = in.readDouble();
            Double yyPos = in.readDouble();
            Double xxVel = in.readDouble();
            Double yyVel = in.readDouble();
            Double mass  = in.readDouble();
            String imgFileName = in.readString();
            p[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass,imgFileName);
            i += 1;
        }
        return p;
    }

    public static void main(String[] args)
    {
        Double T = Double.parseDouble(args[0]);
        Double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");
        for (Planet p : planets) {
            p.draw();
        }
        StdDraw.pause(1);
        StdDraw.enableDoubleBuffering();
        int t = 0;
        Double[] x = new Double[planets.length];
        Double[] y = new Double[planets.length];
        while(t < T)
        {
            for(int i = 0; i < planets.length; i++)
            {
                x[i] = planets[i].calcNetForceExertedByX(planets);
                y[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for(int i = 0; i < planets.length; i++)
            {
                planets[i].update(dt, x[i], y[i]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.pause(10);
            t += dt;
        }
    }

}

