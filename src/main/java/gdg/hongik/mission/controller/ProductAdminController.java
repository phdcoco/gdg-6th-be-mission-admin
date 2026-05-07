package gdg.hongik.mission.controller;

import gdg.hongik.mission.entity.Product;
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
    public String createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PatchMapping("/stock")
    public String addStock(@RequestBody Product product) {
        return productService.addStock(product);
    }

    @DeleteMapping
    public List<Product> deleteProducts(@RequestBody List<Long> ids) {
        return productService.deleteProducts(ids);
    }
}
