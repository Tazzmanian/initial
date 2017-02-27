/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user;

import com.example.AbstractTestRunner;
import com.example.ResultSet;
import com.example.security.UserTokenUtil;
import java.util.Map;
import javax.transaction.Transactional;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author Teodor Todorov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserControllerTest extends AbstractTestRunner {

    @Autowired
    private UserTokenUtil userTokenUtil;

    private static ResultSet resultSet;
    private static String request;
    private static Map response;
    private static Long personId;

    @Test
    @WithUserDetails("mark")
    public void getAllUsers() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "[{\"userName\":\"test\",\"password\":\"test\",\"firstName\":\"test\",\"lastName\":\"test\",\"email\":null},{\"userName\":\"asdf\",\"password\":\"asdf\",\"firstName\":\"asdf\",\"lastName\":\"asdf\",\"email\":null},{\"userName\":\"billy\",\"password\":\"123\",\"firstName\":\"billy\",\"lastName\":\"herrington\",\"email\":null},{\"userName\":\"dungeon_master\",\"password\":\"artist\",\"firstName\":\"van\",\"lastName\":\"darkholme\",\"email\":null},{\"userName\":\"kazuya\",\"password\":\"asdf\",\"firstName\":\"kazuya\",\"lastName\":\"kazuya\",\"email\":null},{\"userName\":\"mark\",\"password\":\"wolf\",\"firstName\":\"mark\",\"lastName\":\"wolf\",\"email\":null},{\"userName\":\"mmm\",\"password\":\"mmm\",\"firstName\":\"mmm\",\"lastName\":\"mmm\",\"email\":null},{\"userName\":\"bored\",\"password\":\"asdf\",\"firstName\":\"bored\",\"lastName\":\"bored\",\"email\":null},{\"userName\":\"of\",\"password\":\"asdf\",\"firstName\":\"of\",\"lastName\":\"of\",\"email\":null},{\"userName\":\"making\",\"password\":\"asdf\",\"firstName\":\"making\",\"lastName\":\"making\",\"email\":null},{\"userName\":\"these\",\"password\":\"asdf\",\"firstName\":\"these\",\"lastName\":\"these\",\"email\":null}]";

        String actualResponse = new JSONArray(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("test")
    public void removeUser() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/users/11").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "{\"userId\":11,\"password\":\"asdf\",\"firstName\":\"these\",\"lastName\":\"these\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"these\",\"accountNonLocked\":true,\"accountNonExpired\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]}";

        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("test")
    public void removeNotExistingUser() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/users/20").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("test")
    public void searchByFirstName() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/users/search/asdf").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "[{\"userName\":\"asdf\",\"password\":\"asdf\",\"firstName\":\"asdf\",\"lastName\":\"asdf\",\"email\":null}]";

        String actualResponse = new JSONArray(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("test")
    public void searchByNotExistingFirstName() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/users/search/asdfasdf").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "[]";

        String actualResponse = new JSONArray(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("test")
    public void searchByFirstNameWithoutName() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/users/search/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("test")
    public void getActiveUsers() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/users/flagged").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "[{\"userId\":1,\"password\":\"test\",\"firstName\":\"test\",\"lastName\":\"test\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":1,\"name\":\"ADMIN\",\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]},\"username\":\"test\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]},{\"userId\":2,\"password\":\"asdf\",\"firstName\":\"asdf\",\"lastName\":\"asdf\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"asdf\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},{\"userId\":3,\"password\":\"123\",\"firstName\":\"billy\",\"lastName\":\"herrington\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":1,\"name\":\"ADMIN\",\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]},\"username\":\"billy\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]},{\"userId\":4,\"password\":\"artist\",\"firstName\":\"van\",\"lastName\":\"darkholme\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"dungeon_master\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},{\"userId\":5,\"password\":\"asdf\",\"firstName\":\"kazuya\",\"lastName\":\"kazuya\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"kazuya\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},{\"userId\":6,\"password\":\"wolf\",\"firstName\":\"mark\",\"lastName\":\"wolf\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mark\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},{\"userId\":7,\"password\":\"mmm\",\"firstName\":\"mmm\",\"lastName\":\"mmm\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mmm\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},{\"userId\":8,\"password\":\"asdf\",\"firstName\":\"bored\",\"lastName\":\"bored\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"bored\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},{\"userId\":9,\"password\":\"asdf\",\"firstName\":\"of\",\"lastName\":\"of\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"of\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},{\"userId\":10,\"password\":\"asdf\",\"firstName\":\"making\",\"lastName\":\"making\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"making\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},{\"userId\":11,\"password\":\"asdf\",\"firstName\":\"these\",\"lastName\":\"these\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"these\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]}]";

        String actualResponse = new JSONArray(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("test")
    public void getInactiveUsers() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/users/unflagged").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "[]";

        String actualResponse = new JSONArray(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("test")
    public void changeUserActiveStatus() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/users/active/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // enabled = false
        String expectedResponse = "{\"userId\":2,\"password\":\"asdf\",\"firstName\":\"asdf\",\"lastName\":\"asdf\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":false,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"asdf\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]}";
        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);

        resultSet = perform(MockMvcRequestBuilders.get("/users/active/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        // enable = true
        expectedResponse = "{\"userId\":2,\"password\":\"asdf\",\"firstName\":\"asdf\",\"lastName\":\"asdf\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"asdf\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]}";
        actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        System.out.println(actualResponse);
        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    private String token;

    @Test
    public void createWithToken() throws Exception, JSONException {

        String requestBody = "{\n"
                + "  \"userName\":\"gosho\",\n"
                + "  \"password\" : \"lul\",\n"
                + "  \"firstName\" : \"gosho\",\n"
                + "  \"phoneNumber\" : \"0888888888\",\n"
                + "  \"email\" : \"gosho@varna.bg\"\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/users/create").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        //String expectedResponse = "{\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb3NobyIsImV4cCI6MTQ4ODU1NDA2NSwiaWF0IjoxNDg3OTQ5MjY1NjM3fQ.KRIaZhDt3AryExRp4cM8UD6LdMSxjK2nUkQM5aTQgdU\"}";
        token = new JSONObject(resultSet.getContentAsString()).getString("token");

        String username = userTokenUtil.getUsernameFromToken(token);

        Assert.assertEquals(username, "gosho");
    }

    @Test
    public void activateUserWithTokenV1() throws Exception, JSONException {

        this.createWithToken();

        resultSet = perform(MockMvcRequestBuilders.get("/users/authorization").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        String expectedResponse = "{\"userName\":\"gosho\",\"password\":\"lul\",\"firstName\":\"gosho\",\"lastName\":null,\"email\":\"gosho@varna.bg\"}";

        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        System.out.println(actualResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    public void activateUserWithFalseTokenV1() throws Exception, JSONException {

        this.createWithToken();

        resultSet = perform(MockMvcRequestBuilders.get("/users/authorization").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token + "1"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void activateUserWithTokenV2() throws Exception, JSONException {

        this.createWithToken();

        resultSet = perform(MockMvcRequestBuilders.get("/users/authorization/" + token + "/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        String expectedResponse = "{\"userName\":\"gosho\",\"password\":\"lul\",\"firstName\":\"gosho\",\"lastName\":null,\"email\":\"gosho@varna.bg\"}";

        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        System.out.println(actualResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    public void activateUserWithFalseTokenV2() throws Exception, JSONException {

        this.createWithToken();

        resultSet = perform(MockMvcRequestBuilders.get("/users/authorization/" + token + "1/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("test")
    public void createUserWithAdminRights() throws Exception, JSONException {

        String requestBody = "{\n"
                + "	\"userName\": \"van\",\n"
                + "	\"password\": \"artist\",\n"
                + "	\"firstName\": \"van\",\n"
                + "	\"lastName\": \"hellsing\",\n"
                + "	\"email\": null\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/users/privileged").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();
        String expectedResponse = "{\"password\":\"artist\",\"firstName\":\"van\",\"lastName\":\"hellsing\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":false,\"role\":{\"id\":1,\"name\":\"ADMIN\",\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]},\"username\":\"van\",\"credentialsNonExpired\":true,\"accountNonLocked\":true,\"accountNonExpired\":true,\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]}";

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }

    @Test
    @WithUserDetails("test")
    public void createUserWithAdminRightsWintInvalidField() throws Exception, JSONException {

        String requestBody = "{\n"
                + "	\"userName\": \"vanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvan\",\n"
                + "	\"password\": \"artist\",\n"
                + "	\"firstName\": \"van\",\n"
                + "	\"lastName\": \"hellsing\",\n"
                + "	\"email\": null\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/users/privileged").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("test")
    public void createUserWithUserRights() throws Exception, JSONException {

        String requestBody = "{\n"
                + "	\"userName\": \"van\",\n"
                + "	\"password\": \"artist\",\n"
                + "	\"firstName\": \"van\",\n"
                + "	\"lastName\": \"hellsing\",\n"
                + "	\"email\": null\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/users/normal").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();
        String expectedResponse = "{\"password\":\"artist\",\"firstName\":\"van\",\"lastName\":\"hellsing\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":false,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"van\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}],\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"accountNonLocked\":true}";

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
    }

    @Test
    @WithUserDetails("test")
    public void createUserWithUserRightsWithInvalidField() throws Exception, JSONException {

        String requestBody = "{\n"
                + "	\"userName\": \"vanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvanvan\",\n"
                + "	\"password\": \"artist\",\n"
                + "	\"firstName\": \"van\",\n"
                + "	\"lastName\": \"hellsing\",\n"
                + "	\"email\": null\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/users/normal").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("test")
    public void updateUser() throws Exception, JSONException {

        String requestBody = "{\n"
                + "	\"userName\": \"van\",\n"
                + "	\"password\": \"artist\",\n"
                + "	\"firstName\": \"van\",\n"
                + "	\"lastName\": \"hellsing\",\n"
                + "	\"email\": null\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.put("/users/2").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();
        String expectedResponse = "{\"userId\":2,\"password\":\"asdf\",\"firstName\":\"van\",\"lastName\":\"hellsing\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"van\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}],\"accountNonLocked\":true,\"accountNonExpired\":true,\"credentialsNonExpired\":true}";

        System.out.println(actualResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("mark")
    public void updateUserWithoutAdminRights() throws Exception, JSONException {

        //it pass with name 1000 character long 
        String requestBody = "{\n"
                + "	\"userName\": \"vanvvanvvanvvanvvanvvavanvvanvvanvvanvvanvvavanvvanvvanvvanvvanvvavanvvanvvanvvanvvanvvavanvvanvvanvvanvvanvvavanvvanvvanvvanvvanvvavanvvanvvanvvanvvanvvavanvvanvvanvvanvvanvvavanvvanvvanvvanvvanvvavanvvanvvanvvanvvanvvavanvvanvvanvvanvvanvvavanvvavanvvava\",\n"
                + "	\"password\": \"artist\",\n"
                + "	\"firstName\": \"van\",\n"
                + "	\"lastName\": \"hellsing\",\n"
                + "	\"email\": null\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.put("/users/2").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
