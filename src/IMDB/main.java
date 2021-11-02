package IMDB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;
import java.io.IOException;

public class main {
     
     public static final String baseUrl = "https://www.imdb.com/find?s=tt&q=avengers&ref_=nv_sr_sm";

     public static boolean isDevelopment(String url){
         //in this fnc we check if the movie is exsist
         try {
             final Document document = Jsoup.connect(url).get();
             if (document.select("div.bAolrB.SubNav__SubNavContentBlock-sc-11106ua-2 > .ipc-link--launch.ipc-link--inherit-color.ipc-link--baseAlt.ipc-link").text().contains("In development: More at IMDbPro"))
                 return false;
             else
                 return true;

         } catch (IOException e) {
             e.printStackTrace();
         }
         return false;


     }

     public static String movieUrl(StringBuilder inspect, String url, Document d,int i){
          //in this methos we take the url 
         String moviaId = inspect.substring(inspect.indexOf("/title/"), inspect.lastIndexOf("?ref_=f"));
         String movieUrl = url + moviaId;
          
         return d.select("tr.even.findResult:nth-of-type("+(i+1)+") > .result_text").text();
     }
     //this functions give as the first movie that hava all the rules
     public static String findUrlMovie(String movieName){
         String url ="https://www.imdb.com";
         String searchResult = "https://www.imdb.com/find?q="+ movieName+ "&s=tt&ttype=ft&ref_=fn_ft";
         String name = null;
         String movieUrl = null;
          
         try {
             final Document document = Jsoup.connect(searchResult).get();
             //here we get the numbers of the search result in IMDB
             int numberOfMovieResult =document.select("tr.odd.findResult > .result_text").size()+document.select("tr.even.findResult > .result_text").size();
            
              //in this loop we check that we  have all the information we looking for about the movie
             for (int i = 0; i < numberOfMovieResult; i++){
                 //building the url to a movie
                 if (i % 2 == 0) {
                     StringBuilder inspect = new StringBuilder(document.select("tr.odd.findResult:nth-of-type("+(i+1)+") > .result_text").toString());
                     String moviaId = inspect.substring(inspect.indexOf("/title/"), inspect.lastIndexOf("?ref_=f"));
                     movieUrl = url + moviaId;
                     name = document.select("tr.odd.findResult:nth-of-type("+(i+1)+") > .result_text").text();
                
                 } else {
                     StringBuilder inspect = new StringBuilder(document.select("tr.even.findResult:nth-of-type("+(i+1)+") > .result_text").toString());
                     String moviaId = inspect.substring(inspect.indexOf("/title/"), inspect.lastIndexOf("?ref_=f"));
                     movieUrl = url + moviaId;
                     name = document.select("tr.even.findResult:nth-of-type("+(i+1)+") > .result_text").text();
                 }
                   if (name.toLowerCase(Locale.ROOT).matches("(.*)"+movieName+"(.*)") || name.toUpperCase(Locale.ROOT).matches("(.*)"+movieName+"(.*)") || name.contains(movieName)){
                     if (isDevelopment(movieUrl)) {
                         Moviadetails m = new Moviadetails();
                         m.movieInfo(movieUrl, m);
                         m.toString(movieName);
                         name="finish";
                     }
                 }

             }
         } catch (IOException e) {
         }
         return name;
     }


     public static void main(String[] args) {
          
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a Movia name: ");
        String UrlMovie = sc.nextLine();
        UrlMovie = findUrlMovie(UrlMovie);
        if (UrlMovie==null) {
            System.out.println("We cant find your movie");
            return;
        }
     }
}
