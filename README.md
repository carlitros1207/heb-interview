This project was created as a coding challenge for HEB.

This project was built using java 8.

Build the project by doing a 

`./gradlew clean build`


a couple of things are need to run this project, set the environment variables below

| Environment Variable           | Value                                                                                                               |
|--------------------------------|---------------------------------------------------------------------------------------------------------------------|
| GOOGLE_APPLICATION_CREDENTIALS | This is the absolute path to the Google Vision APi service account key, this si needed for the object detection API |
| DB_URL                         | The Url for the MySQL database to be used                                                                           |
| DB_USER                        | User Name to use for MySQL database connection                                                                      |
| DB_PASSWORD                    | Password for User used for MySQL database connection                                                                |


Once that's all set we can run the program either using intellij or by running the Jar located under the build/libs folder that was created when running the gradle build

`java -jar build/libs/heb-0.0.1-SNAPSHOT.jars `