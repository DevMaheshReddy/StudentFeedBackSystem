package com.feedback.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feedback.dto.FeedbackDashboard;
import com.feedback.dto.FeedbackResponse;
import com.feedback.entity.Feedback;
import com.feedback.service.StudentFeedbackService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/feedback")
public class StudentFeedbackController {
	@Autowired
    StudentFeedbackService studentFeedbackService;
	@PostMapping
	public Feedback addFeedback(@Valid @RequestBody Feedback feedback) {
		return studentFeedbackService.addStudentFeedback(feedback);
	}
	@GetMapping
	public List<FeedbackResponse> getFeedBack(@RequestParam(required = false)String studentName,
			@RequestParam(required = false)Integer rating){
		return studentFeedbackService.getFeedback(studentName,rating);
	}
	@GetMapping("/{id}")
	public FeedbackResponse getFeedbackById(@PathVariable Long id) {
		return studentFeedbackService.getFeedbackById(id);
	}
	@PutMapping("/{id}")
	public FeedbackResponse updateFeedback
	         (@PathVariable Long id,@Valid @RequestBody Feedback feedback ) {
				return studentFeedbackService.updateFeedback(id, feedback);
	}
	@DeleteMapping("/{id}")
	public String deleteFeedback(@PathVariable Long id) {
		studentFeedbackService.deleteFeedback(id);
		return "DELETED SUCCESSFULLY";
	}
	@GetMapping("/paged")
	public Page<Feedback>getFeedbackPaged(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size){
		return studentFeedbackService.getFeedbackPaged(page,size);
	}
	@GetMapping("/top")
	public List<FeedbackResponse>getTopFeedback(){
		 return studentFeedbackService.getTopFeedback();
	}
	@GetMapping("/dashboard")
	public FeedbackDashboard getDashboard(){
		return studentFeedbackService.getDashboard();
	}
}
