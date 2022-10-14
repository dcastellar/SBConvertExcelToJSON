package es.dcb.exporter;

import es.dcb.ExcelParseException;
import es.dcb.config.ConfigProperties;
import es.dcb.model.DataDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
@Component
public class ExcelParser {

    public static final String TAB = "main.tab";

    private static Workbook workbook;

    public void readExcel(String excelFilePath) {
        if (workbook == null) {
            FileInputStream file = null;
            try {
                file = new FileInputStream(new File(excelFilePath));
                workbook = new XSSFWorkbook(file);
            } catch (FileNotFoundException e) {
                throw new ExcelParseException(e);
            } catch (IOException e) {
                throw new ExcelParseException(e);
            }
        }
    }

    public List<DataDTO> getdata() {
        Sheet sheet = workbook.getSheet(ConfigProperties.instance().properties().getProperty(TAB));

        List<DataDTO> data = StreamSupport.stream(sheet.spliterator(), true)
                .skip(1)
                .map(row -> {
                    DataDTO dataDTO = new DataDTO();
                    dataDTO.setFriendlyName(String.valueOf(row.getCell(0)));
                    dataDTO.setName(String.valueOf(row.getCell(1)));
                    String properties = "";
                    if(row.getCell(2) != null && row.getCell(2).getCellType() == CellType.STRING) {
                        properties += "(property is '" + row.getCell(2).getStringCellValue() + "')";
                    }
                    if(row.getCell(3) != null && row.getCell(3).getCellType() == CellType.STRING) {
                        properties += "(property is '" + row.getCell(3).getStringCellValue() + "')";
                    }
                    dataDTO.setProperties(properties);
                    dataDTO.setPriority(row.getCell(4).getStringCellValue());
                    return dataDTO;
                })
                .collect(Collectors.toList());
        return data;
    }
}
