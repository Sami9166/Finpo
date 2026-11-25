package com.finpo.api.etf;

import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/etfs")
public class EtfController {
    @GetMapping
    public List<EtfSummary> search(
            @RequestParam(required = false) String query
    ) {
        // 검색창에서 ETF 상품을 찾아줌
        return repository.search(query);
    }

    @GetMapping("/{code}")
    public Optional<Etf> detail(
            @PathVariable String code
    ) {
        // 목록에서 하나를 눌러서 디테일한 정보를 확인함.
        return repository.findByCode(code);
    }
}
