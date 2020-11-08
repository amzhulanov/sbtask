package jam.example.sbtask.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Класс содержит методы для преобразования данных в json-структуру и обратно
 *
 * @author JAM
 */
@Component
@Slf4j
public class Converter {

    private final Gson g = new Gson();
    private final Type typeMap = new TypeToken<Map<String, String>>(){}.getType();

    /**
     * Преобразовывает json-строку в Map(поле, значение)
     * @param json исходная json-строка
     * @return готовая Map
     */
    public Map<String, String> convertJsonToMap(String json)  {
        Map<String, String> fieldsGuide = g.fromJson(json, typeMap);
        log.info(json);
        return fieldsGuide;
    }

    /**
     * Разделяет json-структуру на объекты. Каждый объект помещает в отдельную Map
     * @param json исходная json-структура
     * @return Возвращает список Map
     * @throws ParseException исключение, которое может возникнуть при парсинге json
     */
    public List<Map<String, String> >  convertJson(String json) throws ParseException {
        Object obj = new JSONParser().parse(json);
        JSONObject jo = (JSONObject) obj;

        Map<String, String> fieldsCondition;
        Map<String, String> fieldsSearch;
        List<Map<String, String> > result=new ArrayList<>();
        if(jo.get("condition")!=null){
            json =jo.get("condition").toString();
            fieldsCondition = g.fromJson(json, typeMap);
            result.add(fieldsCondition);
        }
        if (jo.get("data")!=null){
            json =jo.get("data").toString();
            fieldsSearch = g.fromJson(json, typeMap);
            result.add(fieldsSearch);
        }
        return result;
    }

    /**
     * Преобразовывает список в json-строку
     * @param list Исходный список
     * @return json-строка с данными
     */
    public String convertListToJson(List list){
        return new Gson().toJson(list);
    }

    /**
     * Преобразовывает json-строку в List строк
     * @param json исходная строка
     * @return Список строк
     */
    public List<String> convertJsonToList(String json) {
        Type typeList = new TypeToken<List<String>>(){}.getType();
        return g.fromJson(json,typeList);
    }
}
