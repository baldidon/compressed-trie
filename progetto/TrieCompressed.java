import java.util.ArrayList;

/*
* ANDREA BALDINELLI
* Istanzio la radice
*/
public class TrieCompressed {

    private final Node root;
    private final ArrayList<String> dict;

    // costruttore, passo per parametro il dizionario e quando istanzio oggetto, lo
    // "riempo" subito!
    public TrieCompressed(final ArrayList<String> dict) {
        this.root = new Node(0, 0, 0);
        this.dict = dict;
        this.buildTrie(this.dict);
    }

    // se è vuoto
    public boolean isEmpty() {
        return this.root != null;
    }

    // restituisce la radice, che è un oggetto null
    public Node getRoot() {
        return this.root;
    }

    // aggiungo al nodo un nuovo figlio, controllo se è figlio unico o ultimo dei
    // fratelli (l'ultimo aggiunto va in coda alla lista!)
    public void addChild(final Node parentNode, final int index, final int begin, final int end) {
        final Node newNode = new Node(index, begin, end);
        newNode.setParent(parentNode);

        if (parentNode.leftChild == null) // non ha alcun figlio
            parentNode.setLeftChild(newNode);
        else // ho già almeno un figlio
        {
            // scandisco i fratelli
            Node auxNode = parentNode.getLeftChild();
            while (auxNode.getRightSibling() != null) {
                auxNode = auxNode.getRightSibling();
            }

            auxNode.setRightSibling(newNode);
        }

    }

    // passa per parametro la lista di parole estrapolate con WordParser e
    // costruisco l'albero!
    private void buildTrie(final ArrayList<String> dict) {
        final int length = dict.size();
        String stringToAdd;
        //scandisco tutte le parole del dizionario passato per parametro
        for(int i=0; i<length; i++){
            stringToAdd = dict.get(i);
            boolean findOccurrence = false;
            Node auxNode = this.root.getLeftChild();
            Node prevAuxNode = this.root;
            /*
            per "visitare ogni nodo dell'albero". TESTATO CON PAROLE TUTTE DIVERSE, SEMBRA FUNZIONARE
            */
            int j=0;//indice che uso per scorrere le parole

            //itero fra i nodi
            while(auxNode != null){
                //controllo solo primo carattere al "primo livello"
                String stringNode = dict.get(auxNode.getStringIndex());
                //se non ho corrispondenza fra prima lettera del nodo e la parola da aggiungere, passo al fratello
                if(stringNode.charAt(j)!=stringToAdd.charAt(j)){
                    prevAuxNode = auxNode;
                    auxNode = auxNode.getRightSibling();
                }
                //se ho corrispondenza fra prima lettera della parola nel nodo e la parola, ma tale nodo non ha figli
                else if(auxNode.getLeftChild()==null){
                    //auxNode ha il primo carattere che combacia!
                    findOccurrence = true;
                    j++;
                    //non ha alcun figlio, devo controllare quante sono le parole che combaciano
                    while(!(stringNode.charAt(j) != stringToAdd.charAt(j))){
                        j++;
                    }
                    //esco quando le due parole non sono più ugueli
                    auxNode.setIndex(auxNode.getStringIndex(), 0, j);
                    this.addChild(auxNode, auxNode.getStringIndex(), j, auxNode.index[2]);
                    this.addChild(auxNode, i, j, stringToAdd.length());
                    
                } 
                //se ho corrispondenza fra un nodo e quest'ultimo ha dei figli, passo la palla al prossimo ciclo while!
                else{
                    //se il primo nodo che ho trovato ha già dei figli, devo controllare i figli
                    prevAuxNode = auxNode;
                    auxNode = auxNode.getLeftChild();
                    j++;
                }
            }

            if(findOccurrence==false)
                if(prevAuxNode == this.root)
                    this.addChild(this.root,i, j, stringToAdd.length());
                else
                    this.addChild(prevAuxNode.getParent(),i,j,stringToAdd.length());
        }

    } 
    
    







    //A MALINCUORE DICO ARRUBBATO DA INTERNET
    // visita all'albero
    public void traverseTree(Node n){ 
        while(n != null) 
        {   
            if(n.equals(this.root))
                n = n.getLeftChild();
            else{
                System.out.print(dict.get(n.getStringIndex())+ " "); 
                if(n.getLeftChild() != null) 
                    n = n.getLeftChild(); 
                else 
                    n = n.getRightSibling();
            }
        } 
    } 










/*

    /*metodo privato per la ricorsione
	private Node RecursiveSearch(String s, Node node){
		if(node != null){
			 if(node.getInfo().equals(s))
				 return node;
			 else {
				 Node nodeLeft = this.RecursiveSearch(s,node.getLeft());
				 if(nodeLeft != null)
					 return nodeLeft;
				 
				 Node nodeRight = this.RecursiveSearch(s,node.getRight());
				 if(nodeRight != null)
					 return nodeRight;
			 }
		}
		return null;
    }
    

    /*
     Restituisce il nodo il cui dato associato è info, o null se tale nodod non esiste 
	public String search(String s) {
        /*dobbiamo visitare l'albero!
        Node res = this.RecursiveSearch(s, this.root);

        if(res != null)
            return this.dict.get(res.index[0]);
        else 
            return "";
	}
*/
}