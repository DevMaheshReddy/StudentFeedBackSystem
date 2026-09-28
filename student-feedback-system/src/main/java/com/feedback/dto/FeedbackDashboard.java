package com.feedback.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDashboard {
   private long totalFeedback;
   private double averageRating;
   private long fiveStarCount;
   private List<FeedbackResponse>latestFeedback;
   
}
