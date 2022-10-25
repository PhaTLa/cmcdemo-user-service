package com.example.user_management.other_service.entiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "api_key_store")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiKeyStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "service_id")
    String serviceId;

    @Column(name = "api_key")
    String apiKey;
}
