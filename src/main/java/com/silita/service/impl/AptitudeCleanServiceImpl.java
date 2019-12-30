package com.silita.service.impl;

import com.google.common.base.Splitter;
import com.silita.dao.*;
import com.silita.model.AllZh;
import com.silita.spider.common.model.Company;
import com.silita.spider.common.model.CompanyQualification;
import com.silita.model.TbCompanyAptitude;
import com.silita.service.IAptitudeCleanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service("aptitudeCleanService")
public class AptitudeCleanServiceImpl implements IAptitudeCleanService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyQualificationMapper companyQualificationMapper;
    @Autowired
    private TbCompanyAptitudeMapper tbCompanyAptitudeMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private AllZhMapper allZhMapper;
    @Autowired
    private AptitudeDictionaryMapper aptitudeDictionaryMapper;

    @Override
    public void splitAllCompanyAptitude() {
        int page = 0;
        int batchCount = 5000;
        Integer count = companyQualificationMapper.getCompanyQualificationTotal();
        if (count % batchCount == 0) {
            page = count / batchCount;
        } else {
            page = count / batchCount + 1;
        }
        Map<String, Object> params;
        List<CompanyQualification> companyQualificationList;
        //数据量较大，按批次处理
        for (int pageNum = 0; pageNum < page; pageNum++) {
            params = new HashMap<>();
            params.put("start", batchCount * pageNum);
            params.put("pageSize", 5000);
            companyQualificationList = companyQualificationMapper.listCompanyQualification(params);
            //遍历证书
            for (int i = 0; i < companyQualificationList.size(); i++) {
                String qualId = companyQualificationList.get(i).getPkid();
                String qualRange = companyQualificationList.get(i).getRange();
                if (companyQualificationList.get(i).getCom_id() != null) {
                    String comId = companyQualificationList.get(i).getCom_id();
                    //有资质
                    if (!StringUtils.isEmpty(qualRange)) {
                        AllZh allZh;
                        TbCompanyAptitude companyAptitude;
                        List<TbCompanyAptitude> companyQualifications = new ArrayList<>();
                        Iterator<String> iterator = Splitter.onPattern("\\||,|，|;|；").omitEmptyStrings().trimResults().split(qualRange).iterator();
                        while (iterator.hasNext()) {
                            String qual = iterator.next();
                            allZh = allZhMapper.getAllZhByName(qual);
                            if (allZh != null) {
                                companyAptitude = new TbCompanyAptitude();
                                companyAptitude.setQualId(qualId);
                                companyAptitude.setComId(comId);
                                companyAptitude.setAptitudeName(aptitudeDictionaryMapper.getMajorNameBymajorUuid(allZh.getMainuuid()));
                                companyAptitude.setAptitudeUuid(allZh.getFinaluuid());
                                companyAptitude.setMainuuid(allZh.getMainuuid());
                                companyAptitude.setType(allZh.getType());
                                companyQualifications.add(companyAptitude);
                            }
                        }
                        if (companyQualifications.size() > 0) {
                            tbCompanyAptitudeMapper.batchInsertCompanyAptitude(companyQualifications);
                        }
                    }
                }
            }
        }
        logger.info("拆分所有资质完成");
    }

    @Override
    public void updateAllCompanyAptitude() {
        int page = 0;
        int batchCount = 5000;
        Integer count = tbCompanyAptitudeMapper.getCompanyAptitudeTotal();
        if (count % batchCount == 0) {
            page = count / batchCount;
        } else {
            page = count / batchCount + 1;
        }
        Map<String, Object> params;
        List<TbCompanyAptitude> tbCompanyAptitudes;
        //分页
        for (int pageNum = 0; pageNum < page; pageNum++) {
            params = new HashMap<>();
            params.put("start", batchCount * pageNum);
            params.put("pageSize", 5000);
            tbCompanyAptitudes = tbCompanyAptitudeMapper.listCompanyAptitude(params);
            Company company;
            TbCompanyAptitude tbCompanyAptitude;
            String comId;
            String allType;
            String allAptitudeUuid;
            StringBuilder sb;
            //遍历
            for (int i = 0; i < tbCompanyAptitudes.size(); i++) {
                tbCompanyAptitude = tbCompanyAptitudes.get(i);
                if (null != tbCompanyAptitude) {
                    comId = tbCompanyAptitude.getComId();
                    allType = tbCompanyAptitude.getType();
                    allAptitudeUuid = tbCompanyAptitude.getAptitudeUuid();
                    if (!StringUtils.isEmpty(allType) && !StringUtils.isEmpty(allAptitudeUuid)) {
                        sb = new StringBuilder();
                        String[] typeArr = allType.split(",");
                        String[] aptitudeUuidArr = allAptitudeUuid.split(",");
                        if (typeArr.length > 0 && aptitudeUuidArr.length > 0 && typeArr.length == aptitudeUuidArr.length) {
                            for (int j = 0; j < typeArr.length; j++) {
                                if (j == typeArr.length - 1) {
                                    sb.append(typeArr[j]).append("/").append(aptitudeUuidArr[j]);
                                } else {
                                    sb.append(typeArr[j]).append("/").append(aptitudeUuidArr[j]).append(",");
                                }
                            }
                        }
                        company = new Company();
                        company.setCom_id(comId);
                        company.setRange(sb.toString());
                        companyMapper.updateCompanyRangeByComId(company);
                    }
                }
            }
        }
        logger.info("更新所有资质完成");
        //删除拆分后的公司资质
//        splitCertService.deleteCompanyAptitude();
    }

    @Override
    public void splitCompanyAptitudeByCompanyId(String companyId) {
        Map<String, Object> param = new HashMap<>();
        //拆之前删除旧资质
        tbCompanyAptitudeMapper.deleteCompanyAptitudeByCompanyId(companyId);
        List<CompanyQualification> companyQualificationList = companyQualificationMapper.getCompanyQualificationByComId(companyId);
        //遍历证书
        for (int i = 0; i < companyQualificationList.size(); i++) {
            String qualId = companyQualificationList.get(i).getPkid();
            String qualRange = companyQualificationList.get(i).getQual_name();
            if (StringUtils.isEmpty(qualRange)) {
                qualRange = companyQualificationList.get(i).getRange();
            }
            String comId = companyId;
            //有资质
            if (!StringUtils.isEmpty(qualRange)) {
                TbCompanyAptitude companyAptitude;
                List<TbCompanyAptitude> companyQualifications = new ArrayList<>();
                Iterator<String> iterator = Splitter.onPattern("\\||,|，|;|；").omitEmptyStrings().trimResults().split(qualRange).iterator();
                StringBuffer subQual;
                StringBuffer subGrade;
                StringBuffer paramGrade;
                while (iterator.hasNext()) {
                    companyAptitude = new TbCompanyAptitude();
                    String qual = iterator.next();
                    if (qual.contains("不分等级")) {
                        subQual = new StringBuffer((qual.replace("不分等级", "")));
                        subGrade = new StringBuffer("0");
                    } else if (qual.contains("级")) {
                        subQual = new StringBuffer(qual.substring(0, qual.indexOf("级") - 1));
                        subGrade = new StringBuffer(qual.substring(qual.indexOf("级") - 1, qual.indexOf("级") + 1));
                    } else {
                        subQual = new StringBuffer(qual);
                        subGrade = new StringBuffer("0");
                    }
                    String resCode = aptitudeDictionaryMapper.queryCodeByAlias(subQual.toString());
                    if (null != resCode) {
                        companyAptitude.setComId(comId);
                        companyAptitude.setQualId(qualId);
                        companyAptitude.setMainuuid(resCode);
                        companyAptitude.setAptitudeName(aptitudeDictionaryMapper.queryQualNameByCode(resCode));
                        param.put("qual", resCode);
                        if ("0".equals(subGrade.toString())) {
                            param.put("grade", "0");
                            StringBuffer pkid = new StringBuffer(aptitudeDictionaryMapper.queryPkidByParam(param));
                            if (null != pkid) {
                                companyAptitude.setAptitudeUuid(pkid.toString());
                            }
                        } else {
                            String resGrade = aptitudeDictionaryMapper.queryCodeByAlias(subGrade.toString());
                            if (null == resGrade) {
                                resGrade = "0";
                            }
                            paramGrade = new StringBuffer(resGrade);
                            param.put("grade", paramGrade.toString());
                            StringBuffer pkid = new StringBuffer(aptitudeDictionaryMapper.queryPkidByParam(param));
                            if (null != pkid) {
                                companyAptitude.setAptitudeUuid(pkid.toString());
                            }
                        }
                        companyAptitude.setType("zhujian");
                        companyQualifications.add(companyAptitude);
                    }
                }
                if (companyQualifications.size() > 0) {
                    tbCompanyAptitudeMapper.batchInsertCompanyAptitude(companyQualifications);
                }
            }
        }
    }

    @Override
    public void updateCompanyAptitude(String companyId) {
        List<TbCompanyAptitude> tbCompanyAptitudes = tbCompanyAptitudeMapper.listCompanyAptitudeByComPanyId(companyId);
        TbCompanyAptitude tbCompanyAptitude;
        List<String> rangs = new ArrayList<>();
        //遍历
        for (int i = 0; i < tbCompanyAptitudes.size(); i++) {
            tbCompanyAptitude = tbCompanyAptitudes.get(i);
            if (null != tbCompanyAptitude) {
                rangs.add(tbCompanyAptitude.getAptitudeUuid());
            }
        }
        Collections.sort(rangs);
        StringBuilder sb = new StringBuilder();
        for (String str : rangs) {
            sb.append(str).append(",");
        }
        Company company = new Company();
        company.setCom_id(companyId);
        company.setRange(sb.toString());
        companyMapper.updateCompanyRangeByComId(company);
    }

    @Override
    public void cleanQualificationBySql(Map params) {
        List<CompanyQualification> qualifications = companyQualificationMapper.getCompanyQualificationBySql(params);
        if (null != qualifications && !qualifications.isEmpty()) {
            for (CompanyQualification qualification : qualifications) {
                String com_id = qualification.getCom_id();
                splitCompanyAptitudeByCompanyId(com_id);
                updateCompanyAptitude(com_id);
            }
            logger.info(String.format("按条件更新资质完成！更新数量：%s", qualifications.size()));
        }
    }
}
