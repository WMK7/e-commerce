package com.example.ecommerce.service;

import com.example.ecommerce.dto.PaymentDTO;
import com.example.ecommerce.entities.Payment;
import com.example.ecommerce.repositories.PaymentRepository;
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
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public PaymentDTO findById(Long id){
        /*Optional<Product> result = repository.findById(id);
        Product product = result.get();
        ProductDTO dto = new ProductDTO(product); return dto;*/
        Payment payment = repository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Recurso não encontrado"));
        return new PaymentDTO(payment);}

    @Transactional(readOnly = true)
    public Page<PaymentDTO> findAll(Pageable pageable){
        Page<Payment> product = repository.findAll(pageable);
        return product.map(x -> new PaymentDTO(x));
    }


    @Transactional
    public PaymentDTO insert(PaymentDTO dto){
        Payment entity = new Payment();
        entity.setMoment(dto.getMoment());
        entity.setOrder(dto.getOrder());

        entity = repository.save(entity);

        return new PaymentDTO(entity);
    }


    @Transactional
    public PaymentDTO update (Long id, PaymentDTO dto) {
        try {
            Payment entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            return new PaymentDTO(entity);
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


    private void copyDtoToEntity(PaymentDTO dto, Payment entity){
        entity.setMoment(dto.getMoment());
    }


}
