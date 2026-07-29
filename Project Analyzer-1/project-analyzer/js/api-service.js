/**
 * Project Analyzer — Mock API Service
 * Standardized service interface prepared for Spring Boot REST API integration.
 * Currently returns promise-wrapped mock data.
 */

const ApiService = (function() {
  // Realistic mock dataset
  const mockProjects = [
    {
      id: "prj-001",
      name: "Project Analyzer",
      description: "Full-stack static analysis and project health intelligence engine",
      technology: "Java / Spring Boot",
      techStack: ["Java 17", "Spring Boot 3.2", "Bootstrap 5", "MySQL"],
      healthScore: 84,
      healthStatus: "Healthy",
      issuesCount: { critical: 3, high: 8, medium: 17, low: 12 },
      codeQualityScore: 87,
      securityScore: 74,
      testCoverage: 76,
      architectureScore: 91,
      dependenciesCount: { total: 42, outdated: 6, vulnerable: 3, upToDate: 33 },
      lastAnalyzed: "10 mins ago",
      repoUrl: "https://github.com/praveen/project-analyzer"
    },
    {
      id: "prj-002",
      name: "E-Commerce API",
      description: "High-throughput RESTful payment & catalog microservice backend",
      technology: "Java / Spring Boot",
      techStack: ["Java 21", "Spring Cloud", "PostgreSQL", "Redis"],
      healthScore: 78,
      healthStatus: "Good",
      issuesCount: { critical: 1, high: 12, medium: 22, low: 15 },
      codeQualityScore: 82,
      securityScore: 80,
      testCoverage: 84,
      architectureScore: 88,
      dependenciesCount: { total: 58, outdated: 10, vulnerable: 1, upToDate: 47 },
      lastAnalyzed: "2 hours ago",
      repoUrl: "https://github.com/praveen/ecommerce-api"
    },
    {
      id: "prj-003",
      name: "Task Management System",
      description: "Enterprise Kanban workflow & team productivity backend service",
      technology: "Java / Spring Boot",
      techStack: ["Java 17", "Spring Security", "React", "Kafka"],
      healthScore: 62,
      healthStatus: "Warning",
      issuesCount: { critical: 5, high: 18, medium: 31, low: 20 },
      codeQualityScore: 65,
      securityScore: 58,
      testCoverage: 52,
      architectureScore: 70,
      dependenciesCount: { total: 36, outdated: 14, vulnerable: 6, upToDate: 16 },
      lastAnalyzed: "1 day ago",
      repoUrl: "https://github.com/praveen/task-management-system"
    },
    {
      id: "prj-004",
      name: "Portfolio Platform",
      description: "Developer profile aggregator and automated static resume engine",
      technology: "JavaScript / Node",
      techStack: ["Node.js", "Express", "MongoDB", "HTML5"],
      healthScore: 92,
      healthStatus: "Excellent",
      issuesCount: { critical: 0, high: 2, medium: 5, low: 8 },
      codeQualityScore: 94,
      securityScore: 90,
      testCoverage: 91,
      architectureScore: 95,
      dependenciesCount: { total: 24, outdated: 2, vulnerable: 0, upToDate: 22 },
      lastAnalyzed: "3 days ago",
      repoUrl: "https://github.com/praveen/portfolio-platform"
    }
  ];

  const mockIssues = [
    {
      id: "iss-101",
      title: "Hardcoded Password",
      severity: "Critical",
      category: "Security",
      file: "UserService.java",
      line: 42,
      status: "Open",
      description: "Plaintext credentials detected in source code string literal during database initialization.",
      recommendation: "Move sensitive credentials to application.yml or environment variables using Spring @Value or AWS Secrets Manager.",
      snippet: `40:  public void connectDatabase() {\n41:      String dbUser = "admin";\n42:      String dbPass = "SuperSecretPass123!"; // HIGH RISK\n43:      DriverManager.getConnection(url, dbUser, dbPass);\n44:  }`
    },
    {
      id: "iss-102",
      title: "SQL Injection Risk",
      severity: "Critical",
      category: "Security",
      file: "UserRepository.java",
      line: 87,
      status: "Open",
      description: "Raw SQL query concatenation detected in Native Query execution without parameterized binding.",
      recommendation: "Use JPA Named Parameters (:username) or CriteriaBuilder instead of String concatenation.",
      snippet: `85:  public User findByUsernameUnsafe(String input) {\n86:      String query = "SELECT * FROM users WHERE name = '" + input + "'"; // SQL INJECTION\n87:      return entityManager.createNativeQuery(query).getSingleResult();\n88:  }`
    },
    {
      id: "iss-103",
      title: "Long Method",
      severity: "Medium",
      category: "Code Quality",
      file: "OrderService.java",
      line: 124,
      status: "In Progress",
      description: "Method `processOrderAndNotifyCustomer()` exceeds 180 lines of code, violating Single Responsibility Principle.",
      recommendation: "Refactor method into smaller helper domain services: OrderValidationService, PaymentGateway, NotificationService.",
      snippet: `122: public OrderResponse processOrderAndNotifyCustomer(OrderRequest request) {\n123:     // 180+ lines of monolithic logic handling validation, pricing, payment, inventory, email...\n124:     validateOrder(request);\n125: }`
    },
    {
      id: "iss-104",
      title: "Duplicate Code",
      severity: "Low",
      category: "Maintainability",
      file: "PaymentService.java",
      line: 65,
      status: "Open",
      description: "Identical 45-line payment hashing algorithm duplicated across StripePaymentService and PaypalPaymentService.",
      recommendation: "Extract duplicate hashing logic into a shared Utility class `PaymentSecurityUtil`.",
      snippet: `63:  private String calculateHmacSha256(String payload, String secret) {\n64:      // Identical duplicate implementation across payment providers\n65:      Mac sha256_HMAC = Mac.getInstance("HmacSHA256");\n66:  }`
    },
    {
      id: "iss-105",
      title: "Unused Dependency Detected",
      severity: "High",
      category: "Dependencies",
      file: "pom.xml",
      line: 112,
      status: "Open",
      description: "Dependency `commons-collections4` imported in pom.xml but never referenced in codebase.",
      recommendation: "Remove unused dependencies to reduce build package size and surface attack area.",
      snippet: `110: <dependency>\n111:     <groupId>org.apache.commons</groupId>\n112:     <artifactId>commons-collections4</artifactId>\n113: </dependency>`
    },
    {
      id: "iss-106",
      title: "Missing Unit Tests",
      severity: "High",
      category: "Testing",
      file: "AuthService.java",
      line: 15,
      status: "Open",
      description: "Core authentication service has 0% code coverage across all token validation branches.",
      recommendation: "Create `AuthServiceTest.java` with JUnit 5 and Mockito testing happy path and invalid JWT scenarios.",
      snippet: `14:  @Service\n15:  public class AuthService {\n16:      public String generateToken(UserDetails user) { ... }\n17:  }`
    }
  ];

  const mockDependencies = [
    { name: "Spring Boot Starter Web", current: "3.2.1", latest: "3.2.3", status: "Up To Date", severity: "None" },
    { name: "Spring Security", current: "6.1.0", latest: "6.2.1", status: "Outdated", severity: "Low" },
    { name: "Jackson Databind", current: "2.14.0", latest: "2.16.1", status: "Vulnerable", severity: "Critical" },
    { name: "Log4j Core", current: "2.17.1", latest: "2.22.0", status: "Up To Date", severity: "None" },
    { name: "MySQL Connector Java", current: "8.0.33", latest: "8.3.0", status: "Outdated", severity: "Medium" },
    { name: "Lombok", current: "1.18.30", latest: "1.18.30", status: "Up To Date", severity: "None" },
    { name: "JUnit Jupiter", current: "5.10.1", latest: "5.10.1", status: "Up To Date", severity: "None" },
    { name: "BCrypt Password Encoder", current: "5.8.0", latest: "6.2.0", status: "Outdated", severity: "High" }
  ];

  const mockReports = [
    { id: "rep-001", title: "Project Analyzer Full Assessment", date: "August 10, 2026", score: 84, status: "Ready", size: "2.4 MB" },
    { id: "rep-002", title: "Security Vulnerability Scan", date: "August 08, 2026", score: 74, status: "Ready", size: "1.8 MB" },
    { id: "rep-003", title: "Architecture & Coupling Benchmark", date: "August 01, 2026", score: 91, status: "Ready", size: "3.1 MB" },
    { id: "rep-004", title: "Test Coverage Audit", date: "July 25, 2026", score: 76, status: "Ready", size: "1.2 MB" }
  ];

  // Helper helper to wrap in promise
  const simulateDelay = (data, delay = 150) => {
    return new Promise((resolve) => {
      setTimeout(() => resolve(JSON.parse(JSON.stringify(data))), delay);
    });
  };

  return {
    getProjects: () => simulateDelay(mockProjects),
    getProject: (id) => simulateDelay(mockProjects.find(p => p.id === id) || mockProjects[0]),
    createProject: (data) => {
      const newProj = {
        id: `prj-${Date.now()}`,
        name: data.name || "New Custom Project",
        description: data.description || "Recently imported codebase",
        technology: data.technology || "Java / Spring Boot",
        techStack: ["Java 17", "Spring Boot", "MySQL"],
        healthScore: 85,
        healthStatus: "Healthy",
        issuesCount: { critical: 1, high: 4, medium: 8, low: 5 },
        codeQualityScore: 88,
        securityScore: 82,
        testCoverage: 78,
        architectureScore: 90,
        dependenciesCount: { total: 30, outdated: 3, vulnerable: 1, upToDate: 26 },
        lastAnalyzed: "Just now",
        repoUrl: data.repoUrl || "Uploaded ZIP"
      };
      mockProjects.unshift(newProj);
      return simulateDelay(newProj);
    },
    deleteProject: (id) => {
      const idx = mockProjects.findIndex(p => p.id === id);
      if (idx !== -1) mockProjects.splice(idx, 1);
      return simulateDelay({ success: true });
    },
    analyzeProject: (id) => simulateDelay({ status: "STARTED", analysisId: `anl-${Date.now()}` }),
    getIssues: (id) => simulateDelay(mockIssues),
    getDependencies: (id) => simulateDelay(mockDependencies),
    getReports: (id) => simulateDelay(mockReports)
  };
})();
