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
public class SlickMaze extends BasicGame
{
    private TiledMap grassMap;
    private Animation sprite;
    Image [] move;
    private float x = 80f, y = 0f;
    private boolean exit_flag;
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
        
        
    public SlickMaze(String currentCheck)
    {
        super("Maze");
        currentCheckpoint = currentCheck;
        System.out.println(findXPos(currentCheckpoint));
        x = xCord[findXPos(currentCheckpoint)];
        y = yCord[findYPos(currentCheckpoint)];
        destinationCheckpoint = "";
        exit_flag = false;
    }
 /*
    public static void main(String[] arguments)
    {
        try
        {
            AppGameContainer app = new AppGameContainer(new PractieMaze("1"));
            app.setDisplayMode(630, 630, false);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
 */
    @Override
    public void init(GameContainer container) throws SlickException
    {
            grassMap = new TiledMap("src\\resources\\map70.tmx");
            //Image [] still = {new Image("src\\resources\\Idle.png")};
            Image [] move = {new Image("src\\resources\\Climb2.png"),new Image("src\\resources\\Climb3.png"),new Image("src\\resources\\Climb4.png"),new Image("src\\resources\\Climb5.png"),new Image("src\\resources\\Climb6.png"),new Image("src\\resources\\Climb7.png")};
            int [] duration = {150,150,150,150,150,150};
            sprite = new Animation (move, duration, false);
    }
 
    boolean pressedDown = false;
    boolean pressedRight = false;
    boolean pressedLeft = false;
    boolean pressedUp = false;
    boolean first = true;
    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        
        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_DOWN) && !pressedLeft && !pressedRight && !pressedUp)
        {
            pressedDown = true;
            pressedLeft = false;
            pressedRight = false;
            pressedUp = false;
        }
        if (input.isKeyPressed(Input.KEY_UP)&& !pressedLeft && !pressedRight && !pressedDown)
        {
            pressedDown = false;
            pressedLeft = false;
            pressedRight = false;
            pressedUp = true;
        }
        if (input.isKeyPressed(Input.KEY_RIGHT)&& !pressedLeft && !pressedDown && !pressedUp)
        {
            pressedDown = false;
            pressedLeft = false;
            pressedRight = true;
            pressedUp = false;
        }
        if (input.isKeyPressed(Input.KEY_LEFT)&& !pressedDown && !pressedRight && !pressedUp)
        {
            pressedDown = false;
            pressedLeft = true;
            pressedRight = false;
            pressedUp = false;
        }
        
        if (currentCheckpoint.equals("1"))
        {
            
                if (first)
                {
                    StartPage.showPopup(currentCheckpoint);
                    first = false;
                }
                destinationCheckpoint = "2";
                String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                int yEnd = Integer.parseInt(coords[2].split(";")[1]);                
                if(pressedDown)
                {
                    sprite.update(delta);
                    y+= delta *0.1f;
                }
                if (y >= yEnd)
                {
                    pressedDown = false;
                    currentCheckpoint = "2";
                    
                    StartPage.showPopup(currentCheckpoint);
                    
                }
        }
        if (currentCheckpoint.equals("2"))
        {
                if (pressedRight)
                {    
                    destinationCheckpoint = "3B";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    
                    int xEnd = Integer.parseInt(coords[2].split(";")[0]);                
                    sprite.update(delta);
                    x+= delta *0.1f;
                    
                    if (x >= xEnd)
                    {
                        pressedRight = false;
                        currentCheckpoint = "3B";
                        StartPage.showPopup(currentCheckpoint);
                    }
                }
                else if (pressedDown)
                {
                    destinationCheckpoint = "3A";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    int yEnd = Integer.parseInt(coords[2].split(";")[1]);                
                    if(pressedDown && y <= yEnd)
                    {
                        sprite.update(delta);
                        y+= delta *0.1f;
                    }
                    if (y >= yEnd)
                    {
                        pressedDown = false;
                        currentCheckpoint = "3A";
                        StartPage.showPopup(currentCheckpoint);
                    }
                }
        }
        if (currentCheckpoint.equals("3A"))
        {
            if (pressedRight)
                {    
                    destinationCheckpoint = "4B";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    System.out.println(this.getCoordinates(currentCheckpoint, destinationCheckpoint));
                    int xEnd = Integer.parseInt(coords[2].split(";")[0]);                
                    sprite.update(delta);
                    x+= delta *0.1f;
                    
                    if (x >= xEnd)
                    {
                        pressedRight = false;
                        currentCheckpoint = "4B";
                        StartPage.showPopup(currentCheckpoint);
                    }
                }
                else if (pressedDown)
                {
                    destinationCheckpoint = "4A";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    int yEnd = Integer.parseInt(coords[2].split(";")[1]);                
                    if(pressedDown && y <= yEnd)
                    {
                        sprite.update(delta);
                        y+= delta *0.1f;
                    }
                    if (y >= yEnd)
                    {
                        pressedDown = false;
                        currentCheckpoint = "4A";
                        StartPage.showPopup(currentCheckpoint);
                    }
                }
        }
        if (currentCheckpoint.equals("3B"))
        {
            if (pressedDown)
                {
                    destinationCheckpoint = "4B";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    int yEnd = Integer.parseInt(coords[2].split(";")[1]);                
                    if(pressedDown && y <= yEnd)
                    {
                        sprite.update(delta);
                        y+= delta *0.1f;
                    }
                    if (y >= yEnd)
                    {
                        pressedDown = false;
                        currentCheckpoint = "4B";
                        StartPage.showPopup(currentCheckpoint);
                    }
                }
                else if (pressedUp)
                {
                    destinationCheckpoint = "4C";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    System.out.println(this.getCoordinates(currentCheckpoint, destinationCheckpoint));
                    int yEnd = Integer.parseInt(coords[1].split(";")[1]);                
                    if(y >= yEnd)
                    {
                        sprite.update(delta);
                        y-= delta *0.1f;
                    }
                    if (y <= yEnd)
                    {
                        int xEnd = Integer.parseInt(coords[1].split(";")[0]);
                        sprite.update(delta);
                        x+= delta *0.1f;
                    
                        if (x >= xEnd)
                        {
                            pressedUp = false;
                            currentCheckpoint = "4C";
                            StartPage.showPopup(currentCheckpoint);
                        }
                    }
                    
                }
        }
        if (currentCheckpoint.equals("4A"))
        {
            if (pressedRight)
                {
                    destinationCheckpoint = "5A";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    System.out.println(this.getCoordinates(currentCheckpoint, destinationCheckpoint));
                    int xEnd = Integer.parseInt(coords[1].split(";")[0]);                
                    if(x <= xEnd)
                    {
                        sprite.update(delta);
                        x+= delta *0.1f;
                    }
                    if (x >= xEnd)
                    {
                        int yEnd = Integer.parseInt(coords[1].split(";")[1]);
                        sprite.update(delta);
                        y-= delta *0.1f;
                    
                        if (y <= yEnd)
                        {
                            pressedRight = false;
                            currentCheckpoint = "5A";
                            StartPage.showPopup(currentCheckpoint);
                        }
                    }
                }
        }
        if (currentCheckpoint.equals("4B"))
        {
            destinationCheckpoint = "5A";
            String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
            int yEnd = Integer.parseInt(coords[2].split(";")[1]);                
            if(pressedDown)
            {
                sprite.update(delta);
                y+= delta *0.1f;
            }
            if (y >= yEnd)
            {
                pressedDown = false;
                currentCheckpoint = "5A";
                StartPage.showPopup(currentCheckpoint);
                
            }
        }
        if (currentCheckpoint.equals("4C"))
        {
            if (pressedRight)
                {    
                    destinationCheckpoint = "5C";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    
                    int xEnd = Integer.parseInt(coords[2].split(";")[0]);                
                    sprite.update(delta);
                    x+= delta *0.1f;
                    
                    if (x >= xEnd)
                    {
                        pressedRight = false;
                        currentCheckpoint = "5C";
                        StartPage.showPopup(currentCheckpoint);
                    }
                }
                else if (pressedDown)
                {
                    destinationCheckpoint = "5B";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    int yEnd = Integer.parseInt(coords[2].split(";")[1]);                
                    if(pressedDown && y <= yEnd)
                    {
                        sprite.update(delta);
                        y+= delta *0.1f;
                    }
                    if (y >= yEnd)
                    {
                        pressedDown = false;
                        currentCheckpoint = "5B";
                        StartPage.showPopup(currentCheckpoint);
                    }
                }
            
        }
        if (currentCheckpoint.equals("5A"))
        {
            if (pressedRight)
                {    
                    destinationCheckpoint = "6A";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    
                    int xEnd = Integer.parseInt(coords[2].split(";")[0]);                
                    sprite.update(delta);
                    x+= delta *0.1f;
                    
                    if (x >= xEnd)
                    {
                        pressedRight = false;
                        currentCheckpoint = "6A";
                        StartPage.showPopup(currentCheckpoint);
                    }
                }
        }
        if (currentCheckpoint.equals("5B"))
        {
            if (pressedDown)
                {
                    destinationCheckpoint = "6B";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    System.out.println(this.getCoordinates(currentCheckpoint, destinationCheckpoint));
                    int yEnd = Integer.parseInt(coords[1].split(";")[1]);                
                    if(y <= yEnd)
                    {
                        sprite.update(delta);
                        y+= delta *0.1f;
                    }
                    if (y >= yEnd)
                    {
                        int xEnd = Integer.parseInt(coords[1].split(";")[0]);
                        sprite.update(delta);
                        x+= delta *0.1f;
                    
                        if (x >= xEnd)
                        {
                            pressedDown = false;
                            currentCheckpoint = "6B";
                            StartPage.showPopup(currentCheckpoint);
                        }
                    }
                }
        }
        if (currentCheckpoint.equals("5C"))
        {
            if (pressedDown)
                {
                    destinationCheckpoint = "6B";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    System.out.println(this.getCoordinates(currentCheckpoint, destinationCheckpoint));
                    int yEnd = Integer.parseInt(coords[1].split(";")[1]);                
                    if(y <= yEnd)
                    {
                        sprite.update(delta);
                        y+= delta *0.1f;
                    }
                    if (y >= yEnd)
                    {
                        int xEnd = Integer.parseInt(coords[1].split(";")[0]);
                        sprite.update(delta);
                        x-= delta *0.1f;
                    
                        if (x <= xEnd)
                        {
                            pressedDown = false;
                            currentCheckpoint = "6B";
                            StartPage.showPopup(currentCheckpoint);
                        }
                    }
                }
        }
        if (currentCheckpoint.equals("6A"))
        {
            if (pressedRight)
                {    
                    destinationCheckpoint = "7";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    
                    int xEnd = Integer.parseInt(coords[2].split(";")[0]);                
                    sprite.update(delta);
                    x+= delta *0.1f;
                    
                    if (x >= xEnd)
                    {
                        pressedRight = false;
                        currentCheckpoint = "7";
                        StartPage.showPopup(currentCheckpoint);
                    }
                }
        }
        if (currentCheckpoint.equals("6B"))
        {
            if (pressedDown)
                {
                    destinationCheckpoint = "7";
                    String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
                    System.out.println(this.getCoordinates(currentCheckpoint, destinationCheckpoint));
                    int yEnd = Integer.parseInt(coords[1].split(";")[1]);                
                    if(y <= yEnd)
                    {
                        sprite.update(delta);
                        y+= delta *0.1f;
                    }
                    if (y >= yEnd)
                    {
                        int xEnd = Integer.parseInt(coords[1].split(";")[0]);
                        sprite.update(delta);
                        x+= delta *0.1f;
                    
                        if (x >= xEnd)
                        {
                            pressedDown = false;
                            currentCheckpoint = "7";
                            StartPage.showPopup(currentCheckpoint);
                        }
                    }
                }
        }
        if (currentCheckpoint.equals("7"))
        {
            destinationCheckpoint = "8";
            String coords[] = this.getCoordinates(currentCheckpoint, destinationCheckpoint).split(",");
            int yEnd = Integer.parseInt(coords[2].split(";")[1]);                
            if(pressedDown)
            {
                sprite.update(delta);
                y+= delta *0.1f;
            }
            if (y >= yEnd)
            {
                pressedDown = false;
                currentCheckpoint = "8";
                StartPage.showPopup(currentCheckpoint);
            }
        }
        if (currentCheckpoint.equals("8"))
        {
            StartPage.showPopup(currentCheckpoint);
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
            if (option.equalsIgnoreCase(endPoint))
            {
                String line[] = option.split(":");
                if (line.length==1)
                {
                    coords += ","+xCord[endPos]+ ";" + yCord[startPos];
                }
                else
                    coords += ","+line[1].split(";")[0] + ";"+line[1].split(";")[1];
            }
        }
        
        coords += "," + xCord[endPos] + ";"+yCord[endPos];
        
        return coords;
        
    }
    public int findXPos(String checkPoint)
    {
        int pos = -1;
        for (int i = 0; i < checkpoints.length; i++) 
        {
            if (checkpoints[i].equalsIgnoreCase(checkPoint))
            {
                pos = i;
            }
            
        }
        return pos;
    }
    public int findYPos(String checkPoint)
    {
        int pos = -1;
        for (int i = 0; i < checkpoints.length; i++) 
        {
            if (checkpoints[i].equalsIgnoreCase(checkPoint))
                pos = i;
            
        }
        return pos;
    }
}