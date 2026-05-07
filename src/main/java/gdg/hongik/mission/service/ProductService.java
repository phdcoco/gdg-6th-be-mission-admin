package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import jakarta.transaction.Transactional;
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
    public ProductResponse getProduct(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("상품 없음"));

        return new ProductResponse(
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }

    // 상품 등록
    public String createProduct(ProductRequest request) {
        if (productRepository.findByName(request.name()).isPresent()) {
            throw new RuntimeException("이미 존재하는 상품입니다.");
        }

        Product product = new Product();

        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());
        productRepository.save(product);

        return "상품 등록 완료";
    }

    // 구매
    @Transactional
    public PurchaseResponse purchase(List<PurchaseRequest> requests) {

        int totalPrice = 0;
        List<PurchaseItemResponse> items = new ArrayList<>();

        for (PurchaseRequest request : requests) {

            // Optional 이기 때문에 한 번에 예외처리
            Product product = productRepository.findById(request.productId())
                    .orElseThrow(() -> new RuntimeException("상품 없음"));

            if (product.getStock() < request.quantity()) {
                throw new RuntimeException("재고 부족");
            }

            product.setStock(product.getStock() - request.quantity());

            int price = product.getPrice() * request.quantity();
            totalPrice += price;

            items.add(new PurchaseItemResponse(
                    product.getName(),
                    request.quantity(),
                    price
            ));
        }

        return new PurchaseResponse(totalPrice, items);
    }

    // 재고 추가
    @Transactional
    public StockResponse addStock(StockRequest request) {

        // Optional이기 때문이 한 번에 예외처리
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("상품 없음"));

        product.setStock(product.getStock() + request.quantity());

        return new StockResponse(
                product.getName(),
                product.getStock()
        );
    }

    // 삭제
    @Transactional
    public List<StockResponse> deleteProducts(List<Long> ids) {

        productRepository.deleteAllById(ids);

        List<Product> products = productRepository.findAll();
        List<StockResponse> result = new ArrayList<>();

        for (Product p : products) {
            result.add(new StockResponse(
                    p.getName(),
                    p.getStock()
            ));
        }

        return result;
    }
}