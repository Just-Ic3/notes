Welcome to the Notes App!
Documentation available under: http://localhost:8080/swagger-ui.html

===USER===

Register:

curl -X POST \
  http://localhost:8080/users/public/register \
  -H 'Content-Type: application/json' \
  -d '{
	"email":"alex@gmail.com",
	"password":"password"
}'

Edit Email:

curl -X PATCH \
  http://localhost:8080/users/email \
  -H 'Authorization: Basic YWxleEBpY29ub3MubXg6cGFzc3dvcmQ=' \
  -H 'Content-Type: text/plain' \
  -d alex@iconos.mx

===NOTES===

List:

curl -X GET \
  http://localhost:8080/notes \
  -H 'Authorization: Basic YWxleEBpY29ub3MubXg6cGFzc3dvcmQ='

Get One:

curl -X GET \
  http://localhost:8080/notes/ff8081816ad3bd5c016ad3bfe8650000 \
  -H 'Authorization: Basic YWxleEBpY29ub3MubXg6cGFzc3dvcmQ='

Create:

curl -X POST \
  http://localhost:8080/notes \
  -H 'Authorization: Basic YWxleEBpY29ub3MubXg6cGFzc3dvcmQ=' \
  -H 'Content-Type: application/json' \
  -d '{
    "title": "My note",
    "note": "My note is the best!"
}'

Edit:

curl -X PUT \
  http://localhost:8080/notes \
  -H 'Authorization: Basic YWxleEBpY29ub3MubXg6cGFzc3dvcmQ=' \
  -H 'Content-Type: application/json' \
  -d '{
    "id": "ff8081816ad3e2bb016ad3f034500000",
    "title": "My note is better everyday",
    "note": "It is a work in progress!"
}'