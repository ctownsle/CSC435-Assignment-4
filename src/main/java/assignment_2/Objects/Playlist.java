package assignment_2.Objects;

public class Playlist{

    private String name;
    private int id;

    public Playlist(int pl_id, String n){
        name = n;
        id = pl_id;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

}

