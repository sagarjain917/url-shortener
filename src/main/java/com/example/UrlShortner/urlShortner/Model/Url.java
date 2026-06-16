package com.example.UrlShortner.urlShortner.Model;

import java.time.LocalDateTime;

import com.example.UrlShortner.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "urls",
  indexes = {
    @Index(name = "idx_short_url", columnList="shortUrl"),
    @Index(name = "idx_created_at", columnList = "createdAt")
  }
)
public class Url {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;

  @Column(nullable=false)
  private String originalUrl;

  @Column(unique=true)
  private String shortUrl;
  
  private LocalDateTime createdAt;

  private LocalDateTime expiredAt;

  @PrePersist
  private void prePersist(){
    this.createdAt = LocalDateTime.now();
  }
}