package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

//import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="feedback")
@Check(constraints = "rating >= 1 and rating <= 5")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order orderFeedback;
    @Column(name = "feedback_content", length = 255)
    private String feedbackContent;
    @Column(name = "feedback_date", columnDefinition = "TIMESTAMP")  // add feedbackDate to ERD
    private LocalDateTime feedbackDate;
    @Column(name = "rating")
    private int rating;
}
