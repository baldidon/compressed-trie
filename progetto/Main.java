public class Main{

    public static void main(String[] args) {

        WordParser dict = new WordParser();
        String path = "./Prova.txt";

        dict.wordsFromFile(path);

        System.out.println(dict.toString()+"\n");

        TrieCompressed tc = new TrieCompressed(dict.getList());
        tc.traverseTree(tc.getRoot());
        

    }
}