package org.example.model;

public record GitHubBranch(
        String name,
        GitHubCommit commit
) {
}
