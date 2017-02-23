/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class TaskControllerTest extends AbstractTestRunner {

    private static ResultSet resultSet;
    private static String request;
    private static Map response;
    private static Long personId;

    @Test
    @WithUserDetails("billy")
    public void accessTasksByEmployer_positive() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedResponse = "[{\"title\":\"LULW\",\"lastUpdated\":null},"
                + "{\"title\":\"PagChomp\",\"lastUpdated\":null},"
                + "{\"title\":\"KaRappa\",\"lastUpdated\":null},"
                + "{\"title\":\"LULW\",\"lastUpdated\":null}]";

        String actualResponse = new JSONArray(resultSet.getContentAsString()).toString();

        Assert.assertTrue("Response: " + actualResponse, expectedResponse.compareTo(actualResponse) == 0);
    }

    @Test
    @WithUserDetails("mark")
    public void accessTasksByEmployee_negative() throws Exception, JSONException {

        resultSet = perform(MockMvcRequestBuilders.get("/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("mark")
    public void updateTaskByCurrentEmployee_positive() throws Exception {

        String requestBody = "{\n"
                + "  \"body\":\"some text\"\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/tasks/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedTitle = "LULW";
        String expectedUpdateBody = "some text";

        String actualTitle = new JSONObject(resultSet.getContentAsString()).getString("title");
        Assert.assertTrue(expectedTitle, expectedTitle.compareTo(actualTitle) == 0);

        String actualUpdates = new JSONObject(resultSet.getContentAsString()).getString("updates");
        JSONArray actualUpdatesArr = new JSONArray(actualUpdates);
        JSONObject update = (JSONObject) actualUpdatesArr.get(0);
        String actualUpdateBody = update.getString("body");
        Assert.assertTrue(expectedUpdateBody, expectedUpdateBody.compareTo(actualUpdateBody) == 0);
    }

    @Test
    @WithUserDetails("billy")
    public void updateTaskByEmployer_negative() throws Exception {
        String requestBody = "{\n"
                + "  \"body\":\"some text\"\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/tasks/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("mark")
    public void updateTaskByEmployeeTextBodyTooLong_negative() throws Exception {
        String requestBody = "{\n"
                + "  \"body\":\"some textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome textsome text\"\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/tasks/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("billy")
    public void createTaskByEmployer_positive() throws Exception {
        String requestBody = "{\n"
                + "	\"title\": \"LULW123123\",\n"
                + "  	\"assignees\": [\n"
                + "            {\n"
                + "              \"id\": 1\n"
                + "          	}\n"
                + "        ]\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/tasks").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String expectedTitle = "LULW123123";
        Long expectedEmployeeId = new Long(1);

        String actualTitle = new JSONObject(resultSet.getContentAsString()).getString("title");
        Assert.assertTrue(expectedTitle, expectedTitle.compareTo(actualTitle) == 0);

        String assigneesStr = new JSONObject(resultSet.getContentAsString()).getString("assignees");
        JSONArray assigneesArr = new JSONArray(assigneesStr);
        JSONObject assignee = assigneesArr.getJSONObject(0);
        Long actualAssigneeId = assignee.getLong("id");
        Assert.assertEquals("Ids are not equal", expectedEmployeeId, actualAssigneeId);
    }

    @Test
    @WithUserDetails("billy")
    public void createTaskByEmployer_negative() throws Exception {
        String requestBody = "{\n"
                + "	\"title\": \"LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123LULW123123\",\n"
                + "  	\"assignees\": [\n"
                + "            {\n"
                + "              \"id\": 1\n"
                + "          	}\n"
                + "        ]\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/tasks").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithUserDetails("mark")
    public void createTaskByEmployee() throws Exception {
        String requestBody = "{\n"
                + "	\"title\": \"LULW123123\",\n"
                + "  	\"assignees\": [\n"
                + "            {\n"
                + "              \"id\": 1\n"
                + "          	}\n"
                + "        ]\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/tasks").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}
