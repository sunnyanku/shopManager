# shopManager
Restful api to fetch/create shop details.
Shop Manager API documentation
 
API endpoint :  http://localhost:8080/ShopmanagerRestApi/api/
 
Web services to:
Create shop. 
Create Manager
Search manager using their username
Get all managers
Search nearby shop using user location (lat, long)
 
 
Create shop (End point : /shop/{force} ):
	   Api end point  : http://localhost:8080/ShopmanagerRestApi/api/shop/{force}
	   	
	  {force} : This parameter can have boolean value like true and false.
	 	    if value is true, code will update shop even if shop is already created by some other manager else an error message will be generated that shop is already created.
	E.g. : Create a new shop : 
End point  : http://localhost:8080/ShopmanagerRestApi/api/shop/false
Method : Post
Content Type :  application/json
Body : 
{
    "shopName": "Gopal",
    "country": "India",
    "postcode": "160046",
    "username": "abhishek"
  }
 
      Response : 
	{
  "message": "Success :: A shop with name Gopal created with id 7",
  "detail": {
    "Existing Shop": null,
    "Updated Shop": {
      "id": 7,
      "shopName": "Gopal",
      "country": "India",
      "postcode": "160046",
      "latitude": "30.702879",
      "longitude": "76.757813",
      "username": "abhishek",
      "version": 0
    }
  }
}
 
IF shop is already existed with same name :
Response :
{
  "errorMessage": "Error :: Unable to create. A shop with name Gopal already exist"
}
 
 
 
  B. Force update existed shop : 
	Endpoint : http://localhost:8080/ShopmanagerRestApi/api/shop/true
	Request will be same as in above example : 
	Response if shop already exist : 
		{
  "message": "Success :: A shop with name YWC12345678 created with id 7",
  "detail": {
    "Existing Shop": {
      "id": 7,
      "shopName": "Gopal",
      "country": "India",
       "postcode": "160046",
      "latitude": "30.702879",
      "longitude": "76.757813",
      "username": "abhishek",
      "version": 1
    },
    "Updated Shop": {
      "id": 7,
      "shopName": "Gopal",
      "country": "India",
      "postcode": "175035",
      "latitude": "31.6275945",
      "longitude": "77.1830243",
      "username": "abhishek",
      "version": 1
    }
  }
}
 
 
2. Create Manager :
	 Api end point  : http://localhost:8080/ShopmanagerRestApi/api/manager/
	   	
	  E.g. : 
End point  :http://localhost:8080/ShopmanagerRestApi/api/manager/
Method : Post
Content Type :  application/json
Body : 
{
    "firstName": "Sunny",
    "lastName": "Kumar",
    "username": "sunny"
  }
 
 
  Response : 
	{
  "message": "Success :: A Manager with username sunny created with id 5",
  "detail": null
}
  
   Error response : 
	{
  "errorMessage": "Error :: Unable to create. A manager with username sunny already exist"
}
 
 
3. Search manager using their username :
	Api end point  : http://localhost:8080/ShopmanagerRestApi/api/manager/{username}
	   	
	  {username} : username assigned to user while manager is created.
	 E.g. :
End point  :http://localhost:8080/ShopmanagerRestApi/api/manager/abhishek 
Method : Get
Content Type :  application/json
 
 
 Response : 
	{
  "id": 1,
  "firstName": "Abhishek",
  "lastName": "Kumar",
  "username": "abhishek"
}
 
  
    Error response : 
	{
  "errorMessage": "Error :: Manager with username abhishek123 not found"
}
 
 
 
 
4. Get all managers : 
	Api end point  : http://localhost:8080/ShopmanagerRestApi/api/manager/
 
	 E.g. : 
End point  :http://localhost:8080/ShopmanagerRestApi/api/manager/
 
Method : Get
Content Type :  application/json
 
 
      Response : 
	[
  {
    "id": 1,
    "firstName": "Abhishek",
    "lastName": "Kumar",
    "username": "abhishek"
  },
  {
    "id": 2,
    "firstName": "Satinder",
    "lastName": "Sharma",
    "username": "satinder"
  },
  {
    "id": 3,
    "firstName": "Dixit",
    "lastName": "Soni",
    "username": "dixit"
  },
  {
    "id": 4,
    "firstName": "Vivek",
    "lastName": "Gautam",
    "username": "vivek"
  },
  {
    "id": 5,
    "firstName": "Michelle",
    "lastName": "Dessler",
    "username": "michelle"
  },
  {
    "id": 6,
    "firstName": "Sunny",
    "lastName": "Kumar",
    "username": "sunny"
  }
]
 
 
  
    Error response : 
	[]
5. Search nearby shop using user location (lat, long) :
    
               Api end point  : http://localhost:8080/ShopmanagerRestApi/api/nearby/
	   	
	      E.g. : 
End point  :http://localhost:8080/ShopmanagerRestApi/api//nearby/
Method : Post
Content Type :  application/json
Body : 
  {
        "latitude": "30.743624",
        "longitude": "76.785689",
        "radius" : 10
    }
 
 
Default radius will be 10 if not given.
 
 
      Response : 
	[
  {
    "id": 1,
    "shopName": "YWC",
    "country": "India",
    "postcode": "160062",
    "latitude": "30.743624",
    "longitude": "76.785689",
    "username": "abhishek",
    "version": null
  },
  {
    "id": 2,
    "shopName": "BN Bakers",
    "country": "India",
    "postcode": "160017",
    "latitude": "30.691840",
    "longitude": "76.728441",
    "username": "abhishek",
    "version": null
  },
  {
    "id": 3,
    "shopName": "Reliance Store",
    "country": "India",
    "postcode": "160026",
    "latitude": "30.723288",
    "longitude": "76.811061",
    "username": "abhishek",
    "version": null
  },
  {
    "id": 4,
    "shopName": "Patanjali",
    "country": "India",
    "postcode": "160055",
    "latitude": "30.733037",
    "longitude": "76.689372",
    "username": "abhishek",
    "version": null
  }
]
 
  
    Error response : 
	{
  "errorMessage": "Error :: Unable to create. A manager with username sunny already exist"
}
 
 
 
	
 
