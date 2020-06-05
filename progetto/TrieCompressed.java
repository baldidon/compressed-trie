import java.util.ArrayList;
import java.util.LinkedList;

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
                //ho trovato una corrispondenza, ho vari scenari
                else 
                {

                    if(stringToAdd.charAt(j) == '*'){
                        prevAuxNode = auxNode;
                        break;
                    }
                    int k = j; //uso per valutare quante lettere ho in comune fra il nodo auxNOde e la parola da aggiungere 
                    //conto quante occorrenze ho fra le stringhe
                    while(stringAuxNode.charAt(k) == stringToAdd.charAt(k) && k<stringAuxNode.length()-1)
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
                        else//auxNode ha dei figli, allora vuol dire che la parola la devo aggiungere aqui, ma devo cambiare anche indici auxNode e "i caratteri"
                            //rimasti fuori 
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
                    else //if(auxNode.getSubstringLength()<k)//vuol dire che ho trovato una corrispondenza fra i prefissi maggiore, e ciò accade quando ho certamente già dei figli!
                    {
                        j = auxNode.getSubstring()[1];
                        prevAuxNode = auxNode;
                        auxNode = auxNode.getLeftChild();
                    }
                }
            }
            //se sono nel primo caso, oppure ho una parola che non è uguale a nessun altro prefisso ricado in questi due casi!
            if(findOccurrence==false)
                if(prevAuxNode == this.root)
                    this.addChild(this.root,i, j, stringToAdd.length());
                else
                    this.addChild(prevAuxNode.getParent(),i,j,stringToAdd.length());
            
        }

    }
    
    



    // visita all'albero
    //ORA SEMBRA FUNZIONARE EGREGIAMENTE!
    //only debug, sparirà brutalmente
    /*idea:
    * scorro tutti i figli sinistri, al primo figlio mancante vado verso i fratelli dell'ultimo figlio sinistro.
      poi risalgo! fino al primo padre avente un fratello destro 
    */
    public void traverseTree(Node n){ 
        while(n != null) 
        {   
            if(n.equals(this.root))
                n = n.getLeftChild();
            else{
                System.out.print(dict.get(n.getStringIndex()).substring(n.getSubstring()[0],n.getSubstring()[1])+" "); 
                if(n.getLeftChild() != null) 
                    n = n.getLeftChild(); 
                else{
                    System.out.println("non ho più bimbi"); 
                    if(n.getRightSibling() != null)
                    { 
                        n = n.getRightSibling();
                    }
                    else //risalgo al primo padre che ha un fratello destro
                    { 
                        System.out.println("non ho più fratelli");  
                        n = n.getParent();
                        while(n.getRightSibling() == null && n != this.root)
                        {
                            n = n.getParent();
                        }
                        
                        if(n.getRightSibling()!= null)
                            n = n.getRightSibling();
                        else 
                            n = null;
                    }
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
    public LinkedList<Integer> searchWord(String word)
    {
        LinkedList<Integer> res = new LinkedList<>();
        //aggiungo il carattere speciale
        word = word + "*";
        //definisco un nodo ausiliare per cercare all'interno del trie, parto dal figlio sinistro della radice
        Node auxNode = this.root.getLeftChild();
        Node rightSibling = auxNode.getRightSibling();
        boolean found = false;
        //mi tiene conto di quante occorrenze ho di quella parola che sto cercando
        int occurrency = 0;

        int j= auxNode.getSubstring()[0]; //mi tengo conto dell primo carattere della sottostringa
        while(auxNode != null && !found)
        {   
            //se il j esimo carattere non combacia, passo tutto ad un eventuale fratello destro di auxNode
            if(this.dict.get(auxNode.getStringIndex()).charAt(j) != word.charAt(j))
            {
                auxNode = auxNode.getRightSibling();
            }
            //se il j-esimo carattere combacia, passo al figlio!
            else if (auxNode.getLeftChild()!= null)
            {
                auxNode = auxNode.getLeftChild();
                j = auxNode.getSubstring()[0];
            }
            else
            {
                //se sono arrivato in un nodo che non ha figli, faccio il controllo che tutti gli altri caratteri rimanenti combacino
                int k = j;
                String wordAuxNode = this.dict.get(auxNode.getStringIndex());
                while(k<wordAuxNode.length() && k < word.length() && wordAuxNode.charAt(k) == word.charAt(k))
                    {
                        k++;
                    }
                if(k==word.length()){
                    found = true;
                    //ho trovato un'occorrenza
                    occurrency++;
                    res.add(auxNode.getStringIndex());
                    //cerco se ce ne sono altre
                    rightSibling = auxNode.getRightSibling();
                    if(rightSibling!=null)
                    {
                        //System.out.print("sono entrato ");
                        String wordRightSibling = this.dict.get(rightSibling.getStringIndex());
                        while(rightSibling != null )
                        {
                            if(wordRightSibling.charAt(rightSibling.getSubstring()[0]) == '*')
                                res.add(rightSibling.getStringIndex());
                            
                            rightSibling = rightSibling.getRightSibling();
                        }
                    }  
                }
                else 
                    //ergo dovrebbe uscire dal ciclo
                    auxNode = auxNode.getLeftChild();
            }
        }

        //se sono uscito perchè auxNode è diventato null, ergo non ho trovato la parola desiderata, ergo sono nella merda
        return res;

    }

}