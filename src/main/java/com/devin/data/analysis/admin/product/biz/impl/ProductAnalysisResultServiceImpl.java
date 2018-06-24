package com.devin.data.analysis.admin.product.biz.impl;

import com.devin.data.analysis.admin.product.biz.ProductAnalysisResultService;
import com.devin.data.analysis.admin.product.dto.DaEveryColorData;
import com.devin.data.analysis.admin.product.dto.example.DaEveryColorDataExample;
import com.devin.data.analysis.admin.product.integration.DaEveryColorDataMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductAnalysisResultServiceImpl implements ProductAnalysisResultService {

    @Resource
    private DaEveryColorDataMapper daEveryColorDataMapper;

    public List<DaEveryColorData> queryByGid(String productCode) {
        DaEveryColorDataExample example = new DaEveryColorDataExample();
        example.or().andProductCodeEqualTo(productCode);
        return daEveryColorDataMapper.selectByExample(example);
    }

    @Override
    public List<DaEveryColorData> querySelective(String productCode, Integer page, Integer size, String sort, String order) {
        DaEveryColorDataExample example = new DaEveryColorDataExample();
        DaEveryColorDataExample.Criteria criteria = example.createCriteria();

        if (productCode != null) {
            criteria.andProductCodeEqualTo(productCode);
        }
        PageHelper.startPage(page, size);
        return daEveryColorDataMapper.selectByExample(example);
    }

    @Override
    public int countSelective(String productCode, Integer page, Integer size, String sort, String order) {
        DaEveryColorDataExample example = new DaEveryColorDataExample();
        DaEveryColorDataExample.Criteria criteria = example.createCriteria();

        if (productCode != null) {
            criteria.andProductCodeEqualTo(productCode);
        }
        return (int) daEveryColorDataMapper.countByExample(example);
    }

    public void updateById(DaEveryColorData daEveryColorData) {
        daEveryColorDataMapper.updateByPrimaryKeySelective(daEveryColorData);
    }

    public void deleteById(String idEveryColorData) {
        daEveryColorDataMapper.deleteByPrimaryKey(idEveryColorData);
    }

    public void add(DaEveryColorData daEveryColorData) {
        daEveryColorDataMapper.insertSelective(daEveryColorData);
    }

    public DaEveryColorData findById(String idEveryColorData) {
        return daEveryColorDataMapper.selectByPrimaryKey(idEveryColorData);
    }

}
