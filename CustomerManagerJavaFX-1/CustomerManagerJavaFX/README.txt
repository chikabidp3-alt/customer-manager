CUSTOMER MANAGER - JAVAFX
==========================

ICT261 Classroom Lab

FEATURES
--------
1. Name field.
2. Province ComboBox.
3. Customer class.
4. ObservableList<Customer>.
5. TableView with Name and Province columns.
6. Input validation.
7. Confirmation before deletion.
8. Keyboard access.
9. Add customer with Enter.
10. Delete selected customer with Delete.
11. Clear form with Escape.
12. Alt+N focuses the Name field.
13. Alt+P focuses the Province list.
14. Alt+A adds a customer.
15. Alt+C clears the form.
16. Alt+D deletes the selected customer.

PROJECT STRUCTURE
-----------------
CustomerManager/
|
+-- pom.xml
+-- README.txt
|
+-- src/
    +-- main/
        +-- java/
            +-- com/
                +-- chikabi/
                    +-- customermanager/
                        +-- Customer.java
                        +-- CustomerManagerApp.java

HOW TO RUN
----------
Recommended: IntelliJ IDEA with Maven and JDK 17.

1. Install JDK 17 or later.
2. Open IntelliJ IDEA.
3. Select "Open" and choose the CustomerManager folder.
4. IntelliJ should detect pom.xml as a Maven project.
5. Wait for Maven dependencies to download.
6. Open CustomerManagerApp.java.
7. Run CustomerManagerApp.

OR FROM TERMINAL
----------------
Open a terminal inside the project folder and run:

mvn clean javafx:run

If Maven is not installed, install Maven first or run it through IntelliJ's
Maven panel.

TESTING THE LAB
---------------
Test 1: Add a valid customer
- Type: Chikabi Phiri
- Select: Lusaka
- Press Enter or click Add Customer.
- The customer should appear in the table.

Test 2: Invalid name
- Leave Name empty.
- Click Add Customer.
- A warning should appear.

Test 3: Invalid province
- Enter a name.
- Do not select a province.
- Click Add Customer.
- A warning should appear.

Test 4: Delete
- Select a row.
- Click Delete Selected.
- Confirm the deletion.

Test 5: Keyboard
- Alt+N = Name
- Alt+P = Province
- Alt+A = Add Customer
- Alt+C = Clear
- Alt+D = Delete Selected
- Enter in the province list = Add
- Delete = Delete selected row
- Escape = Clear form
