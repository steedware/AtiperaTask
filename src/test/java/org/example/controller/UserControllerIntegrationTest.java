package org.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnUserRepositoriesWithBranchesForExistingUser() {

        String username = "steedware";
        String url = "http://localhost:" + port + "/api/users/" + username + "/repositories";


        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());

        String responseBody = response.getBody();
        assertNotNull(responseBody);


        assertTrue(responseBody.startsWith("["));
        assertTrue(responseBody.contains("repositoryName"));
        assertTrue(responseBody.contains("ownerLogin"));
        assertTrue(responseBody.contains("branches"));
        assertTrue(responseBody.contains("name"));
        assertTrue(responseBody.contains("lastCommitSha"));

        assertTrue(responseBody.contains("\"ownerLogin\":\"steedware\""));
    }

    @Test
    void shouldReturn404ForNonExistingUser() {

        String username = "nonexistentuser12345678901234567890";
        String url = "http://localhost:" + port + "/api/users/" + username + "/repositories";


        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        String responseBody = response.getBody();
        assertNotNull(responseBody);


        assertTrue(responseBody.contains("\"status\":404"));
        assertTrue(responseBody.contains("\"message\":\"User not found\""));
    }
}
