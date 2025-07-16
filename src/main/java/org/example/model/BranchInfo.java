package org.example.model;

public record BranchInfo(
        String name,
        String lastCommitSha
) {
}
