/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employee;

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
public class EmployeeControllerTest extends AbstractTestRunner {

    private static ResultSet resultSet;
    private static String request;
    private static Map response;
    private static Long personId;

    @Test
    public void getAllEmployeesDTO() throws Exception {

        resultSet = perform(MockMvcRequestBuilders.get("/employees/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        String expectedResponse = "[{\"firstName\":null,\"middleName\":null,\"lastName\":null,\"phoneNumber\":null,\"sex\":null,\"dob\":null},{\"firstName\":null,\"middleName\":null,\"lastName\":null,\"phoneNumber\":null,\"sex\":null,\"dob\":null},{\"firstName\":null,\"middleName\":null,\"lastName\":null,\"phoneNumber\":null,\"sex\":null,\"dob\":null}]";

        String actualResponse = new JSONArray(resultSet.getContentAsString()).toString();

        //Assert.assertTrue("Response: " + actualResponse, expectedResponse.compareTo(actualResponse) == 0);
        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("billy")
    public void changeEmployeeActiveStatusByEmployer() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employees/active/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // enabled = false
        String expectedResponse = "{\"id\":1,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":6,\"password\":\"wolf\",\"firstName\":\"mark\",\"lastName\":\"wolf\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":false,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mark\",\"accountNonLocked\":true,\"accountNonExpired\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]}";
        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);

        resultSet = perform(MockMvcRequestBuilders.get("/employers/active/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // enable = true
        expectedResponse = "{\"id\":1,\"user\":{\"userId\":3,\"password\":\"123\",\"firstName\":\"billy\",\"lastName\":\"herrington\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":false,\"role\":{\"id\":1,\"name\":\"ADMIN\",\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]},\"username\":\"billy\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"ADMIN\"},{\"authority\":\"PERM_VIEW_USER\"},{\"authority\":\"PERM_DELETE_USER\"},{\"authority\":\"PERM_EDIT_USER\"},{\"authority\":\"PERM_CHANGE_ACTIVE_USER\"},{\"authority\":\"PERM_VIEW_USERFLAG\"}]},\"employees\":[{\"id\":1,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":6,\"password\":\"wolf\",\"firstName\":\"mark\",\"lastName\":\"wolf\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":false,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mark\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]},{\"id\":2,\"employeeNumber\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"workDeptId\":null,\"phoneNumber\":null,\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":null,\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":7,\"password\":\"mmm\",\"firstName\":\"mmm\",\"lastName\":\"mmm\",\"birthDate\":null,\"phoneNumber\":null,\"email\":null,\"enabled\":true,\"role\":{\"id\":2,\"name\":\"USER\",\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"username\":\"mmm\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"privileges\":[{\"authority\":\"PERM_VIEW_USER\"}]},\"updates\":[]}]}";
        actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("test")
    public void changeEmployeeActiveStatusByAdmin() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employees/active/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("mark")
    public void getSelf() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employees/self").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "{\"firstName\":\"mark\",\"middleName\":null,\"lastName\":\"wolf\",\"phoneNumber\":null,\"sex\":null,\"dob\":null}";

        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("billy")
    public void getSelfByNotEmployee() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employees/self").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    //employees/tasks
    @Test
    @WithUserDetails("billy")
    public void getEmployeeTasksByOthers() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employees/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("mark")
    public void getEmployeeTasks() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employees/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "[{\"title\":\"LULW\",\"created\":null,\"updates\":[]},{\"title\":\"PagChomp\",\"created\":null,\"updates\":[]},{\"title\":\"KaRappa\",\"created\":null,\"updates\":[]}]";

        String actualResponse = new JSONArray(resultSet.getContentAsString()).toString();

        System.out.println(actualResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    public void getEmployeeTasked() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/employees/tasked").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "[{\"firstName\":null,\"updates\":[]},{\"firstName\":null,\"updates\":[]},{\"firstName\":null,\"updates\":[]}]";

        String actualResponse = new JSONArray(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    public void saveEmployee() throws Exception, JSONException {

        String requestBody = "{\n"
                + "    \"firstName\": \"mark1\",\n"
                + "    \"middleName\": \"test1\",\n"
                + "    \"lastName\": \"ericson1\",\n"
                + "    \"phoneNumber\": \"123\",\n"
                + "    \"sex\": \"m\"\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "{\"id\":4,\"employeeNumber\":null,\"firstName\":\"mark1\",\"middleName\":\"test1\",\"lastName\":\"ericson1\",\"workDeptId\":null,\"phoneNumber\":\"123\",\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":\"m\",\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":12,\"password\":null,\"firstName\":\"mark1\",\"lastName\":\"ericson1\",\"birthDate\":null,\"phoneNumber\":\"123\",\"email\":null,\"enabled\":false,\"role\":null,\"username\":null,\"accountNonExpired\":true,\"credentialsNonExpired\":true,\"accountNonLocked\":true,\"privileges\":null},\"updates\":null}";
        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    public void saveInvalidEmployee() throws Exception, JSONException {

        String requestBody = "{\n"
                + "    \"firstName\": \"mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1mark1\",\n"
                + "    \"middleName\": \"test1\",\n"
                + "    \"lastName\": \"ericson1\",\n"
                + "    \"phoneNumber\": \"123\",\n"
                + "    \"sex\": \"m\"\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("mark")
    public void updateEmployee() throws Exception, JSONException {

        String requestBody = "{\n"
                + "    \"firstName\": \"markVI\",\n"
                + "    \"middleName\": \"test1\",\n"
                + "    \"lastName\": \"ericsonVI\",\n"
                + "    \"phoneNumber\": \"123\",\n"
                + "    \"sex\": \"m\"\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "{\"id\":4,\"employeeNumber\":null,\"firstName\":\"markVI\",\"middleName\":\"test1\",\"lastName\":\"ericsonVI\",\"workDeptId\":null,\"phoneNumber\":\"123\",\"hireDate\":null,\"job\":null,\"educationLvl\":null,\"sex\":\"m\",\"dob\":null,\"salary\":null,\"bonus\":null,\"commission\":null,\"user\":{\"userId\":12,\"password\":null,\"firstName\":\"markVI\",\"lastName\":\"ericsonVI\",\"birthDate\":null,\"phoneNumber\":\"123\",\"email\":null,\"enabled\":false,\"role\":null,\"username\":null,\"accountNonLocked\":true,\"accountNonExpired\":true,\"credentialsNonExpired\":true,\"privileges\":null},\"updates\":null}";
        String actualResponse = new JSONObject(resultSet.getContentAsString()).toString();

        System.out.println(actualResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @Test
    @WithUserDetails("mark")
    public void updateEmployeeWithNoValidField() throws Exception, JSONException {

        String requestBody = "{\n"
                + "    \"firstName\": \"markVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVImarkVI\",\n"
                + "    \"middleName\": \"test1\",\n"
                + "    \"lastName\": \"ericsonVI\",\n"
                + "    \"phoneNumber\": \"123\",\n"
                + "    \"sex\": \"m\"\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}
