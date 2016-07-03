package aptitude;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class DB
{

    private static final String driver = "org.sqlite.JDBC";
    private static final String url = "jdbc:sqlite:test.db"; //database name
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    /**
     * Establishes a connection between Java and the Database
     */
    public DB()
    {
        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url);
            System.out.println("Connection succesfull");
            createTables();

            // testOutput();
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("Class not found");
        }
        catch (SQLException ex)
        {
            System.out.println("Connection Error");
        }
    }

    //testing tables
  /*  public void testOutput()
     {
     String sql = "SELECT * FROM Question";
     try
     {
     ResultSet rs = this.query(sql);
     while (rs.next())
     {
     System.out.println(rs.getString("Answer"));
     }
     } 
     catch (SQLException ex)
     {
     Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
     }
     }*/
    //method returns the next available log ID from the user table
    public int getNextUserID()
    {
        int logID = 0;
        try
        {
            ResultSet rs = this.query("SELECT * FROM User");
            while (rs.next())
            {
                logID = rs.getInt("UserID");//ID field for the respective table
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Unable to add user", "Upload Error", JOptionPane.ERROR_MESSAGE);
        }
        logID += 1;//increment because it represents the next available ID
        return logID;
    }

    public void createTables()
    {
        try
        {
            /**String sql = ""; //create table SQL statement
            String DropTable1 = "DROP TABLE User";
             statement = connection.prepareStatement(DropTable1);
             statement.executeUpdate();
             String DropTable2 = "DROP TABLE UserQuest";
             statement = connection.prepareStatement(DropTable2);
             statement.executeUpdate();*/
             String DropTable3 = "DROP TABLE Question";
             statement = connection.prepareStatement(DropTable3);
             statement.executeUpdate();
             String DropTable4 = "DROP TABLE Level";
             statement = connection.prepareStatement(DropTable4);
             statement.executeUpdate();
             
            String createUser = "CREATE TABLE IF NOT EXISTS User "
                    + "(UserID     INT PRIMARY KEY         NOT NULL, "
                    + "Name        TEXT                    NOT NULL)";
            this.update(createUser);

            String createUserQuest = "CREATE TABLE IF NOT EXISTS UserQuest "
                    + "(UserID    INT    NOT NULL,"
                    + "QuestID    INT    NOT NULL,"
                    + "PRIMARY KEY (UserID,QuestID),"
                    + " FOREIGN KEY (UserID) REFERENCES User(UserID),"
                    + "FOREIGN KEY (QuestID) REFERENCES Question(QID))";
            this.update(createUserQuest);

            String createQuestion = "CREATE TABLE IF NOT EXISTS Question "
                    + "(QID INT PRIMARY KEY NOT NULL, "
                    + "QuestText  TEXT        NOT NULL,"
                    + "Answer     TEXT        NOT NULL,"
                    + "LevelID    TEXT        NOT NULL,"
                    + "Image      BLOB        NULL,"
                    + "FOREIGN KEY (LevelID) REFERENCES Level(LevelID))";
            this.update(createQuestion);

            String createLevel = "CREATE TABLE IF NOT EXISTS Level "
                    + "(LevelID   TEXT PRIMARY KEY   NOT NULL,"
                    + "Score      INT     NOT NULL)";
            this.update(createLevel);
            this.addQuest();

            statement.close();
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Table(s) created successfully");
    }

    public void addQuest()
    {
        try
        {
            ResultSet rs = this.query("SELECT * FROM Level");
            if (!rs.next())
            {
                String insert = "INSERT INTO Level VALUES('EASY',1)";
                this.update(insert);
                insert = "INSERT INTO Level VALUES('MEDIUM',2)";
                this.update(insert);
                insert = "INSERT INTO Level VALUES('HARD',3)";
                this.update(insert);
            }

            rs = this.query("SELECT * FROM Question");
            if (!rs.next())
            {
                //EASY QUESTIONS
                //1
                String insert = "INSERT INTO Question VALUES (1, 'In the final stretch of a marathon, you quickly ran by the person who is in second place, what place are you in? \n\nPlease write the number of the position only.','2','EASY',NULL)";
                this.update(insert);
                //2
                insert = "INSERT INTO Question VALUES(2, 'A farmer has 17 sheep and all but 9 die, how many sheep are left?', '9', 'EASY', NULL)";
                this.update(insert);
                //3
                insert = "INSERT INTO Question VALUES(3, 'Unscramble \"they see\" into a word that represents a body part.', 'the eyes', 'EASY', NULL)";
                this.update(insert);

                //MEDIUM QUESTIONS
                //1
                insert = "INSERT INTO Question VALUES (4, 'Given: \"XYZ\" \n\nYou may only swap adjacent letters, what are the fewest swaps to attain \"ZYX?\"','3','MEDIUM',NULL)";
                this.update(insert);
                //2
                insert = "INSERT INTO Question VALUES (5, 'There are 5 houses in 5 different colours. In each house lives a person of a different nationality. The 5 owners drink a certain type of beverage, smoke a certain brand of cigar, and keep a certain pet. Using the clues below can you determine who owns the fish?\n"
                        + "The Brit lives in a red house.\n"
                        + "The Swede keeps dogs as pets.\n"
                        + "The Dane drinks tea.\n"
                        + "The green house is on the immediate left of the white house.\n"
                        + "The green house owner drinks coffee.\n"
                        + "The person who smokes Pall Mall rears birds.\n"
                        + "The owner of the yellow house smokes Dunhill.\n"
                        + "The man living in the house right in the middle drinks milk.\n"
                        + "The Norwegian lives in the first house.\n"
                        + "The man who smokes Blend lives next door to the one who keeps cats.\n"
                        + "The man who keeps horses lives next door to the man who smokes Dunhill.\n"
                        + "The owner who smokes Blue Master drinks chocolate.\n"
                        + "The German smokes Prince.\n"
                        + "The Norwegian lives next to the blue house.\n"
                        + "The man who smokes Blend has a neighbour who drinks water.\n\n"
                        + "Note, please only write the nationality of the person','German','MEDIUM',NULL)";
                this.update(insert);
                //3
                insert = "INSERT INTO Question VALUES (6, 'If ROSE → URVH, write PLUM and MEDICINE. Please separate the answers with one space.','YOXP PHGLFLQH','MEDIUM',NULL)";
                this.update(insert);
                
                //HARD QUESTIONS
                //1
                File image = new File("src\\resources\\hat.jpg");
                BufferedImage buf = ImageIO.read(image);
                ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
                ImageIO.write(buf, "jpg", byteArr);
                byteArr.flush();
                byte[] imageInByte = byteArr.toByteArray();
                byteArr.close();
                insert = "INSERT INTO Question VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stm = connection.prepareStatement(insert);
                stm.setInt(1, 7);
                stm.setString(2, "4 criminals are caught and are to be punished. The judge allows them to be freed if they can solve a puzzle. If they do not, they will be hung. They agreed.The 4 criminals are lined up on some steps (shown in picture). They are all facing in the same direction. A wall separates the fourth man from the other three."
                        + "The men are not allowed to talk to each other, each man cannot see behind him, 3 cant see 4. They must yell out their own hat colour (black or white). WHo yells first?\n"
                        + "So to summarise:\n"
                        + "Man 1 can see Man 2 and Man 3.\n"
                        + "Man 2 can see Man 3.\n"
                        + "Man 3 can see none of the others.\n"
                        + "Man 4 can see none of the others.\n"
                        + "The criminals are wearing hats. They are told that there are two white hats and two black hats. The men initially don’t know what colour hat they are wearing. They are told to shout out the colour of the hat that they are wearing as soon as they know for certain what colour it is.\n"
                        + "They are not allowed to turn round or move. They are not allowed to talk to each other. They are not allowed to take their hats off. Who shouts first? "
                        + "\n\nPlease note, only the number of the man should be inserted.");
                stm.setString(3, "2");
                stm.setString(4, "HARD");
                stm.setBytes(5, imageInByte);
                stm.executeUpdate();
                //2
                insert = "INSERT INTO Question VALUES (8, 'A train is running at a speed of 40 km/h and it crosses a post in 18 seconds. What is the length of the train in meter?','200','HARD',NULL)";
                this.update(insert);
                //3
                insert = "INSERT INTO Question VALUES (9, 'Through the door, you come across a chest, it is locked, but seems to have a strange unlocking mechanism. To unlock it, you are required to solve the following and enter the resulting code: The six cards shown below were turned face down, \"shuffled\" (i.e.. re-arranged), and put in a pile, one on top of each other. Each card has an obvious numeric value. In addition, the \"J\", \"Q\", and \"K\" have values of 11, 12, and 13 respectively. Make use of the information provided in the specifications to determine each cards position in the pile (top to bottom). \n"
                        + "3 clubs\n"
                        + "K clubs\n"
                        + "6 diamonds\n"
                        + "Q hearts\n"
                        + "7 clubs\n"
                        + "3 spades\n"
                        + "\n"
                        + "Specifications:\n"
                        + "1. The seven is somewhere above the \"Q\".\n"
                        + "2. The heart is somewhere above the spade.\n"
                        + "3. A three is directly above the six.\n"
                        + "4. One red card is directly on top of the other.\n"
                        + "5. A black card is on the very top.\n"
                        + "6. The bottom card has the value of the sum of three other face down cards','736Q3K','HARD',NULL)";
                this.update(insert);
            }

        }
        catch (SQLException ex)
        {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates the database with the SQL statement in the parameter
     */
    public void update(String update) throws SQLException
    {
        statement = connection.prepareStatement(update);
        statement.executeUpdate();
        statement.close();
    }

    /**
     * Sends a SQL query to the database
     *
     * @return A result set from the database for the query sent
     */
    public ResultSet query(String query) throws SQLException
    {
        statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery();
        return resultSet;

    }
}
