package com.feedback.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.feedback.entity.Feedback;
public interface StudentFeedbackRepository extends JpaRepository<Feedback,Long> {
      List<Feedback>findByStudentName(String name);
}
