package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.ProductRequest;
import gdg.hongik.mission.dto.StockRequest;
import gdg.hongik.mission.dto.StockResponse;
import gdg.hongik.mission.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final ProductService productService;

    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public String createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PatchMapping("/stock")
    public StockResponse addStock(@RequestBody StockRequest request) {
        return productService.addStock(request);
    }

    @DeleteMapping
    public List<StockResponse> deleteProducts(@RequestBody List<Long> ids) {
        return productService.deleteProducts(ids);
    }
}