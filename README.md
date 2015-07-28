# dbBatchUpdater
Batch Database Updater
-- a service written in Java with Spring Boot that will perform monthly bulk updates to a NOSQL DB 


#### How to build
from the command line of the root directory of the project run

'mvn clean package' 

#### How to run

from the command line of the root direcotry

java -jar target/dbBatchUpdater-0.1-SNAPSHOT.jar


#### API ENDPOINTS FOR USER INFO

***Create a user info***

POST /userinfo 

Body {
  "name": "Craig",
  "jobTitle": "CPA",
  "industry": "FINANCE",
  "version": 1
}

***Get the user info***

GET /userinfo/{id}

***Update the user info***

POST /userinfo/{id}
{
  "name": "Craig",
  "jobTitle": "CPA",
  "industry": "MONEY",
  "version": 1
}

NOTE: if a user info with that id does not exist a new one is generated and the user info is created with that id.

***Delete the user info***

Delete /userinfo/{id}

***Ges a page of size 3 user info objects***

GET /userinfo?page={pageNumber}


#### API ENDPOINTS FOR BATCH JOB

POST batchjob/changeJobTitle

Body {
    "toValue":"new value",
    "fromValue":"old value"
}

POST batchjob/changeIndustry

Body {
    "toValue":"new value",
    "fromValue":"old value"
}

Technical Notes:

The crux of the  problem is that the database driver has only a scan method to go through every record.  When a batch job is submitted a "select * where critirea" cannot be performed do that limitation and that the size of the database is hundreds of millions of rows. 

As a result, my solution is as follows.  When a batch job is submitted, on a separate thread a scan is performed and all the user info docuements that match the batch job criteria are found.  The id's of the matching user info documents are stored along with the batch job inside a mongo db collection.

The edge case this creates is what happens if new user info is entered into the database after the batch job has been submitted but before the batch job has been executted.  The user id of this newly created or updated user object must be entered into the appropriate batch job.  This is solved by querying the batch jobs before a user info document is created or updated.

Once a month the batch jobs are retreaved from the database. A thread pool of size 4 (arbitrarily choosen) is created and the each batch job is turned into a job for the pool to complete.  After all the batch jobs are completed, the batch jobs are deleted from the mongo database.

The retry logic for writing to the database is encapsulated inside the thread of the batch job.  If a write fails for a user info object, that object is placed on a "retry queue".  The queue is maintained until all user info documents can be removed from the queue.  User info documents will be removed when they are written to the database.





