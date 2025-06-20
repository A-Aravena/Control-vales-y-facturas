package com.transporte.cl.models.DAO.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserAllDAO {
    private Long id;
    private String email;
    private String nombres;
    private Boolean notificacion;
    private String username;
}
