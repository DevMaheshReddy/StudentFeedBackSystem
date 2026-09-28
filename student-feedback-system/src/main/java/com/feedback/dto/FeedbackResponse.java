package com.feedback.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedbackResponse {
   private String studentName;
   private String courseName;
   private int rating;
   private String comment;
}
