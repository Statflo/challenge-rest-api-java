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
  namespace Code\Controller;

  use Code\Model\User;
  use Code\Model\Db;

  require_once 'src/Model/User.php';

  class ApiController
  {
    public function index($param, $db)
    {
      $cursor = $db->getAllUsers();
      $controller = "Code\Serializer\\" . 'Api' . "Serializer";
      $resp = new $controller;
      $action = 'index';
      $response = $resp->$action($cursor);
    }

    public function indexLogs($param, $db)
    {
      $cursor = $db->getAllLogs();
      $controller = "Code\Serializer\\" . 'Api' . "Serializer";
      $resp = new $controller;
      $action = 'indexLogs';
      $response = $resp->$action($cursor);
    }



    public function show($param, $db)
    {
      //var_dump($param);
      $name = '';
      $role = '';
      $id = '';
      for ($i = 0; $i < sizeof($param); $i++) {
        if (isset($param[$i]['name'])) {
          $name = str_replace('%20', ' ', $param[$i]['name']);
        }
        if (isset($param[$i]['role'])) {
          $role = str_replace('%20', ' ', $param[$i]['role']);
        }
      }
      if ($name == '' && $role == '') {
        if (isset($param['id'])) {
          $id = $param;
          $cursor = $db->getOneUserById($id);
        }
      } else {
        $cursor = $db->getOneUser($name, $role);
      }
      //var_dump($cursor);
      $serializer = "Code\Serializer\\" . 'Api' . "Serializer";
      $resp = new $serializer;
      $action = 'show';
      $response = $resp->$action($cursor);
    }

    public function create($param, $db)
    {
      $name = '';
      $role = '';
      $id = '';
      for ($i = 0; $i < sizeof($param); $i++) {
        if (isset($param[$i]['name'])) {
          $name = str_replace('%20', ' ', $param[$i]['name']);
        }
        if (isset($param[$i]['role'])) {
          $role = str_replace('%20', ' ', $param[$i]['role']);
        }
      }
      $option = false;
      if ($name != '' && $role != ''){
        $cursor = $db->createOneUser($name, $role);
      }
      $serializer = "Code\Serializer\\" . 'Api' . "Serializer";
      $resp = new $serializer;
      $action = 'create';
      $response = $resp->$action($cursor);
    }

    public function error()
    {
      $serializer = "Code\Serializer\\" . 'Api' . "Serializer";
      $resp = new $serializer;
      $action = 'error';
      $response = $resp->$action();
    }
  }

?>
