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
        if (StringUtils.isEmpty(projectTraffic.getProWhere()) || StringUtils.isEmpty(projectTraffic.getComName())) {
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

    /**
     * 解析企业人员证书
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
            if ("监理资质".equals(qualType)) {
                continue;
            }
            //保存企业资质 tb_company_qualification 表
            TbCompanyQualification tbCompanyQualification = new TbCompanyQualification();
            tbCompanyQualification.setPkid(CommonUtil.getUUID());
            tbCompanyQualification.setTab("企业资质资格");
            tbCompanyQualification.setQualType("监理资质");
            tbCompanyQualification.setCertNo(quals.get(i).get("caCode").toString());
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
            if (null != gradeCode && gradeCode.indexOf("级") > -1) {
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
            if (null != quaId) {
                TbCompanyAptitude aptitude = new TbCompanyAptitude();
                String pkid = companyQualificationMapper.queryCompanyQualficationExist(tbCompanyQualification);
                if (null != pkid){
                    companyQualificationMapper.updateCompanyQualfication(pkid);
                    aptitude.setQualId(pkid);
                }else {
                    companyQualificationMapper.inertCompanyQualfication(tbCompanyQualification);
                    aptitude.setQualId(tbCompanyQualification.getPkid());
                }
                aptitude.setType("公路");
                aptitude.setComId(comId);
                aptitude.setAptitudeName(tbCompanyQualification.getQualName());
                aptitude.setAptitudeUuid(quaId);
                aptitude.setMainuuid(qualCode);
                aptitudes.add(aptitude);
            }
        }

        if (null != aptitudes && aptitudes.size() > 0){
            //删除之前旧资质
            tbCompanyAptitudeMapper.deleteCompanyAptiudeByType(new HashedMap(2){{
                put("comId",comId);
                put("type","gonglu");
            }});
            //新增资质
            tbCompanyAptitudeMapper.batchInsertCompanyAptitude(aptitudes);
            //更新企业range字段
            aptitudeCleanService.updateCompanyAptitude(comId);
        }
    }
}
