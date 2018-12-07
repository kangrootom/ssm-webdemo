package cn.itcast.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.itcast.domain.Emp;
import cn.itcast.domain.EmpVo;
import cn.itcast.service.EmpService;
import cn.itcast.util.Result;

@Controller
@RequestMapping("/emp")
public class EmpAction {

	@Resource
	private EmpService empServiceImpl;
	
	@RequestMapping(value="/getEmpStatisticData",method=RequestMethod.POST)
	@ResponseBody
	public Result getEmpStatisticData(@RequestParam("dimensionality") String dimensionality){
		Result result = null;
		if(dimensionality.equals("sal")) {
			result = empServiceImpl.getEmpSalStatisticData();
		}else {
			result = empServiceImpl.getEmpCommStatisticData();
		}
		return result;
	}
	
	@RequestMapping("/empStatisticChartUI")
	public String empUI(){
		return "emp/empStatisticChartUI";
	}

	@RequestMapping("/importExcel")
	public String importExcel(MultipartFile empExcel)throws Exception{

		if(empExcel != null){
			//是否是excel
			if(empExcel.getOriginalFilename().matches("^.+\\.(?i)((xls)|(xlsx))$")){
				//2、导入
				empServiceImpl.importExcel(empExcel);
			}
		}
		return "redirect:empUI";
	}

	@RequestMapping("/exportExcel")
	public void export(HttpServletResponse resp){
		try {
			List<Emp> emps = this.selectAll().getList();
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment;filename="+new String("員工列表.xls".getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = resp.getOutputStream();
			empServiceImpl.exportEmpExcel(emps,outputStream);
			if(outputStream != null) {
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/emps")
	@ResponseBody
	public Result selectAll(){
		Result result = empServiceImpl.selectAll();
		return result;
	}
	@RequestMapping("/mgrs")
	@ResponseBody
	public Result getMgrs(){
		Result result = empServiceImpl.getMgrs();
		return result;
	}
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public Result check(@RequestParam("ename") String ename){
		Result result = empServiceImpl.selectByName(ename);
		return result;
	}
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public Result save(@Valid Emp emp,BindingResult br){

		if(br.hasErrors()){
			//校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			Map<String, Object> map = new HashMap<>();
			List<FieldError> errors = br.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("错误的字段名："+fieldError.getField());
				System.out.println("错误信息："+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			int code = 0;
			Result result = new Result();
			return result.fail(code);
		}else{
			return empServiceImpl.saveOrUpdateEmp(emp);
		}
	}
	@RequestMapping(value="/queryByWhere",method=RequestMethod.POST)
	@ResponseBody
	public Result findEmpByWhere(@Valid EmpVo empVo,BindingResult br){
		int code = 0;
		Result result = new Result();
		if(br.hasErrors()){
			//校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			Map<String, Object> map = new HashMap<>();
			List<FieldError> errors = br.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("错误的字段名："+fieldError.getField());
				System.out.println("错误信息："+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			code = -1;
			return result.fail(code);
		}else{
			List<Emp> emps = empServiceImpl.findEmpByWhere(empVo);
			code = 1;
			result.success(code, emps);
			return result;
		}

	}
	@RequestMapping("/empUI")
	public String empUI(Model model){
		model.addAttribute("result", this.selectAll());
		return "emp/empUI";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{empIds}",method=RequestMethod.DELETE)
	@ResponseBody
	public Result delete(@PathVariable(value="empIds")String empIds,Model model ){
		Result result = new Result();
		int code = 0;
		if(empIds.contains("-")){
			List<Integer> del_ids = new ArrayList<>();
			String[] str_ids = empIds.split("-");
			//组装id的集合
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			code = empServiceImpl.deleteBatch(del_ids);
		}else{
			Integer id = Integer.parseInt(empIds);
			code = empServiceImpl.delete(id);
		}
		if(code>=1) {
			code = 1;
			List<Emp> emps = this.selectAll().getList();
			result.success(code, emps);
		}else {
			result.fail(code);
		}
		return result;
	}
	@RequestMapping(value="/{empId}",method=RequestMethod.GET)
	@ResponseBody
	public Result getById(@PathVariable(value="empId")String empId,Model model ){
		Integer empNo = Integer.parseInt(empId);
		return empServiceImpl.getById(empNo);
	}
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	@ResponseBody
	public Result update(@Valid Emp emp,BindingResult br){

		if(br.hasErrors()){
			//校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			Map<String, Object> map = new HashMap<>();
			List<FieldError> errors = br.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("错误的字段名："+fieldError.getField());
				System.out.println("错误信息："+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			int code = 0;
			Result result = new Result();
			return result.fail(code);
		}else{
			return empServiceImpl.saveOrUpdateEmp(emp);
		}
	}
}
