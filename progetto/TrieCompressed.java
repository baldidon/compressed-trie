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
        this.root = new Node(-1, -1, -1);
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
    public void addChild(Node parentNode, int index, int begin, int end) {
        Node newNode = new Node(index, begin, end);
        newNode.setParent(parentNode);

        if (parentNode.leftChild == null) // non ha alcun figlio
            parentNode.setLeftChild(newNode);
        else // ho già almeno un figlio
        {
            // scandisco i fratelli
            Node auxNode = parentNode.getLeftChild();
            //scandisco finchè ho un fratello a dx
            while (auxNode.getRightSibling() != null) {
                auxNode = auxNode.getRightSibling();
            }
            //esco quando trovo l'ultimo figlio di parent Node
            auxNode.setRightSibling(newNode);
        }

    }
    

    //i figli del nodo n, diventano figli del nodo n1
    //non controllo se ci sono figli per il solo fatto che questo metodo viene invocato QUANDO SO SICURO CHE CI SIANO I BAMBINI DI MEZZO!
    public void swapChildren(Node oldParent, Node newParent){
        Node aux = oldParent.getLeftChild();
        aux.setParent(newParent);
        newParent.setLeftChild(aux);
        aux = aux.getRightSibling();
        //controllo gli eventuali fratelli
        while(aux!= null){
            aux.setParent(newParent);
            aux = aux.getRightSibling();

        }
    }

    //approccio diverso
    //cerco il primo carattere di un nodo, se nessun nodo rispetta caratteristiche, aggiungo un nodo che è il fratello degli altri
    //se qualcuno soddisfa i requisiti, io controllo sempre quanti sono i caratteri in comune
    public void buildTrie(ArrayList<String> dict){
        final int length = dict.size();
        String stringToAdd;
        String stringAuxNode;
        for(int i=0; i<length; i++){
            stringToAdd = dict.get(i);
            //variabile che uso per verificare se ho trovato o meno un'occorrenza fra le parol
            boolean findOccurrence = false;
            Node auxNode = this.root.getLeftChild();
            //tengo il riferimento a prevAuxNode solo perchè, quando auxNode è null, ho il riferimento dell'ultimo nodo non nullo a cui attacco il "nodo da aggiungere"
            Node prevAuxNode = this.root;
            //per "visitare ogni nodo dell'albero     
            int j=0;//indice che uso per scorrere i caratteri ugyali
            //itero fra i nodi
            while(auxNode != null){
                //se non trovo un nodo al livello j con la coincidenza di carattere desiderata, scorro al fratello
                stringAuxNode = dict.get(auxNode.getStringIndex());
                
                if(stringAuxNode.charAt(j) != stringToAdd.charAt(j)){
                    prevAuxNode = auxNode;
                    auxNode = auxNode.getRightSibling();
                }
                //ho trovato una corrispondenza
                else 
                {
                    //System.out.println("trovato corrispondenza!");
                    int k = j; //numero di occorrenze fra prefissi
                    //conto quante occorrenze ho fra le stringhe
                    while(stringAuxNode.charAt(k) == stringToAdd.charAt(k) && k<stringToAdd.length()-1)
                    {
                        k++;
                    }
                    //controllo se la sottostringa ottenuta è più piccola di quella ottenuta precedentemente
                    //se sì devo cambiare indici sottostringa auxNode e devo cambiare 
                    //se non ha figli, la sottostringa associata al nodo è la lunghezza stessa della stringa!

                    /*nuovi caratteri "scoperti" rispetto al ciclo precedente */
                    int q = k-j; 
                    if(auxNode.getSubstringLength()>q)
                    {
                        //se trovo questa relazione, so per certo che la parola viene agganciata a tale nodo!
                        if(auxNode.getLeftChild()==null)
                        {
                            //ho trovato dove mettere la parola!
                            findOccurrence = true;
                            //se il nodo "incriminato non ha figli" cambio gli indici al nodo e gli affibbio due nuovi figli!
                            auxNode.setIndex(auxNode.getStringIndex(), auxNode.getSubstring()[0],k);
                            this.addChild(auxNode, auxNode.getStringIndex(), k, stringAuxNode.length());
                            this.addChild(auxNode, i, k, stringToAdd.length());

                            auxNode = null;
                        }
                        else //auxNode ha dei figli
                        {   
                            findOccurrence = true;
                            Node newAuxNode = new Node(auxNode.getStringIndex(),k,auxNode.getSubstring()[1]);
                            this.swapChildren(auxNode, newAuxNode);
                            auxNode.setIndex(auxNode.getStringIndex(), auxNode.getSubstring()[0], k);
                            auxNode.setLeftChild(newAuxNode);
                            this.addChild(auxNode, i, k, stringToAdd.length());
                            //ONLY FOR DEBUG
                            //this.traverseTree(auxNode);
                            
                            auxNode = null;
                        }
                        

                    }
                    else //if(auxNode.getSubstringLength()<k)//vuol dire che ho trovato una corrispondenza fra i prefissi maggiore, e ciò accade quando ho certamente già dei figli!
                    {
                        j = auxNode.getSubstring()[1];
                        prevAuxNode = auxNode;
                        auxNode = auxNode.getLeftChild();
                    }
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
    //only debug, sparirà brutalmente
    /*idea:
    * scorro tutti i figli sinistri, al primo figlio mancante vado verso i fratelli dell'ultimo figlio sinistro.
      poi risalgo!
    */

    public void traverseTree(Node n){ 
        while(n != null) 
        {   
            if(n.equals(this.root))
            //se sono la radice, passo subito al primo figlio!
                n = n.getLeftChild();
            else{
                System.out.print(dict.get(n.getStringIndex()).substring(n.getSubstring()[0],n.getSubstring()[1])+ " "); 
                if(n.getLeftChild() != null) 
                    n = n.getLeftChild(); 
                else 
                    if(n.getRightSibling() != null) 
                        n = n.getRightSibling();
                    
                
                    else if(n.getParent().getRightSibling() != null) {
                        n = n.getParent().getRightSibling();
                        //System.out.print("\n");
                    }
                    else if((n.getParent() != this.root) && (n.getParent().getParent().getRightSibling()!= null)){
                        n = n.getParent().getParent().getRightSibling();
                    }
                           
                    else {
                        return;
                    }
                
            }

        } 
    } 



    //metodo ricerca
    
    //PER ORA RESTITUISCE LA PRIMA OCCORRENZA DELLA PAROLA, SE PRESENTE

    //molto simile a come abbiamo implementato la "creazione di nodi" in un trie
    /*questo metodo:
        restituisce l'indice della parola cercata, se presente nel trie 
        restituisce -1 se la parola cercata non c'è!*/
    //nel caso di parole con più occorrenze, dovrei stampare tutte le occorrenze

    public int searchWord(String word)
    {
        //aggiungo il carattere speciale
        word = word + "*";
        //definisco un nodo ausiliare per cercare all'interno del trie, parto dal figlio sinistro della radice
        Node auxNode = this.root.getLeftChild();
        //int[] occurrency;
        boolean found = false;

        int j=0;
        while( (auxNode != null) && !found )
        {   
            //se il j esimo carattere non combacia, passo tutto ad un eventuale fratello destro di auxNode
            if(this.dict.get(auxNode.getStringIndex()).charAt(j) != word.charAt(j)){
                auxNode = auxNode.getRightSibling();
            }
            //se il j-esimo carattere combacia, passo al figlio!
            else if (auxNode.getLeftChild()!= null){
                j++;
                auxNode = auxNode.getLeftChild();
            }
            else{
                found = true;
            }
        }

        //se sono uscito perchè auxNode è diventato null, ergo non ho trovato la parola desiderata, ergo sono nella merda
        if(!found)
            return -1;
            
        return  auxNode.getStringIndex() + 1;

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