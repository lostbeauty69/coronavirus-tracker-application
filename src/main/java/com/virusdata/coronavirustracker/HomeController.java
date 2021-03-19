package com.virusdata.coronavirustracker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {
	
	@Autowired  /*Since this ( ServicesCoronaVirusDataService ) is a service I can autowired this into my controller 
	and that's how the controller is going to access it*/
	ServicesCoronaVirusDataService servicesCoronaVirusDataService;  
	
	@GetMapping("/")
	public String Home(Model model) {
		
		/*I am doing here is I am taking the list of objects (allStats) converting into a String (stream) and then
		 mapping to integer (mapToInt) each object (stat) maps to the integer value which is the total cases(getLatestTotalCases) 
		 for that record and the I am going to sum ( sum() ) this up. So basically I am going to take that stream sum 
		  it up and then assign it to an integer (totalReportedCases) */
		
		List<LocationStats> allStats = servicesCoronaVirusDataService.getAllStats();
		int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
	model.addAttribute( "locationStats", allStats); 
	model.addAttribute( "totalReportedCases", totalReportedCases); 
	model.addAttribute( "totalNewCases", totalNewCases); 
	
		return "home";
	}

}
