package com.unipass.server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "recipient_name")
    private String recipientName;

    private String phone;

    @Column(name = "address_line", columnDefinition = "TEXT")
    private String addressLine;

    @Column(length = 1000)
    private String campus;

    @Column(precision = 12, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 12, scale = 8)
    private BigDecimal longitude;

    private String district;

    private String city;

    @Column(name = "is_default")
    private Boolean isDefault;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Address that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

