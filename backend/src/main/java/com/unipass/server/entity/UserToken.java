package com.unipass.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_tokens", indexes = {
        // Query nhanh xem refresh_token này có hợp lệ không mỗi khi user xin cấp lại Access Token
        @Index(name = "idx_tokens_refresh", columnList = "refresh_token")
})
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;

    @Column(name = "expired_date")
    private Instant expiredDate;

    @Builder.Default
    private Boolean revoked = false;

    @Column(name = "created_at")
    private Instant createdAt;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof UserToken that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
