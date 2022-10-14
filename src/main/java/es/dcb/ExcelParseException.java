package es.dcb;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
public class ExcelParseException extends RuntimeException{
    public ExcelParseException(FileNotFoundException e) {
        super(e);
        log.error("File not Found:"+e);
    }

    public ExcelParseException(IOException e) {
        super(e);
        log.error("File not Found:"+e);
    }
}
