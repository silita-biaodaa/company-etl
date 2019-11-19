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
public class CompanyShuiliService {

    private static Logger logger = LoggerFactory.getLogger(CompanyShuiliService.class);

    @Autowired
    private CompanyMapper companyMapper;
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

    /**
     * 解析基本信息
     *
     * @param object
     */
    public void analysisCompanyInfo(JSONObject object) {
        String comName = object.getString("companyName");
        String regisAddress = object.getString("provinceCity");
        String newRegisAddress = null;
        for (Map.Entry<String, String> key : RegionCommon.regionSourcePinYin.entrySet()) {
            int index = regisAddress.indexOf(key.getKey());
            if (index > -1) {
                newRegisAddress = getRegisAddress(regisAddress, key.getKey(), index);
                break;
            }
        }
        if (null == newRegisAddress) {
            String certificateOrg = object.getString("certificateOrg");
            int indexCity = certificateOrg.indexOf("市");
            String city = certificateOrg.substring(0, indexCity + 1);
            newRegisAddress = companyMapper.queryProvinceCity(city);
        }

        String id = CommonUtil.getUUID();
        String creditCode = object.getString("creditCode");
        Map<String, Object> param = new HashedMap(2) {{
            put("comName", comName);
            put("creditCode", creditCode);
        }};
        try {
            Map<String, Object> baseInfo = (Map<String, Object>) object.get("baseInfo");
            Map<String, Object> comMap = companyMapper.queryCompanyExist(param);
            if (MapUtils.isNotEmpty(comMap)) {
                StringBuffer channel = new StringBuffer(MapUtils.getString(comMap, "channel"));
                channel.setCharAt(2, '1');
                comMap.put("channel", channel.toString());
                companyMapper.updateCompanyChannel(comMap);
                comMap.put("com_name", comName);
                comMap.put("regisAddress", newRegisAddress);
                if (comMap.containsKey("pkid") && !comMap.containsKey("com_id_shuili")) {
                    companyMapper.updateCompanyRel(comMap);
                } else {
                    comMap.put("com_id_shuili", id);
                    companyMapper.insertCompanyRel(comMap);
                }
                logger.info("----------------解析【" + comName + "】的基本信息:企业已存在，修改企业的[channel]字段--------------------------------");
            } else {
                TbCompany company = new TbCompany();
                company.setComName(comName);
                company.setComId(id);
                company.setComNamePy(Pinyin.getPinYinFirstChar(comName));
                company.setCreditCode(creditCode);
                company.setLegalPerson(object.getString("legalPerson"));
                company.setSkillLeader(object.getString("technicalLeader"));
                company.setEconomicType(object.getString("regisType"));
                company.setRegisAddress(newRegisAddress);
                company.setRegisCapital(object.getString("regisCapital"));
                if (null != baseInfo && null != baseInfo.get("regisAddress")) {
                    company.setComAddress(baseInfo.get("regisAddress").toString());
                }
                company.setChannel("001");
                companyMapper.insertCompany(company);
                comMap = new HashedMap(3);
                comMap.put("com_id", id);
                comMap.put("com_name", comName);
                comMap.put("com_id_shuili", id);
                comMap.put("regisAddress", newRegisAddress);
                companyMapper.insertCompanyRel(comMap);
            }
            //安许证保存
            if (null != baseInfo && null != baseInfo.get("safeProdLicese") && (!"无".equals(baseInfo.get("safeProdLicese")))) {
                //删除之前的安许证信息
                companyMapper.deleteSafetyCertificate(comName);
                companyMapper.insertSafetyCertificate(baseInfo);
            }
            //资质解析
            if (null != object.get("qualiInfos")) {
                List<Map<String, Object>> quals = (List<Map<String, Object>>) object.get("qualiInfos");
                this.analysisCompanyQuals(quals, MapUtils.getString(comMap, "com_id"), comName);
            }
            //人员解析
            if (null != object.get("perosonInfo")) {
                Map<String, Object> person = (Map<String, Object>) object.get("perosonInfo");
                this.analysisCompanyPersonCert(person, MapUtils.getString(comMap, "com_id"), comName, MapUtils.getString(comMap, "regisAddress"));
            }
            //业绩解析
            if (null != object.get("projectPerformances")) {
                List<Map<String, Object>> projects = (List<Map<String, Object>>) object.get("projectPerformances");
                this.analysisCompanyProject(projects, comName);
            }
            logger.info("----------------解析【" + comName + "】的完成--------------------------------");
        } catch (Exception e) {
            logger.error("解析企业" + comName + "失败！！！", e);
        }
    }

    /**
     * 解析企业人员证书
     */
    public void analysisCompanyPersonCert(Map<String, Object> person, String comId, String comName, String regisAddress) {
        String tabCode = RegionCommon.regionSourcePinYin.get(regisAddress);
        if (null == tabCode) {
            return;
        }
        if (null == person.get("threeTypes")) {
            return;
        }
        List<Map<String, Object>> certs = (List<Map<String, Object>>) person.get("threeTypes");
        if (null == certs || certs.size() <= 0) {
            return;
        }
        for (int i = 0, j = certs.size(); i < j; i++) {
            TbPerson personCert = new TbPerson();
            personCert.setTabCode(tabCode);
            personCert.setType(1);
            personCert.setName(certs.get(i).get("name").toString());
            personCert.setIdCard(certs.get(i).get("idCard").toString());
            personCert.setComId(comId);
            personCert.setComName(comName);
            personCert.setCertNo(certs.get(i).get("creditNo").toString());
            if (null != certs.get(i).get("validDate")) {
                personCert.setValidDate(certs.get(i).get("validDate").toString());
            }
            personCert.setCategory(certs.get(i).get("creditType").toString());
            String pkid = tbPersonMapper.queryPersonCertExist(personCert);
            if (null != pkid) {
                tbPersonMapper.updateDate(personCert);
            } else {
                personCert.setPkid(CommonUtil.getUUID());
                tbPersonMapper.insertPerson(personCert);
            }
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
        if (null != quals && quals.size() > 0) {
            List<TbCompanyAptitude> aptitudes = new ArrayList<>();
            int leg = quals.size();
            for (int i = 0; i < leg; i++) {
                String qualType = quals.get(i).get("quaType").toString();
                if (!"水利工程建设监理单位资质".equals(qualType)) {
                    continue;
                }
                //保存企业资质 tb_company_qualification 表
                TbCompanyQualification tbCompanyQualification = new TbCompanyQualification();
                tbCompanyQualification.setPkid(CommonUtil.getUUID());
                tbCompanyQualification.setTab("企业资质资格");
                tbCompanyQualification.setQualType("水利工程建设监理单位资质");
                tbCompanyQualification.setCertNo(quals.get(i).get("creditNo").toString());
                tbCompanyQualification.setComId(comId);
                tbCompanyQualification.setComName(comName);
                tbCompanyQualification.setCertOrg(quals.get(i).get("lssueOrg").toString());
                tbCompanyQualification.setCertDate(quals.get(i).get("forensicDate").toString());
                tbCompanyQualification.setValidDate(quals.get(i).get("validDate").toString());
                tbCompanyQualification.setChannel(5);
                StringBuffer str = new StringBuffer(quals.get(i).get("profesType").toString());
                String level = quals.get(i).get("level").toString();
                String gradeCode = aptitudeDictionaryMapper.queryGradeCode(level);
                String qualCode = aptitudeDictionaryMapper.queryCodeByAlias(str.toString());
                String quaId;
                if (null != gradeCode) {
                    str.append(level);
                    quaId = aptitudeDictionaryMapper.queryPkidByParam(new HashedMap(2) {{
                        put("grade", gradeCode);
                        put("qual", qualCode);
                    }});
                } else {
                    //非等级资质
                    quaId = aptitudeDictionaryMapper.queryPkidByParam(new HashedMap(2) {{
                        put("grade", "0");
                        put("qual", qualCode);
                    }});
                }
                tbCompanyQualification.setQualName(str.toString());
                tbCompanyQualification.setRange(str.toString());
                if (null != quaId) {
                    TbCompanyAptitude aptitude = new TbCompanyAptitude();
                    String pkid = companyQualificationMapper.queryCompanyQualficationExist(tbCompanyQualification);
                    if (null != pkid) {
                        companyQualificationMapper.updateCompanyQualfication(pkid);
                        aptitude.setQualId(pkid);
                    } else {
                        companyQualificationMapper.inertCompanyQualfication(tbCompanyQualification);
                        aptitude.setQualId(tbCompanyQualification.getPkid());
                    }
                    aptitude.setType("shuili");
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
                    put("type", "shuili");
                }});
                //新增资质
                tbCompanyAptitudeMapper.batchInsertCompanyAptitude(aptitudes);
                //更新企业range字段
                aptitudeCleanService.updateCompanyAptitude(comId);
                logger.info("----------------解析企业资质完成--------------------");
            }
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
