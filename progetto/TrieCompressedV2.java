import java.util.ArrayList;
import java.util.LinkedList;


public class TrieCompressedV2 {

    private final Node root;
    private final ArrayList<String> dict;

    // costruttore, passo per parametro il dizionario e quando istanzio oggetto, lo
    // "riempo" subito!
    public TrieCompressedV2(final ArrayList<String> dict) {
        this.root = new Node(-1, -1, -1);
        this.dict = dict;
        this.buildTrieV2(this.dict);
    }

    // se è vuoto
    public boolean isEmpty() {
        return this.root != null;
    }

    // restituisce la radice, che è un oggetto null
    public Node getRoot() {
        return this.root;
    }

    public void addChildV2(Node parentNode, Node newNode)
    {
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
    //
    public void swapNodeV2(Node n1, Node n2)
    {   
        n2.setParent(n1.getParent());
        /*devo sistemare la lista dei fratelli di n1*/
        Node auxNode = n1.getParent().getLeftChild();
        if(!auxNode.equals(n1)) //se n1 NON è figlio sinistro
        {
            while(!auxNode.getRightSibling().equals(n1))
            {
                auxNode = auxNode.getRightSibling();
            }
            auxNode.setRightSibling(n2);
            n2.setRightSibling(n1.getRightSibling());
        }
        else{
            n1.getParent().setLeftChild(n2);
            n2.setRightSibling(n1.getRightSibling());
        }
    }



    
    //altra idea, prima costruisco il trie standard e poi lo comprimo!
    public void buildTrieV2(ArrayList<String> dict)
    {
        final int length = dict.size();
        String stringToAdd;
        String stringAuxNode;

        for(int i=0; i<length; i++)
        {
            Node auxNode = this.root.getLeftChild();
            //tengo il riferimento a prevAuxNode solo perchè, quando auxNode è null, ho il riferimento dell'ultimo nodo non nullo a cui attacco il "nodo da aggiungere"
            Node prevAuxNode = this.root;
            stringToAdd = dict.get(i);
            int j=0;
            while(auxNode!=null)
            {
                stringAuxNode = dict.get(auxNode.getStringIndex()); //fisso il riferimento alla stringa del nodo in esame
                if(stringAuxNode.charAt(j) != stringToAdd.charAt(j))
                {
                    auxNode = auxNode.getRightSibling();
                }
                else
                {
                    prevAuxNode = auxNode;
                    auxNode = auxNode.getLeftChild();
                    j++;
                }

            }

            if(j>=stringToAdd.length())
            {
                j--;
                //auxNode = new Node(i,j,j);
                //this.addChildV2(prevAuxNode.getParent(), auxNode);
                //il nodo asterisco avrà tutte le occorrenze aggiuntive di quella parola!
                if(prevAuxNode.getListOfOccurrency() == null)
                    prevAuxNode.createListOfOccurrency();

                prevAuxNode.addOccurrency(i);
            }
            else
            {
                auxNode = new Node(i,j,j);
                this.addChildV2(prevAuxNode,auxNode);
                j++;
                //continuo ad aggiungere lettera per lettera
                while(j<stringToAdd.length())
                {
                    Node newNode = new Node(i,j,j);
                    this.addChildV2(auxNode, newNode);
                    auxNode = auxNode.getLeftChild();
                    j++;
                }
            }

        }

        //costruito il trie, comprimo!
        this.compressedTrie();
    }


    //idea del metodo, comprimere tutte le catene fatte di un solo figlio
    public void compressedTrie()
    {
        //mi tengo riferimento del primo nodo che visito e dell'ultimo nodo, quello che ha dei figli
        Node firstNode = this.root.getLeftChild();
        Node lastNode = firstNode;
        int lengthPath = 0;

        while(firstNode != null){
            //se ho due figli oppure non ho più figli da scorrere (fine catena)
            if(lastNode.getLeftChild() == null || lastNode.getLeftChild().getRightSibling() != null)
            {
                if(lengthPath > 0)//altrimenti non posso far nulla
                {
                    lastNode.setIndex(lastNode.getStringIndex(), firstNode.getSubstring()[0], lastNode.getSubstring()[1]);
                    this.swapNodeV2(firstNode, lastNode);
                }
                
                if(lastNode.getLeftChild()!= null)//vado oltre se posso andare oltre
                {
                    firstNode = firstNode.getLeftChild();
                    lastNode = firstNode;
                    lengthPath = 0;
                }
                else if(firstNode.getRightSibling()!= null)//vado al fratello di first node
                {
                    firstNode = firstNode.getRightSibling();
                    lastNode = firstNode;
                    lengthPath = 0;
                }
                else //risalgo al primo nodo con un fratello
                { 
                    firstNode = firstNode.getParent();
                    while(firstNode.getRightSibling() == null && firstNode != this.root)
                    {
                        firstNode = firstNode.getParent();
                    }
                    
                    if(firstNode.getRightSibling()!= null)
                    {
                        firstNode = firstNode.getRightSibling();
                        lastNode = firstNode;
                        lengthPath=0;
                    }
                    else 
                        firstNode = null;
                }

            }
            else
            {
                lengthPath++;
                lastNode = lastNode.getLeftChild();
            }
        }

    }




    public void traverseTreeV2(Node n)
    { 
        while(n != null) 
        {   
            if(n.equals(this.root))
                n = n.getLeftChild();

            else{
                System.out.print(dict.get(n.getStringIndex()).substring(n.getSubstring()[0],n.getSubstring()[1]+1)+" "); 
                if(n.getLeftChild() != null)
                { 
                    n = n.getLeftChild();
                    //System.out.println("passo al figlm");
                }
                else{ 
                    if(n.getRightSibling() != null)
                    { 
                        n = n.getRightSibling();
                        //System.out.println("passo al fratm");
                    }
                    else //risalgo al primo padre che ha un fratello destro
                    { 
                        n = n.getParent();
                        while( n.getRightSibling() == null && n != this.root)
                        {
                            n = n.getParent();
                        }
                        
                        if(n.getRightSibling()!= null){
                            n = n.getRightSibling();
                           // System.out.println("passo ad un parentm");
                        }
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
        LinkedList<Integer> res = new LinkedList<>();//lista (perchè non so a priori quante occorrenze ho!)
        //aggiungo il carattere speciale
        word = word + "*";
        String wordOfNode;
        //definisco un nodo ausiliare per cercare all'interno del trie, parto dal figlio sinistro della radice
        Node auxNode = this.root.getLeftChild();
        boolean found = false;


        int j= 0; //mi tengo conto dell primo carattere della sottostringa
        while(auxNode != null && !found)
        {   
            wordOfNode = this.dict.get(auxNode.getStringIndex());
            //se il j esimo carattere non combacia, passo tutto ad un eventuale fratello destro di auxNode
            if(wordOfNode.charAt(j) != word.charAt(j))
            {
                auxNode = auxNode.getRightSibling();
            }

            //se il j-esimo carattere combacia, passo al figlio!
            else if (auxNode.getLeftChild()!= null){
                auxNode = auxNode.getLeftChild();
                j = auxNode.getSubstring()[0];
            }
            else
            {
                //se sono arrivato in un nodo che non ha figli, faccio il controllo che tutti gli altri caratteri rimanenti combacino
                int k = j;
                String wordAuxNode = this.dict.get(auxNode.getStringIndex());

                while(k<wordAuxNode.length() && k<word.length() &&  wordAuxNode.charAt(k) == word.charAt(k))
                    {
                        k++;
                    }
                
                if(k==word.length()){
                    found = true;
                    //ho trovato un'occorrenza
                    res.add(auxNode.getStringIndex());
                    //cerco se ce ne sono altre
                    if(auxNode.getListOfOccurrency()!=null && auxNode.hasOccurrency())
                    {   
                        System.out.println("ci sono occorrenze");
                        for(int i: auxNode.getListOfOccurrency())
                        {
                            System.out.println("occorrenza beccata");
                            res.addLast(i);
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