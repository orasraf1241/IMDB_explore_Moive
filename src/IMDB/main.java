package IMDB;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


import java.io.IOException;

public class main {

     public static final String baseUrl = "https://www.imdb.com/find?s=tt&q=avengers&ref_=nv_sr_sm";

     public static String findMovie(String movieName){

         String movieUrl ="https://www.imdb.com";
         String url = "https://www.imdb.com/find?q="+ movieName+ "&s=tt&ttype=ft&ref_=fn_ft";

         try {
             final Document document = Jsoup.connect(url).get();

             //building the url to a movie
                 StringBuilder inspect = new StringBuilder(document.select("tr.odd.findResult > .result_text").get(0).toString());
                 String moviaId = inspect.substring(inspect.indexOf("/title/"), inspect.lastIndexOf("?ref_=f"));
                 movieUrl = movieUrl + moviaId;


         } catch (IOException e) {
             e.printStackTrace();
         }
         return movieUrl;
     }


     public static Moviadetails findElement(String movieUrl,Moviadetails movie){
         try {
             movie.genre = new LinkedList<>();
             movie.actors = new LinkedList<>();
             movie.director = new LinkedList<>();
             final Document document = Jsoup.connect(movieUrl).get();


                movie.moviaName = document.select
                        (".dxSWFG.TitleHeader__TitleText-sc-1wu6n3d-0").text();
                movie.duration = document.select
                        (".baseAlt.dxizHm.TitleBlockMetaData__MetaDataList-sc-12ein40-0.ipc-inline-list--show-dividers.ipc-inline-list > li.ipc-inline-list__item:nth-of-type(3)").text();
                movie.url = movieUrl;
                movie.rating = document.select
                        ("li.ipc-inline-list__item:nth-of-type(2) > .rgaOW.TitleBlockMetaData__StyledTextLink-sc-12ein40-1.ipc-link--inherit-color.ipc-link--baseAlt.ipc-link").text();


                for (int i = 1; i < 4; i++) {
                    movie.genre.add(document.select
                            ("a.ipc-chip--on-baseAlt.ipc-chip.fzmeux.GenresAndPlot__GenreChip-cum89p-3:nth-of-type("+i+")").text());
                }
                for (int i = 1; i < 4; i++) {
                    movie.actors.add(document.select
                            (".iGxbgr.PrincipalCredits__PrincipalCreditsPanelWideScreen-hdn81t-0 > .ipc-metadata-list--baseAlt.title-pc-list.ipc-metadata-list--dividers-all.ipc-metadata-list > .ipc-metadata-list-item--link.ipc-metadata-list__item > .ipc-metadata-list-item__content-container > .baseAlt.ipc-metadata-list-item__list-content.ipc-inline-list--inline.ipc-inline-list--show-dividers.ipc-inline-list > li.ipc-inline-list__item:nth-of-type(" + i + ") > .ipc-metadata-list-item__list-content-item--link.ipc-metadata-list-item__list-content-item\n").text());
                }
                for (int i = 1; i < 4; i++) {
                    String str = (document.select
                            ("" +
                                    ".iGxbgr.PrincipalCredits__PrincipalCreditsPanelWideScreen-hdn81t-0 > .ipc-metadata-list--baseAlt.title-pc-list.ipc-metadata-list--dividers-all.ipc-metadata-list > li.ipc-metadata-list__item:nth-of-type(1) > .ipc-metadata-list-item__content-container > .baseAlt.ipc-metadata-list-item__list-content.ipc-inline-list--inline.ipc-inline-list--show-dividers.ipc-inline-list > .ipc-inline-list__item > .ipc-metadata-list-item__list-content-item--link.ipc-metadata-list-item__list-content-item").text());
                    if (movie.director.contains(str))
                        continue;
                    else
                        movie.director.add(str);
                }

         }catch (IOException e) {
             e.printStackTrace();
         }
         return movie;

     }



     public static void main(String[] args) {


        Moviadetails moviadetails = new Moviadetails();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a Movia name: ");
        String str = sc.nextLine();
        str=findMovie(str);
        moviadetails = findElement(str,moviadetails);


        moviadetails.toString();




     }

}
