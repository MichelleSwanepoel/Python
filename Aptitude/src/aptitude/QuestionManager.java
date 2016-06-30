/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aptitude;

/**
 *
 * @author MattLP
 */
public class QuestionManager 
{
    public Question myquestion;
    DBQueries db;
    enum difficulty  {EASY, MEDIUM, HARD};
    
    public QuestionManager(DBQueries _db)
    {
        myquestion = new Question();
        db = _db;
    }
    
    public void loadQuestion(difficulty level)
    {
        myquestion = db.getNextQuestion(level.toString());
    }
    public void saveAnswer(String answer)
    {
        db.addQuestionToListOfCorrectQuestions(myquestion,  answer);
    }
}
