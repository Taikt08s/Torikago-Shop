package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="FeedBack")
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String feedBackID;
    private String feedBackContent;
    private int rating;

    public void setRating(int rating){
        if(rating < 1 || rating > 5){
            throw new IllegalArgumentException(rating+"rating is not valid");
        }
        this.rating = rating;
    }
}
