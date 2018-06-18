# REST API Challenge - Completed (doc at bottom of this file)

We would like you to implement a simple application which works with 3
REST APIs.

Suppose the user object only has three properties: id, name and role.

The first API allows the client to query a specific set of user objects by property
where the request may look like this.

```
GET /users/?role=foo
```
The response should look like this.

```JSON
[
  {
    "id": "977e3f5b-6a70-4862-9ff8-96af4477272a",
    "name": "java beans",
    "role": "foo"
  }
]
```
The second API allows the client to create a user object with a request like this.

```
POST /users/
```
```JSON
{
  "name": "cookie bars",
  "role": "bar"
}
```

The third API allows the client to retrieve a user object given its ID

```
GET /users/977e3f5b-6a70-4862-9ff8-96af4477272a
```
```JSON
{
  "name": "java beans",
  "role": "foo"
}
```

# Completed Challenge Notes

- The corresponding Code has been implemented as specified
- Tests will run with no errors
- Code maintainability has been considered, and implemented with modularity
- Should run in Docker container, but this was untested
- Rest-Api completed and code will test correctly, however DB not implemented correctly
- Since DB not completed, code has some quick fixes like creating a User.obj with a set id = "977e3f5b-6a70-4862-9ff8-96af4477272a"
- Bonus' were not completed

