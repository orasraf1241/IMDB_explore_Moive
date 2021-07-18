package IMDB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Moviadetails {

    public String moviaName;
    public String url;
    public  String rating;
    public  String duration;

    public List<String>  director;
    public List<String>  stars;
    public List<String>  genre;



    public String printList(List<String> list) {
        String str = "";
        for (String s : list) {
            str += s+",";
        }
        return str.substring(0,str.length()-1);
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(

                this.moviaName+" | "
                +printList(genre)
                +" |"+this.rating
                +" |" +this.duration +
                "|"+ printList(director)
                +"| "+printList(stars));
        System.out.println(sb);

        return null;
    }





    public static Moviadetails findElement(String movieUrl, Moviadetails movie){
        try {
            movie.genre = new LinkedList<>();
            movie.stars = new LinkedList<>();
            movie.director = new LinkedList<>();
            final Document document = Jsoup.connect(movieUrl).get();

            movie.moviaName=document.select(".TitleHeader__TitleText-sc-1wu6n3d-0").text();
//            if(document.select
//                    (".TitleHeader__TitleText-sc-1wu6n3d-0").text()=="")
//            movie.moviaName = document.select
//                    (".TitleHeader__TitleText-sc-1wu6n3d-0").text();
//            else
//                movie.moviaName = document.select
//                        (".cLNRlG.TitleHeader__TitleText-sc-1wu6n3d-0").text();



            movie.duration = document.select
                    (".baseAlt.dxizHm.TitleBlockMetaData__MetaDataList-sc-12ein40-0.ipc-inline-list--show-dividers.ipc-inline-list > li.ipc-inline-list__item:nth-of-type(3)").text();
            movie.url = movieUrl;
            movie.rating = document.select
                    ("li.ipc-inline-list__item:nth-of-type(2) > .rgaOW.TitleBlockMetaData__StyledTextLink-sc-12ein40-1.ipc-link--inherit-color.ipc-link--baseAlt.ipc-link").text();

            for (Element e: document.select(".ipc-chip--on-baseAlt.ipc-chip.fzmeux.GenresAndPlot__GenreChip-cum89p-3")) {
                    movie.genre.add(e.text());
            }

            for (Element e: document.select(".iGxbgr.PrincipalCredits__PrincipalCreditsPanelWideScreen-hdn81t-0 > .ipc-metadata-list--baseAlt.title-pc-list.ipc-metadata-list--dividers-all.ipc-metadata-list > li.ipc-metadata-list-item--link.ipc-metadata-list__item:nth-of-type(3) > .ipc-metadata-list-item__content-container > .baseAlt.ipc-metadata-list-item__list-content.ipc-inline-list--inline.ipc-inline-list--show-dividers.ipc-inline-list > li.ipc-inline-list__item")){
                movie.stars.add(e.text());
            }

            for (Element e: document.select(".iGxbgr.PrincipalCredits__PrincipalCreditsPanelWideScreen-hdn81t-0 > .ipc-metadata-list--baseAlt.title-pc-list.ipc-metadata-list--dividers-all.ipc-metadata-list > li.ipc-metadata-list__item:nth-of-type(1) > .ipc-metadata-list-item__content-container > .baseAlt.ipc-metadata-list-item__list-content.ipc-inline-list--inline.ipc-inline-list--show-dividers.ipc-inline-list > li.ipc-inline-list__item"))
            {
                movie.director.add(e.text());
               // System.out.println(e.text());
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return movie;

    }

}
