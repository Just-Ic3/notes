Welcome to the Notes App!
Documentation available under: http://localhost:8080/swagger-ui.html

===USER===

Register:

curl -X POST "http://localhost:8080/users/public/register" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"email\": \"alex@iconos.mx\",  \"password\": \"alex1234\"}"

Login:

curl -X POST "http://localhost:8080/users/public/login" -H  "accept: plain/text" -H  "Content-Type: application/json" -d "{  \"email\": \"alex@iconos.mx\",  \"password\": \"alex1234\"}"

Logout:

curl -X POST "http://localhost:8080/users/logout" -H  "accept: */*" -H  "token: 929762c7-2654-4727-b4f6-ba6ebaf76cc0"

Edit:

curl -X PUT "http://localhost:8080/users" -H  "accept: application/json" -H  "token: token" -H  "Content-Type: application/json" -d "{  \"createdOn\": \"2019-05-17T03:11:43.786Z\",  \"email\": \"string\",  \"id\": \"string\",  \"lastUpdatedOn\": \"2019-05-17T03:11:43.786Z\",  \"password\": \"newpass\"}"


===NOTES===

List:

curl -X GET "http://localhost:8080/notes" -H  "accept: */*" -H  "token: 3764212e-bd55-40db-afab-d46aac626d8d"

Get One:

curl -X GET "http://localhost:8080/notes" -H  "accept: */*" -H  "token: 3764212e-bd55-40db-afab-d46aac626d8d"

Create:

curl -X POST "http://localhost:8080/notes" -H  "accept: application/json" -H  "token: 3764212e-bd55-40db-afab-d46aac626d8d" -H  "Content-Type: application/json" -d "{  \"note\": \"My Note\",  \"title\": \"My note is the best!\"}"

Edit:

curl -X PUT "http://localhost:8080/notes" -H  "accept: application/json" -H  "token: 547fe9b5-a057-4a1d-907f-4266ee0baa9c" -H  "Content-Type: application/json" -d "{  \"id\": \"ff8081816ac3c5a4016ac3ce41ec0001\",  \"title\": \"My note is still the best!\",  \"note\": \"My Note Updated\"}"