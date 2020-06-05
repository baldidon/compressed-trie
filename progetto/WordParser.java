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
import java.text.Normalizer;


public class WordParser {

    private ArrayList<String> words = null;

    //vengono escluse le stopWord
    private String[] stopWord = {"di*", "a*", "da*", "in*", "con*", "su*", "per*", "tra*", "fra*",
                                    "il*", "lo*", "la*", "i*", "gli*", "le*", "un*", "uno*", "una*", "e*", "o*"};

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
            		
                    auxiliaryBuffer = auxiliaryBuffer.replaceAll("'", " "); //sostituisce gli apostrofi con lo spazio

                    System.out.println(auxiliaryBuffer);
                    
                    st = new StringTokenizer(auxiliaryBuffer);

                    while (st.hasMoreTokens()) { //finchè ho parole nella frase

                        auxiliaryWord = st.nextToken();                     
                        
                        auxiliaryWord = normalizedString(auxiliaryWord);  

                        //auxiliaryWord = Normalizer.normalize(auxiliaryWord, Normalizer.Form.NFD);                                  
                        //auxiliaryWord = auxiliaryWord.toLowerCase().trim().replaceAll("[^a-z]", "")+"*"; //tutto minuscolo
                        
                        aggiungiParola = true;                      

                        if(auxiliaryWord.length() < 3) //le parole di una sola lettera non sono ammesse, considero anche '*'
                            aggiungiParola = false;

                        for(int i = 0; i < stopWord.length; i++)
                            if(auxiliaryWord.equals(stopWord[i]))
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

    // 'ripulisce' la stringa dai caratteri speciali
    public String normalizedString(String normalizedString){ 

        normalizedString = Normalizer.normalize(normalizedString, Normalizer.Form.NFD); //rimuove lettere accentate
        return normalizedString = normalizedString.toLowerCase().trim().replaceAll("[^a-z]", "")+"*"; // rimuove tutti i caratteri non compresi fra a-z

    }
    

}