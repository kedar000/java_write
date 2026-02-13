package org.testing;

public class userServiceUnitTest {
}

package org.examplemodulespringboot.jwt_task.service;

import org.examplemodulespringboot.jwt_task.entity.User;
import org.examplemodulespringboot.jwt_task.repository.UserRepository;
import org.examplemodulespringboot.jwt_task.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

        import org.springframework.data.domain.*;

        import java.util.List;

import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchUsers_shouldReturnFilteredPage() {

        User user = new User();
        user.setId(1L);
        user.setName("Kedar");
        user.setUsername("kedar123");
        user.setEmail("kedar@gmail.com");

        Pageable pageable = PageRequest.of(0, 5);
        Page<User> mockPage = new PageImpl<>(List.of(user));

        when(userRepository
                .findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrNameContainingIgnoreCase(
                        "ke", "ke", "ke", pageable))
                .thenReturn(mockPage);

        Page<UserResponse> result =
                userService.searchUsers("ke", 0, 5);

        assertEquals(1, result.getTotalElements());
        assertEquals("kedar123",
                result.getContent().get(0).getUsername());
    }
}
