package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import okhttp3.Request;
import okhttp3.RequestBody;

@Setter
@Getter
@ToString
@Builder
public class AuthRequestDto {
    private String username;
    private String password;


}
