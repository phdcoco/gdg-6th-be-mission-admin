package gdg.hongik.mission.controller;

import gdg.hongik.mission.Product;
import gdg.hongik.mission.ProductStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductUserController {

    // 1. 상품 조회, stream 사용 대신 for 문으로 쉽게 구현해보았습니다..
    @GetMapping
    public Product getProduct(@RequestParam String name) {
        for (Product p : ProductStore.products) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        throw new RuntimeException("상품 없음");
    }

    // 2. 상품 구매
    @PostMapping("/purchase")
    public String purchase(@RequestBody List<Product> requests) {

        int totalPrice = 0;
        String result = "";

        for (Product request : requests) {

            Product found = null;

            // 상품 찾기
            for (Product p : ProductStore.products) {
                if (p.getId().equals(request.getId())) {
                    found = p;
                    break;
                }
            }

            if (found == null) {
                throw new RuntimeException("상품 없음");
            }

            // 재고 확인
            if (found.getStock() < request.getQuantity()) {
                throw new RuntimeException("재고 부족");
            }

            // 재고 감소
            found.setStock(found.getStock() - request.getQuantity());

            int price = found.getPrice() * request.getQuantity();
            totalPrice += price;

            result += found.getName() + " "
                    + request.getQuantity() + "개 구매 ("
                    + price + "원)\n";
        }

        result += "총 금액: " + totalPrice;

        return result; // 그냥 String으로 반환시키기
    }
}
