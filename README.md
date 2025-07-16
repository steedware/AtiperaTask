# GitHub Repository API

A Spring Boot application that provides an API to fetch GitHub user repositories with branch information.

## Features

- List all non-fork repositories for a given GitHub user
- For each repository, provide:
  - Repository name
  - Owner login
  - All branches with their names and last commit SHA
- Proper error handling for non-existing users (404 response)

## Requirements

- Java 21
- Maven 3.5
- Internet connection (to access GitHub API)

## API Endpoints

### Get User Repositories

```
GET /api/users/{username}/repositories
```

**Parameters:**
- `username` - GitHub username

**Success Response (200 OK):**
```json
[
  {
    "repositoryName": "example-repo",
    "ownerLogin": "username",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "abc123def456..."
      },
      {
        "name": "develop",
        "lastCommitSha": "def456ghi789..."
      }
    ]
  }
]
```

**Error Response (404 Not Found):**
```json
{
  "status": 404,
  "message": "User not found"
}
```

## Running the Application

1. Clone the repository
2. Build the project:
   ```bash
   mvn clean compile
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The application will start on `http://localhost:8080`

## Testing

Run the integration test:
```bash
mvn test
```

The test verifies:
- Happy path: fetching repositories for an existing user
- Error case: handling non-existing users with proper 404 response

## Example Usage

```bash
# Get repositories for user 'octocat'
curl http://localhost:8080/api/users/steedware/repositories

# Try with non-existing user
curl http://localhost:8080/api/users/nonexistentuser/repositories
```

## Technical Details

- Uses GitHub API v3 (https://api.github.com)
- Filters out forked repositories
- No pagination support
- Synchronous HTTP client (RestTemplate)
- No external mocking in tests
