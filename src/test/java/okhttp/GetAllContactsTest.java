package okhttp;

import com.google.gson.Gson;
import dto.AllContactsDto;
import dto.ContactDto;
import dto.ContactResponseDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContactsTest {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFudWwtakBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4MjUyNDY0OCwiaWF0IjoxNjgxOTI0NjQ4fQ.Fhpx3Hv4yNzeyXzTbLcRuaT_pc-ttWXZNKAquoRhp-M";

    @Test
    public void getAllContactsSuccessTest() throws IOException {
        Request request = new Request
                .Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response);
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        AllContactsDto allDto = gson.fromJson(response.body().string(), AllContactsDto.class);
        List<ContactDto> contacts = allDto.getContacts();
        for (ContactDto contact : contacts) {
            System.out.println(contact.getId());
            System.out.println(contact.getName());
            System.out.println(contact.getLastName());
            System.out.println(contact.getEmail());
            System.out.println(contact.getPhone());
        }
//        81472242-a005-4156-8f14-a514180a7d83
//        4d40d7ac-c52a-4aa9-9e86-7297b496b8fb
//        eb7e86b9-1789-44f5-8bb0-eb04cda88a16
//        42f5c647-b52c-45a9-ade3-c20f140cecae
//        71ab8c2b-daad-44de-8e83-12601d9a1921
//        eb31a293-ddad-46b5-9c50-46c80984ea96
//        7121a4fb-81da-4922-8da6-51c471981bcc
//        93700343-3f41-4a8b-bacb-3849c3b6d62c
//        0db6ae22-e840-4583-ac77-39da4a69aab5
//        ffc7de8f-1a32-4bc2-88d3-827624575048
//        ee23080d-e687-4990-9ec1-11f702015d04
//        1c14c50e-ed35-46bc-aaf0-91c0f95ee5de
//        0c2f12ea-3e49-49c2-a1e7-78e796a72624
    }
}
