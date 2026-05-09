package com.unipass.server.enums;

public enum ProductStatus {
    DRAFT,          // Nháp (User chưa muốn đăng)
    ACTIVE,         // Đang hiển thị trên sàn
    SOLD_OUT,       // Hết hàng
    HIDDEN,         // Người bán tạm ẩn đi
    BANNED          // Vi phạm chính sách, bị Admin khóa
}
