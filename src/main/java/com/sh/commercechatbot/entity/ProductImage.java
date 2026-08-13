package com.sh.commercechatbot.entity;


import com.sh.commercechatbot.enums.product.ProductImageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "product_image",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_variant_image_order",
                        columnNames = {"product_variant_id", "display_order"}
                )
        }
)
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductImageType imageType;

    @Column(name = "image_url", nullable = false, length = 1000)
    private String imageUrl;

    @Column(name = "object_key", nullable = false, length = 500)
    private String objectKey;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;


}
