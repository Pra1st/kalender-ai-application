# kalender-ai-application
This README Documents the Setup for this Project.
This Project was created and developed for the University Module: "Programieren von Webanwendungen".



To Setup the Porject follow these Steps:

1. Setup you software Envionment and install all mvn dependencys (mvn clean install, mvn clean package)
2. Setup OpenAI Key
    Place your OpenAI API Key in OpenAIService.java  in line 15 => API_KEY = "${your_api_key}"
3. Create Database for Events, Users, Einkaufslisten and EinkaufslistenItems
    therefore import the src/hausarbeit-calendar.sql inside your mysql server
    additionally change the persistence.xml to include your username and password as well as the address of the db.
4. Run The Program
