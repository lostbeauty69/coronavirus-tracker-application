package com.virusdata.coronavirustracker;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service  // this is going to make this a spring service.It will import org.springframework.stereotype.Service
public class ServicesCoronaVirusDataService {

	//make HTTP call to the GitHub data
	
	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private List<LocationStats> allStats = new ArrayList<>();
	@PostConstruct /*execute then when the application starts and  that happens by using this annotation.
	When you construct this [ServicesCoronaVirusDataService] instance of the service, after its done just
	execute this method [fetchVirusData()] */
	
	@Scheduled(cron = "* * 1 * * *") /*When we want to run it in regular basis, so way to doing it add
	scheduled annotation in Spring. The add scheduled annotation schedules the run after on a method 
	[fetchVirusData()] on a regular basis. We can specify a cron expression, so it can run every second.
	We are doing and telling that Spring execute this method [fetchVirusData] every second*/
	
	public void fetchVirusData() throws IOException, InterruptedException { /*throws IOException, 
	InterruptedException--> this is basically happens when client.send fails*/
		
		List<LocationStats> newStats = new ArrayList<>();
		
		HttpClient client = HttpClient.newHttpClient();  //creating a new client
		HttpRequest request = HttpRequest.newBuilder() //this class allows us to use the builder pattern
		       .uri(URI.create(VIRUS_DATA_URL)) /*this gonna be create a uri and 
		       this uri going to be passed to this URI method [ import java.net.URI; ]*/ 
		       .build();
		
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString()); /*this 
		is basically sending doing a client.send off request and then what to do with the body. 
		BodyHandlers.ofString()--> just take the body and return as a string */
		
		StringReader csvBodyReader = new StringReader(httpResponse.body()); /*string reader is basically and
		in an instance of reader which parses string. Now I have a reader [csvBodyReader] from my string 
		[httpResponse] instance [in] , now I am going to pass the reader over the loop.So I can loop
		through this [] and I can get the header value.*/
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader); 
		
		for (CSVRecord record : records) {
			LocationStats locationStat = new LocationStats();
			locationStat.setState(record.get("Province/State"));  //going to populate  the stuff directly here
			locationStat.setCountry(record.get("Country/Region"));
			int latestCases = Integer.parseInt(record.get(record.size() -1));/*whatever 
			we get here it return to String because that's what record.get is gonna give me,
			 we need to convert it into an Integer.parseInt().*/ 
			int prevDayCases = Integer.parseInt(record.get(record.size() -2));
			locationStat.setLatestTotalCases(latestCases);  
			locationStat.setDiffFromPrevDay(latestCases - prevDayCases); 
            newStats.add(locationStat);
            
		}
	    
		this.allStats = newStats; 
		
		
	}
	public List<LocationStats> getAllStats() {
		return allStats;
	}
	public void setAllStats(List<LocationStats> allStats) {
		this.allStats = allStats;
	}
	
	
}
