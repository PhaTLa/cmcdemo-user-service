package com.example.user_management.other_service.repository;

import com.example.user_management.other_service.entiry.ApiKeyStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKeyStore,Long> {

    @Modifying
    @Transactional
    @Query("UPDATE ApiKeyStore api SET api.apiKey = :apikey WHERE api.id = :serviceId")
    Integer updateByServiceId(@Param("apikey") String apiKey,@Param("serviceId") Long serviceId);
}
