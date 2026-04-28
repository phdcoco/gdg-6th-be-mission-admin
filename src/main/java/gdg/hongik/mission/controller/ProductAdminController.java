package gdg.hongik.mission.controller;

import gdg.hongik.mission.Product;
import gdg.hongik.mission.ProductStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class ProductAdminController {

    // 1. 상품 등록
    @PostMapping
    public String createProduct(@RequestBody Product product) {

        for (Product p : ProductStore.products) {
            if (p.getName().equals(product.getName())) {
                throw new RuntimeException("이미 존재하는 상품입니다."); // 우선 500 던지게 하기
            }
        }

        product.setId(ProductStore.sequence++);

        ProductStore.products.add(product);
        return "상품 등록 완료";
    }

    // 2. 재고 추가
    @PatchMapping("/stock")
    public String addStock(@RequestBody Product request) {

        Long productId = request.getId();
        int quantity = request.getQuantity();

        for (Product p : ProductStore.products) {
            if (p.getId().equals(productId)) {

                p.setStock(p.getStock() + quantity);

                return p.getName() + " 재고: " + p.getStock();
            }
        }

        throw new RuntimeException("상품 없음");
    }

    // 3. 상품 삭제
    @DeleteMapping
    public List<Product> deleteProducts(@RequestBody List<Long> ids) {

        for (int i = 0; i < ProductStore.products.size(); i++) {

            Product p = ProductStore.products.get(i);

            if (ids.contains(p.getId())) {
                ProductStore.products.remove(i);
                i--;
            }
        }

        return ProductStore.products;
    }
}
