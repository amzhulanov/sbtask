package jam.example.sbtask.services;

import jam.example.sbtask.repositories.GuideDataRepository;
import jam.example.sbtask.repositories.GuideStructureRepository;
import jam.example.sbtask.utils.PrepareSQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис с реализацией методов работы со структурой справочников и данными
 *
 * @author JAM
 */
@Service
@Slf4j
public class GuideServiceImpl implements GuideService {

    private final GuideStructureRepository guideStructureRepository;
    private final GuideDataRepository guideDataRepository;
    private final PrepareSQL prepareSQL;

    @Autowired
    public GuideServiceImpl(GuideStructureRepository guideStructureRepository, GuideDataRepository guideDataRepository, PrepareSQL prepareSQL) {
        this.guideStructureRepository = guideStructureRepository;
        this.guideDataRepository = guideDataRepository;
        this.prepareSQL = prepareSQL;
    }

    /**
     * Метод для проверки наличия справочника в базе
     *
     * @param name имя справочника для проверки
     * @return возвращает TRUE если справочника нет в базе, FALSE если справочник существует
     */
    public Boolean checkGuide(String name) {
        return guideStructureRepository.checkGuide(prepareSQL.checkGuide(name)).isEmpty();
    }

    /**
     * Реализация метода создания справочника
     *
     * @param fieldsGuide Список и типы полей
     * @param name        Имя таблицы
     * @return Возвращает статус создания справочника
     */
    public Boolean createGuide(Map<String, String> fieldsGuide, String name) {

        if (name != null && checkGuide(name)) {
            guideStructureRepository.createGuide(prepareSQL.sqlCreateGuide(fieldsGuide, name));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Реализация метода обновления (изменения наименования и типа полей) справочника
     *
     * @param fieldsGuide Список полей с новыми значениями
     * @param name        Имя таблицы
     * @param typeEdit    Тип операции (изменить имя или тип поля)
     * @return Возвращает статус операции
     */
    public String updateFieldGuide(Map<String, String> fieldsGuide, String name, String typeEdit) {
        if (name != null && !checkGuide(name)) {
            switch (typeEdit) {
                case ("edit_name_field"):
                    guideStructureRepository.updateNameFields(prepareSQL.updateNameFields(fieldsGuide, name));
                    break;
                case ("edit_type_field"):
                    guideStructureRepository.updateTypeFields(prepareSQL.updateTypeFields(fieldsGuide, name));
                    break;
            }
            return "Success";
        } else {
            return "Guide not found";
        }
    }

    /**
     * Реализация метода добавления полей в справочник
     *
     * @param fieldsGuide список наименований и типов полей
     * @param name        Имя таблицы
     */
    public void addFieldsGuide(Map<String, String> fieldsGuide, String name) {
        guideStructureRepository.addFields(prepareSQL.addFields(fieldsGuide, name));
    }

    /**
     * Реализация метода удаления полей из справочника
     *
     * @param columnsGuide Список полей
     * @param name         Имя таблицы
     */
    public void deleteFieldsGuide(List<String> columnsGuide, String name) {
        guideStructureRepository.deleteFields(prepareSQL.deleteFields(columnsGuide, name));
    }

    /**
     * Реализация метода удаления справочника
     *
     * @param name Имя справочника
     */
    public void deleteGuide(String name) {
        guideStructureRepository.deleteGuide(prepareSQL.deleteGuide(name));
    }

    /**
     * Реализация метода добавления записей в справочник
     *
     * @param fieldsGuide Список полей и значений
     * @param nameTable   Имя таблицы
     */
    public void addRecords(Map<String, String> fieldsGuide, String nameTable) {
        List<List<String>> list = splitMap(fieldsGuide);
        guideDataRepository.addRecord(prepareSQL.addRecords(list.get(0), list.get(1), nameTable));
    }

    /**
     * Реализация метода поиска значений
     *
     * @param fieldsGuide Имя полей и значений, по которым выполняется поиск
     * @param nameTable   Имя таблицы
     * @return Список строк
     */
    public List<Map<String,Object>> find(Map<String, String> fieldsGuide, String nameTable) {
        return guideDataRepository.find(prepareSQL.find(fieldsGuide, nameTable));
    }

    /**
     * Реализация метода обновления записей в справочнике
     * Если размер List 1, значит условия выбора не указаны и обновляются все поля.
     *
     * @param fieldsSearch List содержащий Map со списком полей для обновления и Map со списком условий
     * @param nameTable    Имя таблицы
     */
    public Integer updateRecords(List<Map<String, String>> fieldsSearch, String nameTable) {
        if (fieldsSearch.size() == 2) {
            return guideDataRepository.updateByFields(prepareSQL.updateData(fieldsSearch.get(0), fieldsSearch.get(1), nameTable));
        } else {
            return guideDataRepository.updateByFields(prepareSQL.updateData(new HashMap<>(),fieldsSearch.get(0), nameTable));
        }
    }

    public Integer deleteRecords(Map<String, String> fieldsGuide, String nameTable) {
        return guideDataRepository.deleteRecords(prepareSQL.deleteRecords(fieldsGuide, nameTable));
    }


    /**
     * Реализация метода для разделения Map <Поле, Значение> на два списка <Поле>, <Значение>
     *
     * @param fieldsGuide Список полей со значениями
     * @return Список содержащий два списка <Поле>, <Значение>
     */
    private List<List<String>> splitMap(Map<String, String> fieldsGuide) {
        List<String> fieldsList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();
        List<List<String>> listOut = new ArrayList<>();
        fieldsGuide.forEach((field, data) -> {
            fieldsList.add(field);
            dataList.add(data);
        });
        listOut.add(fieldsList);
        listOut.add(dataList);
        return listOut;
    }


}
