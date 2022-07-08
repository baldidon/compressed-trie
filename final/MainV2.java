public class MainV2{

    public static void main(String[] args) {

        WordParser dict = new WordParser();
        String path = "./prova2.txt";

        dict.wordsFromFile(path);
        System.out.println(dict.getList().size());

        //System.out.println(dict.toString()+"\n");

        long t0 = System.nanoTime();
        TrieCompressedV2 tc = new TrieCompressedV2(dict.getList());
        long t1=System.nanoTime();
        System.out.println("tempo impiegato\t"+(t1-t0)/Math.pow(10, 9) +" secondi");
				
        //tc.traverseTreeV2(tc.getRoot());
        for(int i : tc.searchWord("Manzoni"))
            System.out.println( "\n"+ i+"\n");

        System.out.println(tc.numOfOccurrency("sproposito"));
        System.out.println(tc.isPresent("sproposito"));
        
        //System.out.println(tc.getRoot().getLeftChild().numOfOccurrency()+1);

    }
}