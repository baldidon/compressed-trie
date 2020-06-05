public class MainV2{

    public static void main(String[] args) {

        WordParser dict = new WordParser();
        String path = "./Prova.txt";

        dict.wordsFromFile(path);

        System.out.println(dict.toString()+"\n");

        TrieCompressedV2 tc = new TrieCompressedV2(dict.getList());
        
        tc.traverseTreeV2(tc.getRoot());
        
        for(int i : tc.searchWord("la"))
            System.out.println( "\n"+ i+"\n");
        

    }
}