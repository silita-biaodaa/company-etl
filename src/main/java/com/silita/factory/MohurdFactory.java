package com.silita.factory;

import com.silita.common.Constant;
import com.silita.common.redis.RedisUtils;
import com.silita.model.*;
import com.silita.service.MohurdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:chenzhiqiang
 * @Date:2018/7/5 21:33
 * @Description:全国四库一数据接收并处理工厂
 */
@Component
public class MohurdFactory extends AbstractFactory {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MohurdService mohurdService;

    @Override
    public void process(Object object) {
        if (object instanceof Company) {
            Company company = (Company) object;
            String com_id = company.getCom_id();
            String md5 = company.getMd5();
            boolean exists = redisUtils.hexists(Constant.REDIS_COMPANY, md5);
            if (!exists) {
                mohurdService.insertCompany(company);
                redisUtils.hset(Constant.REDIS_COMPANY, md5, com_id);
            } else {
                logger.info(String.format("=============%s 已存在！", md5));
            }
        } else if (object instanceof CompanyQualification) {

        } else if (object instanceof Project) {

        } else if (object instanceof Person) {

        } else if (object instanceof PersonChange) {

        } else if (object instanceof PersonProject) {

        } else if (object instanceof ProjectCompany) {

        } else if (object instanceof ZhaoTouBiao) {

        } else if (object instanceof ShiGongTuShenCha) {

        } else if (object instanceof ShiGongXuKe) {

        } else if (object instanceof HeTongBeiAn) {

        } else if (object instanceof JunGongBeiAn) {

        }
    }
}
