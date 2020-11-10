package jam.example.sbtask.services;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс сервиса методов для работы со структурой справочника и данными.
 *
 * @author JAM
 */
public interface GuideService {

    Boolean checkGuide(String name);

    Boolean createGuide(Map<String, String> fieldsGuide, String name);

    String updateFieldGuide(Map<String, String> fieldsGuide, String name, String typeEdit);

    void addFieldsGuide(Map<String, String> fieldsGuide, String name);

    void deleteFieldGuide(String columnGuide, String name);

    void dropGuide(String name);

    void addRecords(List<Map<String, String>> fieldsGuide, String name);

    Integer deleteRecords(Map<String, String> fieldsGuide, String name);

    List<Map<String,Object>> find(Map<String, String> fieldsGuide, String name);

    Integer updateRecords(List<Map<String, String>> fieldsSearch, String name);


}
