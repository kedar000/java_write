package org.testing;

public class UserControllerINtegrationTest {
}



package org.examplemodulespringboot.jwt_task.controller;

import org.examplemodulespringboot.jwt_task.entity.User;
import org.examplemodulespringboot.jwt_task.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        User user = new User();
        user.setName("Kedar");
        user.setUsername("kedar123");
        user.setEmail("kedar@gmail.com");
        user.setPassword("pass");

        userRepository.save(user);
    }

    @Test
    void searchEndpoint_shouldReturnMatchingUsers() throws Exception {

        mockMvc.perform(get("/api/users/search")
                        .param("keyword", "ke")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].username")
                        .value("kedar123"));
    }
}
