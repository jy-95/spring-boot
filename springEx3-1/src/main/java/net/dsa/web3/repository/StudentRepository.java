package net.dsa.web3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.dsa.web3.entity.StudentEntity;

@Repository
public interface StudentRepository 
	extends JpaRepository<StudentEntity, Integer>{

}
