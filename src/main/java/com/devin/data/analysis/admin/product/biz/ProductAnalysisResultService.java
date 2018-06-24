package com.devin.data.analysis.admin.product.biz;

import com.devin.data.analysis.admin.product.dto.DaEveryColorData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductAnalysisResultService {

    public List<DaEveryColorData> querySelective(String productCode, Integer page, Integer size, String sort, String order);

    public int countSelective(String productCode, Integer page, Integer size, String sort, String order);

    public void add(DaEveryColorData daEveryColorData);

    public void updateById(DaEveryColorData daEveryColorData);

    public void deleteById(String idEveryColorData);

    public DaEveryColorData findById(String idEveryColorData);
}
