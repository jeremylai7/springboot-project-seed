package com.jeremy.service.;

import com.jeremy.data..model.Aa;
import com.jeremy.data..dao.AaMapper;
import com.jeremy.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Auther: Jeremy
 * @Date: 2020-08-24
 */
@Service
public class AaServiceImpl extends BaseServiceImpl<Aa> implements AaService{

       @Resource
       private AaMapper aaMapper;

}
