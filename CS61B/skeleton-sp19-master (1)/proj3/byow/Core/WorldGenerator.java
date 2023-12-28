package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenerator {
    public static Position createWorld(TETile[][] world, Random random){
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < 100; i += 1){
            Position pos = new Position(random.nextInt(world.length), random.nextInt(world[0].length));
            Room room = new Room(pos, random.nextInt(11) + 5, random.nextInt(11) + 5);
            rooms.add(room);
        }
        addRooms(world, rooms);

        for (Room room: rooms){
            addFloortoroom(world, room, random);
        }
        Position pos1 = rooms.get(random.nextInt(rooms.size())).pos;
        return new Position(pos1.x + 1, pos1.y + 1);
    }

    public static void test(TETile[][] world){
        addaRoom(world, new Room(new Position(10, 10), 10, 10));
        addaRoom(world, new Room(new Position(30, 10), 10, 10));
        findtoleft(world, new Position(30, 15));
    }

    private static boolean isok(TETile[][] world, int x, int y){
        if (x >= world.length || y >= world[0].length || y < 0 || x < 0){
            return true;
        }
        return false;
    }

    private static void addTileRow(TETile[][] world, int length, Position pos, TETile tile){
        for (int i = 0; i < length; i += 1){
            if (isok(world, pos.x + i, pos.y)){
                break;
            }
            world[pos.x + i][pos.y] = tile;
        }
    }

    private static void addTileCol(TETile[][] world, int length, Position pos, TETile tile){
        for (int i = 0; i < length; i += 1){
            if (isok(world, pos.x, pos.y + i)){
                break;
            }
            world[pos.x][pos.y + i] = tile;
        }
    }

    private static void addWallCol(TETile[][] world, int length, Position pos){
        addTileCol(world, length, pos, Tileset.WALL);
    }

    private static void addWallRow(TETile[][] world, int length, Position pos){
        addTileRow(world, length, pos, Tileset.WALL);
    }

    private static void addFloorRow(TETile[][] world, int length, Position pos){
        addTileRow(world, length, pos, Tileset.FLOOR);
    }

    private static void addFloorCol(TETile[][] world, int length, Position pos){
        addTileCol(world, length, pos, Tileset.FLOOR);
    }

    private static void addWallFloorCol(TETile[][] world, int length, Position pos){
        if (!isok(world, pos.x - 1, pos.y) && !isok(world, pos.x + 1, pos.y)){
            addWallCol(world, length, new Position(pos.x - 1, pos.y));
            addFloorCol(world, length, pos);
            addWallCol(world, length, new Position(pos.x + 1, pos.y));
        }
    }

    private static void addWallFloorRow(TETile[][] world, int length, Position pos){
        if (!isok(world, pos.x, pos.y + 1) && !isok(world, pos.x, pos.y - 1)) {
            addWallRow(world, length, new Position(pos.x, pos.y - 1));
            addFloorRow(world, length, pos);
            addWallRow(world, length, new Position(pos.x, pos.y + 1));
        }
    }

    private static void addaRoom(TETile[][] world, Room room){
        Position pos = room.pos;
        int width = room.width;
        int height = room.height;

        Position leftTop = new Position(pos.x, pos.y + height - 1);
        Position rightDown = new Position(pos.x + width - 1, pos.y);

        addWallCol(world, height, pos);
        addWallRow(world, width, pos);
        addWallCol(world, height, rightDown);
        addWallRow(world, width, leftTop);

        for (int i = 1; i < height - 1; i += 1){
            Position floorpos = new Position(pos.x + 1, pos.y + i);
            addFloorRow(world, width - 2, floorpos);
        }
    }

    private static boolean ismore(TETile[][] world, Room room){
        int x1 = room.pos.x, y1 = room.pos.y;
        int x2 = room.diatop().x, y2 = room.diatop().y;

        for (int i = x1; i <= x2; i += 1){
            for (int j = y1; j <= y2; j += 1){
                if (world[i][j] == Tileset.FLOOR){
                    return false;
                }
            }
        }
        return true;
    }

    private static void findtoright(TETile[][] world, Position pos){
        int x = pos.x + 1, y = pos.y;
        if (x < world.length && world[x][y] == Tileset.FLOOR){
            world[x - 1][y] = Tileset.FLOOR;
        }
        while (x < world.length && world[x][y] != Tileset.WALL){
            x += 1;
        }
        if (x + 1 < world.length && world[x + 1][y] == Tileset.FLOOR) {
            addWallFloorRow(world, x - pos.x + 1, pos);
        }
    }

    private static void findtoleft(TETile[][] world, Position pos){
        int x = pos.x - 1, y = pos.y;
        if (x >= 0 && world[x][y] == Tileset.FLOOR){
            world[x + 1][y] = Tileset.FLOOR;
        }
        else {
            while (x > 0 && world[x][y] != Tileset.WALL) {
                x -= 1;
            }
            if (x - 1 >= 0 && world[x - 1][y] == Tileset.FLOOR) {
                addWallFloorRow(world, pos.x - x + 1, new Position(x, pos.y));
            }
        }
    }

    private static void findtotop(TETile[][] world, Position pos){
        int x = pos.x, y = pos.y + 1;
        if (y < world[0].length && world[x][y] == Tileset.FLOOR){
            world[x][y - 1] = Tileset.FLOOR;
        }
        else {
            while (y < world[0].length && world[x][y] != Tileset.WALL) {
                y += 1;
            }
            if (y + 1 < world[0].length && world[x][y + 1] == Tileset.FLOOR) {
                addWallFloorCol(world, y - pos.y + 1, pos);
            }
        }
    }

    private static void findtodown(TETile[][] world, Position pos){
        int x = pos.x, y = pos.y - 1;
        if (y >= 0 && world[x][y] == Tileset.FLOOR){
            world[x][y + 1] = Tileset.FLOOR;
        }
        else {
            while (y > 0 && world[x][y] != Tileset.WALL) {
                y -= 1;
            }
            if (y - 1 >= 0 && world[x][y - 1] == Tileset.FLOOR) {
                addWallFloorCol(world, pos.y - y + 1, new Position(pos.x, y));
            }
        }
    }

    private static void addFloortoroom(TETile[][] world, Room room, Random random){
        Position leftdown = room.pos, righttop = room.diatop();

        Position left = new Position(leftdown.x, random.nextInt(righttop.y - leftdown.y - 1) + leftdown.y + 1);
        Position right = new Position(righttop.x, random.nextInt(righttop.y - leftdown.y - 1) + leftdown.y + 1);
        Position top = new Position(random.nextInt(righttop.x - leftdown.x - 1) + leftdown.x + 1, righttop.y);
        Position down = new Position(random.nextInt(righttop.x - leftdown.x - 1) + leftdown.x + 1, leftdown.y);

        findtoleft(world, left);
        findtoright(world, right);
        findtotop(world, top);
        findtodown(world, down);
    }

    private static void addRooms(TETile[][] world, List<Room> rooms){
        List<Room> delitem = new ArrayList<>();
        for (Room room: rooms){
            int x = room.pos.x, y = room.pos.y;
            if (x + room.width - 1 < world.length && y + room.height - 1 < world[0].length && ismore(world, room)){
                addaRoom(world, room);
            }
            else {
                delitem.add(room);
            }
        }
        for (Room room: delitem){
            rooms.remove(room);
        }
    }
}
