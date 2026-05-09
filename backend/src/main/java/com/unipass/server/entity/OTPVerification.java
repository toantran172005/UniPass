package com.unipass.server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "otp_verifications", indexes = {
        // query lấy mã OTP mới nhất của user ra verify
        @Index(name = "idx_otp_user_created", columnList = "user_id, created_at DESC")
})
public class OTPVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String email;

    @Column(name = "otp_code", columnDefinition = "CHAR(6)")
    private String otpCode;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Builder.Default
    private Integer attempts = 0;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof OTPVerification that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
