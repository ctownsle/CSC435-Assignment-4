package assignment_2.Helper;

import java.io.PrintWriter;


public class HtmlHelper{

    private static HtmlHelper instance = null;

    private HtmlHelper(){

    }

    public static HtmlHelper instance(){
        if (instance == null){
            instance = new HtmlHelper();
        }
        return instance;
    }


    public void printTabs(PrintWriter output){
        output.println("<nav>");
        output.println("<ul>");
        //output.println("<li><a href='/assignment-1'>HOME</a></li>");
        output.println("<li><a href='/assignment-1/display'>Song List</a></li>");
        output.println("<li><a href='/assignment-1/addSong'>Add Song </a></li>");
        output.println("</ul>");
        output.println("</nav>");
    }

}