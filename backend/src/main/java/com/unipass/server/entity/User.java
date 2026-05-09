package com.unipass.server.entity;

import com.unipass.server.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(name = "edu_email", unique = true)
    private String eduEmail;

    @Column(unique = true, length = 20)
    private String phone;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "zalo_phone", length = 20)
    private String zaloPhone;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "university_name")
    private String universityName;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name = "is_seller")
    private Boolean isSeller = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "is_student_verified")
    private Boolean isStudentVerified;

    @Column(name = "student_verified_at")
    private Instant studentVerifiedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<OTPVerification> otpVerifications = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserToken> tokens = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "buyer")
    private Set<Order> boughtOrders = new HashSet<>();  // Đơn hàng đã mua

    @Builder.Default
    @OneToMany(mappedBy = "seller")
    private Set<Order> soldOrders = new HashSet<>();    // Đơn hàng đã bán

    @Builder.Default
    @OneToMany(mappedBy = "buyer")
    private Set<Offer> sentOffers = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "reporterUser")
    private Set<Report> sentReports = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "reportedUser")
    private Set<Report> receivedReports = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Notification> notifications = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "buyer")
    private Set<Rating> sentRatings = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "seller")
    private Set<Rating> receivedRatings = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "buyer")
    private Set<Conversation> buyerConversations = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "seller")
    private Set<Conversation> sellerConversations = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "sender")
    private  Set<Message> messages = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @NonNull
    @Override
    public String getUsername() {
        return email;
    }

    @NonNull
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof User that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
