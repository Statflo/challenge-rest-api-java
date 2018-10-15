<?php

/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
* REST API Challenge - Request from Statflo
*
* Code written by: Daniel Valle
* Date: 2018-10-06
* Local: Toronto - Canada
* Version: Db_class_v1.0
* Development environment: Mac Osx - PHP PHP 7.2.10 (cli) (built: Sep 14 2018 07:07:08) ( NTS )
* Database: MongoDB Version: 4.0.2 - 64bits - Non-relational
*
*
* Challenge DB request: Logs would be stored in a non-relational database.
*
*
*
*
*
* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/


/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
* return false if Exception catched.
*
*/

namespace Code\Model;

use MongoDB\Client as Mongo;

// Definition area

//define('DEBUG', true);  // true - Shows all documents of users or all logs when LOGS are also true
define('LOGS', true);   // true - Shows Logs if DEBUG is true
define('OBJECT_ID_SIZE', 24); // Size of mongoDB ObjectId

// end of definition area


/* Class code area - Instantiate Class MongoDB
* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/

class Db
{
  public function __construct()
  {
    $this->dbname = "challenge";
    $this->collection_users = "users";
    $this->collection_logs = "logs";
    $this->port = "27017";
    $this->collection = 'users';
    $this->db_type = "mongodb";
    //$url = explode(':',$_SERVER['HTTP_HOST'])[0]; // It comes from the local run
    $url = 'mongo'; // It comes from docker composer
    $this->server = $url;
    $this->error = '';

    date_default_timezone_set('America/Toronto');
    $this->today = date("F j, Y, g:i a");
    try {
      $this->db = new Mongo('mongodb://mongo:27017');
      return $this->db;
    } catch (MongoDB\Driver\Exception\ConnectionException $e) {
      $this->$error = "Exception:" . $e->getMessage();
    } catch (MongoDB\Driver\Exception\ConnectionTimeoutException $e) {
      $this->$error = "Exception:" . $e->getMessage();
    } finally {
      if(DEBUG){
        echo $this->error;
      }
      return false;
    }
  }

}

/* End of code area
* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/


?>
