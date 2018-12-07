package cn.itcast.service;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.web.multipart.MultipartFile;

import cn.itcast.domain.Emp;
import cn.itcast.domain.EmpVo;
import cn.itcast.util.Result;

public interface EmpService {
	Result selectAll();
	List<Emp> findEmpByWhere(EmpVo empVo);
	int delete(Integer empNo);
	int deleteBatch(List<Integer> ids);
	Result selectByName(String ename);
	Result saveOrUpdateEmp(Emp emp);
	Result getById(Integer empNo);
	Result getMgrs();
	void exportEmpExcel(List<Emp> emps, ServletOutputStream outputStream);
	void importExcel(MultipartFile empExcel);
	Result getEmpSalStatisticData();
	Result getEmpCommStatisticData();
}
