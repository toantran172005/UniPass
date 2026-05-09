package com.unipass.server.entity;

import com.unipass.server.enums.ProductCondition;
import com.unipass.server.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products", indexes = {
        // Lọc sản phẩm theo danh mục
        @Index(name = "idx_products_category", columnList = "category_id"),
        // Lọc sản phẩm đang bán (ACTIVE)
        @Index(name = "idx_products_status_created", columnList = "status, created_at DESC")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 12)
    private BigDecimal price;

    @Builder.Default
    @Column(name = "stock_quantity")
    private int stockQuantity = 1;

    @Enumerated(EnumType.STRING)
    private ProductCondition condition;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.DRAFT;

    @Column(name = "weight_gram")
    private Integer weightGram;

    @Column(name = "length_cm")
    private Integer lengthCm;

    @Column(name = "width_cm")
    private Integer width;

    @Column(name = "height_cm")
    private Integer height;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> productImages = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetails = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "reportedProduct")
    private Set<Report> receivedReports = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private Set<Offer> offers = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private Set<Conversation> conversations = new HashSet<>();

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Product that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
