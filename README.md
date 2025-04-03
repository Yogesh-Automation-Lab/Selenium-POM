**Web Automation:** Uses Selenium WebDriver to interact with the Erail application, including entering station names and retrieving autocomplete suggestions. <br>
**Page Object Model (POM):** Implements the Page Object Model design pattern to separate web elements and their interactions from the test logic, enhancing code maintainability and readability.<br>
**Excel Integration:** Uses Apache POI to read and write data to an Excel file, facilitating the comparison of actual and expected values. <br>
**Dynamic Waiting:** Replaces hardcoded sleep statements with WebDriverWait to ensure elements are visible before interacting with them, improving test reliability. <br>
**TestNG Framework:** Leverages TestNG for test execution, setup, and teardown, providing a structured and modular approach to testing. <br>
**Project Structure src/main/java:** Contains the Page Object class (ErailPage) and utility class (ExcelUtils). src/test/java: Contains the TestNG test class (ErailTest). src/test/resources: Contains the Excel file (TestData.xlsx) used for storing test data. <br>
