package com.leadme.core.dto;

@Getter
public class OrderDto {
    private Long orderId;
    private Long price;
    private String payment;
    private LocalDateTime orderDate;
    private LocalDateTime payDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Member member;
    private ProgDaily progDaily;
    
    @Builder
    public OrderDto(Long price, String payment, LocalDateTime orderDate, LocalDateTime payDate, OrderStatus status, Member member, ProgDaily progDaily) {
        this.price = price;
        this.payment = payment;
        this.orderDate = orderDate;
        this.payDate = payDate;
        this.status = status;
        this.member = member;
        this.progDaily = progDaily;
    }
    
    public Orders toEntity() {
        return Orders.builder()
          .price(price)
          .payment(payment)
          .orderDate(orderDate)
          .payDate(payDate)
          .status(status)
          .member(member)
          .progDaily(progDaily)
          .build();
    }
    
    public OrderDto(Orders order) {
        this.orderId = order.getOrderId();
        this.price = order.getPrice();
        this.payment = order.getPayment();
        this.orderDate = order.getOrderDate();
        this.payDate = order.getPayDate();
        this.status = order.getStatus();
        this.member = order.getMember();
        this.progDaily = order.getProgDaily();
    }
}
