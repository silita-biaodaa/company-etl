package com.silita.service;

import com.alibaba.fastjson.JSONObject;
import com.silita.common.RegionCommon;
import com.silita.dao.*;
import com.silita.model.*;
import com.silita.utils.CommonUtil;
import com.silita.utils.DateTimeUtils;
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
    private TbCompanyPunishMapper tbCompanyPunishMapper;
    @Autowired
    private SkyProjZbChongqMapper skyProjZbChongqMapper;
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
                persons = null;
            }
            //解析企业资质
            if (object.containsKey("quals")) {
                List<Map<String, Object>> quals = (List<Map<String, Object>>) object.get("quals");
                this.analysisCompanyQuals(quals, comMap.get("com_id").toString(), comName);
                quals = null;
            }
            //解析中标业绩
            if (object.containsKey("zhongbiao")) {
                List<Map<String, Object>> zhongbiaos = (List<Map<String, Object>>) object.get("zhongbiaos");
                this.analysisCompanyZhongbiaoProject(zhongbiaos, comMap.get("com_id").toString(), comName);
                zhongbiaos = null;
            }
            //解析行政处罚
            if (object.containsKey("punishs")) {
                List<Map<String, Object>> punishs = (List<Map<String, Object>>) object.get("punishs");
                this.analysisCompanyPunish(punishs, comMap.get("com_id").toString());
                punishs = null;
            }
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
    public void analysisCompanyProject(List<Map<String, Object>> projects, String comId,String comName) {

        logger.info("--------------解析企业竣工业绩完成-----------------------------");
    }

    /**
     * 解析企业中标业绩
     */
    public void analysisCompanyZhongbiaoProject(List<Map<String, Object>> projects, String comId, String comName) {
        if (null == projects || projects.size() <= 0) {
            return;
        }
        //删除企业下的中标业绩
        skyProjZbChongqMapper.deleteProjectZhongbiao(comId);
        for (int i = 0, j = projects.size(); i < j; i++) {
            SkyProjZbChongq project = new SkyProjZbChongq();
            project.setComId(comId);
            project.setComName(comName);
            project.setProjName(projects.get(i).get("projectName").toString());
            project.setZhaobiaoCode(projects.get(i).get("biddingCode").toString());
            project.setZhaobiaoPerson(projects.get(i).get("tenderee").toString());
            project.setProjManager(projects.get(i).get("projectManager").toString());
            if (null != projects.get(i).get("biddingPrice")) {
                Double amount = Double.valueOf(projects.get(i).get("biddingPrice").toString());
                project.setAmount(String.valueOf(amount / 10000));
            }
            project.setDays(projects.get(i).get("biddingDay").toString());
            project.setUrl(projects.get(i).get("url").toString());
            if (null != projects.get(i).get("pliesNo")) {
                project.setFloorNum(projects.get(i).get("pliesNo").toString());
            }
            if (null != projects.get(i).get("bidDate")) {
                project.setPubDate(DateTimeUtils.strFormat(projects.get(i).get("bidDate").toString(),"yyyy/MM/dd HH:mm:ss","yyyy-MM-dd"));
            }
            if (null != projects.get(i).get("projectScale")) {
                project.setScope(projects.get(i).get("projectScale").toString());
            }
            if (null != projects.get(i).get("biddingScope")) {
                project.setContent(projects.get(i).get("biddingScope").toString());
            }
            skyProjZbChongqMapper.insertProjectZhongbiao(project);
        }
        logger.info("--------------解析企业中标业绩完成-----------------------------");
    }

    /**
     * 解析企业的行政处罚
     */
    public void analysisCompanyPunish(List<Map<String, Object>> punishs, String comId) {
        if (null == punishs || punishs.size() <= 0) {
            return;
        }
        //删除公司下的所有行政处罚
        tbCompanyPunishMapper.deleteCompanyPunish(comId);
        for (int i = 0, j = punishs.size(); i < j; i++) {
            Map<String, Object> entity = (Map<String, Object>) punishs.get(i).get("entity");
            TbCompanyPunish companyPunish = new TbCompanyPunish();
            companyPunish.setComId(comId);
            companyPunish.setPunishCode(entity.get("cf_wsh").toString());
            companyPunish.setPunishType(entity.get("cf_cflb").toString());
            if (null != entity.get("cf_cfmc")) {
                companyPunish.setPunishName(entity.get("cf_cfmc").toString());
            }
            companyPunish.setPunishContent(entity.get("cf_sy").toString());
            companyPunish.setPunishWith(entity.get("cf_yj").toString());
            if (null != entity.get("cf_jg")) {
                companyPunish.setPunishResult(entity.get("cf_jg").toString());
            }
            companyPunish.setPunishDate(entity.get("cf_jdrq").toString());
            if (null != entity.get("cf_xzjg")) {
                companyPunish.setPunishOrg(entity.get("cf_xzjg").toString());
            }
            companyPunish.setUrl(punishs.get(i).get("url").toString());
            tbCompanyPunishMapper.insertCompanyPunish(companyPunish);
        }
    }

    /**
     * 解析企业资质
     */
    public void analysisCompanyQuals(List<Map<String, Object>> quals, String comId, String comName) {
        if (null == quals || quals.size() <= 0) {
            return;
        }
        //删除重庆企业下的资质
        companyQualificationMapper.deleteCompanyQualfication(new HashedMap(2) {{
            put("comId", comId);
            put("channel", 6);
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
