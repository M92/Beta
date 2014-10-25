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
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/users 
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/movielists 


# Possible need another id last!
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/movies/1738
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/users/1738
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/movielists/1738


# Query parameters. NOTE: Must quote URI. Will get list (with possible 0 elements)
curl -v -H "Accept: application/json" "http://localhost:8080/movielibrary_web/webresources/movies/range?fst=0&max=2"
curl -v -H "Accept: application/json" "http://localhost:8080/movielibrary_web/webresources/users/range?fst=0&max=2"
curl -v -H "Accept: application/json" "http://localhost:8080/movielibrary_web/webresources/movielists/range?fst=0&max=2"


# Get primitive type
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/movies/count
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/users/count
curl -v -H "Accept: application/json" http://localhost:8080/movielibrary_web/webresources/movielists/count


# *** POST (create) ***
curl -v -X POST http://localhost:8080/movielibrary_web/webresources/movies --data "title=***POST***&releaseYear=2014"
curl -v -X POST http://localhost:8080/movielibrary_web/webresources/users --data "title=***POST***&releaseYear=2014"
curl -v -X POST http://localhost:8080/movielibrary_web/webresources/movielists --data "title=***POST***&releaseYear=2014"


# *** PUT (update) ***
curl -v -X PUT http://localhost:8080/movielibrary_web/webresources/movies/1787 --data "title=***POST***&releaseYear=2111"
curl -v -X PUT http://localhost:8080/movielibrary_web/webresources/users/1787 --data "title=***POST***&releaseYear=2111"
curl -v -X PUT http://localhost:8080/movielibrary_web/webresources/movielists/1787 --data "title=***POST***&releaseYear=2111"


# *** DELETE (delete) ***
curl -v -X DELETE http://localhost:8080/movielibrary_web/webresources/movies/1787
curl -v -X DELETE http://localhost:8080/movielibrary_web/webresources/users/1787
curl -v -X DELETE http://localhost:8080/movielibrary_web/webresources/movielists/1787



