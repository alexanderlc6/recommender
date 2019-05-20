package com.sp.cf.controller;

import com.sp.cf.common.BeanValidators;
import com.sp.cf.dto.MahoutBaseItemDto;
import com.sp.cf.service.BaseItemRecommenderService;
import com.sp.cf.service.BaseUserRecommenderService;
import com.sp.cf.common.ResponseVO;
import com.sp.cf.dto.MahoutBaseUserDto;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;

/**
 * alexlu 2018.2.22
 */
@RestController
@RequestMapping("/recommander/mahout")
public class MahoutController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MahoutController.class);
    Validator validator = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory().getValidator();
    @Autowired
    private BaseUserRecommenderService baseUserRecommenderService;
    @Autowired
    private BaseItemRecommenderService baseItemRecommenderService;

    @PostMapping("/baseUserRecommender")
    public ResponseVO baseUserRecommender(@RequestBody MahoutBaseUserDto mahoutBaseUserDto){
        Map<String, String> constraintAttrs = BeanValidators.validate(validator, mahoutBaseUserDto);
        if (constraintAttrs != null && !constraintAttrs.isEmpty()) {
            return getFailureWithMap(1001, constraintAttrs);
        }
        try {
            List<RecommendedItem> list = baseUserRecommenderService.baseUserRecommender(mahoutBaseUserDto.getUserId(),mahoutBaseUserDto.getAmount());
            return getFromData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/baseItemRecommender")
    public ResponseVO baseUserRecommender(@RequestBody MahoutBaseItemDto mahoutBaseItemDto){
        Map<String, String> constraintAttrs = BeanValidators.validate(validator, mahoutBaseItemDto);
        if (constraintAttrs != null && !constraintAttrs.isEmpty()) {
            return getFailureWithMap(1001, constraintAttrs);
        }
        try {
            List<RecommendedItem> list = baseItemRecommenderService.baseItemRecommender(mahoutBaseItemDto.getUserId(),mahoutBaseItemDto.getItemId(),mahoutBaseItemDto.getAmount());
            return getFromData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
