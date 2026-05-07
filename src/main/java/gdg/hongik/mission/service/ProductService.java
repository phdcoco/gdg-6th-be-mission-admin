package gdg.hongik.mission.service;

import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 조회
    public Product getProduct(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("상품 없음"));
    }

    // 상품 등록
    public String createProduct(Product product) {

        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new RuntimeException("이미 존재하는 상품입니다.");
        }

        productRepository.save(product);
        return "상품 등록 완료";
    }

    // 구매
    public String purchase(List<Product> requests) {

        int totalPrice = 0;
        String result = "";

        for (Product request : requests) {

            Product product = productRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("상품 없음"));

            if (product.getStock() < request.getQuantity()) {
                throw new RuntimeException("재고 부족");
            }

            // 지금은 DB 반영 안 됨 (의도된 상태)
            product.setStock(product.getStock() - request.getQuantity());

            int price = product.getPrice() * request.getQuantity();
            totalPrice += price;

            result += product.getName() + " "
                    + request.getQuantity() + "개 구매 ("
                    + price + "원)\n";
        }

        result += "총 금액: " + totalPrice;

        return result;
    }

    // 재고 추가
    public String addStock(Product request) {

        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("상품 없음"));

        product.setStock(product.getStock() + request.getQuantity());

        return product.getName() + " 재고: " + product.getStock();
    }

    // 삭제
    public List<Product> deleteProducts(List<Long> ids) {

        // JPA 기본 메서드 사용
        productRepository.deleteAllById(ids);

        List<Product> products = productRepository.findAll();
        List<Product> result = new ArrayList<>();

        for (Product p : products) {
            Product summary = new Product();
            summary.setName(p.getName());
            summary.setStock(p.getStock());
            result.add(summary);
        }

        return result;
    }
}