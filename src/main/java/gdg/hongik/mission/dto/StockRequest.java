package gdg.hongik.mission.dto;

public record StockRequest (
    Long productId,
    int quantity
) {}