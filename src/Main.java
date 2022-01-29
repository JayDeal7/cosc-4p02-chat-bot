import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

        ArrayList<WebScraper> bots = new ArrayList<>();
        bots.add(new WebScraper("https://brocku.ca/", 1));
        bots.add(new WebScraper("https://niagara2022games.ca/", 2));

        //Used for multi-threaded execution
        for(WebScraper w : bots){
            try {
                w.getThread().join();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}
