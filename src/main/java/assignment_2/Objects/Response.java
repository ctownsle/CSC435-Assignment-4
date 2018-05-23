package assignment_2.Objects;

public class Response{
    private String message;
    private int status;

    public Response(int s, String m){
        message = m;
        status = s;
    }

    public int getStatus(){
        return status;
    }

    public String getMessage(){
        return message;
    }
}