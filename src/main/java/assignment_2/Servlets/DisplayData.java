package assignment_2.Servlets;

import assignment_2.Helper.DataModel;
import assignment_2.Helper.Transformer;
import assignment_2.Objects.Song;
import spark.Service;
import java.util.List;

public class DisplayData extends RestEndPoint{
    
    private final String type = "application/json";
    private final DataModel controller = new DataModel();

    public DisplayData(final Service service){
        super(service);
    }

    public void get(){

        getService().get("/data", (request, response) -> {
            response.type(type);
            if(controller.getAllSongs() != null){
                List<Song> allSongs = controller.getAllSongs();
                response.status(200);
                return allSongs;
            } else {
                response.status(404);
                return "404 - Not Found";
            }
            
        }, new Transformer());

        getService().get("/data/:songId", (request, response) -> {
            response.type(type);
            final int songid = Integer.parseInt(request.params(":songId"));
            if(controller.getSongById(songid) != null){
                final Song s = controller.getSongById(songid);
                response.status(200);
                return s;
            } else {
                response.status(404);
                return "404 - Not Found";
            }

        }, new Transformer());
    }

	@Override
	public void post() {
		
	}

	@Override
	public void delete() {
		
	}
    
}