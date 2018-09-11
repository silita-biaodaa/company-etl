package com.silita.service;

/**
 * @Author:chenzhiqiang
 * @Date:2018/7/31 14:13
 * @Description:
 */
/*@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations = {"classpath:config/spring/applicationContext.xml"}) //加载配置文件
public class DeleteCostCompanyServiceTestCase {
    @Autowired
    private DeleteCostCompanyService deleteCostCompanyService;

    @Autowired
    private MohurdService mohurdService;

    @Test
    public void deleteTask() {
        deleteCostCompanyService.deleteTask();
    }

    @Test
    public void updateTest() {
        Company company = mohurdService.selectCompanyById("0bbe7f678a89346c646faa2f816e5bf2");
        if (null != company) {
            company.setCom_name(company.getCom_name() + "_Test");
            int result = mohurdService.updateCompany(company);
            System.out.println("修改条数：" + result);
        }
        company.setCom_id("2222");
        System.out.println("插入条数：" + mohurdService.insertCompany(company));
    }
}*/
