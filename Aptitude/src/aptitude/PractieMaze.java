package aptitude;


 
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
/**
 * @author panos
 */
public class PractieMaze extends BasicGame
{
    private TiledMap grassMap;
    private Animation sprite;
    Image [] move;
    private float x = 80f, y = 0f;
    private String currentCheckpoint, destinationCheckpoint;
    
    String [] checkpoints = {"1"    ,"2"    ,"3A"   ,"3B"   ,"4A"   ,"4B"   ,"4C"   ,"5A"   ,"5B"   ,"5C"   ,"6A"   ,"6B"   ,"7"    ,"8"};
    int [] xCord = {         80     ,80     , 80    ,220    ,80     ,220    ,360    ,220    ,360    ,500    ,360    ,430    ,500    ,500};
    int [] yCord = {         0      ,140    ,280    ,140    ,490    ,280    ,70     ,420    ,210    ,70     ,420    ,280    ,420    ,560};

    String [][] possibilities = 
    {//only extra details for double movements (just lists the first change in form x;y
            {"2"},                          //From checkpoint 1
            {"3A","3B"},                    //From checkpoint 2
            {"4A","4B"},                    //From checkpoint 3A
            {"4B", "4C:220;70"},            //From checkpoint 3B
            {"5A:220;490"},                 //From checkpoint 4A
            {"5A"},                         //From checkpoint 4B
            {"5B", "5C"},                   //From checkpoint 4C
            {"6A"},                         //From checkpoint 5A
            {"6B:360;280"},                 //From checkpoint 5B
            {"6B:500;280"},                 //From checkpoint 5C
            {"7"},                          //From checkpoint 6A
            {"7:430;420"},                  //From checkpoint 6B
            {"8"},                          //From checkpoint 7
            {""}                            //From checkpoint 8
    };
        
        
    public PractieMaze()
    {
        super("Practice Maze");
        currentCheckpoint = "1";
        destinationCheckpoint = "";
    }
 
    public static void main(String[] arguments)
    {
        try
        {
            AppGameContainer app = new AppGameContainer(new PractieMaze());
            app.setDisplayMode(630, 630, false);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
 
    @Override
    public void init(GameContainer container) throws SlickException
    {
            grassMap = new TiledMap("/Users/brandonpickup/Documents/Temp/build/classes/map70.tmx");
            Image [] still = {new Image("/Users/brandonpickup/Documents/Temp/build/classes/Idle.png")};
            Image [] move = {new Image("/Users/brandonpickup/Documents/Temp/build/classes/Climb2.png"),new Image("/Users/brandonpickup/Documents/Temp/build/classes/Climb3.png"),new Image("/Users/brandonpickup/Documents/Temp/build/classes/Climb4.png"),new Image("/Users/brandonpickup/Documents/Temp/build/classes/Climb5.png"),new Image("/Users/brandonpickup/Documents/Temp/build/classes/Climb6.png"),new Image("/Users/brandonpickup/Documents/Temp/build/classes/Climb7.png")};
            int [] duration = {150,150,150,150,150,150};
            sprite = new Animation (move, duration, false);
    }
 
    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        Input input = container.getInput();
        if (currentCheckpoint.equals("1"))
        {
            if (input.isKeyPressed(Input.KEY_DOWN))
            {
                destinationCheckpoint = "2";
                String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                int yEnd = Integer.parseInt(coords[2].split(";")[1]);
                while (y < yEnd)
                {
                    sprite.update(delta);
                    y+= delta *0.1f;
                }
                
            }
        }
            
            
            
            
        if (input.isKeyDown(Input.KEY_DOWN))
        {
            sprite.update(delta);
            y += delta *0.1f;
        }
        if (input.isKeyDown(Input.KEY_UP))
        {
            sprite.update(delta);
            y -= delta *0.1f;
        }
        if (input.isKeyDown(Input.KEY_LEFT))
        {
            sprite.update(delta);
            x -= delta *0.1f;
        }
        if (input.isKeyDown(Input.KEY_RIGHT))
        {
            sprite.update(delta);
            x += delta *0.1f;
        }
    }
 
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        grassMap.render(0, 0);
        sprite.draw((int)x, (int)y);
        
    }
    //Returns a set of 3 coordinates to indicate how to get to the destination
    //start coordinate, intermediate coordinate and final coordinate
    public String getCoordinates (String startPoint, String endPoint)
    {
        int startPos = -1;
        int endPos = -1;
        for (int i = 0; i < checkpoints.length; i++)
        {
            if (checkpoints[i].equalsIgnoreCase(startPoint))
                startPos = i;
            if (checkpoints[i].equalsIgnoreCase(endPoint))
                endPos = i;
        }
        //form x;y,x;y,x;y
        String coords = ""+ xCord[startPos]+ ";" + yCord[startPos];
        
        
        String[] optionLines = possibilities[startPos];
        for (String option : optionLines)
        {
            String line[] = option.split(":");
            String interPos = "";
            if (line.length==1)
                coords += ","+xCord[startPos]+ ";" + yCord[startPos];
            else
                coords += ","+line[1].split(";")[0] + ";"+line[1].split(";")[1];
        }
        
        coords += "," + xCord[endPos] + ";"+yCord[endPos];
        
        return coords;
        
    }
}