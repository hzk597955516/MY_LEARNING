public class Planet {
    double xxPos;
    double yyPos;
    double xxVel;
    double yyVel;
    double mass;
    String imgFileName;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet rocinante) {
        double pow_x = (this.xxPos - rocinante.xxPos) * (this.xxPos - rocinante.xxPos);
        double pow_y = (this.yyPos - rocinante.yyPos) * (this.yyPos - rocinante.yyPos);
        double pow_r = pow_x + pow_y;
        double r = Math.sqrt(pow_r);
        return r;
    }

    public double calcForceExertedBy(Planet p) {
        double G = 6.67e-11;
        double r = calcDistance(p);
        double pow_r = r * r;
        double F = (G * this.mass * p.mass) / pow_r;
        return F;
    }

    public double calcForceExertedByX(Planet rocinante) {
        double F = calcForceExertedBy(rocinante);
        double x = rocinante.xxPos - this.xxPos;
        double r = calcDistance(rocinante);
        double Fx = (F * x) / r;
        return Fx;
    }

    public double calcForceExertedByY(Planet rocinante) {
        double F = calcForceExertedBy(rocinante);
        double y = rocinante.yyPos - this.yyPos;
        double r = calcDistance(rocinante);
        double Fx = (F * y) / r;
        return Fx;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
        int L = allPlanets.length;
        double Sum = 0;
        for (int i = 0; i < L; i++) {
            if (!this.equals(allPlanets[i])) {
                Sum += calcForceExertedByX(allPlanets[i]);
            }
        }
        return Sum;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        int L = allPlanets.length;
        double Sum = 0;
        for (int i = 0; i < L; i++) {
            if (!this.equals(allPlanets[i])) {
                Sum += calcForceExertedByY(allPlanets[i]);
            }
        }
        return Sum;
    }

    public void update(double dt, double fX, double fY) {
        double ax = fX / this.mass;
        double ay = fY / this.mass;
        this.xxVel += dt * ax;
        this.yyVel += dt * ay;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    public void draw ()
    {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
        StdDraw.show();
    }
}
