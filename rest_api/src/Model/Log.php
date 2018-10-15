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
* Class Log - returns http requests to Log Database based on MongoDB
*
* Construct - returns $this->log
* Servers - mongodb://localhost:27017
*         - Built in server - $_SERVER['HTTP_HOST'] to select url - instance of mongoDB
*
*
*
* LOGS  - true (Show logs)
* return false if Exception catched.
*
*/

namespace Code\Model;

// Definition area


//define('LOGS', true);   // true - Shows Logs

// end of definition area


/** +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/

class Log
{
  public function __construct($action)
  {
    $this->log = [];
    $this->action = $action;

    date_default_timezone_set('America/Toronto');
    array_push($this->log,
               ['remote'   => $_SERVER['REMOTE_ADDR']],
               ['script'   => $_SERVER['SCRIPT_NAME']],
               ['protocol' => $_SERVER['SERVER_PROTOCOL']],
               ['time'     => date('Y-m-d h:i:s',$_SERVER['REQUEST_TIME'])],
               ['host'     => $_SERVER['HTTP_HOST'] . $_SERVER['PHP_SELF']],
               ['method'   => $_SERVER['REQUEST_METHOD']],
               ['protoclo' => $_SERVER['SERVER_PROTOCOL']],
               ['query'    => $this->action],
               ['host'     => $_SERVER['HTTP_HOST']]
             );
      if (LOGS == true){
        return $this->log;
      }
    }

  }


/* End of code area
* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/


?>
