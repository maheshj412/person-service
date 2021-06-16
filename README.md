### Project

## person-service

 Add/delete/update person details into database.
 Add/edit/delete person address(s).

## Person-rest-api is created using following tools and technology

- spring boot rest
- spring data jpa
- h2 (In memory Database)
- jUnit
- java 1.8
- Validations
- Exception Handling

## port : 8088

### Person-Controller

| http method | api url | description|
|-------------|----------|-----------| 
| GET         | /api/v1/personservice/count | Count of Persons |
| GET | /api/v1/personservice/getallpersons | Get all Persons |
| POST | /api/v1/personservice/addperson | Add person  |
| PUT | /api/v1/personservice/addpersonaddress/{personid}| Add address to person| 
| POST| /api/v1/personservice/addaddress | Add a person with address |
|PUT| /api/v1/personservice/editperson/{personoid}| Edit person details by person id |
|DELETE| /api/v1/personservice/deleteperson/{personoid}| Delete person by person id|
|DELETE |/api/v1/personservice/deleteaddress/{personid}/{addressid} |Delete address from person |
|PUT| /api/v1/personservice/editaddress/{personid}/{addressid} | Edit address of person| 

## Sample Input

- http://localhost:8088/api/v1/personservice/addperson

        Add Person:

        {
        "firstName": "Joe",
        "lastName": "Dingle"
        }

- http://localhost:8088/api/v1/personservice/addaddress

        Add Person with Address:

        {
        "person":{
        "firstName":"Joe",
        "lastName":"Dingle",
        "addresses":[
                {
                "street":"Arubun Villas",
                "city":"Athlone",
                "state":"Westmeath",
                "postalCode":"N37 PH20"
                }]
            }
        }

- http://localhost:8088/api/v1/personservice/addpersonaddress/{personid}

        Add address to a saved person in db

        {
        "person":{
        "firstName":"Joe",
        "lastName":"Dingle",
        "addresses":[
                {
                "street":"Arubun Villas",
                "city":"Athlone",
                "state":"Westmeath",
                "postalCode":"N37 PH20"
                }]
            }
        }


## How to run,test ?
Clone project: 
And run: run ` mvn clean install spring-boot : run ` command.
