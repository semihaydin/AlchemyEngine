# AlchemyEngine

Automated testing framework for Nivo chart interactions using Selenium WebDriver and TestNG.

## Features

- Selenium WebDriver automation with remote grid support
- TestNG test framework
- Log4j2 logging
- Allure reporting with rich test documentation
- Docker containerization for consistent execution
- GitHub Actions CI/CD integration

## Running Tests

### Docker (Recommended)
```bash
# Single command to run tests headlessly with Selenium Grid
docker-compose up --build

# Run tests only (standalone container)
docker build -t AlchemyEngine
docker run --rm -v $(pwd)/target:/app/target AlchemyEngine

# View live Allure reports in browser
docker-compose up allure-server
# Then open http://localhost:5050
```

### Local Execution
```bash
# Run all tests
mvn clean test

# Run tests in headless mode
mvn clean test -Dheadless=true

# Run tests in parallel
mvn clean test -Pparallel

# Generate and view Allure report
mvn allure:report
mvn allure:serve
```

### GitHub Actions CI/CD

The project includes automated CI/CD with GitHub Actions:

1. **CI Workflow** (`.github/workflows/ci.yml`)
   - Runs on push/PR to master/main branches
   - Uses JDK 21 with Chrome in headless mode
   - Generates Allure results and uploads artifacts
   - Caches Maven dependencies for faster builds

2. **Pages Deployment** (`.github/workflows/pages.yml`)
   - Automatically deploys Allure reports to GitHub Pages
   - Triggered after CI workflow completion
   - Maintains test history across runs

### GitHub Repository 

The workflow will automatically publish reports to: `https://semihaydin.github.io/AlchemyEngine`

## Test Structure

- **Tests**: `src/test/java/tests/`
- **Page Objects**: `src/test/java/pages/`
- **Utilities**: `src/test/java/utils/`
- **Base Classes**: `src/test/java/base/`

## Configuration

### Docker Architecture
- **Selenium Grid**: Uses `seleniarm/standalone-chromium` for ARM64 compatibility
- **Test Container**: Maven-based container with Chromium and ChromeDriver
- **Remote WebDriver**: Tests connect to Selenium Grid at `http://selenium:4444/wd/hub`

### Logging
Log4j2 is configured with:
- Console output for immediate feedback
- File logging to `logs/AlchemyEngine.log`
- Rolling file logs in `logs/test-execution.log`

### Allure Reports
- **Annotations**: Tests include Epic, Feature, Story, and Severity annotations
- **Steps**: Detailed test execution tracking with Allure.step()
- **Attachments**: Captures test data (colors, orientations) as report attachments