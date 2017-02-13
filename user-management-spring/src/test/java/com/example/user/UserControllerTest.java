/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user;

import java.util.Locale;
import org.junit.Test;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 *
 * @author Teodor Todorov
 */
public class UserControllerTest {

    @Test
    //@WithMockUser(username = "test", password = "test", roles = "ADMIN")
    public void testUsersPage() throws Exception {
        UserController controller = new UserController();

        MockMvc mockMvc = standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public MappingJackson2JsonView resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();

        mockMvc.perform(get("/user-management-spring/users"))
                .andExpect(status().is4xxClientError());
        //.andExpect(MockMvcResultMatchers.content().contentType(MediaType.));
    }
}
