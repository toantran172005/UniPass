package com.unipass.server.enums;

public enum TransactionType {
    TOPUP,                  // Nạp tiền từ nguồn bên ngoài (Ngân hàng, Momo, ZaloPay) vào ví UniPass
    WITHDRAW,               // Rút tiền từ UniPass về tài khoản cá nhân
    ORDER_PAYMENT,          // Thanh toán tiền cho đơn hàng
    ORDER_RECEIVE,          // Người bán nhận tiền
    REFUND,                 // Hoàn tiền
    COMMISSION,             // Phí sàn
    PROMTION                // Tiền thưởng hoặc tiền vouchers
}
