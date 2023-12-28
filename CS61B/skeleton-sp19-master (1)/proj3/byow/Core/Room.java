package byow.Core;

public class Room {
    int width;
    int height;
    Position pos;

    public Room(Position pos, int width, int height){
        this.width = width;
        this.height = height;
        this.pos = pos;
    }

    public Position lefttop(){
        return new Position(pos.x, pos.y + height - 1);
    }

    public Position diatop(){
        return new Position(pos.x + width - 1, pos.y + height - 1);
    }

    public Position rightdown(){
        return new Position(pos.x + width - 1, pos.y);
    }
}
