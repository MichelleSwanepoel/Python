package aptitude;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBQueries
{

    DB db;
    ArrayList<Question> questions = new ArrayList();
    int userId;

    DBQueries()
    {
        db = new DB();
        questions.addAll(getQuestions("Easy"));
        questions.addAll(getQuestions("Medium"));
        questions.addAll(getQuestions("Hard"));
    }

    /**
     * Function that sets the name of a user in the database. The database is
     * first queried to determine if this user already exists. This function will be called from the GUI
     *
     * @param name String used to set the name
     * @return False if the user could not be added and true if the user was
     * added successfully
     */
    public Boolean addUser(String name)
    {
        try
        { 
            ResultSet result = db.query("SELECT * FROM 'User' WHERE 'Name' IN(name);");
            if (!result.next())
            {
                userId = db.getNextUserID();
                db.update("INSERT INTO User VALUES(userID(),name)");
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
     * Function that returns the next question with the same difficulty as the parameter, and removes it from the list of questions
     * @param difficulty The difficulty of the question that should be returned
     * @return The next question 
     */
    public Question getQuestion(String difficulty)
    {
        for (Question question : questions)
        {
            if(question.getLevel().equals(difficulty))
            {
                questions.remove(question);
                return question;
            }
        }
        return null;
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
            ResultSet result = db.query("SELECT * FROM Question WHERE LevelID IN (difficulty)");

            while (result.next())
            {
                ResultSet levelResult = db.query("SELECT SCORE FROM Level WHERE LevelID IN (difficulty)"/*"Query that selects obtains the score of the question by using the LevelID"*/);
                Question question = new Question();
                question.setAnswer(result.getObject("Answer", String.class));
                question.setBlob(result.getBlob("Image"));
                question.setQuestion(result.getObject("Question", String.class));
                question.setLevel(difficulty);
                question.setScore(levelResult.getObject("Score", Integer.class));
                question.setQuestID(levelResult.getObject("QID", Integer.class));
                resultArray.add(question);
            }

            return resultArray;
        }
        catch (SQLException ex)
        {
            return null;
        }
    }
    
    /**
     * Function that adds a question to the list of correct questions associated with the user, if the answer that the user gave is correct
     * @param question The question object that contains the question asked with the answer
     * @param answer 
     */
    public void addQuestionToListOfCorrectQuestions(Question question, String answer)
    {
        if(question.getAnswer().equals(answer))
        {
            
        }
    }

}
