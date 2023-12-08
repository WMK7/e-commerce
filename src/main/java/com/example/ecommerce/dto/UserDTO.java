package com.example.ecommerce.dto;

import com.example.ecommerce.entities.Product;
import com.example.ecommerce.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    private Long id;
    @Size(min = 3, max = 100, message = "Nome precisa ter entre 3 e 100 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;
    @Size(min = 3, max = 100, message = "E-mail precisa ter entre 3 e 100 caracteres")
    @NotBlank(message = "Campo requerido")
    private String email;
    @Size(min = 8, max = 20, message = "Informe um número válido! Tamanho máximo de 20 caracteres")
    @NotBlank(message = "Campo requerido")
    private String phone;
    @Size(min = 8, max = 20, message = "Informe uma data válida! Tamanho máximo de 20 caracteres")
    @NotBlank(message = "Campo requerido")
    private String birthDate;
    @Size(min = 6, max = 32, message = "Senha precisa ter entre 6 e 32 caracteres")
    @NotBlank(message = "Campo requerido")
    private String password;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, String phone, String birthDate, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.password = password;
    }

    public UserDTO(User entity){
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        phone = entity.getPhone();
        birthDate = entity.getBirthDate();
        password = entity.getPassword();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
