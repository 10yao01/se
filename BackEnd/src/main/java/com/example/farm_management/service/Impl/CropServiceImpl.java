package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.CropMapper;
import com.example.farm_management.pojo.Crop;
import com.example.farm_management.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropServiceImpl implements CropService {

    @Autowired
    CropMapper cropMapper;

    @Override
    public List<Crop> list(String cid, String cname, String cinfo, Integer wtime, Integer ftime) {
        return cropMapper.list(cid, cname, cinfo, wtime, ftime);
    }

    @Override
    public void add(Crop crop) {
        cropMapper.insert(crop);
    }

    @Override
    public void delete(List<String> ids) {
        cropMapper.delete(ids);
    }

    @Override
    public void update(Crop crop) {
        cropMapper.update(crop);
    }
}
