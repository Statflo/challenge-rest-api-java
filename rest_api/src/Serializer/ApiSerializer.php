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
namespace Code\Serializer;

use Code\Model\Db;

class ApiSerializer
{


  public function index($cursor)
  {
    $out = [];
    if ($cursor !== false) {
      foreach ($cursor as $result => $row) {
        if (isset($row->name)) {
          isset($row->id) === (true ? $identifier = $row->id : $identifier = '');
          //$identifier = explode("'", (string) new \MongoDB\BSON\ObjectId("$row->_id"))[0];
          array_push($out,['id' => $identifier,'name' => $row->name,'role' => $row->role]);
        }
      }
    }
    echo json_encode(($out));
  }

  public function indexLogs($cursor)
  {
    $out = [];
    if ($cursor !== false) {
      foreach ($cursor as $result => $row) {
        if (isset($row->action)) {
          array_push($out,$row);
        }
      }
    }
    echo json_encode(($out));
  }

  public function show($cursor)
  {
    $out = [];
    if ($cursor !== false) {
      foreach ($cursor as $result => $row) {
        if (isset($row->name)) {
          isset($row->id) === (true ? $identifier = $row->id : $identifier = '');
          //$identifier = explode("'", (string) new \MongoDB\BSON\ObjectId("$row->_id"))[0];
          array_push($out,['id' => $identifier,'name' => $row->name,'role' => $row->role]);
        }
      }
    }
    echo json_encode(($out));
  }

  public function create($cursor)
  {
    if (!$cursor) {
      $out = [];
      array_push($out,['Error code' => '400 Bad Request','Field'      => 'Create user','Message'    => 'Invalid entry!']);
      echo json_encode(($out));
    } else {
      $out = [];
      array_push($out,['Success code' => '200 OK','Field'      => 'Create user','Message'    => 'User created!']);
      echo json_encode(($out));
    }
  }


  public function error()
  {
    $out = [];
    array_push($out,['Error code' => '400 Bad Request','Field'      => 'name,role or id','Message'    => 'Invalid entry!']);
    echo json_encode(($out));
  }

}
?>
