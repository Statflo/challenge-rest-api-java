##### Implementation 

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


###### Notes 
