[![Deploy to Koyeb](https://www.koyeb.com/static/images/deploy/button.svg)](https://app.koyeb.com/deploy?name=eshop-kurrorro&type=git&repository=B-Keisha-Vania-Laurent-2406437331%2FModul-2-CICD-DevOps&branch=main&builder=dockerfile&instance_type=free&regions=was&instances_min=0&autoscaling_sleep_idle_delay=3900&ports=8080%3Bhttp%3B%2F&hc_protocol%5B8080%5D=tcp&hc_grace_period%5B8080%5D=5&hc_interval%5B8080%5D=30&hc_restart_limit%5B8080%5D=3&hc_timeout%5B8080%5D=5&hc_path%5B8080%5D=%2F&hc_method%5B8080%5D=get)

## Deployment Link
https://bewildered-essa-adpro-tutorial-kurrorro-a7136900.koyeb.app

<details>
<summary><b>Module 1 - Coding Standards</b></summary>

# Reflection 1

## 1. Coding Principles
In implementing this project, I have applied several Clean Code and Secure Coding practices to ensure the software is maintainable, readable, and secure:

* **Meaningful Names (Penamaan yang Bermakna):** I used clear and descriptive names for variables, methods, and classes (e.g., `ProductRepository`, `create`, `delete`). This practice makes the code **self-documenting**, allowing other developers to understand the logic easily without needing extensive comments.

* **Single Responsibility Principle (SRP):** I adhered to SRP by strictly separating application logic into distinct layers. The **Controller** handles HTTP requests, the **Service** contains business logic, and the **Repository** manages data access. This separation ensures that each class has only one reason to change.

* **Secure Coding (UUID Generation):** Instead of using sequential integers (1, 2, 3...), I utilized **UUIDs** (Universally Unique Identifiers) for generating unique product IDs. This approach significantly enhances security by preventing **ID Enumeration Attacks**, where an attacker could otherwise guess valid IDs by simply incrementing numbers.

## 2. Areas for Improvement
Despite the implementations above, I identified a few critical areas where the code quality and security can be improved:

* **Input Validation:** Previously, the application crashed with a `TypeMismatchException` when submitting empty forms because the primitive `int` type could not handle null values. Additionally, there was no validation preventing negative quantities.  
    * *The Improvement (BackEnd):* I refactored the model to use the `Integer` Wrapper Class instead of `int`, allowing safe handling of null values. I then implemented validation using annotations like `@NotNull` and `@Min(1)` and updated the Controller to use `@Valid` and `BindingResult`. This ensures that invalid input is caught and not causing a system crash (Whitelabel Error Page).
    * *The Improvement (FrontEnd):* I implemented **Client-Side** Validation directly in the HTML. I added the `required` attribute to ensure fields are not empty and the `min="1"` attribute to restrict negative input.
 
# Reflection 2

## 1. Unit Testing & Code Coverage
After writing the unit tests for this project, here are my key takeaways regarding code quality and testing metrics:

* **Confidence in Code Stability:**
    Writing unit tests has significantly increased my confidence in the application's stability. I can verify that core features (Create, Edit, Delete) function correctly. More importantly, these tests act as a safety net during **refactoring**, ensuring that future changes do not break existing functionality.

* **Number of Unit Tests:**
    There is no fixed number of unit tests required for a class. Instead of focusing on quantity, the focus should be on **Scenario Coverage**. A good test suite must cover:
    * **Positive Cases (Happy Path):** Ensures the code works as expected under normal conditions.
    * **Negative Cases (Error Handling):** Ensures the code handles invalid input or exceptions gracefully.
    * **Edge Cases (Boundary Values):** Ensures the code handles limits (e.g., empty lists, maximum values).

* **100% Code Coverage:**
    I learned that **100% Code Coverage does not guarantee bug-free code**. Coverage only measures the percentage of lines executed during tests, but it fails to detect:
    * **Missing Requirements:** Features that were never implemented cannot be caught by coverage tools.
    * **Uncaught Edge Cases:** If a developer forgets to write a test for a specific edge case, the coverage might still be 100% while the bug remains.
    Therefore, code coverage is a useful metric but must be complemented by thoughtful test design.

## 2. Functional Test Cleanliness & Code Quality
Regarding the creation of a new functional test suite to verify the number of items in the product list:

* **Code Quality Issue: Code Duplication**
    If I simply copy-paste the setup procedures (instance variables like `serverPort`, `testBaseUrl`, and `@BeforeEach` setup) from `CreateProductFunctionalTest.java` to a new test class, I would be violating the **DRY (Don't Repeat Yourself)** principle. This creates unnecessary **boilerplate code** in every test file.

* **Maintenance Nightmare:**
    Duplicating setup code drastically reduces maintainability. For example, if the testing configuration changes (e.g., changing how the `baseUrl` is constructed or switching from a random port to a fixed port), I would have to manually update **every single test file**. This increases the risk of inconsistencies and human error.

* **Readability & Focus:**
    A clean test class should focus solely on the **test logic** (verifying the product count), not on **infrastructure setup**. Cluttering the class with repetitive setup code makes it harder for other developers to read and understand what is actually being tested.

* **Proposed Solution: Inheritance (Base Test Class)**
    To improve code cleanliness, I should implement a **Base Functional Test Class** (e.g., `BaseFunctionalTest`).
    * **Encapsulation:** This class would hold the common setup logic (`@LocalServerPort`, `@Value`, `setUp()` method).
    * **Inheritance:** Specific test classes (like `CreateProductFunctionalTest` and the new `CountProductFunctionalTest`) would simply **extend** this base class.
    * **Benefit:** This approach eliminates duplication, centralizes configuration management, and keeps the test classes clean and focused on their specific scenarios.
</details>

<details>
<summary><b>Module 2 - CI/CD & DevOps</b></summary>

# Reflection 1

## 1. Code Quality Issue(s)

During this module, I utilized **SonarCloud** as a static analysis tool to identify and resolve various code quality issues. Since I am using the free tier of SonarCloud, I had to merge my changes to the `main` branch first to access the detailed analysis reports, as I couldn't identify the specific issues beforehand, this allowed me to finally see and resolve them properly :D.

The following issues were identified and resolved:

* **Security Hotspot:** I addressed a warning regarding unverified dependencies in the project. My strategy was to generate a `verification-metadata.xml` file to verify the integrity and authenticity of external libraries. This ensures that all dependencies are safe and have not been tampered with before they are used in the application build.
* **Field Injection:** I refactored instances of field injection using `@Autowired` into **Constructor Injection** to improve the maintainability and testability of the code.
* **Unused Imports:** I removed several unused import statements to keep the codebase clean and organized.
* **Redundant Exception Declarations:** I removed unnecessary `throws Exception` clauses in the test files as they were not required for the specific test logic.
* **Missing Assertions:** I added appropriate assertions to test classes, such as `EshopApplicationTests`, to ensure that the tests effectively validate the application's behavior.
* **Dependency Management:** I reorganized the dependencies in the `build.gradle.kts` file by grouping them logically for better readability.

Beyond source code issues, I also encountered several technical difficulties during the deployment phase such as:

* **Workflow Resolution:** I faced an error where the GitHub Action for Koyeb could not be found. To fix this, I implemented a manual installation of the Koyeb CLI using the official shell script provided in the documentation (Source: https://www.koyeb.com/docs/build-and-deploy/cli/installation).

## 2. Has the current implementation met the definition of Continuous Integration and Continuous Deployment?

I believe the current implementation successfully meets the definitions of Continuous Integration (CI) and Continuous Deployment (CD). The CI aspect is fulfilled by the automated workflow that triggers test suites and static analysis via SonarCloud upon every push, ensuring code integrity before it is merged. The CD aspect is achieved through the automated deployment to Koyeb, which ensures that any successful push to the `main` branch is immediately reflected in the live production environment. Additionally, the use of `paths-ignore` for documentation files demonstrates an efficient pipeline that avoids unnecessary deployment cycles for non-code changes.

</details>
