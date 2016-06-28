package aptitude;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class DB
{
    private static final String driver = "org.sqlite.JDBC";
    private static final String url = "jdbc:sqlite:test.db"; //database name
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    
    /**
     *Establishes a connection between Java and the Database
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
    private int getNextUserID()
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
        logID+=1;//increment because it represents the next available ID
        return logID;
    }
    public void createTables( )
    {
      try 
      {
        String sql = ""; //create table SQL statement
		/*String DropTable1 = "DROP TABLE User";
                statement = connection.prepareStatement(DropTable1);
		statement.executeUpdate();
		String DropTable2 = "DROP TABLE UserQuest";
                statement = connection.prepareStatement(DropTable2);
		statement.executeUpdate();
		String DropTable3 = "DROP TABLE createQuestion";
                statement = connection.prepareStatement(DropTable3);
		statement.executeUpdate();
                String DropTable4 = "DROP TABLE Level";
                statement = connection.prepareStatement(DropTable4);
		statement.executeUpdate();
                */
		String createUser = "CREATE TABLE IF NOT EXISTS User " 
				+ "(UserID     INT PRIMARY KEY         NOT NULL, "
                                + "Name        TEXT                    NOT NULL)";
		this.update(createUser);
		
		
		String createUserQuest = "CREATE TABLE IF NOT EXISTS UserQuest "
			+ "(UserID    INT    NOT NULL,"
			+ "QuestID    INT    NOT NULL,"
			+ "PRIMARY KEY (UserID,QuestID),"
                        +  " FOREIGN KEY (UserID) REFERENCES User(UserID),"
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
      catch ( Exception e ) 
      {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
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
                String insert = "INSERT INTO Question VALUES(1, 'Unscramble \"they see\" into a word that represents a body part.', 'the eyes', 'EASY', NULL)";
                this.update(insert);
                insert = "INSERT INTO Question VALUES(2, 'A farmer has 17 sheep and all but 9 die, how many sheep are left?', '9', 'EASY', NULL)";
                this.update(insert);
                insert = "INSERT INTO Question VALUES (3, 'In the final stretch of a marathon, you quickly ran by the person who is in second place, what place are you in?','2','EASY',NULL)";
                this.update(insert);
            }
            
                
        } 
        catch (SQLException ex){Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);}
    }
    /**
     *Updates the database with the SQL statement in the parameter
     */
    public void update (String update) throws SQLException
    {        
        statement = connection.prepareStatement(update);
        statement.executeUpdate();
        statement.close();
    }
    /**
     *Sends a SQL query to the database
     * @return A result set from the database for the query sent
     */
    public ResultSet query (String query) throws SQLException 
    {
        statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery();
        return resultSet;
        
    }
}
