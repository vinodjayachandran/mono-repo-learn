# PR Workflow (Maven Project)

```mermaid
flowchart TD
  A[PR opened or updated] --> B[Checkout code]
  B --> C[Setup JDK 21]
  C --> D[Cache Maven m2]
  D --> E[Run mvn test]
```
