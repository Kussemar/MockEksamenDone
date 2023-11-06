# Eksamen 3. semester efterår 2023 - Jon Bertelsen

## Task 1

Testing started at 17:12 ...
HTTP/1.1 200 OK
Date: Fri, 03 Nov 2023 16:12:14 GMT
Content-Type: application/json
Content-Length: 403

Response file saved.
> 2023-11-03T171215.200.json

Response code: 200 (OK); Time: 768ms (768 ms); Content length: 403 bytes (403 B)
HTTP/1.1 200 OK
Date: Fri, 03 Nov 2023 16:12:15 GMT
Content-Type: application/json
Content-Length: 76

Response file saved.
> 2023-11-03T171215-1.200.json

Response code: 200 (OK); Time: 9ms (9 ms); Content length: 76 bytes (76 B)
HTTP/1.1 500 Server Error
Date: Fri, 03 Nov 2023 16:12:15 GMT
Content-Type: application/json
Content-Length: 118

Response file saved.
> 2023-11-03T171215.500.json

Response code: 500 (Server Error); Time: 35ms (35 ms); Content length: 118 bytes (118 B)
HTTP/1.1 200 OK
Date: Fri, 03 Nov 2023 16:12:15 GMT
Content-Type: application/json
Content-Length: 2

Response file saved.
> 2023-11-03T171215-2.200.json

Response code: 200 (OK); Time: 4ms (4 ms); Content length: 2 bytes (2 B)
HTTP/1.1 500 Server Error
Date: Fri, 03 Nov 2023 16:12:15 GMT
Content-Type: application/json
Content-Length: 419

Response file saved.
> 2023-11-03T171215-1.500.json

Response code: 500 (Server Error); Time: 51ms (51 ms); Content length: 419 bytes (419 B)

## Task 2
| HTTP method | REST Ressource | Exceptions and status(es)                                             |
|-------------|---------------------------|-----------------------------------------------------------------------|
| GET | `/api/plants` | 200 for okay or 500                                                   |
| GET | `/api/plants/{id}` | if id dosent exist -> 404, 2004 for okay, 500 for server error        |
| GET | `/api/plants/type/{type}` | 200 for okay, empty array if type doesent exist, 500 for server error |
| POST | `/api/plants` | wrong json: 400, 201 for okay                                         |


GET http://localhost:7070/api/v1/plants/10

HTTP/1.1 500 Server Error
Date: Fri, 03 Nov 2023 16:53:23 GMT
Content-Type: application/json
Content-Length: 119

{
"status": 500,
"message": "Internal server error while fetching plant by id",
"timestamp": "2023-11-03T17:53:23.997469100"
}
Response file saved.
> 2023-11-03T175324.500.json

Response code: 500 (Server Error); Time: 4ms (4 ms); Content length: 119 bytes (119 B)

## Task 3
3.4 Please note in your README.md file which programming paradigm the stream API is inspired by.
> The stream API is inspired by the functional programming paradigm.
## Task 4

Testing started at 14:41 ...
HTTP/1.1 200 OK
Date: Mon, 06 Nov 2023 13:41:05 GMT
Content-Type: application/json
Content-Length: 481

Response file saved.
> 2023-11-06T144106.200.json

Response code: 200 (OK); Time: 364ms (364 ms); Content length: 481 bytes (481 B)
HTTP/1.1 200 OK
Date: Mon, 06 Nov 2023 13:41:06 GMT
Content-Type: application/json
Content-Length: 89

Response file saved.
> 2023-11-06T144106-1.200.json

Response code: 200 (OK); Time: 22ms (22 ms); Content length: 89 bytes (89 B)
HTTP/1.1 500 Server Error
Date: Mon, 06 Nov 2023 13:41:06 GMT
Content-Type: application/json
Content-Length: 119

Response file saved.
> 2023-11-06T144106.500.json

Response code: 500 (Server Error); Time: 60ms (60 ms); Content length: 119 bytes (119 B)
HTTP/1.1 200 OK
Date: Mon, 06 Nov 2023 13:41:06 GMT
Content-Type: application/json
Content-Length: 2

Response file saved.
> 2023-11-06T144106-2.200.json

Response code: 200 (OK); Time: 65ms (65 ms); Content length: 2 bytes (2 B)
HTTP/1.1 200 OK
Date: Mon, 06 Nov 2023 13:41:06 GMT
Content-Type: application/json
Content-Length: 77

Response file saved.
> 2023-11-06T144106-3.200.json

Response code: 200 (OK); Time: 16ms (16 ms); Content length: 77 bytes (77 B)

## Task 5
Unit-test er hvor man tester en enkelt metode,
og integration test er hvor man tester mange metoder sammen, altså man tester fx mellem databasen, controlleren og metoderne.
## Task 6
Forskellen er at i PlantDAO test, tester vi på selve databasen
og i PlantController test, tester vi på selve endpointsne.