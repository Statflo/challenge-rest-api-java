<?php


/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * REST API Challenge - Request from Statflo
 *
 * Code written by: Daniel Valle
 * Date: 2018-10-06
 * Local: Toronto - Canada
 * Version: Api_index_v1.0
 * Development environment: Mac Osx - PHP PHP 7.2.10 (cli) (built: Sep 14 2018 07:07:08) ( NTS )
 * Database: MongoDB Version: 4.0.2 - 64bits - Non-relational
 *
 *
 *
 *
 *
 *
 *
 *
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */


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
  *
  * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

 // Definition area

 define('DEBUG', true);  // true - Shows all documents of users or all logs when LOGS are also true

 // end of definition area
  use Code\Model\User;

  require   "bootstrap.php";
  require   "routes.php";
  //require_once 'src/Model/DB.php';

  $controller = "";
  $action = "";
  $param = "";
  // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // Get url path for controller selection
  // Read url and clear all non necessary information
  $url = substr($_SERVER['REQUEST_URI'], 1);
  $url = explode('/', $url);

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // Identify the first part of the url between '/'

  if (isset($url[0]) && $url[0] !== ""){
    $controller = $url[0];
    $method = 'none';
    if ($_SERVER['REQUEST_METHOD'] == 'GET'){
      $method = 'get';
    } else if ($_SERVER['REQUEST_METHOD'] == 'POST'){
      $method = 'post';
    } else {
      $controller = $default_controller;
    }
  } else {
    $controller = $default_controller;
  }

  // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // Search for the controller in the routes table

  $search = $controller;
  if (array_key_exists($search, $routes)) {
    $controller = $routes[$search];
  } else {
    $controller = $default_controller;
  }



  // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // Defines the Controller action to be executed
  // $url[1] - ?role=nnnnnn&name=xxxxxxx or ?role=nnnnnn or ?name=xxxxxx or ? or 12345678 or whatever...

  if (isset($url[1])) {
    if ($url[1] === '') {
      $search = '';
    } else {
      $action = explode('?', $url[1]);
      // Check if ? exists
      if (isset($action[1]) && $action[0] === '') {
        // Check if request comes from POST or GET
        if ($method !== 'none') {
          if ($method == 'post') {
            $controller = $controller . '_create';
          } else {
            $controller = $controller . '_show';
          }
        }
        // All search parameters found to be searching the DB
        $param = $action[1];
      } else {
        $param = $action[0];
        $controller = $controller . '_show';
      }
      $search = $controller;
    }
    $search = $controller;
  } else {
    $search = $controller;
  }

  // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // Search for action in the routes/action table

  if (array_key_exists($search, $actions)) {
    $action = $actions[$search];
  } else {
    $action = $default_action;
  }

  // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // Search for action in the routes/action table - set controller final name

  $temp = explode('_', $controller);
  isset($temp[0]) == (true ?  $controller = $temp[0] : $controller = $default_controller);


  // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // Get all the parameters from the url
  $parameters = [];
  $temp = explode('&', $param);
  for ($i = 0; $i < sizeof($temp); $i++) {
    if (isset($temp[$i]) && ($temp[$i] != '')) {
      $temp1 = explode('=',$temp[$i]);
      if (isset($temp1[1])) {
        $key[$i]   = $temp1[0];
        $value[$i] = $temp1[1];
      } else {
        break;
      }
      array_push($parameters, [$key[$i] => $value[$i]]);

    } else {
      if (DEBUG) {
        $action = $actions['Api_index'];
      } else {
        $action = $default_action;
      }
      break;
    }
  }
  if (sizeof($parameters) == 0) {
    $parameters = ['id' => $param];
    if ($parameters['id'] == 'logs'){
      $action = 'indexLogs';
    }
  }
  // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // Instantiate the Class Controller selected , action selected and passing parameters as $param.
  $controller = "Code\Controller\\" . $controller . "Controller";
  $resp = new $controller;
  $db = new User;
  $response = $resp->$action($parameters, $db);
  print_r($response);
?>
