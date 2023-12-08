package com.example.ecommerce.controllers;


import com.example.ecommerce.dto.CustomError;
import com.example.ecommerce.dto.OrderItemDTO;
import com.example.ecommerce.dto.ValidationError;
import com.example.ecommerce.service.OrderItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;

@RestController
@RequestMapping(value = "/orderitens")
public class OrderItemController {

    @Autowired
    private OrderItemService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderItemDTO> findById(@PathVariable Long id) {
        OrderItemDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public Page<OrderItemDTO> findAll(Pageable pageable){
        return service.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> insert(@Valid @RequestBody OrderItemDTO dto){
        /*dto = service.insert(dto);
        return dto;*/
        // se você quiser resumir pode retornar direto assim:
        //  service.insert(dto);
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OrderItemDTO> update(@PathVariable Long id, @Valid @RequestBody OrderItemDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        for (FieldError f: e.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }
        /*err.addError("name", "Mensagem de teste");
        err.addError("price", "Preço inválido");*/
        return ResponseEntity.status(status).body(err);
    }

}
