package com.feedback.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotBlank(message = "Student name is required")
    private String studentName;
	@NotBlank(message = "Course name is required")
    private String courseName;
	@Min(value = 1,message = "Rating must be atleast 1")
	@Max(value = 5,message = "Rating must not exceed 5")
    private int rating;
	@Size(max = 100, message = "Comment should be less than 200 characters")
    private String comment;
}
