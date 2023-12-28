Feature: Validating Ecom Test case

Scenario: Validating login, placing the order and deleting it

Given login with valid details using "login" as the resource and "Post" call
When creating the product using "Creating the product" and "Post" call
Then verify product id using "View" as resource and "Get" call
And create order using "create" and "Post" call
Then delete order using "deleteProduct" and "Delete" call
