package assignment_2;

import assignment_2.Servlets.DisplayData;
import assignment_2.Servlets.PlaylistHandler;
import assignment_2.Servlets.RestEndPoint;
import spark.Service;

public class Main {

    private static Service service;

    public static void main(String[] args) {
        
        service = Service.ignite().port(8080);
        service.get("/", (req, res) -> "API Running");
        RestEndPoint dataEndPoint = new DisplayData(service);
        RestEndPoint playlistEndPoint = new PlaylistHandler(service);

    }
}