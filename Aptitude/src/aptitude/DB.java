package aptitude;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


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
                String DropTable4 = "DROP TABLE createLevel";
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
			  + "(LevelID   TEXT    NOT NULL,"
			  + "Score      INT     NOT NULL,"
                          + "PRIMARY KEY (LevelID))";		
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
            String insertTest = "INSERT INTO Level VALUES('EASY',10)";
            this.update(insertTest);
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
