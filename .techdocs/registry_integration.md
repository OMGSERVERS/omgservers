### Useful links
- Distribution Registry Token Authentication - https://distribution.github.io/distribution/spec/auth/
- Docker Auth Flow - https://wjw465150.gitbooks.io/keycloak-documentation/content/server_admin/topics/sso-protocols/docker.html
- Resource Scope Grammar - https://distribution.github.io/distribution/spec/auth/scope/#resource-scope-grammar

### Docker Authentication Flow

1. The `docker login` command sends a `GET /token` request with `offlineToken=true` in the request body to obtain a
   long-lived `refresh_token`.

2. When running `docker push`, the following steps occur:
    - The Docker client contacts the registry.
    - If no authentication token is present, the registry responds with an authentication challenge, for example:

      ```
      Www-Authenticate: Bearer realm="https://auth.docker.io/token", service="registry.docker.io", scope="repository:samalba/my-app:pull,push"
      ```

    - The client parses the challenge and extracts the `realm`, `service`, and `scope` values.
    - It then sends a `POST /token` request to the specified `realm`, including:
        - `grant_type=refresh_token`
        - The `refresh_token` obtained during login
        - The `service` and `scope` values from the challenge

    - The server responds with a short-lived `access_token`, which the client uses to authenticate the image upload.
