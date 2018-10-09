
<?php
/* REST API Challenge - Request from Statflo
*
* Code written by: Daniel Valle
* Date: 2018-10-02
* Local: Toronto - Canada
* Version: Challenge_API_access_v1.0
* Development environment: Mac Osx - PHP PHP 7.2.10 (cli) (built: Sep 14 2018 07:07:08) ( NTS )
* HTTP request: Curl
*
*
* Server built-in running at 'localhost' Port: 3030
* API - Server built-in running at 'localhost' Port: 8080
* MongoDb Server running at Port: 27017
/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*
* Variables: $resp   -> response from the Http requests
*            $obj    -> JSON returned from the API request
*            $method -> Selected method to be used for the Http request
*            $post   -> Indicates the message must be sent as a post to the server
*            $msg    -> Returns massages in the Message field to the operator
*            $_GET, $_POST and $_SERVER used to capture operator entry
/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
  require 'config.php';
  // Initialization area
  $resp = false;
  $obj = '';
  $method = "none";
  $post = false;
  $msg = '';
  $log_active = false;

  /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
  //   Beginning of data entry

  // Checks if submit button was pushed and which one of it.
  if ($_SERVER['REQUEST_METHOD'] == 'GET' && isset($_GET['search'])) {
    $post = false;
    $ok = true;


    // GET through Search button was entered
    // Capture inputs from the form
    if (isset($_GET['name']) && $_GET['name'] !== ''){
      $name = $_GET['name'];
      $name = str_replace(' ', '%20', $name);
    } else {
      $name = '';
    }

    if (isset($_GET['role']) && $_GET['role'] !== ''){
      $role = $_GET['role'];
      $role = str_replace(' ', '%20', $role);
    } else {
      $role = '';
    }

    if (isset($_GET['id'])){
      $id = $_GET['id'];
    } else {
      $id = '';
    }
    $method = 'get';

  } elseif ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['create'])){

      // POST through Submit button was entered
      // Capture inputs from the form and validates the mandatory presence of name and role
      // $ok = false if any entry was missed
      $ok = true;
      if (isset($_POST['name']) && $_POST['name'] !== '' && isset($_POST['create'])){
        $name = $_POST['name'];
        $name = str_replace(' ', '%20', $name);
      } else {
        $name = '';
        $ok = false;
      }
      if (isset($_POST['role']) && $_POST['role'] !== '' && isset($_POST['create'])){
        $role = $_POST['role'];
        $role = str_replace(' ', '%20', $role);
      } else {
        $role = '';
        $ok = false;
      }
      $msg = '';
      $method = 'post';
    } else {
      $msg = '';
      $ok = false;
      //header("Location, #");
    }

    /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    //   Beginning entry process for create the requests

    if ($method === 'get' && isset($_GET['search'])){
      if (isset($_GET['logs']) && $_GET['logs'] === 'Logs'){
        $log_active = true;
        $get = URL_API . ':' . PORT . '/' . COLLECTION . '/logs';     //'http://localhost:8080/users';
        $resp = request($get,$ok);
      } else {
        if ($name === '' && $role === '' && $id === ''){
          $get = URL_API . ':' . PORT . '/' . COLLECTION;     //'http://localhost:8080/users';
          $resp = request($get,$ok);
        } else {
          if (isset($id) && $id !== ''){
            $get = URL_API . ':' . PORT . '/' . COLLECTION . '/' . $id;
            $resp = request($get,$ok);
          } else {
            $get = URL_API . ':' . PORT . '/' . COLLECTION . '/?' . 'name='. $name . '&role=' . $role;
            $resp = request($get,$ok);
          }
        }
      }
    } else {
      if ($method !== 'none'){
        // method post - request message creation and calls
        $get = URL_API . ':' . PORT . '/' . COLLECTION . '/?' . 'name='. $name . '&role=' . $role;
        $post = true;
        $resp = request($get,$ok,$post);
      }
    }

    // Checks the returning message from the http requests
    // array  - list of user(s)
    // true   - User Creation
    // string - Message to the message field
    if (is_array($resp) !== false){
      if (sizeof($resp) != 0){
        $obj = $resp;
      } else {
        $msg = "404 - Not Found";
      }
    } else {
      if ($resp === true){
        $msg = "User successfully created!";
      } else {
        if ($resp === null){
          $msg = "Information did not return from server!";
        } else {
        $msg = $resp;
      }
    }
  }
/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*
* Function request
* Parameters:
*       $get  - message to send to the server (post or get)
*       $ok   - boolean that represents the state of the Request
*               true - request allowed
*               false - request denied by invalid entry
*       $post - default = false -> method get
*                         true  -> method post
*
* function:     Sends http requests to the api_rest_challenge API
*               requests: get an user data through name or role
*                         creates a new user - name and role must be passed
*
* returns:      true   -> user successfully created
*               array  -> list of selected user(s)
*               string -> Error messages regarding the request
* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/

  function request($get,$ok,$post = false){
    if ($ok){
      $ch = curl_init();
      // Common setup of curl - defaul = method get
      curl_setopt($ch, CURLOPT_URL, $get);
      curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
      curl_setopt($ch, CURLOPT_HEADER,0);
      // method post selected to create a new user
      if ($post){
        curl_setopt($ch,CURLOPT_POST, 2);
        curl_setopt($ch,CURLOPT_POSTFIELDS, $get);
      }
      $output = curl_exec($ch);
      // Checks if request was successfully done
      if ($output === false){
        $msg = "Invalid Http request - " . curl_error($ch);
        return $msg;
      }
      // close connection and returns true - create user success / array list
      curl_close($ch);
      if ($post){
        $msg = true;
        return $msg;
      } else {
        $obj= json_decode($output, true);
        return $obj;
      }
      // missing parameters from the entry form
    } else {
      $msg = 'Invalid entry - Missing user information!';
      return $msg;
    }
  }

// end of php code and beginning of the entry and message forms
//* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
?>


<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <link rel= "stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.0/normalize.min.css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css" />
    <title>REST API Challenge</title>
  </head>
  <body>

    <div class="row mt-5">
      <form class="search_form col-11 col-lg-4 mx-auto border" action="#" method="get">
        <div class="form-group">
          <h1 class="text-center title">Search</h1>
        </div>
        <div class="form-group" >
          <input type="text" class="form-control" aria-describedby="Name" placeholder="Name" name="name">
        </div>
        <div class="form-group">
          <input type="text" class="form-control" aria-describedby="Role" placeholder="Role" name="role">
        </div>
        <div class="form-group">
          <input type="text" class="form-control" aria-describedby="Id" placeholder="ID" name="id">
        </div>
        <div class="form-group">
          <input type="checkbox" name="logs" value="Logs">&nbsp &nbsp Show Logs<br>
        </div>
        <div class="form-group text-center">
          <button type="submit text-center" name="search" class="btn btn-primary">Search</button>
        </div>
      </form>
    </div>

      <div class="row mt-5">
      <form class="create_form col-11 col-lg-4 mx-auto border" action="#" method="post">
        <div class="form-group">
          <h1 class="text-center">Create</h1>
        </div>
        <div class="form-group">
          <input type="text" class="form-control" aria-describedby="Name" placeholder="Name" name="name">
        </div>
        <div class="form-group">
          <input type="text" class="form-control" aria-describedby="Role" placeholder="Role" name="role">
        </div>
        <div class="form-group text-center">
          <button type="submit text-center" name="create" class="btn btn-primary">Submit</button>
        </div>
        <div class="form-group text-center">
         <label class"response">Message: <?=$msg?></label>
       </div>
      </form>
    </div>

    <div class="row justify-content-center mt-5 mb-5">
      <div class="container response border p-5">
        <h1 class="text-center mx-auto">Response</h1>
        <textarea class="test row form-control col-12 mx-auto" rows="6" id="comment">
          <?php
            if ($obj != null && sizeof($obj) != 0) {
              for ($itens = 0; $itens < sizeof($obj); $itens++){
                if (!$log_active){
                  echo "\n";
                  echo "Id: "   . $obj[$itens]['id']   ."\n";
                  echo "Name: " . $obj[$itens]['name'] ."\n";
                  echo "Role: " . $obj[$itens]['role'] ."\n";
                  echo "\n";
                } else {
                  print_r($obj);
                  $log_active = false;
                }
              }
            } else {
              echo $obj;
            }
            ?>
          </textarea>
        </div>
      </div>


    <!-- JavaScript -->

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

  </body>
</html>
