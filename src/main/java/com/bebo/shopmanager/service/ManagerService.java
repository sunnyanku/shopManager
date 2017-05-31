package com.bebo.shopmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bebo.shopmanager.dto.ManagerDTO;
import com.bebo.shopmanager.entity.Manager;
import com.bebo.shopmanager.repository.RepositoryFactory;

@Service
public class ManagerService {

	@Autowired
	private RepositoryFactory repositoryFactory;


	public List<ManagerDTO> findAll(){
		List<Manager> managers =  repositoryFactory.getManagerRepository().findAll();

		List<ManagerDTO> managerDTOs = new ArrayList<ManagerDTO>();

		for (Manager manager : managers) {
			ManagerDTO managerDTO = new ManagerDTO(manager.getId(),manager.getFirstName(), manager.getLastName(), manager.getUsername());
			managerDTOs.add(managerDTO);
		}

		return managerDTOs;
	}


	public ManagerDTO findByUsername(String username) {
		Manager manager =  repositoryFactory.getManagerRepository().findByUsername(username);
		
		ManagerDTO managerDTO = null;
		if(manager!=null){
			managerDTO = new ManagerDTO(manager.getId(),manager.getFirstName(), manager.getLastName(), manager.getUsername());
		}
					
		return managerDTO;
	}


	public ManagerDTO save(ManagerDTO managerDTO) {
		 Manager manager  = new Manager(managerDTO.getFirstName(), managerDTO.getLastName(),managerDTO.getUsername());
		 repositoryFactory.getManagerRepository().save(manager);
		 managerDTO.setId(manager.getId());
		 return managerDTO;
	}


}
