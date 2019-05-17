Welcome to the Notes App!
Documentation available under: http://localhost:8080/swagger-ui.html

===USER===

Register:

curl -X POST "http://localhost:8080/users/public/register" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"email\": \"alex@iconos.mx\",  \"password\": \"alex1234\"}"

Login:

curl -X POST "http://localhost:8080/users/public/login" -H  "accept: plain/text" -H  "Content-Type: application/json" -d "{  \"email\": \"alex@iconos.mx\",  \"password\": \"alex1234\"}"

Logout:



===NOTES===

List:




Get One:

curl -X GET "http://localhost:8080/notes" -H  "accept: */*" -H  "token: 3764212e-bd55-40db-afab-d46aac626d8d"

Create:

curl -X POST "http://localhost:8080/notes" -H  "accept: application/json" -H  "token: 3764212e-bd55-40db-afab-d46aac626d8d" -H  "Content-Type: application/json" -d "{  \"note\": \"My Note\",  \"title\": \"My note is the best!\"}"

Edit:
