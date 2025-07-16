package org.example.model;

import java.util.List;

public record RepositoryResponse(
        String repositoryName,
        String ownerLogin,
        List<BranchInfo> branches
) {
}
