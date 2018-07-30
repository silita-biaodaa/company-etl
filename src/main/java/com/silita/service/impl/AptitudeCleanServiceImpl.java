package com.silita.service.impl;

import com.google.common.base.Splitter;
import com.silita.dao.*;
import com.silita.model.*;
import com.silita.service.IAptitudeCleanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service("aptitudeCleanService")
public class AptitudeCleanServiceImpl implements IAptitudeCleanService {

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
            List<TbCompanyAptitude> companyQualifications = new ArrayList<>();
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
                        Iterator<String> iterator = Splitter.onPattern("\\||,|，").omitEmptyStrings().trimResults().split(qualRange).iterator();
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
                    }
                }
                if (companyQualifications.size() > 0 && companyQualifications.size() % 100 == 0) {
                    tbCompanyAptitudeMapper.batchInsertCompanyAptitude(companyQualifications);
                }
            }
            tbCompanyAptitudeMapper.batchInsertCompanyAptitude(companyQualifications);
        }
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
        //删除拆分后的公司资质
//        splitCertService.deleteCompanyAptitude();
    }

    @Override
    public void splitCompanyAptitudeByCompanyId(String companyId) {
        //拆之前删除旧资质
        tbCompanyAptitudeMapper.deleteCompanyAptitudeByCompanyId(companyId);
        List<CompanyQualification> companyQualificationList = companyQualificationMapper.getCompanyQualificationByComId(companyId);
        //遍历证书
        for (int i = 0; i < companyQualificationList.size(); i++) {
            String qualId = companyQualificationList.get(i).getPkid();
            String qualRange = companyQualificationList.get(i).getRange();
            String comId = companyQualificationList.get(i).getCom_id();
            //有资质
            if (!StringUtils.isEmpty(qualRange)) {
                AllZh allZh;
                TbCompanyAptitude companyAptitude;
                List<TbCompanyAptitude> companyQualifications = new ArrayList<>();
                Iterator<String> iterator = Splitter.onPattern("\\||,|，").omitEmptyStrings().trimResults().split(qualRange).iterator();
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

    @Override
    public void updateCompanyAptitude(String companyId) {
        List<TbCompanyAptitude> tbCompanyAptitudes = tbCompanyAptitudeMapper.listCompanyAptitudeByComPanyId(companyId);
        StringBuilder sb;
        Company company;
        TbCompanyAptitude tbCompanyAptitude;
        //遍历
        for (int i = 0; i < tbCompanyAptitudes.size(); i++) {
            tbCompanyAptitude = tbCompanyAptitudes.get(i);
            String comId = tbCompanyAptitude.getComId();
            String allType = tbCompanyAptitude.getType();
            String allAptitudeUuid = tbCompanyAptitude.getAptitudeUuid();
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
        System.out.println("完成id为" + companyId + "的企业数据更新！");
    }


}
