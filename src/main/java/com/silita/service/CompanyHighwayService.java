package com.silita.service;

import com.alibaba.fastjson.JSONObject;
import com.silita.common.RegionCommon;
import com.silita.dao.*;
import com.silita.model.*;
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
    @Autowired
    private AptitudeDictionaryMapper aptitudeDictionaryMapper;
    @Autowired
    private CompanyQualificationMapper companyQualificationMapper;
    @Autowired
    private TbCompanyAptitudeMapper tbCompanyAptitudeMapper;
    @Autowired
    private IAptitudeCleanService aptitudeCleanService;

    /**
     * 解析基本信息
     *
     * @param object
     */
    public Map<String, Object> analysisCompanyInfo(JSONObject object) {
        String comName = object.getString("corpName").replace(")", "）").replace("(", "）").replaceAll(" ", "");
        String md5 = object.getString("md5");
        String regisAddress = object.getString("regProvinceCode");
        String newRegisAddress = null;
        if ("兵团站".equals(regisAddress)) {
            newRegisAddress = "新疆维吾尔自治区";
        } else {
            for (Map.Entry<String, String> key : RegionCommon.regionSourcePinYin.entrySet()) {
                int index = regisAddress.indexOf(key.getKey());
                if (index > -1) {
                    newRegisAddress = getRegisAddress(regisAddress, key.getKey(), index);
                    break;
                }
            }
            if (null == newRegisAddress) {
                for (Map.Entry<String, String> key : RegionCommon.regionSourceShort.entrySet()) {
                    int index = regisAddress.indexOf(key.getKey());
                    if (index > -1) {
                        newRegisAddress = getRegisAddress(regisAddress, key.getKey(), index);
                        break;
                    }
                }
                newRegisAddress = RegionCommon.regionSourceShortName.get(newRegisAddress);
            }
        }

        String id;
        if (object.containsKey("comId")) {
            id = object.getString("comId");
        } else {
            id = object.getString("id");
        }
        String creditCode = object.getString("creditCode");
        Map<String, Object> param = new HashedMap(2) {{
            put("comName", comName);
        }};
        Map<String, Object> comMap = companyMapper.queryCompanyExist(param);
        if (MapUtils.isNotEmpty(comMap)) {
            StringBuffer channel = new StringBuffer(MapUtils.getString(comMap, "channel"));
            channel.setCharAt(1, '1');
            comMap.put("channel", channel.toString());
            companyMapper.updateCompanyChannel(comMap);
            comMap.put("com_name", comName);
            comMap.put("regisAddress", newRegisAddress);
            if (comMap.containsKey("pkid") && !comMap.containsKey("com_id_highway")) {
                companyMapper.updateCompanyRel(comMap);
            } else {
                comMap.put("com_id_highway", id);
                companyMapper.insertCompanyRel(comMap);
            }
            logger.info("----------------解析【" + comName + "】的基本信息完成--------------------------------");
            return comMap;
        }
        try {
            TbCompany company = new TbCompany();
            company.setComName(comName);
            company.setComId(id);
            company.setComNamePy(Pinyin.getPinYinFirstChar(comName));
            company.setCreditCode(creditCode);
            company.setLegalPerson(object.getString("legalRepresentative"));
            company.setSkillLeader(object.getString("technicalLeader"));
            company.setEconomicType(object.getString("natureType"));
            company.setRegisAddress(newRegisAddress);
            company.setRegisCapital(object.getString("regFund"));
            if (null != object.get("address") && StringUtils.isNotEmpty(object.getString("address"))) {
                company.setComAddress(object.getString("address"));
            }
            company.setBusinessNum(object.getString("businessLicence"));
            company.setChannel("010");
            company.setMd5(md5);
            companyMapper.insertCompany(company);
            comMap = new HashedMap(3);
            comMap.put("com_id", id);
            comMap.put("com_name", comName);
            comMap.put("com_id_highway", id);
            comMap.put("regisAddress", newRegisAddress);
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
            String province = object.getString("province");
            if (null == province) {
                return;
            }
            tabCode = RegionCommon.regionSourceShort.get(province);
            if (null == tabCode) {
                tabCode = RegionCommon.regionSourcePinYin.get(province);
            }
        }
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
            person.setInnerId(perId);
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
            if (StringUtils.isNotEmpty(pkid)) {
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
        if (StringUtils.isEmpty(projectTraffic.getComName())) {
            return;
        }
        Integer pkid = tbProjectTrafficMapper.queryProjectExits(projectTraffic);
        if (null != pkid && pkid > 0) {
            projectTraffic.setPkid(pkid);
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

    /**
     * 解析企业资质
     *
     * @param object
     */
    public void analysisCompanyQuals(JSONObject object) {
        Map<String, Object> company = this.analysisCompanyInfo(object);
        if (MapUtils.isEmpty(company)) {
            return;
        }
        String comId = MapUtils.getString(company, "com_id");
        List<Map<String, Object>> quals = (List<Map<String, Object>>) object.get("quals");
        String qualType;
        List<TbCompanyAptitude> aptitudes = new ArrayList<>();
        for (int i = 0, j = quals.size(); i < j; i++) {
            qualType = quals.get(i).get("isMain").toString();
            if (!"监理资质".equals(qualType)) {
                continue;
            }
            //保存企业资质 tb_company_qualification 表
            TbCompanyQualification tbCompanyQualification = new TbCompanyQualification();
            tbCompanyQualification.setPkid(CommonUtil.getUUID());
            tbCompanyQualification.setTab("企业资质资格");
            tbCompanyQualification.setQualType("监理资质");
            tbCompanyQualification.setCertNo(quals.get(i).get("caCode").toString().replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+", ""));
            tbCompanyQualification.setComId(comId);
            tbCompanyQualification.setComName(quals.get(i).get("corpName").toString());
            tbCompanyQualification.setCertOrg(quals.get(i).get("issueDepartment").toString());
            tbCompanyQualification.setCertDate(quals.get(i).get("validBeginDate").toString());
            tbCompanyQualification.setValidDate(quals.get(i).get("validEndDate").toString());
            tbCompanyQualification.setUrl("https://glxy.mot.gov.cn/company/getCompanyAptitudeList.do?comId=" + comId);
            tbCompanyQualification.setChannel(4);
            //判断是否是等级 如是等级需要取catype字段一起解析
            String grade = quals.get(i).get("grade").toString();
            String gradeCode = aptitudeDictionaryMapper.queryGradeCode(grade);
            String quaId;
            String qualCode;
            if (null != gradeCode && grade.indexOf("级") > -1) {
                String catype = quals.get(i).get("catype").toString();
                StringBuffer str = new StringBuffer(catype);
                str.append(grade);
                tbCompanyQualification.setQualName(str.toString());
                tbCompanyQualification.setRange(str.toString());
                qualCode = aptitudeDictionaryMapper.queryCodeByAlias(catype);
                quaId = aptitudeDictionaryMapper.queryPkidByParam(new HashedMap(2) {{
                    put("grade", gradeCode);
                    put("qual", qualCode);
                }});
            } else {
                //非等级资质
                qualCode = aptitudeDictionaryMapper.queryCodeByAlias(grade);
                quaId = aptitudeDictionaryMapper.queryPkidByParam(new HashedMap(2) {{
                    put("grade", "0");
                    put("qual", qualCode);
                }});
            }
            if (StringUtils.isEmpty(tbCompanyQualification.getRange())) {
                continue;
            }
            String pkid = companyQualificationMapper.queryCompanyQualficationExist(tbCompanyQualification);
            if (null != pkid) {
                companyQualificationMapper.updateCompanyQualfication(pkid);
            } else {
                companyQualificationMapper.inertCompanyQualfication(tbCompanyQualification);
                pkid = tbCompanyQualification.getPkid();
            }
            if (null != quaId) {
                TbCompanyAptitude aptitude = new TbCompanyAptitude();
                aptitude.setQualId(pkid);
                aptitude.setType("gonglu");
                aptitude.setComId(comId);
                aptitude.setAptitudeName(tbCompanyQualification.getQualName());
                aptitude.setAptitudeUuid(quaId);
                aptitude.setMainuuid(qualCode);
                aptitudes.add(aptitude);
            }
        }

        if (null != aptitudes && aptitudes.size() > 0) {
            //删除之前旧资质
            tbCompanyAptitudeMapper.deleteCompanyAptiudeByType(new HashedMap(2) {{
                put("comId", comId);
                put("type", "gonglu");
            }});
            //新增资质
            tbCompanyAptitudeMapper.batchInsertCompanyAptitude(aptitudes);
            //更新企业range字段
            aptitudeCleanService.updateCompanyAptitude(comId);
            logger.info("----------------解析企业资质完成--------------------");
        }
    }

    /**
     * 获取新的注册地
     *
     * @param oldRegisAddress 不规范的注册地
     * @param key
     * @return
     */
    private String getRegisAddress(String oldRegisAddress, String key, int index) {
        String newRegisAddress;
        int regisLeg = oldRegisAddress.length();
        int keyLeg = key.length();
        if (regisLeg > keyLeg) {
            newRegisAddress = oldRegisAddress.substring(index, keyLeg + index);
        } else {
            newRegisAddress = oldRegisAddress;
        }
        return newRegisAddress;
    }
}
