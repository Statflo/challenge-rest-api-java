### Implementation 

- I decided to split the design into a domain, dao (database), service, and controller layer
###### Domain 
  There are two models which I created. One is the User model, and a UserRequest model. The reason I implemented a UserRequest model 
  is because the user id is randomly generated on creation. This is based off the specifications laid out in the challenge
  (the request body only contains role, and name). Therefore I created a user request model just for creation. 
###### Dao
  I used JPA, and Hibernate in order to automatically create the User table. I use the entity manager to interact with the database,
  for CRUD operations. 
###### Service
  Pretty simple service which calls the dao methods, and then sends it to the controller. 
###### Controller
  This is where all the endpoints are being implemented, with some basic error checking. For each endpoint I return a
  Response Entity so that I can add in the HttpStatus codes. 
  
### Notes 
  - I decided to focus on the Circuit Breaker focus however, I was having issues with Hystrix and its dependency. I understand the pattern, and my goal was to create a fall back for one of the endpoints. For example, timeout and then recall the endpoint. 
  
  - I also commented out the test cases, even though I have manually tested all the endpoints. The reason its commented out is because the user id is unknown so I would test the create differently (after creation save the id, and then use it for other test cases). I tried to focus more on the API design so I didn't change the test cases. 
  
  - I have done logging previously but I haven't really played too much with NoSQL databases, so that's something I will try to work on in my own time. 
