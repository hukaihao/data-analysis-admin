package com.devin.data.analysis.admin.product.web;

import com.devin.data.analysis.admin.core.util.ResponseUtil;
import com.devin.data.analysis.admin.login.annotation.LoginAdmin;
import com.devin.data.analysis.admin.product.biz.ProductAnalysisResultService;
import com.devin.data.analysis.admin.product.dto.DaEveryColorData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/analiysis")
public class AnalysisResultController {

    private final Log logger = LogFactory.getLog(AnalysisResultController.class);

    @Autowired
    private ProductAnalysisResultService productAnalysisResultService;

    @GetMapping("/list")
    public Object list(@LoginAdmin String idDaAdmin,
                       String productCode,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order) {
        if (idDaAdmin == null) {
            return ResponseUtil.unlogin();
        }

        List<DaEveryColorData> analiysisList = productAnalysisResultService.querySelective(productCode, page, limit, sort, order);
        int total = productAnalysisResultService.countSelective(productCode, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", analiysisList);
        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin String idDaAdmin, @RequestBody DaEveryColorData daEveryColorData) {
        if (idDaAdmin == null) {
            return ResponseUtil.unlogin();
        }
        productAnalysisResultService.add(daEveryColorData);
        return ResponseUtil.ok(daEveryColorData);
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin String idDaAdmin, String idEveryColorData) {
        if (idDaAdmin == null) {
            return ResponseUtil.unlogin();
        }

        if (idEveryColorData == null) {
            return ResponseUtil.badArgument();
        }

        DaEveryColorData daEveryColorData = productAnalysisResultService.findById(idEveryColorData);
        return ResponseUtil.ok(daEveryColorData);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin String idDaAdmin, @RequestBody DaEveryColorData daEveryColorData) {
        if (idDaAdmin == null) {
            return ResponseUtil.unlogin();
        }
        productAnalysisResultService.updateById(daEveryColorData);
        return ResponseUtil.ok(daEveryColorData);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin String idDaAdmin, @RequestBody DaEveryColorData daEveryColorData) {
        if (idDaAdmin == null) {
            return ResponseUtil.unlogin();
        }
        productAnalysisResultService.deleteById(daEveryColorData.getIdEveryColorData());
        return ResponseUtil.ok();
    }

}
