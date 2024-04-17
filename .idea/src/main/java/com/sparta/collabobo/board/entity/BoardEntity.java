package com.sparta.collabobo.board.entity;

import com.sparta.collabobo.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@Where(clause = "deleted_date IS NULL")
@Builder
@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 30)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String color;

  @Column(nullable = false)
  private boolean deleted = false;

  @Column(nullable = false)
  @CreatedDate
  private LocalDateTime createdDate;
  @Column
  private LocalDateTime updatedDate;
  @Column
  @LastModifiedDate
  private LocalDateTime deletedDate;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "board")
  private Set<BoardUser> collaborators = new HashSet<>();

  public boolean isValidColor(String color) {
    // Check if color matches hex code pattern
    String colorPattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
    boolean matchesHexPattern = color.matches(colorPattern);

    // Check if color is a predefined color name
    boolean isPredefinedColor = PredefinedColor.contains(color.toUpperCase());

    return matchesHexPattern || isPredefinedColor;
  }

  public void setColor(String color) {
    if (isValidColor(color)) {
      if (PredefinedColor.contains(color.toUpperCase())) {
        // If color is a predefined color name, set its hex value
        this.color = PredefinedColor.valueOf(color.toUpperCase()).getHexValue();
      } else {
        // Otherwise, color is already a valid hex code
        this.color = color;
      }
    } else {
      throw new IllegalArgumentException("잘못된 색상입니다.");
    }
  }

  public enum PredefinedColor {
    RED("#FF0000"),
    GREEN("#00FF00"),
    BLUE("#0000FF"),
    BLACK("#000000"),
    WHITE("#FFFFFF");

    private final String hexValue;

    PredefinedColor(String hexValue) {
      this.hexValue = hexValue;
    }

    public String getHexValue() {
      return hexValue;
    }

    public static boolean contains(String test) {
      for (PredefinedColor c : PredefinedColor.values()) {
        if (c.name().equalsIgnoreCase(test)) {
          return true;
        }
      }
      return false;
    }
  }
}
