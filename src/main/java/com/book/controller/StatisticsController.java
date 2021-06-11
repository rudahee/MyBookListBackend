package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.service.statistics.AdminStatsService;
import com.book.service.statistics.AuthorStatsService;
import com.book.service.statistics.UserStatsService;

/*
* Controller for API Rest. 
* 
* Annotated by @RestController and @RequestMapping. its mapped in [url]:[port]/statistics
* 
* This controller return statistics for an specific user.
* 
* @author J. Rubén Daza
*/
@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

	@Autowired
	private UserStatsService userStatsService;
	
	@Autowired
	private AuthorStatsService authorStatsService;
	
	@Autowired
	private AdminStatsService adminStatsService;

	/* HTTP/GET
	 * 
	 * This method returns statistics for user.
	 * 
	 * @param id Long
	 * 
	 * @return ResponseEntity<UserStatisticsDTO>.
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserStatistics(@PathVariable Long id) {
		 return ResponseEntity.status(HttpStatus.OK).body(userStatsService.getUserStatisticsDTO(id));		
	}
	
	/* HTTP/GET
	 * 
	 * This method returns statistics for author.
	 * 
	 * @param id Long
	 * 
	 * @return ResponseEntity<AuthorStatisticsDTO>.
	 */
	@GetMapping("/author/{id}")
	public ResponseEntity<?> getAuthorStatistics(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(authorStatsService.getAuthorStatisticsDTO(id));
	}
	
	/* HTTP/GET
	 * 
	 * This method returns statistics for admin.
	 * 
	 * @param id Long
	 * 
	 * @return ResponseEntity<AdminStatisticsDTO>.
	 */
	@GetMapping("/admin")
	public ResponseEntity<?> getAdminStatistics() {
		return ResponseEntity.status(HttpStatus.OK).body(adminStatsService.getAdminStatisticsDTO());
	}
}
