### Useful links
- Distribution Registry Token Authentication - https://distribution.github.io/distribution/spec/auth/
- Docker Auth Flow - https://wjw465150.gitbooks.io/keycloak-documentation/content/server_admin/topics/sso-protocols/docker.html
- Resource Scope Grammar - https://distribution.github.io/distribution/spec/auth/scope/#resource-scope-grammar

### Examples

Registry return auth challenge if no auth token is present in the request
- Www-Authenticate: Bearer realm="https://auth.docker.io/token",service="registry.docker.io",scope="repository:samalba/my-app:pull,push"