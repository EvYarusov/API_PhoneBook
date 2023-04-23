package okhttp;

import com.google.gson.Gson;
import dto.AllContactsDto;
import dto.ContactDto;
import dto.ContactResponseDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIdTests {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFudWwtakBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4MjUyNDY0OCwiaWF0IjoxNjgxOTI0NjQ4fQ.Fhpx3Hv4yNzeyXzTbLcRuaT_pc-ttWXZNKAquoRhp-M";

    String id;

    @BeforeMethod
    public void precondition() throws IOException {
        int i = new Random().nextInt(1000) + 1000;

        ContactDto contactDto = ContactDto.builder()
                .name("Bob")
                .lastName("White")
                .email("bob" + i + "@gm.co")
                .phone("2165474123" + i)
                .address("Paris")
                .description("friend will be friend")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto), JSON);

        Request request = new Request
                .Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        ContactResponseDto resDto = gson.fromJson(response.body().string(), ContactResponseDto.class);
        //System.out.println(resDto.getMessage());

        String message = resDto.getMessage();
        String[] split = message.split("ID: ");
        id = split[1];
    }

    @Test
    public void deleteContactByIdSuccessTest() throws IOException {
        Request request = new Request
                .Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/" + id)
                .addHeader("Authorization", token)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        //System.out.println(response);
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        ContactResponseDto resDto = gson.fromJson(response.body().string(), ContactResponseDto.class);
        //System.out.println(resDto.getMessage());
        Assert.assertEquals(resDto.getMessage(), "Contact was deleted!");
    }
}
