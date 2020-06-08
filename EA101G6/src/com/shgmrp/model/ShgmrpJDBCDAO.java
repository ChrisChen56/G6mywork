package com.shgmrp.model;

import java.util.List;

public class ShgmrpJDBCDAO implements ShgmrpDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "hr";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
		"INSERT INTO  (empno,ename,job,hiredate,sal,comm,deptno) VALUES (emp2_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT empno,ename,job,to_char(hiredate,'yyyy-mm-dd') hiredate,sal,comm,deptno FROM emp2 order by empno";
	private static final String GET_ONE_STMT = 
		"SELECT empno,ename,job,to_char(hiredate,'yyyy-mm-dd') hiredate,sal,comm,deptno FROM emp2 where empno = ?";
	private static final String DELETE = 
		"DELETE FROM emp2 where empno = ?";
	private static final String UPDATE = 
		"UPDATE emp2 set ename=?, job=?, hiredate=?, sal=?, comm=?, deptno=? where empno = ?";
	
	public void insert(ShgmrpVO shgmrpvo) {
		
	}

	public void update(ShgmrpVO shgmrpvo) {
		
	}

	public void delete(Integer shgmrpno) {
		
	}

	public ShgmrpVO findByPrimaryKey(Integer shgmrpno) {
		return null;
	}

	public List<ShgmrpVO> getAll() {
		return null;
	}

}
