import java.awt.*;

public class Yarn {
    public String name;
    public int xpos;
    public int ypos;
    public int dx;      //speed of hero in x direction
    public int dy;      //speed of hero in y direction
    public int width;
    public int height;
    public boolean isAlive;
    public Rectangle rec;

    public Yarn(int pXpos, int pYpos, int pDx, int pDy){
        xpos = pXpos;
        ypos = pYpos;
        dx = pDx;
        dy = pDy;
        width = 50;
        height = 60;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);
    }

    public void move(){
        ypos = ypos+dy;
        xpos = xpos+dx;
        rec = new Rectangle(xpos, ypos, width, height);
    }


}
