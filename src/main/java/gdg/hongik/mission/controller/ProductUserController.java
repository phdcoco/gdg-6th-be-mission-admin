package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.ProductResponse;
import gdg.hongik.mission.dto.PurchaseRequest;
import gdg.hongik.mission.dto.PurchaseResponse;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductUserController {

    private final ProductService productService;

    public ProductUserController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ProductResponse getProduct(@RequestParam String name) {
        return productService.getProduct(name);
    }

    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody List<PurchaseRequest> requests) {
        return productService.purchase(requests);
    }
}
