#Scenario: 
##Task Management API
You are hired by a company to build a Task Management API that allows users to create, retrieve, update, delete, and mark tasks as completed.

Your solution should follow RESTful API principles and use Spring Boot.

###Requirements

<ol>
<li>Create a Task: A user should be able to create a new task with the following fields:</li>
<ul>
<li>id (auto-generated)</li>
<li>title (mandatory)</li>
<li>description (optional)</li>
<li>dueDate (optional)</li>
<li>completed (default: false)</li>
</ul>

<li>Retrieve Tasks:</li>

<ul>
<li>Get a list of all tasks.</li>
<li>Get a task by ID.</li>
</ul>

<li>Update a Task:</li>
<ul>
<li>A user can update any field of a task.</li>
</ul>

<li>Delete a Task:</li>
<ul>


<li>A user can delete a task by ID.</li>
</ul>

<li>Mark a Task as Completed:</li>
<ul>
<li>A user can mark a task as completed.</li>
</ul>

</ol>

   


###Bonus Points
<ul>
<li>
Implement pagination when retrieving tasks.
</li>
<li>
Add a due date validation (e.g., due date should not be in the past).
</li>
<li>
Implement exception handling for missing tasks and validation errors.
</li>
<li>
Use an in-memory database (H2/PostgreSQL/MySQL).
</li>
<li>
Implement pagination when retrieving tasks.
</li>
</ul>

***

##Deliverables
1. A Spring Boot REST API (Java 17+)
2. Proper unit tests for the service layer (JUnit 5, Mockito)
3. Swagger/OpenAPI documentation (optional)
4. A Postman collection for API testing (optional)
