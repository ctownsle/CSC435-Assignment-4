package assignment_2.Objects;



public class Song{
    private String artist;
    private String title;
    private int id;

    public Song(String a, String t, int i){
        this.artist = a;
        this.title = t;
        this.id = i;
    }

    public String getTitle(){
        return title;
    }

    public int getId(){
        return id;
    }

    public String getArtist(){
        return artist;
    }

    public String asValue(){
        return String.format("'%s' : '%s' ", artist, title);
    }
}