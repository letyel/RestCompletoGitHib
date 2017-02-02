import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Skx on 01/02/2017.
 */
public class Language {

    public String language;
    public double val;

    public Language() {
    }

    public Language(String language, double val) {
        this.language = language;
        this.val = val;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
    }

    public List<Language> findAll(Map<String, Integer> mapa){

        List<Language> languages = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {

            Language language = new Language(entry.getKey(), entry.getValue());

            languages.add(language);

        }

        return languages;

    }

    public List<Language> findAllByPorcentage(Map<String, Integer> mapa){

        List<Language> languages = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            double porcentage = (((double) entry.getValue()) / totalLanguage(mapa) * 100);

            Language language = new Language(entry.getKey(), porcentage);

            languages.add(language);

            System.out.println("Key : " + language.getLanguage() + " Porcentage : " + language.getVal() + "%") ;
        }

        return languages;

    }

    public Language findBy(Map<String, Integer> map, String value){

        Language language = new Language(null, -1);

        for(Map.Entry<String, Integer> entry : map.entrySet()){

            if(entry.getKey() == value){
                language.setLanguage(entry.getKey());
                language.setVal(entry.getValue());
                break;
            }

        }

        return language;

    }

    public int totalLanguage(Map<String, Integer> mapa){

        int count = 0;

        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            count = count + entry.getValue();
        }

        return  count;
    }

    public boolean findByOne(Map<String, Integer> map, String value){

        boolean state = false;

        for(Map.Entry<String, Integer> entry : map.entrySet()){

            if(entry.getKey().compareToIgnoreCase(value) == 0){

                state = true;
                break;
            }

        }
        return state;
    }
}
