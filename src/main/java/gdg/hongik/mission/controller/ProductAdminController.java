package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.ProductRequest;
import gdg.hongik.mission.dto.ProductResponse;
import gdg.hongik.mission.dto.StockRequest;
import gdg.hongik.mission.dto.StockResponse;
import gdg.hongik.mission.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
@CrossOrigin(origins = "*")
public class ProductAdminController {

    private final ProductService productService;

    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        System.out.println("POST /admin/products 호출됨");
        System.out.println(request);
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