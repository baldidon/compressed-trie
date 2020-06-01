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
                    	auxiliaryWord = st.nextToken().toLowerCase(); //tutto minuscolo
                    	/*
                    	 *qui sotto dico: tanto i segni di punteggiatura so sempre in coda alla 
                    	 */
                    	if(!stringExaminator(auxiliaryWord).equals(""))
                    		words.add(auxiliaryWord.trim().replaceAll("[ \\p{Punct}]", ""));
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
    
    //"pulisco" la stringa da segni di punteggiatura (nell'ipotesi che a scrivere il testo sia stato un competente, i segni
    //di punteggiatura vanno sempre in fondo alla parola) e restituisco stringa vuota se stringa è un - o un numero
    private String stringExaminator(String auxiliaryWord) {
    	if(auxiliaryWord.contains("("))
    		auxiliaryWord = auxiliaryWord.substring(1,auxiliaryWord.length());
    	if(auxiliaryWord.contains("("))
    		auxiliaryWord = auxiliaryWord.substring(0,auxiliaryWord.length()-2);
   
    	if(auxiliaryWord.equals("-") /*|| da aggiungere se c'è un numero*/)
			auxiliaryWord = "";

		return auxiliaryWord;
    }

    public String toString() {
        String s = "";

        for (int i = 0; i < words.size(); i++) {
            s += i + ") " + words.get(i) + " \n";
        }
        return s;
    }

}