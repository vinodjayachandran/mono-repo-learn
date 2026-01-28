# Release Workflow (Maven Project)

```mermaid
flowchart TD
  A[Manual dispatch with version inputs] --> B[Checkout code]
  B --> C[Setup JDK 21]
  C --> D[Configure Git]
  D --> E[Run mvn test]
  E --> F[Set release version in pom xml]
  F --> G[Commit and push release]
  G --> H[Create and push tag]
  H --> I[Run mvn clean package]
  I --> J[Create GitHub Release]
  J --> K[Login to GHCR]
  K --> L[Build and push Docker image]
  L --> M[Create kind cluster]
  M --> N[Create GHCR pull secret]
  N --> O[Apply k8s manifests]
  O --> P[Wait for pods ready]
  P --> Q[Port forward and run E2E tests]
  Q --> R{Failure}
  R -- yes --> S[Rollback cleanup and restore latest]
  R -- no --> T[Set next snapshot version]
  T --> U[Commit and push snapshot]
```
