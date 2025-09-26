package com.codecraft.veterinary_pet_system.controller;

import com.codecraft.veterinary_pet_system.entity.Invoice;
import com.codecraft.veterinary_pet_system.dto.InvoiceDTO;
import com.codecraft.veterinary_pet_system.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @PostMapping
    public ResponseEntity<InvoiceDTO> addInvoice(@RequestBody InvoiceDTO dto) {
        return ResponseEntity.ok(invoiceService.addInvoice(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDTO> updateInvoice(
            @PathVariable Long id,
            @RequestBody InvoiceDTO dto) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping
//    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
//        return ResponseEntity.ok(invoiceService.createInvoice(invoice));
//    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Invoice>> getInvoicesByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(invoiceService.getInvoicesByOwner(ownerId));
    }
}
