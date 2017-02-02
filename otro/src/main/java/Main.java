import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;


import java.util.*;

import static spark.Spark.*;
/**
 * Created by Skx on 31/01/2017.
 */
public class Main {

    public static void main(String[] args) {//
        port(9080);

        Language language = new Language();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object[]> object = restTemplate.getForEntity("https://api.github.com/orgs/stackbuilders/repos", Object[].class);

        List<Object> lists = Arrays.asList(object.getBody());
        List<String> listaLenguajes = new ArrayList<>();
        for(Object simpleObject : lists){

            Map<String, String> newObject = (Map<String, String>) simpleObject;
            if(newObject.get("language") != null){
                listaLenguajes.add(newObject.get("language"));
            }
        }

        Map<String, Integer> valores = new HashMap<>();

        Collections.sort(listaLenguajes);

        for(String temp : listaLenguajes){

            Integer count = valores.get(temp);
            valores.put(temp, (count == null) ? 1 : count + 1);

        }

        get("/newAll", (request, response) -> {

            Map<String, Object> model = new HashMap<>();
            model.put("valores", language.findAllByPorcentage(valores));
            return new ModelAndView(model, "newAll.hbs");


        }, new HandlebarsTemplateEngine());

        get("/", (request, response) -> {

            Map<String, Object> model = new HashMap<>();
            model.put("palabra", request.cookie("palabra"));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sign-in", (request, response) -> {
            Map<String, String> model = new HashMap<String, String>();
            String palabra = request.queryParams("palabra");

            if (language.findByOne(valores, palabra)){

                response.cookie("palabra", palabra + " Si existe en GitHub");
                model.put("palabra", palabra + " Si existe en GitHub");
            }
            else{
                response.cookie("palabra", palabra + " No existe en GitHub");
                model.put("palabra", palabra + " No existe en GitHub");
            }

            return new ModelAndView(model, "find.hbs");


        }, new HandlebarsTemplateEngine());


    }






}
