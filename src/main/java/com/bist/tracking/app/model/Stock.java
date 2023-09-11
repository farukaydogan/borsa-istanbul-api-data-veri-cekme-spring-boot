package com.bist.tracking.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "user_id")  // Veritabanı kolon adı, eğer farklı ise değiştirin
  @JsonIgnore
  private User user;

  private   String name;
  private   String code;
  private   double price;
  private   int lot;
  private   double  cost;
  private double earning;
  private double earnPercentage;

}
