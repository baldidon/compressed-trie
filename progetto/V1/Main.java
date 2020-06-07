public class Main{

    public static void main(String[] args) {

        String s = "ciao";
        System.out.print(s.substring(1,4));
        WordParser dict = new WordParser();
        String path = "./Prova.txt";

        dict.wordsFromFile(path);

        System.out.println(dict.toString()+"\n");

        TrieCompressed tc = new TrieCompressed(dict.getList());
        
        tc.traverseTree(tc.getRoot());
        
        for(int i : tc.searchWord("la"))
            System.out.println( "\n"+ i+"\n");
        

    }
}