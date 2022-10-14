package es.dcb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DataDTO {

    @JsonIgnore
    private String name;
    private String friendlyName;
    private String properties;
    private Boolean synced;
    @JsonIgnore
    private String priority;
}
