package com.mbrpf.model;

import java.util.*;
import java.sql.*;

public class MbrpfJDBCDAO implements MbrpfDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA101";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO mbrpf (mbrno,mbract,mbrpw,mbrname,mbrimg,birth,sex,mail,phone,mbrac,nickname,points,status,ratedtotal,startotal,unattend,ttattend) VALUES (mem_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT mbrno,mbract,mbrpw,mbrname,mbrimg,to_char(birth,'yyyy-mm-dd') birth,sex,mail,phone,mbrac,nickname,points,status,ratedtotal,startotal,unattend,ttattend FROM mbrpf order by mbrno";
	private static final String GET_ONE_STMT = "SELECT mbrno,mbract,mbrpw,mbrname,mbrimg,to_char(birth,'yyyy-mm-dd') birth,sex,mail,phone,mbrac,nickname,points,status,ratedtotal,startotal,unattend,ttattend FROM mbrpf where mbrno = ?";
	private static final String DELETE = "DELETE FROM mbrpf where mbrno = ?";
	private static final String UPDATE = "UPDATE mbrpf set mbract=?,mbrpw=?,mbrname=?,mbrimg=?,birth=?,sex=?,mail=?,phone=?,mbrac=?,nickname=?,points=?,status=?,ratedtotal=?,startotal=?,unattend=?,ttattend=? where mbrno = ?";
	private static final String FIND_BY_MBRACT = "SELECT mbract,mbrpw,mbrname FROM mbrpf where mbract = ?";
	private static final String LOGIN = "SELECT * FROM mbrpf WHERE mbract = ?";
	private static final String UPDATEPOINT = "UPDATE mbrpf set points=? where mbrno = ?";

	@Override
	public void insert(MbrpfVO mbrpfVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(2, mbrpfVO.getMbract());
			pstmt.setString(3, mbrpfVO.getMbrpw());
			pstmt.setString(4, mbrpfVO.getMbrname());
			pstmt.setBytes(5, mbrpfVO.getMbrimg());
			pstmt.setDate(6, mbrpfVO.getBirth());
			pstmt.setInt(7, mbrpfVO.getSex());
			pstmt.setString(8, mbrpfVO.getMail());
			pstmt.setString(9, mbrpfVO.getPhone());
			pstmt.setString(10, mbrpfVO.getMbrac());
			pstmt.setString(11, mbrpfVO.getNickname());
			pstmt.setInt(12, mbrpfVO.getPoints());
			pstmt.setInt(13, mbrpfVO.getStatus());
			pstmt.setInt(14, mbrpfVO.getRatedtotal());
			pstmt.setInt(15, mbrpfVO.getStartotal());
			pstmt.setInt(16, mbrpfVO.getUnattend());
			pstmt.setInt(17, mbrpfVO.getTtattend());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(MbrpfVO mbrpfVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mbrpfVO.getMbract());
			pstmt.setString(2, mbrpfVO.getMbrpw());
			pstmt.setString(3, mbrpfVO.getMbrname());
			pstmt.setBytes(4, mbrpfVO.getMbrimg());
			pstmt.setDate(5, mbrpfVO.getBirth());
			pstmt.setInt(6, mbrpfVO.getSex());
			pstmt.setString(7, mbrpfVO.getMail());
			pstmt.setString(8, mbrpfVO.getPhone());
			pstmt.setString(9, mbrpfVO.getMbrac());
			pstmt.setString(10, mbrpfVO.getNickname());
			pstmt.setInt(11, mbrpfVO.getPoints());
			pstmt.setInt(12, mbrpfVO.getStatus());
			pstmt.setInt(13, mbrpfVO.getRatedtotal());
			pstmt.setInt(14, mbrpfVO.getStartotal());
			pstmt.setInt(15, mbrpfVO.getUnattend());
			pstmt.setInt(16, mbrpfVO.getTtattend());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String mbrno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mbrno);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public MbrpfVO findByPrimaryKey(String mbrno) {

		MbrpfVO mbrpfVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mbrno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// mbrpfVO 也稱為 Domain objects
				mbrpfVO = new MbrpfVO();
				mbrpfVO.setMbrno(rs.getString("mbrno"));
				mbrpfVO.setMbract(rs.getString("mbract"));
				mbrpfVO.setMbrpw(rs.getString("mbrpw"));
				mbrpfVO.setMbrname(rs.getString("mbrname"));
				mbrpfVO.setMbrimg(rs.getBytes("mbrimg"));
				mbrpfVO.setBirth(rs.getDate("birth"));
				mbrpfVO.setSex(rs.getInt("sex"));
				mbrpfVO.setMail(rs.getString("mail"));
				mbrpfVO.setPhone(rs.getString("phone"));
				mbrpfVO.setMbrac(rs.getString("mbrac"));
				mbrpfVO.setNickname(rs.getString("nickname"));
				mbrpfVO.setPoints(rs.getInt("points"));
				mbrpfVO.setStatus(rs.getInt("status"));
				mbrpfVO.setRatedtotal(rs.getInt("ratedtotal"));
				mbrpfVO.setStartotal(rs.getInt("satrtotal"));
				mbrpfVO.setUnattend(rs.getInt("unattend"));
				mbrpfVO.setTtattend(rs.getInt("ttattend"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return mbrpfVO;
	}

	@Override
	public List<MbrpfVO> getAll() {
		List<MbrpfVO> list = new ArrayList<MbrpfVO>();
		MbrpfVO mbrpfVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// mbrpfVO 也稱為 Domain objects
				mbrpfVO = new MbrpfVO();
				mbrpfVO.setMbrno(rs.getString("mbrno"));
				mbrpfVO.setMbract(rs.getString("mbract"));
				mbrpfVO.setMbrpw(rs.getString("mbrpw"));
				mbrpfVO.setMbrname(rs.getString("mbrname"));
				mbrpfVO.setMbrimg(rs.getBytes("mbrimg"));
				mbrpfVO.setBirth(rs.getDate("birth"));
				mbrpfVO.setSex(rs.getInt("sex"));
				mbrpfVO.setMail(rs.getString("mail"));
				mbrpfVO.setPhone(rs.getString("phone"));
				mbrpfVO.setMbrac(rs.getString("mbrac"));
				mbrpfVO.setNickname(rs.getString("nickname"));
				mbrpfVO.setPoints(rs.getInt("points"));
				mbrpfVO.setStatus(rs.getInt("status"));
				mbrpfVO.setRatedtotal(rs.getInt("ratedtotal"));
				mbrpfVO.setStartotal(rs.getInt("satrtotal"));
				mbrpfVO.setUnattend(rs.getInt("unattend"));
				mbrpfVO.setTtattend(rs.getInt("ttattend"));
				list.add(mbrpfVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public MbrpfVO findByMbract(String mbract) {

		MbrpfVO mbrpfVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mbract);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// mbrpfVO 也稱為 Domain objects
				mbrpfVO = new MbrpfVO();
				mbrpfVO.setMbract(rs.getString("mbract"));
				mbrpfVO.setMbrpw(rs.getString("mbrpw"));
				mbrpfVO.setMbrname(rs.getString("mbrname"));
				mbrpfVO.setMbrimg(rs.getBytes("mbrimg"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return mbrpfVO;
	}

	@Override
	public MbrpfVO login(String mbract) {

		MbrpfVO mbrpfVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(LOGIN);

			pstmt.setString(1, mbract);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// mbrpfVO 也稱為 Domain objects
				mbrpfVO = new MbrpfVO();
				mbrpfVO.setMbrno(rs.getString("mbrno"));
				mbrpfVO.setMbract(rs.getString("mbract"));
				mbrpfVO.setMbrpw(rs.getString("mbrpw"));
				mbrpfVO.setMbrname(rs.getString("mbrname"));
				mbrpfVO.setMbrimg(rs.getBytes("mbrimg"));
				mbrpfVO.setBirth(rs.getDate("birth"));
				mbrpfVO.setSex(rs.getInt("sex"));
				mbrpfVO.setMail(rs.getString("mail"));
				mbrpfVO.setPhone(rs.getString("phone"));
				mbrpfVO.setMbrac(rs.getString("mbrac"));
				mbrpfVO.setNickname(rs.getString("nickname"));
				mbrpfVO.setPoints(rs.getInt("points"));
				mbrpfVO.setStatus(rs.getInt("status"));
				mbrpfVO.setRatedtotal(rs.getInt("ratedtotal"));
				mbrpfVO.setStartotal(rs.getInt("startotal"));
				mbrpfVO.setUnattend(rs.getInt("unattend"));
				mbrpfVO.setTtattend(rs.getInt("ttattend"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return mbrpfVO;
	}

	public static void main(String[] args) {

		MbrpfDAO dao = new MbrpfDAO();

		// 新增
		MbrpfVO mbrpfVO1 = new MbrpfVO();
		mbrpfVO1.setMbrno("585");
		mbrpfVO1.setMbract("iam555");
		mbrpfVO1.setMbrpw("555");
		mbrpfVO1.setMbrname("5");
		mbrpfVO1.setMbrimg(null);
		mbrpfVO1.setBirth(java.sql.Date.valueOf("2005-05-05"));
		mbrpfVO1.setSex(1);
		mbrpfVO1.setMail("555@gmail.com");
		mbrpfVO1.setPhone("0955555555");
		mbrpfVO1.setMbrac("5555-5555-5555-5555");
		mbrpfVO1.setNickname("V");
		mbrpfVO1.setPoints(500);
		mbrpfVO1.setStatus(1);
		mbrpfVO1.setRatedtotal(50);
		mbrpfVO1.setStartotal(50);
		mbrpfVO1.setUnattend(0);
		mbrpfVO1.setTtattend(50);
		dao.insert(mbrpfVO1);

		// 修改
//	MbrpfVO mbrpfVO2 = new MbrpfVO();
//	mbrpfVO2.setMbrno("666");
//	mbrpfVO2.setMbract("iam666");
//	mbrpfVO2.setMbrpw("666");
//	mbrpfVO2.setMbrname("5");
//	mbrpfVO2.setMbrimg(null);
//	mbrpfVO2.setBirth(java.sql.Date.valueOf("2005-05-05"));
//	mbrpfVO2.setSex(1);
//	mbrpfVO2.setMail("555@gmail.com");
//	mbrpfVO2.setPhone("0955555555");
//	mbrpfVO2.setMbrac("5555-5555-5555-5555");
//	mbrpfVO2.setNickname("V");
//	mbrpfVO2.setPoints(500);
//	mbrpfVO2.setStatus(1);
//	mbrpfVO2.setRatedtotal(50);
//	mbrpfVO2.setStartotal(50);
//	mbrpfVO2.setUnattend(0);
//	mbrpfVO2.setTtattend(50);
//	dao.update(mbrpfVO2);
//
//	// 刪除
//	dao.delete("666");
//
//	// 查詢
//	MbrpfVO mbrpfVO3 = dao.findByPrimaryKey("666");
//	System.out.print(mbrpfVO3.getMbrno() + ",");
//	System.out.print(mbrpfVO3.getMbract() + ",");
//	System.out.print(mbrpfVO3.getMbrpw() + ",");
//	System.out.print(mbrpfVO3.getMbrname() + ",");
//	System.out.print(mbrpfVO3.getMbrimg() + ",");
//	System.out.print(mbrpfVO3.getBirth() + ",");
//	System.out.print(mbrpfVO3.getSex() + ",");
//	System.out.print(mbrpfVO3.getMail() + ",");
//	System.out.print(mbrpfVO3.getPhone() + ",");
//	System.out.print(mbrpfVO3.getMbrac() + ",");
//	System.out.print(mbrpfVO3.getNickname() + ",");
//	System.out.print(mbrpfVO3.getPoints() + ",");
//	System.out.print(mbrpfVO3.getStatus() + ",");
//	System.out.print(mbrpfVO3.getRatedtotal() + ",");
//	System.out.print(mbrpfVO3.getStartotal() + ",");
//	System.out.print(mbrpfVO3.getUnattend() + ",");
//	System.out.print(mbrpfVO3.getTtattend() + ",");
//	System.out.println("---------------------");
//
//	// 查詢
//	List<MbrpfVO> list = dao.getAll();
//	for (MbrpfVO aEmp : list) {
//		System.out.print(mbrpfVO3.getMbrno() + ",");
//		System.out.print(mbrpfVO3.getMbract() + ",");
//		System.out.print(mbrpfVO3.getMbrpw() + ",");
//		System.out.print(mbrpfVO3.getMbrname() + ",");
//		System.out.print(mbrpfVO3.getMbrimg() + ",");
//		System.out.print(mbrpfVO3.getBirth() + ",");
//		System.out.print(mbrpfVO3.getSex() + ",");
//		System.out.print(mbrpfVO3.getMail() + ",");
//		System.out.print(mbrpfVO3.getPhone() + ",");
//		System.out.print(mbrpfVO3.getMbrac() + ",");
//		System.out.print(mbrpfVO3.getNickname() + ",");
//		System.out.print(mbrpfVO3.getPoints() + ",");
//		System.out.print(mbrpfVO3.getStatus() + ",");
//		System.out.print(mbrpfVO3.getRatedtotal() + ",");
//		System.out.print(mbrpfVO3.getStartotal() + ",");
//		System.out.print(mbrpfVO3.getUnattend() + ",");
//		System.out.print(mbrpfVO3.getTtattend() + ",");
//		System.out.println();
//	}
	}
	
	@Override
	public void updatePoint(MbrpfVO mbrpfVO, Connection conn) {

		PreparedStatement pstmt = null;

		try {

			pstmt = conn.prepareStatement(UPDATEPOINT);

			pstmt.setInt(1, mbrpfVO.getPoints());
			pstmt.setString(2, mbrpfVO.getMbrno());
			pstmt.executeUpdate();

		} catch (SQLException se) {

			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.getStackTrace();
				}
			}

		}

	}
	
	@Override
	public void updatePoint(MbrpfVO mbrpfVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATEPOINT);

			pstmt.setInt(1, mbrpfVO.getPoints());
			pstmt.setString(2, mbrpfVO.getMbrno());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
}