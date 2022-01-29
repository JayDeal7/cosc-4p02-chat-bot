import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class WebScraper implements Runnable{
    private static final int MAX_DEPTH = 2;                             //Number of links to recursively visit
    private Thread thread;
    private String website;                                             //first link to visit
    private ArrayList<String> visitedLinks = new ArrayList<String>();   //list of visited links
    private int ID;                                                     //ID of threads


    public WebScraper(String link, int id){
        System.out.print("WebScraper Created");
        website = link;
        ID = id;

        thread = new Thread(this);      //creates thread
        thread.start();

    }

    @Override
    public void run() {
        scrape(1, website);
    }

    //Recursively scape links off given website
    private void scrape(int depth, String url){
        if(depth <= MAX_DEPTH){
            Document doc = request(url);    //checks connection

            if(doc != null){

                for(Element link : doc.select("a[href]")){      //finds all links off website
                    String next_link = link.absUrl("href");   //removes href tag and returns next website link
                    if(visitedLinks.contains(next_link) == false){       //checks if  we visited the website
                        scrape(depth++, next_link);
                    }
                }
            }
        }
    }

    //Helper function that request access to given website and checks if connection is valid
    private Document request(String url){
        try{
            Connection connection = Jsoup.connect(url);
            Document doc = connection.get();

            //if connection is established print out the url and title of website
            if(connection.response().statusCode() == 200){
                System.out.println("\nBot ID:" + ID + " Received Webpage at " + url);

                String title = doc.title();
                System.out.println(title);
                visitedLinks.add(url);

                return doc;
            }
            //If connection fails return null
            return null;

        } catch (IOException e) {
            return null;
        }
    }

    public Thread getThread(){
        return thread;
    }
}
