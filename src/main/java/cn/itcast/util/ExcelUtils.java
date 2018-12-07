package cn.itcast.util;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import cn.itcast.dao.DeptMapper;
import cn.itcast.dao.EmpMapper;
import cn.itcast.domain.Dept;
import cn.itcast.domain.Emp;
@Component
public class ExcelUtils {
	@Resource
	private  EmpMapper empMapper;
	@Resource
	private  DeptMapper deptMapper;
	public  void exportEmpExcel(List<Emp> emps, ServletOutputStream outputStream) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(); 
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 7);
			HSSFCellStyle titleStyle = createCellStyle(workbook,(short) 20);
			HSSFCellStyle colTitleStyle = createCellStyle(workbook,(short) 14);
			HSSFSheet sheet = workbook.createSheet("員工列表");
			sheet.addMergedRegion(cellRangeAddress);
			sheet.setDefaultColumnWidth(22);
			HSSFRow row_title = sheet.createRow(0);
			HSSFCell cell_title = row_title.createCell(0);
			cell_title.setCellStyle(titleStyle);
			cell_title.setCellValue("員工列表");
			HSSFRow row_colTitle = sheet.createRow(1);
			String colTitles[] = {"empno","ename","job","hiredate","sal","comm","主管","dept"};
			for(int i=0;i<colTitles.length;i++) {
				HSSFCell cell_colTitle = row_colTitle.createCell(i);
				cell_colTitle.setCellStyle(colTitleStyle);
				cell_colTitle.setCellValue(colTitles[i]);
			}
			if(emps !=null && emps.size()>0) {
				for(int j=0;j<emps.size();j++) {
					HSSFRow row_emp = sheet.createRow(j+2);
					HSSFCell cell_emp_0 = row_emp.createCell(0);
					cell_emp_0.setCellValue(emps.get(j).getEmpno()!=null ? emps.get(j).getEmpno().toString() : "");
					HSSFCell cell_emp_1 = row_emp.createCell(1);
					cell_emp_1.setCellValue(emps.get(j).getEname() !=null ? emps.get(j).getEname(): "");
					HSSFCell cell_emp_2 = row_emp.createCell(2);
					cell_emp_2.setCellValue(emps.get(j).getJob() !=null ? emps.get(j).getJob(): "");
					HSSFCell cell_emp_3 = row_emp.createCell(3);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					cell_emp_3.setCellValue(emps.get(j).getHiredate() !=null ? sdf.format(emps.get(j).getHiredate()) : "");
					HSSFCell cell_emp_4 = row_emp.createCell(4);
					cell_emp_4.setCellValue(emps.get(j).getSal() !=null ? emps.get(j).getSal().toString() : "");
					HSSFCell cell_emp_5 = row_emp.createCell(5);
					cell_emp_5.setCellValue(emps.get(j).getComm() !=null ? emps.get(j).getComm().toString() : "");
					HSSFCell cell_emp_6 = row_emp.createCell(6);
					cell_emp_6.setCellValue(emps.get(j).getMgrName() !=null ? emps.get(j).getMgrName(): "");
					HSSFCell cell_emp_7 = row_emp.createCell(7);
					cell_emp_7.setCellValue(emps.get(j).getDept().getDname() !=null ? emps.get(j).getDept().getDname() : "");
				}
			}
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void importExcel(MultipartFile empExcel) {
		try {
			FileInputStream fileInputStream = (FileInputStream) empExcel.getInputStream();
			boolean is03Excel = empExcel.getOriginalFilename().matches("^.+\\.(?i)(xls)$");
			//1、读取工作簿
			Workbook workbook = is03Excel ? new HSSFWorkbook(fileInputStream):new XSSFWorkbook(fileInputStream);
			//2、读取工作表
			Sheet sheet = workbook.getSheetAt(0);
			//3、读取行
			if(sheet.getPhysicalNumberOfRows() > 2){
				Emp emp = null;
				for(int k = 2; k < sheet.getPhysicalNumberOfRows(); k++){
					//4、读取单元格
					Row row = sheet.getRow(k);
					emp = new Emp();
					//员工编号
					Cell cell0 = row.getCell(0);
					emp.setEmpno(null);
					//员工名
					Cell cell1 = row.getCell(1);
					String empName = cell1.getStringCellValue();
					if(empName != null && empName.trim() != "") {
						boolean empExist = isEmpExist(empName);
						if(!empExist) {
							emp.setEname(empName);
						}else {
							continue;
						}
					}else {
						continue;
					}
					//职位
					Cell cell2 = row.getCell(2);
					emp.setJob(cell2.getStringCellValue());
					//入职时间
					Cell cell3 = row.getCell(3);
					if(cell3.getDateCellValue() != null){
						emp.setHiredate(cell3.getDateCellValue());
					}
					//月薪
					Cell cell4 = row.getCell(4);
					emp.setSal(BigDecimal.valueOf(cell4.getNumericCellValue()).doubleValue());
					//奖金
					Cell cell5 = row.getCell(5);
					emp.setComm(BigDecimal.valueOf(cell5.getNumericCellValue()).doubleValue());
					//主管
					Cell cell6 = row.getCell(6);
					String mgrName = cell6.getStringCellValue();
					emp.setMgr(getMgrByName(mgrName) != null ? getMgrByName(mgrName).getEmpno() : null);
					//部门名称
					Cell cell7 = row.getCell(7);
					String deptName = cell7.getStringCellValue();
					emp.setDeptno(getDeptByName(deptName) != null ? getDeptByName(deptName).getDeptno() : null);
					//5、保存用户
					excelSaveEmp(emp);
				}
			}
			workbook.close();
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private  HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints(fontSize);
		cellStyle.setFont(font);
		return cellStyle;
	}
	private  boolean isEmpExist(String empName) {
		List<Emp> emps = empMapper.selectAll();
		for (Emp emp : emps) {
			if(empName.equals(emp.getEname())) {
				return true;
			}
		}
		return false;
	} 

	private  void excelSaveEmp(Emp emp) {
		empMapper.insert(emp);
	}

	private  Emp getMgrByName(String mgrName) {
		if(mgrName != null && mgrName.trim() != "") {
			List<Emp> emps = empMapper.selectAll();
			for (Emp emp : emps) {
				if(mgrName.equals(emp.getEname())) {
					return emp;
				}
			}
		}
		return null;
	}

	private  Dept getDeptByName(String deptName) {
		if(deptName != null && deptName.trim() != "") {
			List<Dept> depts = deptMapper.selectAll();
			for (Dept dept : depts) {
				if(deptName.equals(dept.getDname())) {
					return dept;
				}
			}
		}
		return null;
	}

}
