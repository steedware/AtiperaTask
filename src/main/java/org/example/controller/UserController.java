package org.example.controller;

import org.example.model.ErrorResponse;
import org.example.model.RepositoryResponse;
import org.example.service.GitHubService;
import org.example.service.RepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final RepositoryService repositoryService;

    public UserController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/{username}/repositories")
    public ResponseEntity<?> getUserRepositories(@PathVariable String username) {
        try {
            List<RepositoryResponse> repositories = repositoryService.getUserRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (GitHubService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "User not found"));
        }
    }
}
