package byow.lab12;

import byow.Core.WorldGenerator;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 90;
    private static final int HEIGHT = 60;

    public static void addrow(TETile[][] world, int x, int y, TETile color, int size){
        for (int i = x; i < x + size; i += 1){
            world[i][y] = color;
        }
    }

    public static void addhex_top(TETile[][] world, int x, int y, TETile color, int size){
        int curr_size = size + 2 * (size - 1);
        int begin_x = x;
        int begin_y = y;

        for (int i = 0; i < size; i += 1){
            addrow(world, begin_x, begin_y, color, curr_size);
            begin_x += 1;
            begin_y += 1;
            curr_size -= 2;
        }
    }

    public static void addHexagon(TETile[][] world, int x, int y, TETile color, int size){
        addhex_down(world, x, y, color, size);
        addhex_top(world, x, y + size, color, size);
    }

    public static void addhex_down(TETile[][] world, int x, int y, TETile color, int size){
        int curr_size = size;
        int begin_x = x + size - 1;
        int begin_y = y;

        for (int i = 0; i < size; i += 1){
            addrow(world, begin_x, begin_y, color, curr_size);
            begin_x -= 1;
            begin_y += 1;
            curr_size += 2;
        }
    }

    public static void addrow_hex(TETile[][] world, int x, int y, int size, int num){
        int begin_x = x;
        int begin_y = y;

        for (int i = 0; i < num; i += 1){
            addHexagon(world, begin_x, begin_y, randomTile(), size);
            begin_x += size + 2 * (size - 1) + size;
        }
    }

    public static void addHex_top(TETile[][] world, int x, int y, int size){
        int begin_x = x;
        int begin_y = y;
        int begin_num = size;
        int chang_x = 2 * size - 1;
        int chang_y = size;

        for (int i = 0; i < size; i += 1){
            addrow_hex(world, begin_x, begin_y, size, begin_num);
            begin_x += chang_x;
            begin_y += chang_y;
            begin_num -= 1;
        }
    }

    public static void addHex_mid(TETile[][] world, int x, int y, int size) {
        int begin_x_1 = x;
        int begin_x_2 = x + 2 * size - 1;
        int begin_y_1 = y;
        int begin_y_change = size;

        boolean isLess = true;

        for (int i = 0; i < 2 * (size - 2) + 1; i += 1){
            if (isLess){
                addrow_hex(world, begin_x_2, begin_y_1, size, size - 1);
                isLess = false;
            }
            else {
                addrow_hex(world, begin_x_1, begin_y_1, size, size);
                isLess = true;
            }
            begin_y_1 += begin_y_change;

        }
    }

    public static void addHex_down(TETile[][] world, int x, int y, int size){
        int chang_x = 2 * size - 1;
        int chang_y = size;
        int begin_x = x + (size - 1) * chang_x;
        int begin_y = y;
        int begin_num = 1;

        for (int i = 0; i < size; i += 1){
            addrow_hex(world, begin_x, begin_y, size, begin_num);
            begin_x -= chang_x;
            begin_y += chang_y;
            begin_num += 1;
        }
    }

    public static void addbigHexagon(TETile[][] world, int x, int y, int size){
        int begin_x = x;
        int begin_y = y;
        int chang_y_1 = size * size;
        int chang_y_2 = size * 2 * (size - 1) - size;

        addHex_down(world, begin_x, begin_y, size);
        begin_y += chang_y_1;
        addHex_mid(world, x, begin_y, size);
        begin_y += chang_y_2;
        addHex_top(world, begin_x, begin_y, size);
    }

    private static TETile randomTile() {
        long SEED = (int)(Math.random() * 1000000);
        Random RANDOM = new Random(SEED);
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.SAND;
            case 5: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Random random = new Random();
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        WorldGenerator.createWorld(world, random);

        //WorldGenerator.test(world);

        ter.renderFrame(world);
    }
}
