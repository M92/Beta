****************************
*
*  cURL testing of ProducCatalogueResource 
*
*****************************
(there's a nice REST Console as an extension to Chrome)

Testing the ProductCatalogueResource from command line.
This runs on GlassFish 4.0 

------------------------------------------------------------
# *** GET *** (read) NOTE: 8080 is for GlassFish
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/movies

//Hämta alla listor i katalogen
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/users/aaa/lists 

//Visa alla publika listor
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/lists


# Possible need another id last! 
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/movies/1738

//Hämta alla filmer i listan
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/users/aaa/lists/839


# Query parameters. NOTE: Must quote URI. Will get list (with possible 0 elements) NO NEED 
curl -v -H "Accept: application/json" "http://localhost:8080/movielibrary_web/webresources/movies/range?fst=0&max=2"
curl -v -H "Accept: application/json" "http://localhost:8080/movielibrary_web/webresources/users/lists/range?fst=0&max=2"
curl -v -H "Accept: application/json" "http://localhost:8080/movielibrary_web/webresources/movielists/range?fst=0&max=2"


# Get primitive type NO NEED
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/movies/count
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/users/lists/count
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/movielists/count


# *** POST (create) ***
curl -v -X POST http://localhost:8080/movielibrary_web/webresources/movies --data "title=***POST***&releaseYear=2014"

//Skapa en ny lista till katalogen
curl -v -X POST http://localhost:8080/movielibrary_web/webresources/users/aaa/lists --data "nickname=New list to user"

curl -v -X POST http://localhost:8080/movielibrary_web/webresources/movielists --data "title=***POST***&releaseYear=2014"


# *** PUT (update) ***
curl -v -X PUT http://localhost:8080/movielibrary_web/webresources/movies/1787 --data "title=***POST***&releaseYear=2111"

//Lägg till en film i listan
curl -v -X PUT http://localhost:8080/movielibrary_web/webresources/users/aaa/lists/839 --data "title=***POST***&releaseYear=2111"

curl -v -X PUT http://localhost:8080/movielibrary_web/webresources/movielists/1787 --data "title=***POST***&releaseYear=2111"


# *** DELETE (delete) ***
curl -v -X DELETE http://localhost:8080/movielibrary_web/webresources/movies/1787

//DEL - Ta bort den specifika listan från katalogen
curl -v -X DELETE http://localhost:8080/movielibrary_web/webresources/users/aaa/lists/901

//DEL - Ta bort den specifika filmen från listan
curl -v -X DELETE http://localhost:8080/movielibrary_web/webresources/users/aaa/lists/839/852

//DEL - Ta bort usern
curl -v -X DELETE http://localhost:8080/movielibrary_web/webresources/users/aaa

curl -v -X DELETE http://localhost:8080/movielibrary_web/webresources/movielists/1787



