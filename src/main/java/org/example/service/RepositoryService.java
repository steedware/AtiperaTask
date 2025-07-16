package org.example.service;

import org.example.model.BranchInfo;
import org.example.model.GitHubBranch;
import org.example.model.GitHubRepository;
import org.example.model.RepositoryResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepositoryService {

    private final GitHubService gitHubService;

    public RepositoryService(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    public List<RepositoryResponse> getUserRepositories(String username) {
        GitHubRepository[] repositories = gitHubService.getRepositories(username);

        return Arrays.stream(repositories)
                .filter(repo -> !repo.isFork())
                .map(this::mapToRepositoryResponse)
                .collect(Collectors.toList());
    }

    private RepositoryResponse mapToRepositoryResponse(GitHubRepository repository) {
        GitHubBranch[] branches = gitHubService.getBranches(repository.owner().login(), repository.name());

        List<BranchInfo> branchInfos = Arrays.stream(branches)
                .map(branch -> new BranchInfo(branch.name(), branch.commit().sha()))
                .collect(Collectors.toList());

        return new RepositoryResponse(
            repository.name(),
            repository.owner().login(),
            branchInfos
        );
    }
}
