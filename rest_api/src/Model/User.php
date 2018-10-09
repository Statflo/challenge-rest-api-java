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
*         - getAllLogs()
*                  - Entry parameters - none
*                  - Returns list of logs - non-blocking write to mongoDB
*                    (Write Concern - 'w = 0' - Fire and forget)
*                  Timestamp present in all log messages
*                  Timezone set for 'America/Toronto'
*
* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/
  namespace Code\Model;

  use Code\Model\Db;

  // Definition area

  //define('DEBUG', true);  // true - Shows all documents of users or all logs when LOGS are also true
  define('UID_FORMAT', '%04x%04x-%04x-%04x-%04x-%04x%04x%04x');
  // end of definition area


  class User extends Db
  {

    /* Function getAllUsers() code area
    * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    */

    public function getAllUsers()
    {
      $error = '';
      $log = '';
      $dbname = $this->dbname;
      $collection = $this->collection_users;
      try {
        $cursor = $this->db->$dbname->$collection->find();
        $log = new Log("index - All users");
      } catch (MongoDB\Driver\Exception\ConnectionException $e) {
          $error = array('message' => $e->getMessage(), 'created_at' => $this->today);
      } catch (MongoDB\Driver\Exception\ConnectionTimeoutException $e) {
          $error = array('message' => $e->getMessage(), 'created_at' => $this->today);
      } finally {
          $collection = $this->collection_logs;
          $insertResult = $this->db->$dbname->$collection->insertOne($log, array("w" => 0));
      }
      return $cursor;
    }

    /* Function getOneUser code area
    * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    */

    public function getOneUser($name, $role)
    {
      $error = '';
      $dbname = $this->dbname;
      $collection = $this->collection_users;
      $user_query = ['$or' => [['name' => $name],['role' => $role]]];
      try {
        $cursor = $this->db->$dbname->$collection->find($user_query);
        $log = new Log("show - One user");
      } catch (MongoDB\Driver\Exception\InvalidArgumentException $e) {
        $error = "Exception:" . $e->getMessage();
        $cursor = false;
      } catch (MongoDB\Driver\Exception\ConnectionException $e) {
          $error = array('message' => $e->getMessage(), 'created_at' => $this->today);
      } catch (MongoDB\Driver\Exception\ConnectionTimeoutException $e) {
          $error = array('message' => $e->getMessage(), 'created_at' => $this->today);
      } finally {
        $collection = $this->collection_logs;
        $insertResult = $this->db->$dbname->$collection->insertOne($log, array("w" => 0));
      }
      return $cursor;
    }

    /* Function getOneUserById() code area
    * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    */

    public function getOneUserById($id)
    {
      $error = '';
      $dbname = $this->dbname;
      $collection = $this->collection_users;
      try {
        $user_query = $id;
        $cursor = $this->db->$dbname->$collection->find($user_query);
        $log = new Log("show - One user by id");
      } catch (MongoDB\Driver\Exception\InvalidArgumentException $e) {
        $error = "Exception:" . $e->getMessage();
        $cursor = false;
      } catch (MongoDB\Driver\Exception\ConnectionException $e) {
          $error = array('message' => $e->getMessage(), 'created_at' => $this->today);
      } catch (MongoDB\Driver\Exception\ConnectionTimeoutException $e) {
          $error = array('message' => $e->getMessage(), 'created_at' => $this->today);
      } finally {
        $collection = $this->collection_logs;
        $insertResult = $this->db->$dbname->$collection->insertOne($log, array("w" => 0));
      }

      return $cursor;
    }

    /* Function createOneUser() code area
    * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    */

    public function createOneUser($name, $role, $option = true)
    {
      $dbname = $this->dbname;
      $collection = $this->collection_users;
      $opt = array("upsert" => $option);
      $user_update = array("id" => $this->generate_uid(), "name" => $name, "role" => $role, $opt);
      $collection = $this->db->$dbname->$collection;
      try {
        $error = '';
        $updateResult = $collection->updateOne(['name' => $name], ['$set' => $user_update], $opt, array("w" => "1"));
        $log = new Log("create - One user");
        return true;
      } catch (MongoDB\Driver\Exception\WriteException $e) {
        $error = array('message' => $e->getMessage(), 'created_at' => $this->today);
      } catch (MongoDB\Driver\Exception\ConnectionException $e) {
          $error = array('message' => $e->getMessage(), 'created_at' => $this->today);
      } catch (MongoDB\Driver\Exception\ConnectionTimeoutException $e) {
          $error = array('message' => $e->getMessage(), 'created_at' => $this->today);
      } finally {
        $collection = $this->collection_logs;
        $insertResult = $this->db->$dbname->$collection->insertOne($log, array("w" => 0));
      }
    }





    /* Function getAllLogs() code area
    * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    */

    public function getAllLogs()
    {
      $error = '';
      $dbname = $this->dbname;
      $collection = $this->collection_logs;
      try {
        $cursor = $this->db->$dbname->$collection->find();
        $log = new Log("index - All logs");
      } catch (MongoDB\Driver\Exception\ConnectionException $e) {
          $error = array('message' => $e->getMessage(), 'created_at' => $this->today);
      } catch (MongoDB\Driver\Exception\ConnectionTimeoutException $e) {
          $error = array('message' => $e->getMessage(), 'created_at' => $this->today);
      } finally {
        $collection = $this->collection_logs;
        $insertResult = $this->db->$dbname->$collection->insertOne($log, array("w" => 0));
      }
      return $cursor;
    }

    function generate_uid()
    {
      return sprintf( UID_FORMAT,
          mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff ),
          // 16 bits for "time_mid"
          mt_rand( 0, 0xffff ),
          // 16 bits for "time_hi_and_version",
          // four most significant bits holds version number 4
          mt_rand( 0, 0x0fff ) | 0x4000,
          // 16 bits, 8 bits for "clk_seq_hi_res",
          // 8 bits for "clk_seq_low",
          // two most significant bits holds zero and one for variant DCE1.1
          mt_rand( 0, 0x3fff ) | 0x8000,
          // 48 bits for "node"
          mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff )
      );
    }

  }

?>
