package com.example.SappS.controllers.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.SappS.config.secure.JwtTokenAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceManageControllerTest {

    @Autowired
    private WebApplicationContext applicationContext;
    @Autowired
    private JwtTokenAuthenticationFilter filter;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity(filter))
                .build();
    }

    @Test
    public void createService_ShouldBeSuccess() throws Exception {
        MvcResult result = mvc.perform(post("/service/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"test\" }"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isNotNull();
    }

    @Test
    @WithMockUser(username = "root", password = "root")
    public void getAllPermissions() throws Exception {
        MvcResult result = mvc.perform(get("/service/permissions/all"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse()).isNotNull();
        String permissions = result.getResponse().getContentAsString();
        System.out.println(permissions);
    }
}