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
     public static String findUrlMovie(String movieName){
         String url ="https://www.imdb.com";
         String searchResult = "https://www.imdb.com/find?q="+ movieName+ "&s=tt&ttype=ft&ref_=fn_ft";
         String name;
         String movieUrl = null;
         try {

             final Document document = Jsoup.connect(searchResult).get();
             int numberOfMovieResult =document.select("tr.odd.findResult > .result_text").size()+document.select("tr.even.findResult > .result_text").size();
             for (int i = 0; i < numberOfMovieResult; i++)
              {
                 //building the url to a movie
                 if (i % 2 == 0) {
                     StringBuilder inspect = new StringBuilder(document.select("tr.odd.findResult:nth-of-type("+(i+1)+") > .result_text").toString());
                     String moviaId = inspect.substring(inspect.indexOf("/title/"), inspect.lastIndexOf("?ref_=f"));
                     movieUrl = url + moviaId;
                     name = document.select("tr.odd.findResult:nth-of-type("+(i+1)+") > .result_text").text();
                     //System.out.println(i+") "+name);

                 } else {
                     StringBuilder inspect = new StringBuilder(document.select("tr.even.findResult:nth-of-type("+(i+1)+") > .result_text").toString());
                     String moviaId = inspect.substring(inspect.indexOf("/title/"), inspect.lastIndexOf("?ref_=f"));
                     movieUrl = url + moviaId;
                     name = document.select("tr.even.findResult:nth-of-type("+(i+1)+") > .result_text").text();
                    // System.out.println(i+") "+name);

                 }
                 if (name.toLowerCase(Locale.ROOT).matches("(.*)"+movieName+"(.*)") || name.toUpperCase(Locale.ROOT).matches("(.*)"+movieName+"(.*)") || name.contains(movieName)){
                     if (isDevelopment(movieUrl))
                          return movieUrl;
                 }

             }
         } catch (IOException e) {
         }
         return null;
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
        Moviadetails moviadetails = new Moviadetails();
        moviadetails.findElement(UrlMovie,moviadetails);


        moviadetails.toString();




     }

}
