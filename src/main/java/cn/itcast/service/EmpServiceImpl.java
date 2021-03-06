package cn.itcast.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.itcast.dao.DeptMapper;
import cn.itcast.dao.EmpMapper;
import cn.itcast.domain.Dept;
import cn.itcast.domain.Emp;
import cn.itcast.domain.EmpExample;
import cn.itcast.domain.EmpExample.Criteria;
import cn.itcast.domain.EmpVo;
import cn.itcast.util.ExcelUtils;
import cn.itcast.util.Result;
@Service
public class EmpServiceImpl implements EmpService{

	@Resource
	private EmpMapper empMapper;
	@Resource
	private DeptMapper deptMapper;
	@Resource
	private ExcelUtils excelUtils;
	@Override
	public Result selectAll() {
		// TODO Auto-generated method stub
		int code = 0 ;
		Result result = new Result();
		try {
			List<Emp> emps = empMapper.selectAll();
			for (Emp emp : emps) {
				if(emp.getMgr()!=null) {
					String mgrName = empMapper.selectByPrimaryKey(emp.getMgr()).getEname();

					Dept dept = deptMapper.selectByPrimaryKey(emp.getDeptno());
					emp.setMgrName(mgrName);
					emp.setDept(dept);
				}else {
					Dept dept = deptMapper.selectByPrimaryKey(emp.getDeptno());
					emp.setDept(dept);
				}
			}
			code =1;
			result.success(code, emps);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.fail(code);
		}

		return result ;
	}

	@Override
	public List<Emp> findEmpByWhere(EmpVo empVo) {
		// TODO Auto-generated method stub
		List<Emp> emps = empMapper.findEmpByWhere(empVo);
		for (Emp emp : emps) {
			if(emp.getMgr()!=null) {
				String mgrName = empMapper.selectByPrimaryKey(emp.getMgr()).getEname();

				Dept dept = deptMapper.selectByPrimaryKey(emp.getDeptno());
				emp.setMgrName(mgrName);
				emp.setDept(dept);
			}else {
				Dept dept = deptMapper.selectByPrimaryKey(emp.getDeptno());
				emp.setDept(dept);
			}
		}
		return emps ;
	}

	@Override
	public int delete(Integer empNo) {
		int code = 0;
		try {
			code = empMapper.deleteByPrimaryKey(empNo);empMapper.deleteByPrimaryKey(empNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	public int deleteBatch(List<Integer> ids) {
		int code = 0;
		EmpExample example = new EmpExample();
		Criteria criteria = example.createCriteria();
		//delete from xxx where emp_id in(1,2,3)
		criteria.andEmpnoIn(ids);
		try {
			code = empMapper.deleteByExample(example);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}

	@Override
	public Result selectByName(String ename) {
		int code = 0;
		Result result = new Result();
		//先判断用户名是否是合法的表达式;
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!ename.matches(regx)){
			result.fail(code);
			return result;
		}
		EmpExample example = new EmpExample();
		Criteria criteria = example.createCriteria();
		criteria.andEnameEqualTo(ename);
		long count = empMapper.countByExample(example);
		if(count<1) {
			code = 1;
			result.success(code, null);
		}else {
			result.fail(code);
		}
		return result;
	}

	@Override
	public Result saveOrUpdateEmp(Emp emp) {
		int code = 0;
		Result result = new Result();
		if(emp.getEmpno() != null) {
			if(emp.getMgr() != null && emp.getMgr()==0) {
				emp.setMgr(null);
			}
			code = empMapper.updateByPrimaryKeySelective(emp);
		}else {
			code = empMapper.insert(emp);
		}
		if(code==1) {
			result = this.selectAll(); 
		}else {
			result.fail(code);
		}
		return result;
	}

	@Override
	public Result getById(Integer empNo) {
		// TODO Auto-generated method stub
		int code = 0 ;
		Result result = new Result();
		try {
			Emp _emp = empMapper.selectByPrimaryKey(empNo);
			List<Emp> emps = new ArrayList<>();
			emps.add(_emp);
			for (Emp emp : emps) {
				if(emp.getMgr()!=null) {
					String mgrName = empMapper.selectByPrimaryKey(emp.getMgr()).getEname();

					Dept dept = deptMapper.selectByPrimaryKey(emp.getDeptno());
					emp.setMgrName(mgrName);
					emp.setDept(dept);
				}else {
					Dept dept = deptMapper.selectByPrimaryKey(emp.getDeptno());
					emp.setDept(dept);
				}
				code =1;
				result.success(code, emps);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.fail(code);
		}

		return result ;
	}

	@Override
	public Result getMgrs() {
		int code = 0 ;
		Result result = new Result();
		try {
			List<Integer> _mgrs = empMapper.selectDistinctMgr();
			List<Emp> emps = new ArrayList<>();
			for (Integer _mgr : _mgrs) {
				Emp emp = new Emp();
				String mgrName = empMapper.selectByPrimaryKey(_mgr).getEname();
				emp.setMgr(_mgr);
				emp.setMgrName(mgrName);
				emps.add(emp);
			}
			code =1;
			result.success(code, emps);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.fail(code);
		}

		return result ;
	}

	@Override
	public void exportEmpExcel(List<Emp> emps, ServletOutputStream outputStream) {
		excelUtils.exportEmpExcel(emps, outputStream);
	}


	@Override
	public void importExcel(MultipartFile empExcel) {
		excelUtils.importExcel(empExcel);
	}

	@Override
	public Result getEmpSalStatisticData() {
		int code = 0 ;
		Result result = new Result();
		List<Emp> list = empMapper.selectEmpSalStatisticData();
		if(list != null && list.size() > 0) {
			code = 1;
			List<Map<String,Object>> chartData = new ArrayList<>();
			Map<String,Object> map = null;
			for (Emp emp : list) {
				map = new HashMap<>();
				String empName = emp.getEname();
				map.put("label", empName);
				map.put("value",emp.getSal()!=null?emp.getSal():"");
				chartData.add(map);
			}
			return result.success(code, chartData);
		}
		return result.fail(code);
	}

	@Override
	public Result getEmpCommStatisticData() {
		int code = 0 ;
		Result result = new Result();
		List<Emp> list = empMapper.selectCommStatisticData();
		if(list != null && list.size() > 0) {
			code = 1;
			List<Map<String,Object>> chartData = new ArrayList<>();
			Map<String,Object> map = null;
			for (Emp emp : list) {
				map = new HashMap<>();
				String empName = emp.getEname();
				map.put("label", empName);
				map.put("value",emp.getComm()!=null?emp.getComm():"");
				chartData.add(map);
			}
			return result.success(code, chartData);
		}
		return result.fail(code);
	}

}
