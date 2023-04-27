package com.dado.quanlytailieu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "id", "type", "amount", "paymentMethod", "status", "transCreatedAt",
        "walletMoney", "walletBonus", "walletBalance",
        "orderId", "orderDetail", "orderCreatedAt", "extTransDetail"})
public class TransCsv {

    @JsonProperty("Mã giao dịch")
    private Integer id;

    @JsonProperty("Loại\ngiao dịch")
    private String type;

    @JsonProperty("Số tiền")
    private Integer amount;

    @JsonProperty("Phương thức\nthanh toán")
    private String paymentMethod;

    @JsonProperty("Trạng thái")
    private String status;

    @JsonProperty("Thời gian\ntạo giao dịch")
    private String transCreatedAt;

    @JsonProperty("Biến động ví\n(tiền)")
    private Integer walletMoney;

    @JsonProperty("Biến động ví\n(bonus)")
    private Integer walletBonus;

    @JsonProperty("Số dư ví\nsau khi giao dịch")
    private String walletBalance;

    @JsonProperty("Mã đơn hàng")
    private Integer orderId;

    @JsonProperty("Thông tin\nđơn hàng")
    private String orderDetail;

    @JsonProperty("Thời gian\ntạo đơn hàng")
    private String orderCreatedAt;

    @JsonProperty("Thông tin\nchuyển tiền")
    private String extTransDetail;
}
