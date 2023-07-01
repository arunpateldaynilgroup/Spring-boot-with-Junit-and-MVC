```
---Class Room Rest Api--------
GET - /api/classroom  ==> Get All Class Room
GET - /api/classroom/getClassRoomDropdownResponse?name={classroomName} ==> Get Class Room Dropdwon name is not mandatory
GET - /api/classroom/{id} ==> Get Class Room By Id 
POST - /api/classroom ==> Create Class Room
PUT - /api/classroom/{id} ==> Update Class Room
DELETE - /api/classroom/{id} ==> Delete Class Room

---Student Rest Api--------
GET - /api/student  ==> Get All Student
GET - /api/student/findByClassRoomId/{classRoomId} ==> Get Student By Class Room Id
GET - /api/student/{id} ==> Get Student By Id
POST - /api/student ==> Create Student
PUT - /api/student/{id} ==> Update Student
DELETE - /api/student/{id} ==> Delete Student


---FRONTEND--------
localhost:9090/index.html  ==> for class room
localhost:9090/student.html  ==> for student

if you dont want insert data in manually then you can 
Enable command line runner code in Main class 

```
