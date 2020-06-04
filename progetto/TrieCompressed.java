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
    public void addChild(Node parentNode, int index, int begin, int end) {
        Node newNode = new Node(index, begin, end);
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

    //scambio la posizione di due sottoalberi
    //i figli del nodo n, diventano figli del nodo n1
    public void swapChildren(Node n, Node n1){
        Node aux = n.getLeftChild();
        while(aux!= null){

            this.addChild(n1, aux.getStringIndex(), aux.getSubstring()[0], aux.getSubstring()[1]);

            aux = aux.getRightSibling();

        }
    }

    // passa per parametro la lista di parole estrapolate con WordParser e
    // costruisco l'albero!
    /*
    private void buildTrie(final ArrayList<String> dict) {
        final int length = dict.size();
        String stringToAdd; //variabile ausiliaria in cui salvo la i-esima parola da aggiungere all'interno della struttura dati!
        //scandisco tutte le parole del dizionario passato per parametro
        for(int i=0; i<length; i++){
            stringToAdd = dict.get(i);
            //variabile che uso per verificare se ho trovato o meno un'occorrenza fra le parol
            boolean findOccurrence = false;
            Node auxNode = this.root.getLeftChild();
            //tengo il riferimento a prevAuxNode solo perchè, quando auxNode è null, ho il riferimento dell'ultimo nodo non nullo a cui attacco il "nodo da aggiungere"
            Node prevAuxNode = this.root;
            //per "visitare ogni nodo dell'albero     
            int j=0;//indice che uso per scorrere le parole
            //itero fra i nodi

            while(auxNode != null){
                //controllo solo primo carattere al "primo livello"
                String stringNode = dict.get(auxNode.getStringIndex());
                //se non ho corrispondenza fra prima lettera del nodo e la parola da aggiungere, passo al fratello destro
                if(stringNode.charAt(j)!= stringToAdd.charAt(j)){
                    prevAuxNode = auxNode;
                    auxNode = auxNode.getRightSibling();
                }
                //se ho corrispondenza fra prima lettera della parola nel nodo e la parola, ma tale nodo non ha figli
                else if(auxNode.getLeftChild()==null){
                    //auxNode ha il j-esimo carattere che combacia!
                    findOccurrence = true;
                    //non ha alcun figlio, devo controllare quante sono le parole che combaciano
                    while(stringNode.charAt(j) == stringToAdd.charAt(j) ){
                        j++;
                    }
                    //esco quando le due parole non sono più ugueli
                    auxNode.setIndex(auxNode.getStringIndex(), auxNode.getSubstring()[0], j);
                    this.addChild(auxNode, auxNode.getStringIndex(), j, stringNode.length());
                    this.addChild(auxNode, i, j, stringToAdd.length());
                    
                } 
                //se ho corrispondenza fra un nodo e quest'ultimo ha dei figli, passo la palla al prossimo ciclo while!
                else{
                    //se il primo nodo che ho trovato ha già dei figli, devo controllare i figli, mi riporto al problema di "partenza, soltanto ad un livello diverso dell'albero!"
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
    */




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
                    int k = j; //numero di occorrenze fra prefissi
                    //conto quante occorrenze ho fra le stringhe
                    while(stringAuxNode.charAt(k) == stringToAdd.charAt(k) && k<stringToAdd.length()-1)
                    {
                        k++;
                    }
                    //controllo se la sottostringa ottenuta è più piccola di quella ottenuta precedentemente
                    //se sì devo cambiare indici sottostringa auxNode e devo cambiare 
                    //se non ha figli, la sottostringa associata al nodo è la lunghezza stessa della stringa!
                    if(auxNode.getSubstringLength()>k)
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
                            
                            auxNode = null;
                        }
                        

                    }
                    else//vuol dire che ho trovato una corrispondenza fra i prefissi maggiore, e ciò accade quando ho certamente già dei figli!
                    {
                        j++;
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
    public void traverseTree(Node n){ 
        while(n != null) 
        {   
            if(n.equals(this.root))
                n = n.getLeftChild();
            else{
                System.out.print(dict.get(n.getStringIndex()).substring(n.getSubstring()[0],n.getSubstring()[1])+ " "); 
                if(n.getLeftChild() != null) 
                    n = n.getLeftChild(); 
                else 
                    if(n.getRightSibling() != null) 
                        n = n.getRightSibling();
                    else{
                        n = n.getParent().getRightSibling();
                        System.out.print("\n");
                    }
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