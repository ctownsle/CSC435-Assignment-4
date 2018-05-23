package assignment_2.Helper;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;
import assignment_2.Objects.Playlist;
import assignment_2.Objects.Response;
import assignment_2.Objects.Song;

public class DataModel{
    // Query holder
    public DataModel(){

    }

    public Response addSongToPlaylist(int playlistid, int songid){
        Response response;
        // get queries
        String query = "INSERT INTO pl_sg " + "SELECT * FROM (SELECT ?, ?) AS tmp WHERE NOT EXISTS ( "
        + "SELECT id, pl_id FROM pl_sg WHERE id=? AND pl_id=?) LIMIT 1;";
        try(Connection conn = new SQLConnection().createConnection(); 
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, songid);
            stmt.setInt(2, playlistid);
            stmt.setInt(3, songid);
            stmt.setInt(4, playlistid);
            stmt.execute();
            if(stmt.getUpdateCount() > 0){
                response = new Response(201, "201 - Song was added to playlist!");
            } else {
                response = new Response(400, "400 - Song already exists in playlist!");
            }
            return response;
        } catch (SQLException e) {
            String message = "500 - Internal data error";
            response = new Response(500, message);

            return response;
      }
    }

    public Response addPlaylist(JsonObject object){
        Response r;
        String query = "INSERT INTO playlist(name)" + 
        " SELECT * FROM (SELECT ?) AS tmp WHERE NOT EXISTS" + 
        " (SELECT name FROM playlist WHERE name=?)";
        try(Connection conn = new SQLConnection().createConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
           // Playlist pl;
            String name = object.get("name").getAsString();
            stmt.setString(1, name);
            stmt.setString(2, name);
            stmt.execute();
            if(stmt.getUpdateCount() > 0){
                r = new Response(201, "201 - New playlist added!");
                return r;
            } else {
                r = new Response(400, "400 - Playlist already exists");
            }
            return r;
        } catch (SQLException e) {
            e.printStackTrace();
            r = new Response(500, "500 - Something went wrong with database");
            return r;
        }
    }

    public List<Playlist> getAllPlaylists(){
        List<Playlist> playlists = new ArrayList<>();
        ResultSet results;
        String query = "SELECT * FROM playlist";
        try(Connection conn = new SQLConnection().createConnection();
        PreparedStatement stmt = conn.prepareStatement(query);){
            results = stmt.executeQuery();
            while(results.next()){
                Playlist pl = new Playlist(results.getInt("pl_id"), results.getString("name"));
                playlists.add(pl);
            }
            return playlists;
        } catch(SQLException e){
           //e.printStackTrace();
            return null;
        }
    }

    public Playlist getPlaylistById(int playlistid){
        ResultSet r;
        String query = "SELECT * FROM playlist WHERE pl_id=?";
        try(Connection conn = new SQLConnection().createConnection();
        PreparedStatement stmt = conn.prepareStatement(query);) {
        stmt.setInt(1, playlistid);
        r = stmt.executeQuery();
        if( r.next()){
            Playlist pl = new Playlist(r.getInt("pl_id"), r.getString("name"));
            return pl;
        }
        return null;    
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Song> getAllSongs(){

        List<Song> songs = new ArrayList<>();
        ResultSet r;
        String query = "SELECT * FROM songs"; 

        try(Connection conn = new SQLConnection().createConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            
            r = stmt.executeQuery();
            while(r.next()){
                Song sg = new Song(r.getString("artist"), r.getString("title"), r.getInt("id"));
                songs.add(sg);
            }
            return songs;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Song> getSongsFromPlaylist(int playlistid){
        List<Song> songs = new ArrayList<>();
        ResultSet r;
        String query = "SELECT * FROM songs s" + 
        " JOIN pl_sg ps ON s.id=ps.id JOIN playlist pl ON pl.pl_id=ps.pl_id WHERE pl.pl_id=?";
        try(Connection conn = new SQLConnection().createConnection();
        PreparedStatement stmt = conn.prepareStatement(query);) {
           stmt.setInt(1, playlistid);
           r = stmt.executeQuery();
           
           while(r.next()){
               Song sg = new Song(r.getString("artist"), r.getString("title"), r.getInt("id"));
               songs.add(sg);
           }
           return songs;
        } catch (SQLException e) {
            return null;
        }
    }
    public Song getSongFromPlaylist(int playlistid, int songid){
        ResultSet r;
        Song song;
        String query = "SELECT * FROM songs s WHERE id=?" + 
        " JOIN pl_sg ps ON s.id=ps.id JOIN playlist pl ON pl.pl_id=ps.pl_id WHERE pl.pl_id=?";
        try(Connection conn = new SQLConnection().createConnection();
        PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setInt(1, songid);
            stmt.setInt(2, playlistid);
            r = stmt.executeQuery();
            if(r.next()){
                song = new Song(r.getString("artist"), r.getString("title"), r.getInt("id"));
                return song;
            }
            return null;
        } catch (SQLException e) {
            return null;
        } 
    }
    public Response deletePlaylist(int playlistid){
        Response r;
        String authordelete = "DELETE FROM playlist WHERE pl_id=?";
        String song_playlistdelete = "DELETE FROM pl_sg WHERE pl_id=?";
        try(Connection conn = new SQLConnection().createConnection();
        PreparedStatement stmt = conn.prepareStatement(authordelete);
        PreparedStatement stmt2 = conn.prepareStatement(song_playlistdelete);) {
            stmt2.setInt(1, playlistid);
            stmt2.execute();
            stmt.setInt(1, playlistid);
            stmt.execute();
            if(stmt.getUpdateCount() > 0){
                r = new Response(200, "200 - Playlist removed successfully");
            } else {
                r = new Response(404, "404 - Playlist not found");
            }
            return r;
        } catch (SQLException e) {
            r = new Response(500, "Yikes, something went wrong on our end");
            return r;
        }
    }
    public Response deleteSongFromPlaylist(int songid, int playlistid){
        Response r;
        String query = "DELETE FROM pl_sg WHERE id=? AND pl_id=?";
        try(Connection conn = new SQLConnection().createConnection();
        PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setInt(1, songid);
            stmt.setInt(2, playlistid);
            stmt.execute();
            if(stmt.getUpdateCount() > 0){
                r = new Response(200, "200 - Song deleted from playlist successfully!");
            } else {
                r = new Response(404, "404 - Song not found");
            }
            return r;
        } catch (SQLException e) {
            r = new Response(500, "500 - Yikes, something went wrong on our end");
            return r;
        }
        
    }

    public Song getSongById(int songid){
        ResultSet r;
        String query = "SELECT * FROM songs WHERE id=?";
        try(Connection conn = new SQLConnection().createConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, songid);
            r = stmt.executeQuery();
            if(r.next()){
                Song sg = new Song(r.getString("artist"), r.getString("title"), r.getInt("id"));
                return sg;
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

}