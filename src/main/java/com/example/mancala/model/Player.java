package com.example.mancala.model;

import com.sun.javafx.beans.IDProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Random;

@Getter
@Setter
@ToString
public class Player {


    private int id;
    private String name;
    private boolean isWinner;
    private boolean isActive;
    private int score;
    public Player() {}
    public Player(String name, boolean isWinner, boolean isActive, int score) {
        Random rand = new Random();
        this.id = rand.nextInt(1000000000);
        this.name = name;
        this.isWinner = isWinner;
        this.isActive = isActive;
        this.score = score;
    }

}
