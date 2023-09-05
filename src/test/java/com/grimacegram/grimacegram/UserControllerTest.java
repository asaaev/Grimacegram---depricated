package com.grimacegram.grimacegram;

import com.grimacegram.grimacegram.model.User;
import com.grimacegram.grimacegram.repository.UserRepository;
import com.grimacegram.grimacegram.shared.GenericResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class UserControllerTest {

    public static final String API_1_0_USERS = "/api/1.0/users";
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    UserRepository userRepository;
    @Before
    public void cleanup(){
        userRepository.deleteAll();
    }

    private User createValidUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("P4ssword");
        return user;
    }

    @Test
    public void postUser_whenUserIsValid_receiveOk(){
        User user = createValidUser();

        ResponseEntity<Object> objectResponseEntity = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);

        assertThat(objectResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postUser_whenUserIsValid_receiveSuccessMessage(){
        User user = createValidUser();

        ResponseEntity<GenericResponse> objectResponseEntity = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);

        assertThat(objectResponseEntity.getBody().getMessage()).isNotNull();
    }

    @Test
    public void postUser_whenUserIsValid_userSavedToDatabase(){

        User user = createValidUser();
        testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);

        assertThat(userRepository.count()).isEqualTo(1);

    }
    @Test
    public void postUser_whenUserIsValid_passwordIsHasheedInDatabase(){
        User user = createValidUser();
        testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);

        List<User> users = userRepository.findAll();
        User inDB = users.get(0);
        assertThat(inDB.getPassword()).isNotEqualTo(user.getPassword());
    }
    @Test
    public void postUser_whenUserHasNullUsername_receiveBadRequest(){
        User user = createValidUser();
        user.setUsername(null);

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasNullDisplayName_receiveBadRequest(){
        User user = createValidUser();
        user.setDisplayName(null);

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasNullPassword_receiveBadRequest(){
        User user = createValidUser();
        user.setPassword(null);

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasUsernameHasLessThanRequired_receiveBadRequest(){
        User user = createValidUser();
        user.setUsername("abc");

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasDisplayNameHasLessThanRequired_receiveBadRequest(){
        User user = createValidUser();
        user.setDisplayName("abc");

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasPasswordWithLessThanRequired_receiveBadRequest(){
        User user = createValidUser();
        user.setPassword("P4sswd");

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasUsernameExceedTheLengthLimit_receiveBadRequest(){
        User user = createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setUsername(valueOf256Chars);

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasDisplayNameExceedTheLengthLimit_receiveBadRequest(){
        User user = createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setDisplayName(valueOf256Chars);

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasPasswordExceedTheLengthLimit_receiveBadRequest(){
        User user = createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setPassword(valueOf256Chars + "A1");

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasPasswordWithAllLowercase_receiveBadRequest(){
        User user = createValidUser();
        user.setPassword("alllowercase");

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasPasswordWithAllUppercase_receiveBadRequest(){
        User user = createValidUser();
        user.setPassword("ALLUPPERCASE");

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postUser_whenUserHasPasswordWithAllNumber_receiveBadRequest(){
        User user = createValidUser();
        user.setPassword("1234567890");

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


}
