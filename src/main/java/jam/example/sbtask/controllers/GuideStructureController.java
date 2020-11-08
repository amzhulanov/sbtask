package jam.example.sbtask.controllers;

import jam.example.sbtask.utils.Converter;
import jam.example.sbtask.services.GuideService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST-контроллер для работы со структурой справочника
 *
 * @author JAM
 */
@Controller
@RequestMapping("/db")
@Slf4j
public class GuideStructureController {
    private Map<String, String> fieldsGuide;

    private final Converter converter;
    private final GuideService guideService;

    public GuideStructureController(Converter converter, GuideService guideService) {
        this.converter = converter;
        this.guideService = guideService;
    }

    /**
     * Метод для создания справочника
     *
     * @param name Имя таблицы
     * @param json Текст запроса
     * @return Возвращает статус выполнения
     */
    @PutMapping("/{name}/create")
    public ResponseEntity<String> addGuide(@PathVariable String name, @RequestBody String json) {
        fieldsGuide = converter.convertJsonToMap(json);
        if (name != null && guideService.createGuide(fieldsGuide, name)) {
            return new ResponseEntity<>("The guide created", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The guide already exists", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод для добавления полей в структуру справочника
     *
     * @param name Имя справочника
     * @param json Список наименований и типов новых полей
     */
    @PutMapping("/{name}")
    public void addFieldsGuide(@PathVariable String name, @RequestBody String json) {
        fieldsGuide = converter.convertJsonToMap(json);
        guideService.addFieldsGuide(fieldsGuide, name);
    }

    /**
     * Метод для изменения типа или наименования полей справочника
     *
     * @param name Имя таблицы
     * @param type Тип операции: изменение типа или наименования полей справочника (edit_type_field, edit_name_field)
     * @param json Текст запроса
     * @return Возвращает статус выполнения
     */
    @PostMapping("/{name}/{type}")
    public ResponseEntity<String> updateFieldGuide(@PathVariable String name, @PathVariable String type, @RequestBody String json) {
        fieldsGuide = converter.convertJsonToMap(json);
        String result;
        if (name != null) {
            result = guideService.updateFieldGuide(fieldsGuide, name, type);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Must specify a name", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод для удаления справочника или полей из справочника
     *
     * @param name Имя справочника
     * @param json Текст запроса (опционально)
     */
    @DeleteMapping("/{name}")
    public void deleteGuide(@PathVariable String name, @RequestBody String json) {
        if (name != null) {
            if (json != null) {
                List<String> columnList = converter.convertJsonToList(json);
                if (!columnList.isEmpty()) {
                    guideService.deleteFieldsGuide(columnList, name);
                }
            } else {
                guideService.deleteGuide(name);
            }
        }

    }


}
