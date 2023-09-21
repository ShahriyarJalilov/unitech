package az.unibank.cn.unitech.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse <T> implements Serializable {

    int code;
    String message;
    T data;

    public static<T> CommonResponse<T> getInstance(HttpStatus httpStatus, T data){
        return new CommonResponse<T>()
                .setCode(httpStatus.value())
                .setMessage(httpStatus.getReasonPhrase())
                .setData(data);
    }

    public static<T> CommonResponse<T> successInstance(){
        return new CommonResponse<T>()
                .setCode(HttpStatus.OK.value())
                .setMessage(HttpStatus.OK.getReasonPhrase());
    }
}
