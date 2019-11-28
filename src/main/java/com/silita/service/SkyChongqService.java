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
public class SkyChongqService {

    private static Logger logger = LoggerFactory.getLogger(SkyChongqService.class);

    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private SkyPersonChongqMapper skyPersonChongqMapper;
    @Autowired
    private TbPersonMapper tbPersonMapper;
    @Autowired
    private TbProjectShuiliMapper tbProjectShuiliMapper;
    @Autowired
    private AptitudeDictionaryMapper aptitudeDictionaryMapper;
    @Autowired
    private CompanyQualificationMapper companyQualificationMapper;
    @Autowired
    private TbCompanyAptitudeMapper tbCompanyAptitudeMapper;
    @Autowired
    private IAptitudeCleanService aptitudeCleanService;
    @Autowired
    private AliasService aliasService;

    /**
     * 解析基本信息
     *
     * @param object
     */
    public void analysisCompanyInfo(JSONObject object) {
        String comName = object.getString("companyName").replace(")", "）").replace("(", "）").replaceAll(" ", "");
        String regisAddress = object.getString("province");
        String id = CommonUtil.getUUID();
        String creditCode = object.getString("businesslicenseNo");
        Map<String, Object> param = new HashedMap(1) {{
            put("comName", comName);
        }};
        try {
            Map<String, Object> comMap = companyMapper.queryCompanyExist(param);
            if (MapUtils.isNotEmpty(comMap)) {
                StringBuffer channel = new StringBuffer(MapUtils.getString(comMap, "channel"));
                if (channel.length() >= 4) {
                    channel.setCharAt(3, '1');
                } else {
                    channel.append("1");
                }
                comMap.put("channel", channel.toString());
                companyMapper.updateCompanyChannel(comMap);
                comMap.put("com_name", comName);
                comMap.put("regisAddress", regisAddress);
            } else {
                TbCompany company = new TbCompany();
                company.setComName(comName);
                company.setComId(id);
                company.setComNamePy(Pinyin.getPinYinFirstChar(comName));
                company.setCreditCode(creditCode);
                company.setLegalPerson(object.getString("legalPerson"));
                company.setEconomicType(object.getString("economicnature"));
                company.setRegisAddress(regisAddress);
                company.setRegisCapital(object.getString("registeredCapital"));
                company.setComAddress(object.getString("registerAddress"));
                company.setUrl(object.getString("url"));
                company.setChannel("0001");
                if (StringUtils.isNotEmpty(company.getRegisCapital())) {
                    company.setRegisCapital(company.getRegisCapital().replace("(人民币)", ""));
                }
                companyMapper.insertCompany(company);
                comMap = new HashedMap(3);
                comMap.put("com_id", id);
                comMap.put("com_name", comName);
                comMap.put("regisAddress", regisAddress);
            }
            //解析人员
            if (object.containsKey("person")) {
                List<Map<String, Object>> persons = (List<Map<String, Object>>) object.get("person");
                this.analysisCompanyPersonCert(persons, comMap.get("com_id").toString(), regisAddress);
            }
            //解析企业资质
            if (object.containsKey("quals")) {
                List<Map<String, Object>> quals = (List<Map<String, Object>>) object.get("quals");
                this.analysisCompanyQuals(quals, comMap.get("com_id").toString(), comName);
            }
            //解析业绩
            logger.info("----------------解析【" + comName + "】的完成--------------------------------");
        } catch (Exception e) {
            logger.error("解析企业" + comName + "失败！！！", e);
        }
    }

    /**
     * 解析企业人员证书
     */
    public void analysisCompanyPersonCert(List<Map<String, Object>> persons, String comId, String regisAddress) {
        if (null == persons || persons.size() <= 0) {
            return;
        }
        String tabCode = RegionCommon.regionSourcePinYin.get(regisAddress);
        if (null == tabCode) {
            return;
        }
        //删除企业下所有人员
        skyPersonChongqMapper.batchDeletePerson(comId);
        String perType;
        for (int i = 0, j = persons.size(); i < j; i++) {
            perType = persons.get(i).get("perType").toString();
            SkyPersonChongq personCert = new SkyPersonChongq();
            personCert.setName(persons.get(i).get("name").toString().replaceAll(" ", ""));
            personCert.setComId(comId);
            personCert.setUrl(persons.get(i).get("url").toString());
            personCert.setIsValid(1);
            if (null != persons.get(i).get("personType")) {
                String category = persons.get(i).get("personType").toString();
                //拼装人员注册类别
                if ("register".equals(perType)) {
                    category = category.replace("(", "（").replace(")", "）");
                    String[] cates = category.split("（");
                    StringBuffer stringBuffer = new StringBuffer("");
                    stringBuffer.append(cates[1].replace("）", "")).append(cates[0]);
                    personCert.setCategory(stringBuffer.toString());
                } else {
                    personCert.setCategory(category);
                }
            }
            if (null != persons.get(i).get("major")) {
                personCert.setMajor(persons.get(i).get("major").toString());
            }
            if (null != persons.get(i).get("certificateNo")) {
                personCert.setCertNo(persons.get(i).get("certificateNo").toString());
            }
            if (null != persons.get(i).get("issueDate")) {
                personCert.setCertNo(persons.get(i).get("issueDate").toString());
            }
            if (null != persons.get(i).get("indate")) {
                personCert.setCertNo(persons.get(i).get("indate").toString());
            }
            //判断是否专业是否由','隔开，如是，需要拆分成多记录存入数据库
            if (null != personCert.getMajor()) {
                personCert.setMajor(personCert.getMajor().replaceAll("，", ","));
                if (personCert.getMajor().indexOf(",") > -1) {
                    String[] majors = personCert.getMajor().split(",");
                    for (int k = 0, n = majors.length; k < n; k++) {
                        personCert.setMajor(majors[k]);
                        savePerson(personCert);
                    }
                    break;
                }
            }
            savePerson(personCert);
        }
        logger.info("--------------解析人员证书结束-----------------------------");
    }

    /**
     * 解析企业业绩
     */
    public void analysisCompanyProject(List<Map<String, Object>> projects, String comName) {
        int leg = projects.size();
        List<Map<String, Object>> projectPersons;
        List<Map<String, Object>> projectAwards;
        for (int i = 0; i < leg; i++) {
            TbProjectShuili projectShuili = new TbProjectShuili(projects.get(i));
            if (StringUtils.isEmpty(projectShuili.getComName())) {
                continue;
            }
            projectShuili.setComName(comName);
            if (null != projects.get(i).get("projectPersons")) {
                projectPersons = (List<Map<String, Object>>) projects.get(i).get("projectPersons");
                if (null != projectPersons && projectPersons.size() > 0) {
                    int k = projectPersons.size();
                    List<Map<String, Object>> persons = new ArrayList<>(k);
                    for (int j = 0; j < k; j++) {
                        Map map = new HashedMap(8);
                        map.put("姓名", projectPersons.get(j).get("name"));
                        map.put("身份证号", projectPersons.get(j).get("idCard"));
                        map.put("职务", projectPersons.get(j).get("post"));
                        map.put("职称", projectPersons.get(j).get("job"));
                        map.put("证书名称", projectPersons.get(j).get("creditName"));
                        map.put("证书编号", projectPersons.get(j).get("creditNo"));
                        map.put("证书专业", projectPersons.get(j).get("creditMajor"));
                        map.put("等级", projectPersons.get(j).get("level"));
                        persons.add(map);
                    }
                    projectShuili.setPersons(JSONObject.toJSONString(persons));
                }
            }
            if (null != projects.get(i).get("projcetAwards")) {
                projectAwards = (List<Map<String, Object>>) projects.get(i).get("projcetAwards");
                if (null != projectAwards && projectAwards.size() > 0) {
                    int k = projectAwards.size();
                    List<Map<String, Object>> awards = new ArrayList<>(k);
                    for (int j = 0; j < k; j++) {
                        Map map = new HashedMap(7);
                        map.put("奖项名称", projectAwards.get(j).get("awardName"));
                        map.put("奖项类别", projectAwards.get(j).get("awardType"));
                        map.put("奖项级别", projectAwards.get(j).get("awardRank"));
                        map.put("奖项等别", projectAwards.get(j).get("awardLevel"));
                        map.put("颁奖单位", projectAwards.get(j).get("issueUnit"));
                        map.put("颁奖文号", projectAwards.get(j).get("issueNo"));
                        map.put("颁奖时间", projectAwards.get(j).get("issueTime"));
                        awards.add(map);
                    }
                    projectShuili.setPrizes(JSONObject.toJSONString(awards));
                }
            }
            Integer pkid = tbProjectShuiliMapper.queryProjectExits(projectShuili);
            if (null != pkid && pkid > 0) {
                projectShuili.setPkid(pkid);
                tbProjectShuiliMapper.updateProjectTraffic(projectShuili);
            } else {
                tbProjectShuiliMapper.insertProjectTraffic(projectShuili);
            }
        }
        logger.info("--------------解析企业业绩完成-----------------------------");
    }

    /**
     * 解析企业资质
     */
    public void analysisCompanyQuals(List<Map<String, Object>> quals, String comId, String comName) {
        if (null == quals || quals.size() <= 0) {
            return;
        }
        //删除重庆企业下的资质
        companyQualificationMapper.deleteCompanyQualfication(new HashedMap(2){{
            put("comId",comId);
            put("channel",6);
        }});
        List<TbCompanyAptitude> aptitudes = new ArrayList<>();
        int leg = quals.size();
        for (int i = 0; i < leg; i++) {
            String qualType = quals.get(i).get("sequence").toString();
            //保存企业资质 tb_company_qualification 表
            TbCompanyQualification tbCompanyQualification = new TbCompanyQualification();
            tbCompanyQualification.setPkid(CommonUtil.getUUID());
            tbCompanyQualification.setTab("企业资质资格");
            tbCompanyQualification.setQualType("建筑业企业资质");
            tbCompanyQualification.setCertNo(quals.get(i).get("certificateNo").toString());
            tbCompanyQualification.setComId(comId);
            tbCompanyQualification.setComName(comName);
            tbCompanyQualification.setValidDate(quals.get(i).get("periodValidity").toString());
            tbCompanyQualification.setChannel(6);
            StringBuffer str = new StringBuffer(quals.get(i).get("aptitudeType").toString());
            String level = quals.get(i).get("aptitudeGrade").toString();
            String gradeCode = null;
            String gradeName = null;
            if ("不分等级".equals(level)) {
                gradeCode = "0";
                gradeName = level;
            } else {
                Map<String, Object> gradeMap = aptitudeDictionaryMapper.queryGradeByAlias(level);
                //判断dic_alias表中是否有等级别名
                if (MapUtils.isEmpty(gradeMap)) {
                    //没有等级别名需加入tb_analysis_alias表中用来填补别名表
                    aliasService.saveAlias(level, 2);
                } else {
                    gradeCode = gradeMap.get("code").toString();
                    gradeName = gradeMap.get("name").toString();
                }
            }
            String qualCode = aptitudeDictionaryMapper.queryCodeByAlias(str.toString());
            if (null == qualCode) {
                //没有资质别名需加入tb_analysis_alias表中用来填补别名表
                aliasService.saveAlias(str.toString(), 1);
                continue;
            }
            if (null == gradeCode) {
                continue;
            }
            String quaId;
            String grade = gradeCode;
            str.append(qualType).append(gradeName);
            quaId = aptitudeDictionaryMapper.queryPkidByParam(new HashedMap(2) {{
                put("qual", qualCode);
                put("grade", grade);
            }});
            tbCompanyQualification.setQualName(str.toString());
            tbCompanyQualification.setRange(str.toString());
            String pkid = companyQualificationMapper.queryCompanyQualficationCertNo(tbCompanyQualification);
            if (null != pkid) {
                companyQualificationMapper.updateCompanyQualfication(pkid);
            } else {
                companyQualificationMapper.inertCompanyQualfication(tbCompanyQualification);
                pkid = tbCompanyQualification.getPkid();
            }
            //保存重庆备案资质
            aliasService.saveQualRegion(str.toString(), "重庆市");
            if (null != quaId) {
                TbCompanyAptitude aptitude = new TbCompanyAptitude();
                aptitude.setQualId(pkid);
                aptitude.setType("sky_chongq");
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
                put("type", "sky_chongq");
            }});
            //新增资质
            tbCompanyAptitudeMapper.batchInsertCompanyAptitude(aptitudes);
            //更新企业range字段
            aptitudeCleanService.updateCompanyAptitude(comId);
            logger.info("----------------解析企业资质完成--------------------");
        }
    }

    /**
     * 保存人员(逻辑为：查询该人员证书是否存在与全国四库一，查询该人员是否存在于重庆四库一)
     *
     * @param person
     */
    private void savePerson(SkyPersonChongq person) {
        //查询全国四库一是否存在该人员的注册证书
        String pkid = tbPersonMapper.queryPersonExist(person);
        person.setIsAll(StringUtils.isEmpty(pkid) ? 0 : 1);
        //查询重庆人员表是否存在
        String perId = skyPersonChongqMapper.queryPersonExits(person);
        if (null == perId) {
            return;
        }
        //添加人员
        skyPersonChongqMapper.insertPerson(person);
    }
}
