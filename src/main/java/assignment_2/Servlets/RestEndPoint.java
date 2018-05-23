package assignment_2.Servlets;

import spark.Service;

public abstract class RestEndPoint{

    private Service service;

    public RestEndPoint(Service service){
        this.service = service;
        configure();
    }

    private void configure(){
        get();
        post();
        delete();
    }

    public Service getService(){
        return service;
        
    }

    

    public abstract void get();
    public abstract void post();
    public abstract void delete();
}