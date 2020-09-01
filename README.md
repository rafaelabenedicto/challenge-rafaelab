Please see below some comments regarding this repository:

In API_TESTS path you can find challenge related to API (get and post).
To do this challenge was used Java and RestAssured.

In UI_TESTS path you can find challenge related to Test Cases (TC) and automation.
To do this challenge was used Java, Cucumber and Selenium.
Not all scenarios are implemented. The ones pending implementation are marked with @ignore tag.

Some observations related to this e-commerce:

In Register page, depending on the country selected, the State combobox should be populated based on contry selected.
The same behavior for populate city combobox. Wait for State selection and then load corresponding cities.
In addition, depending on country selected the postal code mask required should change also, because not all countries 
has the same postal code mask.

Then, if I was a tester of this application, this should be a detail that I would like to check with PO.


