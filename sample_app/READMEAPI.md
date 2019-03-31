# REST API Challenge

```
GET api/user-details?role=bar
```
The response should look like this.

```JSON
[
  {
      "id": 116,
      "name": "java beans",
      "role": "bar",
      "created_at": "2019-03-31 01:50:25",
      "updated_at": "2019-03-31 01:52:26"
  }
]
```
The second API allows the client to create a user object with a request like this.

```
POST api/user-details/
```
```JSON
{
  "name": "cookie bars",
  "role": "bar"
}
```

The third API allows the client to retrieve a user object given its ID

```
GET api/user-details/116
```
```JSON
 {
      "id": 116,
      "name": "java beans",
      "role": "bar",
      "created_at": "2019-03-31 01:50:25",
      "updated_at": "2019-03-31 01:52:26"
 }
```

###### GET API - User Details
![GET API - User Details](https://github.com/jhalak04/challenge-rest-api-java/blob/461e4e3b44cefe92be4d43bddc221ce84e53b773/sample_app/screenshots/GET%20API%20-%20User%20Details.png)

###### GET API - User Search By ID

![GET API - User Search By ID](https://github.com/jhalak04/challenge-rest-api-java/blob/461e4e3b44cefe92be4d43bddc221ce84e53b773/sample_app/screenshots/GET%20API%20-%20User%20Search%20By%20ID.png)

###### GET API - User Search By Role

![GET API - User Search By Role](https://github.com/jhalak04/challenge-rest-api-java/blob/461e4e3b44cefe92be4d43bddc221ce84e53b773/sample_app/screenshots/GET%20API%20-%20User%20Search%20By%20Role.png)

###### POST API - Create User

![POST API - Create User](https://github.com/jhalak04/challenge-rest-api-java/blob/461e4e3b44cefe92be4d43bddc221ce84e53b773/sample_app/screenshots/POST%20API%20-%20Create%20User.png)

###### PUT API - Update User

![PUT API - Update User](https://github.com/jhalak04/challenge-rest-api-java/blob/461e4e3b44cefe92be4d43bddc221ce84e53b773/sample_app/screenshots/PUT%20API%20-%20Update%20User.png)

###### PHP UNIT TEST

![PHP UNIT TEST](https://github.com/jhalak04/challenge-rest-api-java/blob/461e4e3b44cefe92be4d43bddc221ce84e53b773/sample_app/screenshots/PHP%20UNIT%20TEST.png)


## How to run code

I have developed API using the Laravel framework. 
In case to run please follow below command to setup an environment using command line.

###### Install Laravel
```
composer global require laravel/installer
```

##### Create new project in laravel
```
composer create-project --prefer-dist laravel/laravel blog
```

##### Update .env file with below code
```
DB_CONNECTION=mysql
DB_HOST=127.0.0.1
DB_PORT=13306
DB_DATABASE=stat_rest_api
DB_USERNAME=add_db_user_name
DB_PASSWORD=add_db_password
```

##### Run Laravel project on local server

```
php artisan serve
```

###### Create database table 
```
php artisan migrate
```

###### Insert dummy data into database table 
```
php artisan db:seed
```

###### Run PHP Unit test
```
phpunit
```

###### API will be run using the following link 
```
http://localhost/api/user-details/
```
