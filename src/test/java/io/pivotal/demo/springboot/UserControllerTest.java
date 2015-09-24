package io.pivotal.demo.springboot;

import io.pivotal.demo.springboot.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserControllerTest {

    private static final String USER_ENDPOINT = "/users";

    private static final String DEFAULT_FIRSTNAME = "John";
    private static final String DEFAULT_LASTNAME = "Smith";
    private static final String DEFAULT_PASSWORD = "password";

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    private User user;

    @PostConstruct
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webAppContext)
                .build();
    }

    @Before
    public void initTest() {
        user = new User();
        user.setFirstname(DEFAULT_FIRSTNAME);
        user.setLastname(DEFAULT_LASTNAME);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    public void userShouldExist() throws Exception {
        // Get the user
        mockMvc.perform(get(USER_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))

                .andExpect(jsonPath("$.first_name", is(DEFAULT_FIRSTNAME)))
                .andExpect(jsonPath("$.last_name", is(DEFAULT_LASTNAME)))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void shouldReturn404() throws Exception {
        mockMvc.perform(get(USER_ENDPOINT + "random"))
                .andExpect(status().isNotFound());
    }
}