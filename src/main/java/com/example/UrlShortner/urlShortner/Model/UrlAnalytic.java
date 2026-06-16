package com.example.UrlShortner.urlShortner.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "urlAnalytics",
  indexes = {
    @Index(name = "idx_url", columnList = "url_id"),
    @Index(name = "idx_created_at", columnList = "createdAt")
  }
)
public class UrlAnalytic {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "url_id")
  private Url url;

  private String os;
  
  private String browser;

  private String country;

  private String device;

  private LocalDateTime createdAt;

  @PrePersist
  private void prePersist(){
    this.createdAt = LocalDateTime.now();
  }
}