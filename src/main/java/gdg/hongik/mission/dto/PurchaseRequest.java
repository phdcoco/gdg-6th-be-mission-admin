package gdg.hongik.mission.dto;

public record PurchaseRequest (
    Long productId,
    int quantity
) {}