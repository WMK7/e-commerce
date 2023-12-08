package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderItemDTO;
import com.example.ecommerce.entities.OrderItem;
import com.example.ecommerce.repositories.OrderItemRepository;
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
public class OrderItemService {

    @Autowired
    private OrderItemRepository repository;

    public OrderItemDTO findById(Long id){
        OrderItem orderitem = repository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Recurso não encontrado"));
        return new OrderItemDTO(orderitem);}


    @Transactional(readOnly = true)
    public Page<OrderItemDTO> findAll(Pageable pageable){
        Page<OrderItem> product = repository.findAll(pageable);
        return product.map(x -> new OrderItemDTO(x));
    }

    @Transactional
    public OrderItemDTO insert(OrderItemDTO dto){
        OrderItem entity = new OrderItem();
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        entity.setOrder(dto.getId().getOrder());
        entity.setProduct(dto.getId().getProduct());

        entity = repository.save(entity);

        return new OrderItemDTO(entity);
    }

    @Transactional
    public OrderItemDTO update (Long id, OrderItemDTO dto) {
        try {
            OrderItem entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            return new OrderItemDTO(entity);
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

    private void copyDtoToEntity(OrderItemDTO dto, OrderItem entity){
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        entity.setOrder(dto.getId().getOrder());
        entity.setProduct(dto.getId().getProduct());
    }

}
