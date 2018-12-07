package cn.itcast.dao;



import java.util.List;
import org.apache.ibatis.annotations.Param;

import cn.itcast.domain.Emp;
import cn.itcast.domain.EmpExample;
import cn.itcast.domain.EmpVo;

public interface EmpMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emp
     *
     * @mbg.generated Mon Nov 26 19:57:06 CST 2018
     */
    long countByExample(EmpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emp
     *
     * @mbg.generated Mon Nov 26 19:57:06 CST 2018
     */
    int deleteByExample(EmpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emp
     *
     * @mbg.generated Mon Nov 26 19:57:06 CST 2018
     */
    int deleteByPrimaryKey(Integer empno);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emp
     *
     * @mbg.generated Mon Nov 26 19:57:06 CST 2018
     */
    int insert(Emp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emp
     *
     * @mbg.generated Mon Nov 26 19:57:06 CST 2018
     */
    int insertSelective(Emp record);
    
    List<Emp> selectAll();
    
    List<Emp> findEmpByWhere(EmpVo empVo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emp
     *
     * @mbg.generated Mon Nov 26 19:57:06 CST 2018
     */
    List<Emp> selectByExample(EmpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emp
     *
     * @mbg.generated Mon Nov 26 19:57:06 CST 2018
     */
    Emp selectByPrimaryKey(Integer empno);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emp
     *
     * @mbg.generated Mon Nov 26 19:57:06 CST 2018
     */
    int updateByExampleSelective(@Param("record") Emp record, @Param("example") EmpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emp
     *
     * @mbg.generated Mon Nov 26 19:57:06 CST 2018
     */
    int updateByExample(@Param("record") Emp record, @Param("example") EmpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emp
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    int updateByPrimaryKeySelective(Emp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table emp
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    int updateByPrimaryKey(Emp record);

	List<Emp> selectByName(String ename);

	List<Integer> selectDistinctMgr();

	List<Emp> selectEmpSalStatisticData();

	List<Emp> selectCommStatisticData();
}