package cn.itcast.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import cn.itcast.domain.Dept;
import cn.itcast.domain.DeptExample;

public interface DeptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dept
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    long countByExample(DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dept
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    int deleteByExample(DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dept
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    int deleteByPrimaryKey(Integer deptno);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dept
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    int insert(Dept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dept
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    int insertSelective(Dept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dept
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    List<Dept> selectByExample(DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dept
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    Dept selectByPrimaryKey(Integer deptno);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dept
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    int updateByExampleSelective(@Param("record") Dept record, @Param("example") DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dept
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    int updateByExample(@Param("record") Dept record, @Param("example") DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dept
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    int updateByPrimaryKeySelective(Dept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dept
     *
     * @mbg.generated Mon Nov 26 19:57:07 CST 2018
     */
    int updateByPrimaryKey(Dept record);

	List<Dept> selectAll();
}