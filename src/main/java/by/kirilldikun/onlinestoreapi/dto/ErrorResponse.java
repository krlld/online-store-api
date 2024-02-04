package by.kirilldikun.onlinestoreapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String code;

    private String message;

    private List<Field> fields;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Field {

        private String name;

        private String message;
    }

    public void addField(String name, String message) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.add(new Field(name, message));
    }
}
