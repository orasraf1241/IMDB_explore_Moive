package IMDB;

import java.util.List;

public class Moviadetails {




    public String moviaName;
    public String moviaTitle;
    public String url;
    public  String rating;
    public  String duration;

    public List<String>  director;
    public List<String>  actors;
    public List<String>  genre;

    @Override
    public String toString(){
      //  String genreList = genre.toString(customers.toArray()).replace("[", "").replace("]", "");

        StringBuilder sb = new StringBuilder(this.moviaName+" | "
                +this.genre.toString().replace("[","").replace("]","")
                +" |"+this.rating
                +" |" +this.duration +
                "|"+ this.director.toString().replace("[","").replace("]","")
                +"|"+this.actors.toString().replace("[","").replace("]",""));

        sb.deleteCharAt('[');

        System.out.println(sb);
        return null;
    }

    public String getUrl() {
        return url;
    }

    public String getMoviaTitle() {
        return moviaTitle;
    }

    public List<String> getGenre() {
        return genre;
    }

    public String getRating() {
        return rating;
    }

    public String getDuration() {
        return duration;
    }

    public List<String> getDirector() {
        return director;
    }

    public List<String> getActors() {
        return actors;
    }

}
