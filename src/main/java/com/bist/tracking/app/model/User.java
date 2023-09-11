package com.bist.tracking.app.model;

import com.bist.tracking.app.token.Token;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;
  private String firstname;
  private String lastname;
  private String email;
  private String password;


  @Temporal(TemporalType.TIMESTAMP)
  private Date createDate;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Hidden
  @OneToMany(mappedBy = "user")
  private List<Token> tokens;


  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Stock> stocks = new ArrayList<>();

  @Override
  @Hidden
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Hidden
  @Override
  public String getPassword() {
    return password;
  }

  @Hidden
  @Override
  public String getUsername() {
    return email;
  }

  @Hidden
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Hidden
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Hidden
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Hidden
  @Override
  public boolean isEnabled() {
    return true;
  }
  @PrePersist
  protected void onCreate() {
    this.createDate = new Date();
  }
}
