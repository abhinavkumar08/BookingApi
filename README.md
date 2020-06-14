# BOOKING API
# A simple Springboot project to create a hotel booking.
-----------------------------------------------------------------------

Steps to run the Project : 

1. Extract the zip folder to your local machine.

2. Import the project as Maven Project to Eclipse or Intellij, Make sure the IDE is using JAVA 8.

3. Download Mysql Server 8.x on your local machine and make sure that it is running.

4. Connect to mysql client and run the below commands to create a new Database and user :

	create database hotel_management;
	create user 'user'@'%' identified by 'Password';
	grant all on hotel_management.* to 'user'@'%';

5. After the database and user is created, go to application.properties in src/main/resources folder in the imported project and paste the below configuration:
	
	spring.datasource.url=jdbc:mysql://localhost:3306/hotel_management
	spring.datasource.username=user
	spring.datasource.password=Password
	spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
	spring.jpa.database-platform = org.hibernate.dialect.MySQL5Dialect
	spring.jpa.generate-ddl=true
	spring.jpa.hibernate.ddl-auto = create
	spring.jpa.show-sql=true
	spring.jpa.properties.hibernate.format_sql=true
	spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect


6. Build the project and Navigate to class BookingApiApplication.java in the io.bookingapi package, Right click and run as Java Application.

7. After you run the project as Java Application, it does the following things :
	- Creates All the necessary Tables and the relationship between them.
	- Start the integrated Tomcat Server
	- All the APIs are exposed and we are ready to make booking.

9. After the server is started, checkout the below URL to see the API docs :
	- http://localhost:8080/swagger-ui.html#/


10. The below sequence needs to be followed to make a booking request : 

	-Create a User using the helper user-controller API.
	-Register a Hotel using the helper hotel-controller API.
	-Register a room with hotel using the helper hotel-controller API.

    The above APIs help to load the data to DB so that we can proceed with the booking request.For more details checkout the API Docs URL in step 9.

11. Make a call to reservation-controller API to make a booking using POSTMAN. The request body and expected response can be checkd at the URL location mentioned in step 9.





ASSUMPTIONS : 

1.The application does support multi threading but it can be enhanced further by creating Synchronized Block.
2. The availability check is done at the code side, but in real time it better to do it on DB side using stored procedure to prevent out of heap memory issues.
3. The Payload received by the booking endpoint assumes that the list of rooms to be booked are valid.
4. If there are multiple rooms and any one room is not available for the check in and chcekout date then the whole booking fails.
















