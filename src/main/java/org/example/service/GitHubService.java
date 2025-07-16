package org.example.service;

import org.example.model.GitHubBranch;
import org.example.model.GitHubRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubService {

    private final RestTemplate restTemplate;
    private static final String GITHUB_API_URL = "https://api.github.com";

    public GitHubService() {
        this.restTemplate = new RestTemplate();
    }

    public GitHubRepository[] getRepositories(String username) {
        try {
            return restTemplate.getForObject(
                GITHUB_API_URL + "/users/{username}/repos",
                GitHubRepository[].class,
                username
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User not found");
        }
    }

    public GitHubBranch[] getBranches(String username, String repositoryName) {
        try {
            return restTemplate.getForObject(
                GITHUB_API_URL + "/repos/{username}/{repo}/branches",
                GitHubBranch[].class,
                username,
                repositoryName
            );
        } catch (HttpClientErrorException e) {
            return new GitHubBranch[0];
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
