# dbBatchUpdater
Batch Database Updater

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


