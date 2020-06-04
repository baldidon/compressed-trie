/* ANDREA BALDINELLI */
/*
"Classe che si occupa di setacciare un testo e salvare le parole in funzione dell'ordine di "incontro nel testo" 
*/

/*
rispetto ad un'implementazione di un classico array, ho il vantaggio di accedere alle parole via indice! 
*/
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;


public class WordParser {

    private ArrayList<String> words = null;

    private String[] preposizioni = {"di*", "a*", "da*", "in*", "con*", "su*", "per*", "tra*", "fra*"};

    private boolean aggiungiParola; //variabile utilizzata per capire se la parola è ammessa

    public WordParser() {
        this.words = new ArrayList<>();
    }

    public boolean wordsFromFile(String pathToFile) {
    	StringTokenizer st = null; //serve per splittare una frase in parole, ove incontra uno spazio
        try {
        	
            String auxiliaryBuffer = null; //salvo la riga attuale del file
            String auxiliaryWord = null; //salvo la singola parola filtrata
            BufferedReader importFile = Files.newBufferedReader(Paths.get(pathToFile)); //da Grilli preso 

            while ((auxiliaryBuffer = importFile.readLine()) != null /*&& (!auxiliaryBuffer.isEmpty())*/) {
            		st = new StringTokenizer(auxiliaryBuffer);
                    while (st.hasMoreTokens()) { //finchè ho parole nella frase                  
                                         
                        auxiliaryWord = st.nextToken().toLowerCase().trim().replaceAll("[^a-z]", "")+"*"; //tutto minuscolo
                        aggiungiParola = true;                      

                        for(int i = 0; i < preposizioni.length; i++)
                            if(auxiliaryWord.equals(preposizioni[i]))
                                aggiungiParola = false;

                        if(!auxiliaryWord.equals("") && aggiungiParola)
                            words.add(auxiliaryWord);



                        // PARTE FATTA DA ANDREA
                        //auxiliaryWord = st.nextToken().toLowerCase().trim().replaceAll("[^a-z]", "")+"*"; //tutto minuscolo                                         

                        /*
                    	 *qui sotto dico: tanto i segni di punteggiatura so sempre in coda alla 
                    	 */                    	

                        //if(!auxiliaryWord.equals(""))
                    		//words.add(auxiliaryWord);
                     }

            }
            return true;

        } catch (FileNotFoundException fnfe) {
            return false;
        } catch (IOException ioe) {
            return false;
        } catch (NullPointerException npe) {
            System.out.println("\nerror during import, file not imported!");
            return false;
        }
    }
    
    public ArrayList<String> getList(){
        return this.words;
    }

    public String toString() {
        String s = "";

        for (int i = 0; i < words.size(); i++) {
            s += i + ") " + words.get(i) + " \n";
        }
        return s;
    }

}