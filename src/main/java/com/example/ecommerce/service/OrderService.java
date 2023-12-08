package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.repositories.OrderRepository;
import com.example.ecommerce.service.exceptions.DatabaseException;
import com.example.ecommerce.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public OrderDTO findById(Long id){
        /*Optional<Product> result = repository.findById(id);
        Product product = result.get();
        ProductDTO dto = new ProductDTO(product); return dto;*/
        Order order = repository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Recurso não encontrado"));
        return new OrderDTO(order);}


    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable){
        Page<Order> order = repository.findAll(pageable);
        return order.map(x -> new OrderDTO(x));
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto){
        Order entity = new Order();
        entity.setMoment(dto.getMoment());
        entity.setStatus(dto.getStatus());

        entity = repository.save(entity);

        return new OrderDTO(entity);
    }

    @Transactional
    public OrderDTO update (Long id, OrderDTO dto) {
        try {
            Order entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            return new OrderDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional (propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        try{
            repository.deleteById(id);
        } catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        } catch(DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integrigadade referencial");
        }
    }

    private void copyDtoToEntity(OrderDTO dto, Order entity){
        entity.setMoment(dto.getMoment());
        entity.setStatus(dto.getStatus());
    }

}
