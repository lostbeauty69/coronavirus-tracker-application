# coronavirus-tracker-application
I have built a coronavirus tracker application which essentially deals with tracking the cases all over 
the world in a tabular form for a html website. So the Technology I have used under this Spring Boot
and in html I am using thymleaf here for creating template. For data I am using a github repository 
to read and for this huge bulk data I am using CSV. The main implementation reading a csv file 
stored on a shared path, github in my case and I am making a http request to read that particular 
string and I am storing that inside in a string builder. So how am I going to call Http using the HTTP 
client that’s available in JAVA. For this I created a class Service Coronavirus Data Service which gonna 
be give us the data and when the application loads, it’s going to make a call It’s gonna have code 
which makes a call to the URL and fetches the raw data from github. At the end of the project 
looking at the website we get a proper analysis or a proper look how the coronavirus is getting 
increased day by day.

Technology used- Spring Boot and Thymeleaf
