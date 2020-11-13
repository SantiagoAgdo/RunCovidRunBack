package com.runcovidrunback.runcovidrun.services;

import com.runcovidrunback.runcovidrun.entity.GetData;
import com.runcovidrunback.runcovidrun.repository.GetDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DataService {
    @Autowired
    GetDataRepository dataRepository;

    public List<GetData> data(){
        return dataRepository.findAll();
    }

}
