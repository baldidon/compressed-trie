public class Main{

    public static void main(String[] args) {

        
        WordParser dict = new WordParser();
        String path = "./Prova.txt";

        dict.wordsFromFile(path);

        System.out.println(dict.toString()+"\n");

        TrieCompressed tc = new TrieCompressed(dict.getList());
        
        System.out.print(tc.searchWord("a"));

        tc.traverseTree(tc.getRoot());
        
        

    }
}