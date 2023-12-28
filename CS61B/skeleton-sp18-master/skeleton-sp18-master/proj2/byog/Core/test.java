package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.RandomWorldDemo;

import java.util.Random;

public class test {
    private static final int WIDTH = 90;
    private static final int HEIGHT = 60;
    private static RandomWorldDemo w = new RandomWorldDemo();


    public static void printnoting(int N){
        for (int i = 0; i < N; i++){
            System.out.print(' ');
        }
    }

    public static void printsomething(int N){
        for (int i = 0; i < N; i++){
            System.out.print('a');
        }
    }

    public static void pieceofhexagons(int nothing, int something){
        printnoting(nothing);
        printsomething(something);
        printnoting(nothing);
        System.out.println();
    }
    public static void halfhexagons1(int s){
        int sum =  3 * s - 2;
        for (int n = s; n <= sum; n+=2){
            int nothing = (sum - n) / 2;
            pieceofhexagons(nothing, n);
        }
    }

    public static void halfhexagons2(int s){
        int sum = 3 * s - 2;
        for (int n = sum; n >= s; n-=2){
            int nothing = (sum - n) / 2;
            pieceofhexagons(nothing, n);
        }
    }

    public static void addHexagon(int s){
        halfhexagons1(s);
        halfhexagons2(s);
    }

    public static void newpieceofhalfhexagons(TETile[][] world, int nothing, int something, TETile color, int h, int w){
        int y = h;
        for (int x = w; x < w+2*nothing+something; x++){
            if (x > w + nothing - 1 && x < w + nothing + something){
                world[x][y] = color;
            }
        }
    }

    public static void newHexagons(TETile[][] world, int s, int h, int w, TETile color){
        int sum = 3 * s - 2;
        int H = h;
        for (int n = s; n <= sum; n+=2){
            int black = (sum - n) / 2;
            newpieceofhalfhexagons(world, black, n, color, H, w);
            H += 1;
        }
        for (int n = sum; n >= s; n-=2){
            int black = (sum - n) / 2;
            newpieceofhalfhexagons(world, black, n, color, H, w);
            H += 1;
        }
    }

    public static void addrowHexagons(TETile[][] world, int n, int s, int x, int y){
        for (int i = 0; i < n; i++){
            newHexagons(world, s, y, x, w.randomTile());
            x += 4 * s - 2;
        }
    }

    public static void addsan(TETile[][] world, int s, int x, int y){
        int count = s;
        for (int i = 0; i < s; i++){
            addrowHexagons(world, count, s, x, y);
            count -= 1;
            y += s;
            x += 2 * s - 1;
        }
    }

    public static void addsan_(TETile[][] world, int s, int x, int y){
        int count = 1;
        int w = (s - 1) * (2 * s - 1);
        for (int i = 0; i < s; i++){
            addrowHexagons(world, count, s, w + x, y);
            count += 1;
            y += s;
            x -= 2 * s - 1;
        }
    }

    public static void addsanmid(TETile[][] world, int s, int x, int y){
        int count = 1 + 2 * (s - 2);
        for (int i = count; i >= 1; i--){
            if (i % 2 == 0){
                addrowHexagons(world, s, s, x, y);
            }
            if (i % 2 == 1){
                addrowHexagons(world, s - 1, s, x + 2 * s - 1, y);
            }
            y += s;
        }
    }

    public static void addgreatsan(TETile[][] world, int s, int x, int y){
        int count = 1 + 2 * (s - 2);
        int midup = s * count;
        addsan_(world, s, x, y);
        addsanmid(world, s, x, y + s * s);
        addsan(world, s, x, y + s * s + midup);
    }
    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        addgreatsan(world, 3, 20, 20);
        ter.renderFrame(world);
    }
}
