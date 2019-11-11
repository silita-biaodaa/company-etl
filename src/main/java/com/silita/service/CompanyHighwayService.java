package com.silita.service;

import com.alibaba.fastjson.JSONObject;
import com.silita.common.RegionCommon;
import com.silita.dao.CompanyMapper;
import com.silita.dao.TbPersonMapper;
import com.silita.dao.TbProjectTrafficMapper;
import com.silita.model.TbCompany;
import com.silita.model.TbPerson;
import com.silita.model.TbProjectTraffic;
import com.silita.model.TbTrafficPerson;
import com.silita.utils.CommonUtil;
import com.silita.utils.Pinyin;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 公路公司解析
 * Created by zhushuai on 2019/11/11.
 */
@Service
public class CompanyHighwayService {

    private static Logger logger = LoggerFactory.getLogger(CompanyHighwayService.class);

    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private TbPersonMapper tbPersonMapper;
    @Autowired
    private TbProjectTrafficMapper tbProjectTrafficMapper;

    /**
     * 解析基本信息
     *
     * @param object
     */
    public Map<String, Object> analysisCompanyInfo(JSONObject object) {
        String comName = object.getString("corpName");
        String md5 = object.getString("md5");
        String regisAddress = object.getString("regProvinceCode");
        String id;
        if (object.containsKey("comId")) {
            id = object.getString("comId");
        } else {
            id = object.getString("id");
        }
        String creditCode = object.getString("creditCode");
        logger.info("----------------解析【" + comName + "】的基本信息--------------------------------");
        Map<String, Object> param = new HashedMap(2) {{
            put("comName", comName);
            put("creditCode", creditCode);
        }};
        Map<String, Object> comMap = companyMapper.queryCompanyExist(param);
        if (MapUtils.isNotEmpty(comMap)) {
            StringBuffer channel = new StringBuffer(MapUtils.getString(comMap, "channel"));
            channel.setCharAt(1, '1');
            comMap.put("channel", channel.toString());
            companyMapper.updateCompanyChannel(comMap);
            comMap.put("com_name", comName);
            comMap.put("regisAddress", regisAddress);
            if (comMap.containsKey("pkid") && !comMap.containsKey("com_id_highway")) {
                companyMapper.updateCompanyRel(comMap);
            } else {
                comMap.put("com_id_highway", id);
                companyMapper.insertCompanyRel(comMap);
            }
            logger.info("----------------解析【" + comName + "】的基本信息:企业已存在，修改企业的[channel]字段--------------------------------");
            return comMap;
        }
        try {
            TbCompany company = new TbCompany();
            company.setComName(comName);
            company.setComId(id);
            company.setComNamePy(Pinyin.getPinYinFirstChar(comName));
            company.setCreditCode(creditCode);
            company.setLegalPerson(object.getString("enterpriseLeader"));
            company.setSkillLeader(object.getString("technicalLeader"));
            company.setEconomicType(object.getString("natureType"));
            company.setRegisAddress(regisAddress);
            company.setChannel("010");
            company.setMd5(md5);
            companyMapper.insertCompany(company);
            comMap = new HashedMap(3);
            comMap.put("com_id", id);
            comMap.put("com_name", comName);
            comMap.put("com_id_highway", id);
            comMap.put("regisAddress", regisAddress);
            companyMapper.insertCompanyRel(comMap);
            logger.info("----------------解析【" + comName + "】的基本信息完成--------------------------------");
        } catch (Exception e) {
            logger.error("解析企业" + comName + "失败！！！", e);
        }
        return comMap;
    }

    /**
     * 解析企业人员证书
     *
     * @param object
     */
    public void analysisCompanyPersonCert(JSONObject object) {
        Map<String, Object> company = this.analysisCompanyInfo(object);
        if (MapUtils.isEmpty(company) || null == company.get("regisAddress")) {
            return;
        }
        String comId = MapUtils.getString(company, "com_id");
        String regisAddress = MapUtils.getString(company, "regisAddress");
        String tabCode = RegionCommon.regionSourcePinYin.get(regisAddress);
        if (null == tabCode) {
            return;
        }
        List<Map<String, Object>> certs = (List<Map<String, Object>>) object.get("certs");
        String name = object.getString("name");
        String sex = object.getString("sex");
        String idCard = object.getString("idCard").replaceAll("(\\d{10})\\d{6}(\\w{2})", "$1*****$2");
        String perId = object.getString("id");
        logger.info("--------------解析人员证书开始:name【" + name + "】-----------------------------");
        for (int i = 0, j = certs.size(); i < j; i++) {
            int type = object.getInteger("type");
            TbPerson person = new TbPerson();
            person.setTabCode(tabCode);
            person.setPerId(perId);
            person.setType(type);
            person.setName(name);
            person.setSex(sex);
            person.setIdCard(idCard);
            person.setComId(comId);
            person.setComName(object.getString("company"));
            person.setCertNo(certs.get(i).get("regCANumber").toString());
            if (null != certs.get(i).get("regValidityTerm")) {
                person.setValidDate(certs.get(i).get("regValidityTerm").toString());
            }
            if (null != certs.get(i).get("regAnnounceDate")) {
                person.setCertDate(certs.get(i).get("regAnnounceDate").toString());
            }
            if (type == 1) {
                person.setCategory(certs.get(i).get("regLevel").toString());
                person.setMajor(certs.get(i).get("regName").toString());
            } else {
                person.setCategory(certs.get(i).get("regTypeName").toString());
            }
            String pkid = tbPersonMapper.queryPersonCertExist(person);
            if (null != pkid) {
                tbPersonMapper.updateDate(person);
            } else {
                person.setPkid(CommonUtil.getUUID());
                tbPersonMapper.insertPerson(person);
            }
        }
        logger.info("--------------解析人员证书结束:name【" + name + "】-----------------------------");
    }

    /**
     * 解析企业业绩
     *
     * @param object
     */
    public void analysisCompanyProject(JSONObject object) {
        TbProjectTraffic projectTraffic = new TbProjectTraffic(object);
        if (StringUtils.isEmpty(projectTraffic.getProWhere())) {
            return;
        }
        Integer pkid = tbProjectTrafficMapper.queryProjectExits(projectTraffic);
        if (null != pkid && pkid > 0) {
            tbProjectTrafficMapper.updateProjectTraffic(projectTraffic);
        } else {
            tbProjectTrafficMapper.insertProjectTraffic(projectTraffic);
        }
        if (object.containsKey("persons")) {
            List<Map<String, Object>> persons = (List<Map<String, Object>>) object.get("persons");
            if (null != persons && persons.size() > 0) {
                int proId = projectTraffic.getPkid();
                int j = persons.size();
                List<TbTrafficPerson> pers = new ArrayList<>(j);
                for (int i = 0; i < j; i++) {
                    pers.add(new TbTrafficPerson(proId, persons.get(i)));
                }
                tbProjectTrafficMapper.deleteProjectPerson(proId);
                tbProjectTrafficMapper.batchTrafficPerson(pers);
            }

        }
        logger.info("--------------解析企业业绩完成-----------------------------");
    }
}
