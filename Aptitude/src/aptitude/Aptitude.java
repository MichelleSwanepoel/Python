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
        System.out.println(queries.getNumberOfCorrectQuestions("EASY"));
        nextQuest = queries.getNextQuestion("HARD");
        queries.addQuestionToListOfCorrectQuestions(nextQuest, "2");
        System.out.println(queries.getScoreOfUserSoFar());
        System.out.println(queries.getNumberOfCorrectQuestions("EASY"));
        System.out.println(queries.getNumberOfCorrectQuestions("HARD"));
        
        
    }

}
