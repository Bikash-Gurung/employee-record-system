This repo is created for training purpose.

This project is created using Java Servlet(4.0.1) technology and MySQL Database.

This system exposes the APIs to register the employee, get employee(s) information, update employee information and delete employee information etc.

API Documentation:

1. {url}/employee/employees      [POST] (To register the employee)
2. {url}/employee/employees      [GET] (To get all employee)
3. {url}/employee/employees/{id} [GET] (To the employee with id)
4. {url}/employee/employees/{id} [PUT] (To update the employee)
5. {url}/employee/employees/{id} [DELETE] (To delete the employee)

Request Body Fomat For POST and PUT Request:

{

    "first_name": "ram",
    "middle_name": "bahadur",
    "last_name": "sahi",
    "address": "address one",
    "phone": "0990909090",
    "email": "ramsahi@mail.com",
    "department": "Finance"
    
}
