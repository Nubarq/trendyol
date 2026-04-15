package com.ecommerce.trendyoldemo.controller;


import com.ecommerce.trendyoldemo.dto.request.AddProductRequest;
import com.ecommerce.trendyoldemo.dto.response.ProductResponse;
import com.ecommerce.trendyoldemo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@MyRestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Bu api mehsul elave etmek ucundur",
    description = "Bunu yalniz sirket userleri ede bilir")
    @PostMapping("/addProduct")
    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody AddProductRequest request) {
        return ResponseEntity.ok(productService.addProduct(request));
    }

    @GetMapping("/getMyProducts")
    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    public ResponseEntity<Page<ProductResponse>> getMyProducts(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getMyProducts(page, size));
    }

    @GetMapping("/getAll")
    public Page<ProductResponse> getAllProducts(int page, int size) {
        return productService.getAllProducts(page, size);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    public ProductResponse updateProduct(@RequestParam Long id, @RequestBody AddProductRequest request){
        return productService.updateProduct(id, request);
    }
}
