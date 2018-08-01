package com.silita.service;


import com.google.common.collect.ImmutableMap;
import com.silita.consumer.RedisUtils;
import com.silita.dao.DeleteCostCompanyMapper;
import com.silita.model.Person;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 删除既有非造价企业又有造价企业的所有记录
 */
@Service
public class DeleteCostCompanyService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeleteCostCompanyMapper mapper;

    @Autowired
    private RedisUtils redisUtils;

    public static final ImmutableMap<String, String> provinces = ImmutableMap.<String, String>builder()
            .put("北京", "beij")
            .put("天津", "tianj")
            .put("河北", "hebei")
            .put("山西", "sanx")
            .put("内蒙古", "neimg")
            .put("辽宁", "liaon")
            .put("吉林", "jil")
            .put("黑龙江", "heilj")
            .put("上海", "shangh")
            .put("江苏", "jiangs")
            .put("浙江", "zhej")
            .put("安徽", "anh")
            .put("福建", "fuj")
            .put("江西", "jiangx")
            .put("山东", "shand")
            .put("河南", "henan")
            .put("湖北", "hubei")
            .put("广东", "guangd")
            .put("广西", "guangx")
            .put("海南", "hain")
            .put("重庆", "chongq")
            .put("四川", "sichuan")
            .put("贵州", "guiz")
            .put("云南", "yunn")
            .put("陕西", "shanxi")
            .put("甘肃", "gans")
            .put("青海", "qingh")
            .put("宁夏", "ningx")
            .put("湖南", "hunan")
            .put("新疆", "xinjiang")
            .put("西藏", "xizang")
            .build();

    public void deleteTask() {
        try {
            //第一步：根据企业名称、统一社会信用代码、组织机构代码、营业执照编号，查询出所有重复的造价和非造价企业
            //第二步：根据以上查出的com_id，删除企业信息、资质、人员，这三张表的信息（项目表及关系表由于造价企业没有项目，暂不考虑）
            //第三步：删除每条记录的id删除redis缓存
            List<Map<String, Object>> companys1 = mapper.selectAllCompanyByName();
            List<Map<String, Object>> companys2 = mapper.selectAllCompanyByCreditCode();
            List<Map<String, Object>> companys3 = mapper.selectAllCompanyByOrgCode();
            List<Map<String, Object>> companys4 = mapper.selectAllCompanyByBussNum();
            List<Map<String, Object>> companys = new ArrayList<>();
            companys.addAll(companys1);
            companys.addAll(companys2);
            companys.addAll(companys3);
            companys.addAll(companys4);
            logger.info(String.format("名字相同%s个，统一社会信用代码相同%s个，组织机构代码相同%s个，工商营业执照相同%s个，共%s个", companys1.size(), companys2.size(), companys3.size(), companys4.size(), companys.size()));
            for (Map<String, Object> company : companys) {
                String com_name = (String) company.get("com_name");
                String regis_address = (String) company.get("regis_address");
                if (StringUtils.isNotBlank(com_name) && StringUtils.isNotBlank(regis_address)) {
                    List<Map<String, Object>> companys_cost = mapper.selectCompany_Cost(com_name);
                    if (null != companys_cost && !companys_cost.isEmpty()) {
                        logger.info(String.format("正在处理：%s 查询出相同名字的企业%s个", com_name, companys_cost.size()));
                        for (Map<String, Object> com : companys_cost) {
                            String com_id = (String) com.get("com_id");
                            mapper.deleteCompanyById(com_id);
                            logger.info(String.format("id为%s的企业基本信息已删除", com_id));
                            long result1 = redisUtils.hdel("Cache_Company", com_id);
                            logger.info(String.format("id为%s的企业缓存删除状态：%s", com_id, result1));

                            List<Map<String, Object>> quals = mapper.selectCompanyQual_Cost(com_id);
                            if (null != quals && !quals.isEmpty()) {
                                for (Map<String, Object> qual : quals) {
                                    String pkid = (String) qual.get("pkid");
                                    mapper.deleteCompanyQualById(pkid);
                                    logger.info(String.format("id为%s的企业资质已删除", pkid));
                                    long result2 = redisUtils.hdel("Cache_CompanyQual", pkid);
                                    logger.info(String.format("id为%s的资质缓存删除状态：%s", pkid, result2));
                                }
                            }

                            for (Map.Entry<String, String> entry : provinces.entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue();
                                if (regis_address.contains(key)) {
                                    regis_address = value;
                                }
                            }
                            Person person = new Person();
                            person.setTable(regis_address);
                            person.setCom_id(com_id);

                            List<Map<String, Object>> persons = mapper.selectPerson__Cost(person);
                            if (null != persons && !persons.isEmpty()) {
                                for (Map<String, Object> p : persons) {
                                    String pkid = (String) p.get("pkid");
                                    mapper.deletePersonById(person);
                                    logger.info(String.format("id为%s的人员已删除", pkid));
                                    long result3 = redisUtils.hdel("Cache_Person", pkid);
                                    logger.info(String.format("id为%s的人员缓存删除状态：%s", pkid, result3));
                                }
                            }
                            logger.info(String.format("****************com_id为%s的相关数据删除完毕****************", com_id));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }
}