public class Main{

    public static void main(String[] args) {

        WordParser dict = new WordParser();
        String path = "./prova.txt";

        dict.wordsFromFile(path);

        System.out.println(dict.toString());

    }
}