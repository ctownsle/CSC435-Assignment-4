package assignment_2.Objects;

public class Songpl{

    private int playlist_id;
    private int song_id;
    public Songpl(int p_id, int s_id){
        playlist_id = p_id;
        song_id = s_id;
    }

    public int getPlId(){
        return playlist_id;
    }

    public int getSgId(){
        return song_id;
    }
}