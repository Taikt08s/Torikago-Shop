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
@Table(name ="Feedback")
@Check(constraints = "rating >= 1 and rating <= 5")
public class Feedback {
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private BirdCageOrder orderFeedback;
    @Column(name = "feedback_content", length = 255)
    private String feedbackContent;
    @Column(name = "feedback_date", columnDefinition = "TIMESTAMP")  // add feedbackDate to ERD
    private LocalDateTime feedbackDate;
    @Column(name = "rating")
    private int rating;
}
