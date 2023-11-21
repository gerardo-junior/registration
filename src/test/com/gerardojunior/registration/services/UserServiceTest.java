import com.gerardojunior.registration.config.JwtService;
import com.gerardojunior.registration.dto.*;
import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.exception.NotFoundException;
import com.gerardojunior.registration.exception.ValidateException;
import com.gerardojunior.registration.mappers.IUserMapper;
import com.gerardojunior.registration.repositories.UserRepository;
import com.gerardojunior.registration.services.AuthenticationService;
import com.gerardojunior.registration.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private IUserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ValidRequest_ReturnsAuthenticationResponse() {
        // Arrange
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("test@example.com");
        registerUserRequest.setPassword("password");
        when(userRepository.countByEmailOrDocument("test@example.com", null)).thenReturn(0L);
        User savedUser = new User();
        savedUser.setEmail("test@example.com");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(any(User.class))).thenReturn("fakeAccessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("fakeRefreshToken");

        // Act
        AuthenticationResponse response = userService.register(registerUserRequest);

        // Assert
        assertNotNull(response);
        assertEquals("fakeAccessToken", response.getAccessToken());
        assertEquals("fakeRefreshToken", response.getRefreshToken());
        verify(authenticationService, times(1)).saveUserToken(any(User.class), eq("fakeAccessToken"));
    }

    @Test
    void register_UserAlreadyRegistered_ThrowsValidateException() {
        // Arrange
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("test@example.com");
        registerUserRequest.setPassword("password");
        when(userRepository.countByEmailOrDocument("test@example.com", null)).thenReturn(1L);

        // Act & Assert
        assertThrows(ValidateException.class, () -> userService.register(registerUserRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void update_ValidRequest_ReturnsUserResponse() {
        // Arrange
        String document = "12345678900";
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        User existingUser = new User();
        existingUser.setDocument(document);
        when(userRepository.findByDocument(document)).thenReturn(Optional.of(existingUser));
        User savedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        UserResponse expectedResponse = new UserResponse();
        when(userMapper.map(savedUser)).thenReturn(expectedResponse);

        // Act
        UserResponse response = userService.update(document, updateUserRequest);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void update_NonexistentUser_ThrowsNotFoundException() {
        // Arrange
        String document = "12345678900";
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        when(userRepository.findByDocument(document)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.update(document, updateUserRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void find_ValidDocument_ReturnsUserResponse() {
        // Arrange
        String document = "12345678900";
        User existingUser = new User();
        when(userRepository.findByDocument(document)).thenReturn(Optional.of(existingUser));
        UserResponse expectedResponse = new UserResponse();
        when(userMapper.map(existingUser)).thenReturn(expectedResponse);

        // Act
        UserResponse response = userService.find(document);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void find_NonexistentUser_ThrowsNotFoundException() {
        // Arrange
        String document = "12345678900";
        when(userRepository.findByDocument(document)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.find(document));
    }

    @Test
    void search_ValidRequest_ReturnsPageOfUserResponse() {
        // Arrange
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        Pageable pageable = Pageable.unpaged();
        User existingUser = new User();
        Page<User> userPage = new PageImpl<>(Collections.singletonList(existingUser));
        when(userRepository.findAll(any(), eq(pageable))).thenReturn(userPage);
        UserResponse expectedResponse = new UserResponse();
        when(userMapper.map(existingUser)).thenReturn(expectedResponse);

        // Act
        Page<UserResponse> response = userService.search(searchUserRequest, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals(expectedResponse, response.getContent().get(0));
    }

    @Test
    void refreshToken_ValidRequest_ReturnsAuthenticationResponse() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer fakeRefreshToken");
        when(jwtService.extractUsername("fakeRefreshToken")).thenReturn("test@example.com");
        User existingUser = new User();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        when(jwtService.isTokenValid("fakeRefreshToken", existingUser)).thenReturn(true);
        when(jwtService.generateToken(existingUser)).thenReturn("fakeAccessToken");
        UserResponse expectedResponse = new UserResponse();
        when(userMapper.map(existingUser)).thenReturn(expectedResponse);

        // Act
        userService.refreshToken(request, response);

        // Assert
        verify(authenticationService, times(1)).saveUserToken(existingUser, "fakeAccessToken");
        verify(response.getOutputStream(), times(1)).write(any(byte[].class));
    }

    @Test
    void refreshToken_InvalidRefreshToken_DoesNotSaveToken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer invalidToken");

        // Act
        userService.refreshToken(request, response);

        // Assert
        verify(authenticationService, never()).saveUserToken(any(User.class), anyString());
        verify(response.getOutputStream(), never()).write(any(byte[].class));
    }
}

