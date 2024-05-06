package com.cristianml.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cristianml.models.AutorizarModel;

public interface IAutorizar extends JpaRepository<AutorizarModel, Integer> {

}
