# REST API Challenge

```
GET api/user-details?role=bar
```
The response should look like this.

#####Get API - User Details

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

## How to run code

I have developed API using the Laravel framework. 
In case to run please follow below command to setup an environment using command line.

####### Run Laravel project on local server
```
php artisan serve
```

####### Create database table 
```
php artisan migrate
```

####### Insert dummy data into database table 
```
php artisan db:seed
```

####### Run PHP Unit test
```
phpunit
```

####### API will be run using the following link 
```
http://localhost/api/user-details/
```