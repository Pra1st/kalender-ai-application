This README Documents the Setup for this Project.
This Project was created and developed for the University Module: "Programieren von Webanwendungen".



To Setup the Porject follow these Steps:
    
1. Clone the porject using git: "git clone https://github.com/Pra1st/kalender-ai-application.git"
2. Setup you software Envionment and install all mvn dependencys
3. Setup OpenAI Key
    Place your OpenAI API Key in OpenAIService.java  in line 15 => API_KEY = "${your_api_key}"
4. Create Database for Events, Users, Einkaufslisten and EinkaufslistenItems
    therefore import the hausarbeit-calendar.sql inside your mysql server
    additionally change the persistence.xml to include your username and password as well as the address of the db.
5. Run The Program