public class MainV2{

    public static void main(String[] args) {

        WordParser dict = new WordParser();
        String path = "./Prova.txt";

        dict.wordsFromFile(path);

        System.out.println(dict.toString()+"\n");

        long t0 = System.nanoTime();
        TrieCompressedV2 tc = new TrieCompressedV2(dict.getList());
        long t1=System.nanoTime();
        System.out.println("tempo impiegato\t"+(t1-t0)/Math.pow(10,9)+" nano secondi");
				
        tc.traverseTreeV2(tc.getRoot());
        
        for(int i : tc.searchWord("la"))
            System.out.println( "\n"+ i+"\n");
        System.out.println(tc.numOfOccurrency("la"));
        System.out.println(tc.isPresent("la"));
        
        //System.out.println(tc.getRoot().getLeftChild().numOfOccurrency()+1);

    }
}