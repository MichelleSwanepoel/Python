package aptitude;


import java.sql.*;


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
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        statement.close();
      } 
      catch ( Exception e ) 
      {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
      }
      
      System.out.println("Table(s) created successfully");
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
