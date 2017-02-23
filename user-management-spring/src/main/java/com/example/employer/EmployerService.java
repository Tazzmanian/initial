/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author msol-pc
 */
@Service
public class EmployerService {

    @Autowired
    private EmployerRepository repo;

    public EmployerDTO save(Employer empl) {
        return EmployerMapper.mapEntityIntoDTO(repo.save(empl));
    }

    public Page<EmployerDTO> getAllEmployers(Pageable pageRequest) {
        Page<Employer> employers = repo.findAll(pageRequest);
        return EmployerMapper.mapEntityPageIntoDTOPage(pageRequest, employers);
    }

    public Employer getByUsername(String username) {
        return repo.findByUserUserName(username);
    }

    public Employer changeActive(Long id) {
        Employer empl = repo.findOne(id);
        empl.getUser().setEnabled(!empl.getUser().isEnabled());
        repo.save(empl);
        return empl;
    }

}
