package aptitude;

import java.sql.Blob;

class Question
{

    int questionID;
    int score;
    String level;
    String question;
    String answer;
    Blob blob;

    public void setScore(int _score)
    {
        score = _score;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String _question)
    {
        question = _question;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String _answer)
    {
        answer = _answer;
    }

    public Blob getBlob()
    {
        return blob;
    }

    public void setBlob(Blob _blob)
    {
        blob = _blob;
    }

    public int getScore()
    {
        return score;
    }

    public void setLevel(String _level)
    {
        level = _level;
    }

    public String getLevel()
    {
        return level;
    }

    public void setQuestID(int id)
    {
        questionID = id;
    }

    public int getQuestID()
    {
        return questionID;
    }
}
