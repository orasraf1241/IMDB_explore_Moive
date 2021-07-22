package IMDB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Moviadetails {

    public String moviaName;
    public String url;
    public  String rating;
    public  String duration;

    public List<String>  director;
    public List<String>  stars;
    public List<String>  genre;


//This methos help as to print the list like
    public String printList(List<String> list) {
        String str = "";
        for (String s : list) {
            str += s+",";
        }
        return str;//.substring(0,str.length()-1);
    }


    public void toString(String moviaName){

        if (this.moviaName.toLowerCase(Locale.ROOT).matches("(.*)"+moviaName+"(.*)") || this.moviaName.toUpperCase(Locale.ROOT).matches("(.*)"+moviaName+"(.*)") || this.moviaName.contains(moviaName)) {
            StringBuilder sb = new StringBuilder(

                    this.moviaName + " | "
                            + printList(genre)
                            + " |" + this.rating
                            + " |" + this.duration +
                            "|" + printList(director)
                            + "| " + printList(stars));
            if (sb.charAt(sb.length()-1)==',');
                sb.deleteCharAt(sb.length()-1);
            System.out.println(sb);

        }

    }


    //this methos gives us all the information that we  need to now about the movie
    public static Moviadetails movieInfo(String movieUrl, Moviadetails movie){
        try {
            movie.genre = new LinkedList<>();
            movie.stars = new LinkedList<>();
            movie.director = new LinkedList<>();
            final Document document = Jsoup.connect(movieUrl).get();
            //get the movie name
            movie.moviaName=document.select(".TitleHeader__TitleText-sc-1wu6n3d-0").text();

            //get the movie duration
            movie.duration = document.select
                    (".baseAlt.dxizHm.TitleBlockMetaData__MetaDataList-sc-12ein40-0.ipc-inline-list--show-dividers.ipc-inline-list > li.ipc-inline-list__item:nth-of-type(3)").text();

            //get the rating of the movie
            movie.rating = document.select
                    ("li.ipc-inline-list__item:nth-of-type(2) > .rgaOW.TitleBlockMetaData__StyledTextLink-sc-12ein40-1.ipc-link--inherit-color.ipc-link--baseAlt.ipc-link").text();

            //get the list of genre
            for (Element e: document.select(".ipc-chip--on-baseAlt.ipc-chip.fzmeux.GenresAndPlot__GenreChip-cum89p-3")) {
                    movie.genre.add(e.text());
            }

            //get the list of actores
            for (Element e: document.select(".iGxbgr.PrincipalCredits__PrincipalCreditsPanelWideScreen-hdn81t-0 > .ipc-metadata-list--baseAlt.title-pc-list.ipc-metadata-list--dividers-all.ipc-metadata-list > li.ipc-metadata-list-item--link.ipc-metadata-list__item:nth-of-type(3) > .ipc-metadata-list-item__content-container > .baseAlt.ipc-metadata-list-item__list-content.ipc-inline-list--inline.ipc-inline-list--show-dividers.ipc-inline-list > li.ipc-inline-list__item")){
                movie.stars.add(e.text());
            }

            //get the list of director
            for (Element e: document.select(".iGxbgr.PrincipalCredits__PrincipalCreditsPanelWideScreen-hdn81t-0 > .ipc-metadata-list--baseAlt.title-pc-list.ipc-metadata-list--dividers-all.ipc-metadata-list > li.ipc-metadata-list__item:nth-of-type(1) > .ipc-metadata-list-item__content-container > .baseAlt.ipc-metadata-list-item__list-content.ipc-inline-list--inline.ipc-inline-list--show-dividers.ipc-inline-list > li.ipc-inline-list__item"))
            {
                movie.director.add(e.text());
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return movie;

    }


}
