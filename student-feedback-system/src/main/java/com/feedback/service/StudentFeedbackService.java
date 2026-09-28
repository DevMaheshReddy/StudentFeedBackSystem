package com.feedback.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.feedback.dto.FeedbackDashboard;
import com.feedback.dto.FeedbackResponse;
import com.feedback.entity.Feedback;
import com.feedback.exception.ResourceNotFoundException;
import com.feedback.repository.StudentFeedbackRepository;

@Service
public class StudentFeedbackService {
	@Autowired
	private StudentFeedbackRepository studentFeedbackRepository;

	public Feedback addStudentFeedback(Feedback feedback) {
		return studentFeedbackRepository.save(feedback);
	}
	public FeedbackResponse getFeedbackById(Long id) {
		Feedback feedback = studentFeedbackRepository.findById(id)
		.orElseThrow(()->new ResourceNotFoundException("feed back not found for given id"));
		FeedbackResponse feedbackResponse=new FeedbackResponse();
		feedbackResponse.setStudentName(feedback.getStudentName());
		feedbackResponse.setCourseName(feedback.getCourseName());
		feedbackResponse.setRating(feedback.getRating());
		feedbackResponse.setComment(feedback.getComment());
		return feedbackResponse;
	}
	public List<FeedbackResponse> getFeedback(String studentName,Integer rating) {
		List<Feedback> list = studentFeedbackRepository.findAll();

	    // filter by student name
	    if (studentName != null && !studentName.isBlank()) {
	        list = list.stream()
	                .filter(f -> f.getStudentName().equalsIgnoreCase(studentName))
	                .toList();
	    }

	    // filter by rating
	    if (rating != null) {
	        list = list.stream()
	                .filter(f -> f.getRating() == rating)
	                .toList();
	    }

	    // convert to DTO
	    return list.stream()
	            .map(f -> {
	                FeedbackResponse res = new FeedbackResponse();
	                res.setStudentName(f.getStudentName());
	                res.setCourseName(f.getCourseName());
	                res.setRating(f.getRating());
	                res.setComment(f.getComment());
	                return res;
	            })
	            .toList();
	}
   public FeedbackResponse updateFeedback(Long studentId,Feedback feedback) {
		 Feedback existing = studentFeedbackRepository.findById(studentId)
		.orElseThrow(()->new ResourceNotFoundException("feedback not found with id"));
		 existing.setStudentName(feedback.getStudentName());
		 existing.setCourseName(feedback.getCourseName());
		 existing.setRating(feedback.getRating());
		 existing.setComment(feedback.getComment());
		 Feedback updated = studentFeedbackRepository.save(existing);
		 FeedbackResponse response = new FeedbackResponse();
		 response.setStudentName(updated.getStudentName());
		 response.setCourseName(updated.getCourseName());
		 response.setRating(updated.getRating());
		 response.setComment(updated.getComment());
		 return response;
	}
   public void deleteFeedback(@PathVariable Long id) {
		Feedback feedback = studentFeedbackRepository.findById(id)
		.orElseThrow(()->new ResourceNotFoundException("feedback not for given id"));
		studentFeedbackRepository.delete(feedback);
	}
   public Page<Feedback> getFeedbackPaged(int page, int size) {

	    PageRequest pageable = PageRequest.of(page, size, Sort.by("rating").descending());

	    Page<Feedback> pageData = studentFeedbackRepository.findAll(pageable);

	    // 🔥 Fix
	    if (page >= pageData.getTotalPages() && pageData.getTotalPages() != 0) {
	        throw new ResourceNotFoundException("Page number " + page + " does not exist");
	    }

	    return pageData;
	}
   public List<FeedbackResponse> getTopFeedback() {
	    return studentFeedbackRepository.findAll()
	            .stream()
	            .filter(f -> f.getRating() == 5)
	            .sorted((f1, f2) -> Long.compare(f2.getId(), f1.getId())) // 🔥 sort by id desc
	            .map(f -> new FeedbackResponse(
	                    f.getStudentName(),
	                    f.getCourseName(),
	                    f.getRating(),
	                    f.getComment()
	            ))
	            .toList();
	}
   public FeedbackDashboard getDashboard() {
	   //total count of rating
	   List<Feedback> list = studentFeedbackRepository.findAll();
	   FeedbackDashboard dashboard = new FeedbackDashboard();
	   dashboard.setTotalFeedback(list.size());
	   
	   //average rating
	   double avg=list.stream()
	   .mapToInt(Feedback::getRating)
	   .average()
	   .orElse(0.0);
	   dashboard.setAverageRating(avg);
	   
	   //Five star count
	  long fiveStar= list.stream()
	   .filter(
			   f->f.getRating()==5
		)
	   .count();
	  dashboard.setFiveStarCount(fiveStar);
	  
	  //latest 3 feedback
	  
	    List<FeedbackResponse> latest = list.stream()
	    .sorted((f1,f2)->Long.compare(f2.getId(),f1.getId()))
	    .limit(3)
	    .map(f->
	    {
	          FeedbackResponse response=new FeedbackResponse();
	          response.setStudentName(f.getStudentName());
	          response.setCourseName(f.getCourseName());
	          response.setRating(f.getRating());
	          response.setComment(f.getComment());
	          return response;
	    }
	     )
	    .toList();
	    dashboard.setLatestFeedback(latest);
	    return dashboard;
   }
}
