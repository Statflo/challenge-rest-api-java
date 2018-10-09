/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * REST API Challenge - Request from Statflo
 *
 * Code written by: Daniel Valle
 * Date: 2018-10-06
 * Local: Toronto - Canada
 * Version: Api_index_v1.0
 * Development environment: Mac OSX - PHP PHP 7.2.10 (cli) (built: Sep 14 2018 07:07:08) ( NTS )
 * Database: MongoDB Version: 4.0.2 - 64bits - Non-relational
 *
 *
 * API ported to docker and connected through port 8081.
 *
 * MongoDB utilizes database "challenge", collections "users" and "logs". Both empty at start.
  Users can be added through the View App create button or through HTTP request applications.
 *
 *
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * Class Db - Database based on MongoDB
 *
 * Construct - Instantiate MongoDB class returns $this->db
 * Servers - mongodb://localhost:27017
 *         - Built in server - $_SERVER['HTTP_HOST'] to select url - instance of mongoDB
 * Exceptions triggers:
 *         - Authentication;
 *         - Connection;
 *         - Timeout;
 *
 * DEBUG - true (Show all users or logs)
 * LOGS  - true (Show logs)
 * return false if Exception is caught.

 /* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  * Class User - Database based on MongoDB
  *
  * Construct - Instantiate MongoDB class as $this->db
  * Servers - mongodb://localhost:27017
  *         - Built in server - $_SERVER['HTTP_HOST'] to select url - instance of mongoDB
  * Exceptions triggers:
  *         - Authentication;
  *         - Connection;
  *         - Timeout;
  *
  *
  *
  *
  * Available Methods:
  *
  /* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  * Class User - Access to Database based on MongoDB
  *
  *
  * DEBUG - true (Show all users or logs)
  * LOGS  - true (Show logs)
  * return false if Exception catch.
  *
  *
  * Available Methods:
  *         - getAllUsers()
  *                  - Entry parameters - None
  *                  - Returns an JSON with all documents of collection <users>
  *         - getOneUser()
  *                  - Entry parameters - $user, $role - User name or role or both
  *                  - Returns an JSON with one document of collection <users>
  *                    filtered by $user or $role.
  *         - createOneUser()
  *                  - Entry parameters - $name, $role, $option
  *                  - Returns an JSON with {'result' : true}
  *                            - creation successfully done.
  *                  - Returns an JSON with {'result' : false}
  *                            - creation not done - option = true - do not repeat user.
  *                  - Returns an JSON with {'result' : true}
  *                            - creation successfully done - option = false - could repeat user.
  *         - getOneUserById()
  *                  - Entry parameters - $id
  *                  - Returns an JSON with one document of collection <users>
  *                    Checks if $id has ObjectId format - 24 Hex digits.
  *                    Returns an empty array if $id not found
  *                    Returns none if wrong $id entered
  *         - logUsers()
  *                  - Entry parameters - $cursor and $action
  *                  - Returns nothing - non-blocking write to mongoDB
  *                    (Write Concern - 'w = 0' - Fire and forget)
  *                  $cursor - Error massage if $action = 'System'
  *                          - Access to MongoDB db for user creation if $action = "Creation"
  *                  Timestamp present in all log messages
  *                  Timezone set for 'America/Toronto'
  *
  * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  /* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  * Class ApiSerializer - Serializes all the data to be sent out as response to requests
  *
  *
  *
  *
  *
  *
  *
  * Available Methods:
  *         - index()
  *                  - Entry parameters - None
  *                  - Returns an JSON with all documents of collection <users>
  *         - show()
  *                  - Entry parameters - $user, $role - User name or role or both
  *                  - Returns an JSON with one document of collection <users>
  *                    filtered by $user or $role.
  *         - create()
  *                  - Entry parameters - $name, $role, $option
  *                  - Returns an JSON with {'result' : true}
  *                            - creation successfully done.
  *                  - Returns an JSON with {'result' : false}
  *                            - creation not done - option = true - do not repeat user.
  *                  - Returns an JSON with {'result' : true}
  *                            - creation successfully done - option = false - could repeat user.
  *         - error()
  *                  - Entry parameters - none
  *                  - Returns an JSON with error message
  *     
  * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
  /* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  * Class ApiController - Responsible for all calls to the Database data.
  *                       Process calls and instantiates the Serializer
  *
  *
  *
  *
  *
  *
  *
  * Available Methods:
  *         - index()
  *                  - Entry parameters - None
  *                  - Returns an JSON with all documents of collection <users>
  *         - show()
  *                  - Entry parameters - $user, $role - User name or role or both
  *                  - Returns an JSON with one document of collection <users>
  *                    filtered by $user or $role.
  *         - create()
  *                  - Entry parameters - $name, $role, $option
  *                  - Returns an JSON with {'result' : true}
  *                            - creation successfully done.
  *                  - Returns an JSON with {'result' : false}
  *                            - creation not done - option = true - do not repeat user.
  *                  - Returns an JSON with {'result' : true}
  *                            - creation successfully done - option = false - could repeat user.
  *          - error()
  *                  - Entry parameters - none
  *                  - Returns an JSON with error message
  *
  * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
