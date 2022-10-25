package com.example.user_management.security;

import com.example.user_management.common.AppConstants;
import com.example.user_management.common.ScriptingUtil;
import com.example.user_management.other_service.entiry.ApiKeyStore;
import com.example.user_management.other_service.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class ApiKeyGenerator {

    private ApiKeyRepository repository;

    public ApiKeyGenerator(@Autowired ApiKeyRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init(){
        long count = repository.count();
        boolean isUpdate = false;
        if(count>0L){
            isUpdate = true;
        }
        for(String sId: AppConstants.SERVICE_PRODUCT_ID){
            String apiKey = ScriptingUtil.encodeBase64(sId);
            ApiKeyStore apiKeyStore = new ApiKeyStore();
            apiKeyStore.setServiceId(sId);
            apiKeyStore.setApiKey(apiKey);
            if(isUpdate){
                List<ApiKeyStore> apiKeyStoreList = repository.findAll();
                Optional<ApiKeyStore> apiKeyStoreOpt = apiKeyStoreList.stream().filter(api->api.getServiceId()
                        .equals(sId)).findFirst();
                if(apiKeyStoreOpt.isPresent()){
                    repository.updateByServiceId(apiKey,apiKeyStoreOpt.get().getId());
                }else{
                    repository.save(apiKeyStore);
                }
            }else{
                repository.save(apiKeyStore);
            }
        }
    }
}
