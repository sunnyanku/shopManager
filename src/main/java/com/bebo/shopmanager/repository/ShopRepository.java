package com.bebo.shopmanager.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bebo.shopmanager.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findByShopName(String shopName);
    
   
}

