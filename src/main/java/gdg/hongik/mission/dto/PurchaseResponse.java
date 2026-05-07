package gdg.hongik.mission.dto;

import java.util.List;

public record PurchaseResponse (
    int totalPrice,
    List<PurchaseItemResponse> items
) {}