package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Test
    public void loginSuccessTest() throws IOException {
        AuthRequestDto auth = AuthRequestDto
                .builder()
                .username("manul-j@gmail.com")
                .password("Manul1234$")
                .build();


        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request
                .Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        AuthResponseDto responseDto = gson.fromJson(response.body().string(), AuthResponseDto.class);
        //System.out.println(responseDto.getToken());

        //token
        //eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFudWwtakBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4MjUyNDY0OCwiaWF0IjoxNjgxOTI0NjQ4fQ.Fhpx3Hv4yNzeyXzTbLcRuaT_pc-ttWXZNKAquoRhp-M
    }
    @Test
    public void loginWithWrongEmailTest() throws IOException {
        AuthRequestDto auth = AuthRequestDto
                .builder()
                .username("manul-jgmail.com")
                .password("Manul1234$")
                .build();


        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request
                .Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
//        System.out.println(errorDto.getError());
//        System.out.println(errorDto.getMessage());
//        System.out.println(errorDto.getPath());
        Assert.assertEquals(errorDto.getError(), "Unauthorized");
        Assert.assertEquals(errorDto.getMessage(), "Login or Password incorrect");
    }
}
