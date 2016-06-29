package aptitude;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBQueries
{

    DB db;
    ArrayList<Question> questions = new ArrayList();

    DBQueries()
    {
        db = new DB();
        questions.addAll(getQuestions("Easy"));
        questions.addAll(getQuestions("Medium"));
        questions.addAll(getQuestions("Hard"));

    }

    /**
     * Function that sets the name of a user in the database. The database is
     * first queried to determine if this user already exists
     *
     * @param name String used to set the name
     * @return False if the user could not be added and true if the user was
     * added successfully
     */
    public Boolean addUser(String name)
    {
        try
        {
            ResultSet result = db.query("Query that selects users with the name 'name'");
            if (!result.next())
            {
                db.update("Query that adds the user to the database and sets the name");
                return true;
            }
            return false;

        }
        catch (SQLException exc)
        {
            return false;
        }
    }

    /**
     * Function to select all the questions from the database that has the same
     * difficulty as the parameter difficulty. An ArrayList is then created that
     * stores all of the questions
     *
     * @param difficulty The difficulty level that the queried questions should
     * have
     * @return The ArrayList containing all the questions that were returned
     * from the database
     */
    private ArrayList<Question> getQuestions(String difficulty)
    {
        ArrayList<Question> resultArray = new ArrayList<>();
        try
        {
            ResultSet result = db.query("Query that selects all questions where the LevelID is equal to difficulty");

            while (result.next())
            {
                ResultSet levelResult = db.query("Query that selects obtains the score of the question by using the LevelID");
                Question question = new Question();
                question.setAnswer(result.getObject("Answer", String.class));
                question.setBlob(result.getBlob("Image"));
                question.setQuestion(result.getObject("Question", String.class));
                question.setLevel(difficulty);
                question.setScore(levelResult.getObject("Score", Integer.class));
                resultArray.add(question);
            }

            return resultArray;
        }
        catch (SQLException ex)
        {
            return null;
        }
    }

}
