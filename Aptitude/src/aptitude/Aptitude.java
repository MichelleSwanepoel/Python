package aptitude;

public class Aptitude
{

    public static void main(String[] args)
    {
        DBQueries queries = new DBQueries();
        queries.addUser("michelle");
        Question nextQuest = queries.getNextQuestion("EASY");
        queries.addQuestionToListOfCorrectQuestions(nextQuest, "the eyes");
        System.out.println(queries.getScoreOfUserSoFar());
        nextQuest = queries.getNextQuestion("EASY");
        queries.addQuestionToListOfCorrectQuestions(nextQuest, "9");
        System.out.println(queries.getScoreOfUserSoFar());
    }

}
