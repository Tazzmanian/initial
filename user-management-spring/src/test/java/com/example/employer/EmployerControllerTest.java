/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employer;

import com.example.AbstractTestRunner;
import com.example.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContentAssert;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author Teodor Todorov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EmployerControllerTest extends AbstractTestRunner {

    private static ResultSet resultSet;
    private static String request;
    private static Map response;
    private static Long personId;

    @Test
    @WithUserDetails("test")
    public void getAllEmployersByAdmin() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employers/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "[{\"id\":1,\"employees\":[{\"id\":1,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":6,\"password\":\"wolf\",\"firstName\":\"mark\",\"lastName\":\"wolf\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mark\",\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]},{\"id\":2,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":7,\"password\":\"mmm\",\"firstName\":\"mmm\",\"lastName\":\"mmm\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mmm\",\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]}]},{\"id\":2,\"employees\":[{\"id\":3,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":8,\"password\":\"asdf\",\"firstName\":\"bored\",\"lastName\":\"bored\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"bored\",\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]}]},{\"id\":3,\"employees\":[]}]";
        String actualResponse = new JSONArray(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("asdf")
    public void getAllEmployersByNotAdmin() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employers/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("billy")
    public void getEmployersEmployeesCount() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employers/employeecount").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        Long expectedResponse = new Long(2);
        String actualResponse = resultSet.getContentAsString();
        Long actualR = Long.parseLong(actualResponse, 10);

        Assert.assertEquals("Count is not correct", expectedResponse, actualR);
    }

    @Test
    @WithUserDetails("billy")
    public void getEmployersEmployees() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employers/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "[{\"id\":1,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":6,\"password\":\"wolf\",\"firstName\":\"mark\",\"lastName\":\"wolf\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"username\":\"mark\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]},{\"id\":2,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":7,\"password\":\"mmm\",\"firstName\":\"mmm\",\"lastName\":\"mmm\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"username\":\"mmm\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]}]";

        String actualResponse = new JSONArray(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("test")
    public void changeEmployerActiveStatusByAdmin() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employers/active/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // enabled = false
        String expectedResponse = "{\"id\":1,\"user\":{\"userId\":3,\"password\":\"123\",\"firstName\":\"billy\",\"lastName\":\"herrington\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":false,\"role\":{\"id\":1,\"name\":\"ADMIN\",\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]},\"username\":\"billy\",\"credentialsNonExpired\":true,\"accountNonLocked\":true,\"accountNonExpired\":true,\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]},\"employees\":[{\"id\":1,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":6,\"password\":\"wolf\",\"firstName\":\"mark\",\"lastName\":\"wolf\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mark\",\"credentialsNonExpired\":true,\"accountNonLocked\":true,\"accountNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]},{\"id\":2,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":7,\"password\":\"mmm\",\"firstName\":\"mmm\",\"lastName\":\"mmm\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mmm\",\"credentialsNonExpired\":true,\"accountNonLocked\":true,\"accountNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]}]}";
        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);

        resultSet = perform(MockMvcRequestBuilders.get("/employers/active/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // enable = true
        expectedResponse = "{\"id\":1,\"user\":{\"userId\":3,\"password\":\"123\",\"firstName\":\"billy\",\"lastName\":\"herrington\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":1,\"name\":\"ADMIN\",\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]},\"username\":\"billy\",\"credentialsNonExpired\":true,\"accountNonLocked\":true,\"accountNonExpired\":true,\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]},\"employees\":[{\"id\":1,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":6,\"password\":\"wolf\",\"firstName\":\"mark\",\"lastName\":\"wolf\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mark\",\"credentialsNonExpired\":true,\"accountNonLocked\":true,\"accountNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]},{\"id\":2,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":7,\"password\":\"mmm\",\"firstName\":\"mmm\",\"lastName\":\"mmm\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mmm\",\"credentialsNonExpired\":true,\"accountNonLocked\":true,\"accountNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]}]}";
        actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("kazuya")
    public void changeEmployerActiveStatusByEmployer() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employers/active/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("test")
    public void changeNotExistingEmployerActiveStatus() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employers/active/10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void saveEmployerByAdmin() throws Exception, JSONException {

        String requestBody = "{\n"
                + "	\"employees\": []\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/employers").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "{\"id\":4,\"employees\":[]}";
        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("billy")
    public void updateExistingEmployeeByEmployer() throws Exception, JSONException {

        String requestBody = "{\n"
                + "    \"salary\": 200,\n"
                + "    \"bonus\": 200,\n"
                + "    \"commission\": 200\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.put("/employers/employees/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "{\"id\":1,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":200,\"bonus\":200,\"commission\":200,\"user\":{\"userId\":6,\"password\":\"wolf\",\"firstName\":\"mark\",\"lastName\":\"wolf\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mark\",\"accountNonLocked\":true,\"accountNonExpired\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]}";
        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("billy")
    public void updateNotExistingEmployeeByEmployer() throws Exception, JSONException {

        String requestBody = "{\n"
                + "    \"salary\": 200,\n"
                + "    \"bonus\": 200,\n"
                + "    \"commission\": 200\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.put("/employers/employees/10").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("billy")
    public void updateExistingEmployeeByEmployerWithIncorrectData() throws Exception, JSONException {

        String requestBody = "{\n"
                + "    \"salary\": 200,\n"
                + "    \"bonus\": 200,\n"
                + "    \"commission\": \"asdf\"\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.put("/employers/employees/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
