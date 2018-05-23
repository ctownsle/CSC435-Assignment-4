package assignment_2.Servlets;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import assignment_2.Helper.DataModel;
import assignment_2.Helper.Transformer;
import spark.Service;
import assignment_2.Objects.Playlist;
import assignment_2.Objects.Response;
import assignment_2.Objects.Song;

public class PlaylistHandler extends RestEndPoint{
    private final String type = "application/json";
    private final DataModel controller = new DataModel();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public PlaylistHandler(final Service service){
        super(service);
    }

    public void get(){
        getService().get("/playlist", (request, response) -> {
            response.type(type);
            if(controller.getAllPlaylists() != null){
                final List<Playlist> playlists = controller.getAllPlaylists();
                response.status(200);
                return playlists;
            } else {
                response.status(404);
                return "404 - Not Found";
            }
        }, new Transformer());

        getService().get("/playlist/:playlistId", (request, response) -> {
            response.type(type);
            final int playlistid = Integer.parseInt(request.params(":playlistId"));
            if(controller.getPlaylistById(playlistid) != null){
                final Playlist playlist = controller.getPlaylistById(playlistid);
                response.status(200);
                return playlist;
            } else {
                response.status(404);
                return "404 - Not Found";
            }
        }, new Transformer());

        getService().get("/playlist/:playlistId/songs", (request, response) -> {
            response.type(type);
            final int playlistid = Integer.parseInt(request.params(":playlistId"));
            if(controller.getPlaylistById(playlistid) != null){
                final List<Song> songs = controller.getSongsFromPlaylist(playlistid);
                response.status(200);
                return songs;
            } else {
                response.status(404);
                return "404 - Not Found";
            }
        }, new Transformer());
    }

    public void post(){

        getService().post("/playlist", (request, response) -> {
            response.type(type);
            final JsonObject object = gson.fromJson(request.body(), JsonObject.class);
            final Response res = controller.addPlaylist(object);
            response.status(res.getStatus());
            return res;
        }, new Transformer());

        getService().post("/playlist/:playlistId/songs", (request, response) -> {
            response.type(type);
            final int playlistid = Integer.parseInt(request.params(":playlistId"));
            final JsonObject object = gson.fromJson(request.body(), JsonObject.class);
            final int songid = object.get("id").getAsInt();
            Response res = controller.addSongToPlaylist(playlistid, songid);
            response.status(res.getStatus());
            return res;
        }, new Transformer());
    }

    public void delete(){

        getService().delete("/playlist/:playlistId", (request, response) -> {

            response.type(type);
            final int playlistid = Integer.parseInt(request.params(":playlistId"));
            Response res = controller.deletePlaylist(playlistid);
            response.status(res.getStatus());
            return res;
        }, new Transformer());

        getService().delete("playlist/:playlistId/songs/:songId", (request, response) -> {

            response.type(type);
            final int playlistid = Integer.parseInt(request.params(":playlistId"));
            final int songid = Integer.parseInt(request.params(":songId"));
            Response res = controller.deleteSongFromPlaylist(songid, playlistid);
            response.status(res.getStatus());
            return res;
        }, new Transformer());
    }
}
