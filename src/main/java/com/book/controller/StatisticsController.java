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

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

	@Autowired
	private UserStatsService userStatsService;
	
	@Autowired
	private AuthorStatsService authorStatsService;
	
	@Autowired
	private AdminStatsService adminStatsService;
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserStatistics(@PathVariable Long id) {
		 return ResponseEntity.status(HttpStatus.OK).body(userStatsService.getUserStatisticsDTO(id));		
	}
	
	@GetMapping("/author/{id}")
	public ResponseEntity<?> getAuthorStatistics(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(authorStatsService.getAuthorStatisticsDTO(id));
	}
	
	@GetMapping("/admin")
	public ResponseEntity<?> getAdminStatistics() {
		return ResponseEntity.status(HttpStatus.OK).body(adminStatsService.getAdminStatisticsDTO());
	}
}
