package gdg.hongik.mission.dto;

public record PurchaseItemResponse (
    String name,
    int quantity,
    int price
) {}