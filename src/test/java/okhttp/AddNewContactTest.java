package okhttp;


import com.google.gson.Gson;
import dto.ContactDto;
import dto.ContactResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class AddNewContactTest {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFudWwtakBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4MjUyNDY0OCwiaWF0IjoxNjgxOTI0NjQ4fQ.Fhpx3Hv4yNzeyXzTbLcRuaT_pc-ttWXZNKAquoRhp-M";

    @Test
    public void addNewContactSuccessTest() throws IOException {

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
        //System.out.println(response);
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        ContactResponseDto resDto = gson.fromJson(response.body().string(), ContactResponseDto.class);
        //System.out.println(resDto.getMessage());
        Assert.assertTrue(resDto.getMessage().contains("Contact was added!"));
    }

    @Test
    public void addNewContactWithWrongLengthPhoneTest() throws IOException {

        int i = new Random().nextInt(1000) + 1000;

        ContactDto contactDto = ContactDto.builder()
                .name("Bob")
                .lastName("White")
                .email("bob" + i + "@gm.co")
                .phone("123456789")
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
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
//        System.out.println(errorDto.getMessage());
        Assert.assertEquals(errorDto.getError(),"Bad Request");
    }

    @Test
    public void addNewContactAsUnauthorizedUserTest() throws IOException {

        int i = new Random().nextInt(1000) + 1000;

        ContactDto contactDto = ContactDto.builder()
                .name("Rob")
                .lastName("White")
                .email("rob" + i + "@gm.co")
                .phone("12345678910")
                .address("Paris")
                .description("friend will be friend")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto), JSON);

        Request request = new Request
                .Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", "fhghdDGF")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getError(),"Unauthorized");
    }
//
//    @Test
//    public void addDoublingContactNegativeTest() throws IOException {
//
//        ContactDto contactDto = ContactDto.builder()
//                .id("49b34ae6-6b3d-4d35-912b-96e6a7d613a2")
//                .name("Bob")
//                .lastName("White")
//                .email("bob1194@gm.co")
//                .phone("21654741231194")
//                .address("Paris")
//                .description("friend will be friend")
//                .build();
//
//        RequestBody body = RequestBody.create(gson.toJson(contactDto), JSON);
//
//        Request request = new Request
//                .Builder()
//                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
//                .addHeader("Authorization", token)
//                .post(body)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        System.out.println(response);
////        Assert.assertFalse(response.isSuccessful());
////        Assert.assertEquals(response.code(), 409);
//
//        ContactResponseDto resDto = gson.fromJson(response.body().string(), ContactResponseDto.class);
//        System.out.println(resDto.getMessage());
//        //Assert.assertTrue(resDto.getMessage().contains("Contact was added!"));
//    }
}
