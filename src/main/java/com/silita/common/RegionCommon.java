package com.silita.common;


import java.util.HashMap;
import java.util.Map;

public class RegionCommon {


    public static Map<String,String> regionSource = new HashMap<>(31);

    public static Map<String, String> regionSourcePinYin = new HashMap<>(31);

    public static Map<String, String> regionSourceShort = new HashMap<>(33);

    public static Map<String, String> regionSourceShortName = new HashMap<>(33);

    static {
        regionSourcePinYin.put("安徽省","anh");
        regionSourcePinYin.put("北京市","beij");
        regionSourcePinYin.put("重庆市","chongq");
        regionSourcePinYin.put("福建省","fuj");
        regionSourcePinYin.put("甘肃省","gans");
        regionSourcePinYin.put("广东省","guangd");
        regionSourcePinYin.put("广西壮族自治区","guangx");
        regionSourcePinYin.put("贵州省","guiz");
        regionSourcePinYin.put("海南省","hain");
        regionSourcePinYin.put("河北省","hebei");
        regionSourcePinYin.put("黑龙江省","heilj");
        regionSourcePinYin.put("河南省","henan");
        regionSourcePinYin.put("湖北省","hubei");
        regionSourcePinYin.put("湖南省","hunan");
        regionSourcePinYin.put("江苏省","jiangs");
        regionSourcePinYin.put("江西省","jiangx");
        regionSourcePinYin.put("吉林省","jil");
        regionSourcePinYin.put("辽宁省","liaon");
        regionSourcePinYin.put("内蒙古自治区","neimg");
        regionSourcePinYin.put("宁夏回族自治区","ningx");
        regionSourcePinYin.put("青海省","qingh");
        regionSourcePinYin.put("山西省","sanx");
        regionSourcePinYin.put("山东省","shand");
        regionSourcePinYin.put("上海市","shangh");
        regionSourcePinYin.put("陕西省","shanxi");
        regionSourcePinYin.put("四川省","sichuan");
        regionSourcePinYin.put("天津市","tianj");
        regionSourcePinYin.put("新疆维吾尔自治区","xinjiang");
        regionSourcePinYin.put("西藏自治区","xizang");
        regionSourcePinYin.put("云南省","yunn");
        regionSourcePinYin.put("浙江省","zhej");
        regionSource.put("anh","安徽省");
        regionSource.put("beij","北京市");
        regionSource.put("chongq","重庆市");
        regionSource.put("fuj","福建省");
        regionSource.put("gans","甘肃省");
        regionSource.put("guangd","广东省");
        regionSource.put("guangx","广西壮族自治区");
        regionSource.put("guiz","贵州省");
        regionSource.put("hain","海南省");
        regionSource.put("hebei","河北省");
        regionSource.put("heilj","黑龙江省");
        regionSource.put("henan","河南省");
        regionSource.put("hubei","湖北省");
        regionSource.put("hunan","湖南省");
        regionSource.put("jiangs","江苏省");
        regionSource.put("jiangx","江西省");
        regionSource.put("jil","吉林省");
        regionSource.put("liaon","辽宁省");
        regionSource.put("neimg","内蒙古自治区");
        regionSource.put("ningx","宁夏回族自治区");
        regionSource.put("qingh","青海省");
        regionSource.put("sanx","山西省");
        regionSource.put("shand","山东省");
        regionSource.put("shangh","上海市");
        regionSource.put("shanxi","陕西省");
        regionSource.put("sichuan","四川省");
        regionSource.put("tianj","天津市");
        regionSource.put("xinjiang","新疆维吾尔自治区");
        regionSource.put("xizang","西藏自治区");
        regionSource.put("yunn","云南省");
        regionSource.put("zhej","浙江省");
        regionSourceShort.put("安徽","anh");
        regionSourceShort.put("北京","beij");
        regionSourceShort.put("重庆","chongq");
        regionSourceShort.put("福建","fuj");
        regionSourceShort.put("甘肃","gans");
        regionSourceShort.put("广东","guangd");
        regionSourceShort.put("广西","guangx");
        regionSourceShort.put("贵州","guiz");
        regionSourceShort.put("海南","hain");
        regionSourceShort.put("河北","hebei");
        regionSourceShort.put("黑龙江","heilj");
        regionSourceShort.put("龙江","heilj");
        regionSourceShort.put("河南","henan");
        regionSourceShort.put("湖北","hubei");
        regionSourceShort.put("湖南","hunan");
        regionSourceShort.put("江苏","jiangs");
        regionSourceShort.put("江西","jiangx");
        regionSourceShort.put("吉林","jil");
        regionSourceShort.put("辽宁","liaon");
        regionSourceShort.put("内蒙古","neimg");
        regionSourceShort.put("内蒙","neimg");
        regionSourceShort.put("宁夏","ningx");
        regionSourceShort.put("青海","qingh");
        regionSourceShort.put("山西","sanx");
        regionSourceShort.put("山东","shand");
        regionSourceShort.put("上海","shangh");
        regionSourceShort.put("陕西","shanxi");
        regionSourceShort.put("四川","sichuan");
        regionSourceShort.put("天津","tianj");
        regionSourceShort.put("新疆","xinjiang");
        regionSourceShort.put("西藏","xizang");
        regionSourceShort.put("云南","yunn");
        regionSourceShort.put("浙江","zhej");
        regionSourceShortName.put("安徽","安徽省");
        regionSourceShortName.put("北京","北京市");
        regionSourceShortName.put("重庆","重庆市");
        regionSourceShortName.put("福建","福建省");
        regionSourceShortName.put("甘肃","甘肃省");
        regionSourceShortName.put("广东","广东省");
        regionSourceShortName.put("广西","广西壮族自治区");
        regionSourceShortName.put("贵州","贵州省");
        regionSourceShortName.put("海南","海南省");
        regionSourceShortName.put("河北","河北省");
        regionSourceShortName.put("黑龙江","黑龙江省");
        regionSourceShortName.put("龙江","黑龙江省");
        regionSourceShortName.put("河南","河南省");
        regionSourceShortName.put("湖北","湖北省");
        regionSourceShortName.put("湖南","湖南省");
        regionSourceShortName.put("江苏","江苏省");
        regionSourceShortName.put("江西","江西省");
        regionSourceShortName.put("吉林","吉林省");
        regionSourceShortName.put("辽宁","辽宁省");
        regionSourceShortName.put("内蒙","内蒙古自治区");
        regionSourceShortName.put("内蒙古","内蒙古自治区");
        regionSourceShortName.put("宁夏","宁夏回族自治区");
        regionSourceShortName.put("青海","青海省");
        regionSourceShortName.put("山西","山西省");
        regionSourceShortName.put("山东","山东省");
        regionSourceShortName.put("上海","上海市");
        regionSourceShortName.put("陕西","陕西省");
        regionSourceShortName.put("四川","四川省");
        regionSourceShortName.put("天津","天津市");
        regionSourceShortName.put("新疆","新疆维吾尔自治区");
        regionSourceShortName.put("西藏","西藏自治区");
        regionSourceShortName.put("云南","云南省");
        regionSourceShortName.put("浙江","浙江省");
    }




}
