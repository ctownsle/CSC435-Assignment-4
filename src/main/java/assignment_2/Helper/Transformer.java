package assignment_2.Helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.ResponseTransformer;

public class Transformer implements ResponseTransformer{

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String render(final Object object) throws Exception{
        return gson.toJson(object);
    }
}