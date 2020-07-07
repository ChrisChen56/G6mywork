package com.mbrpf.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MbrpfJNDIDAO implements MbrpfDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
			"INSERT INTO mbrpf (mbrno,mbract,mbrpw,mbrname,mbrimg,birth,sex,mail,phone,mbrac,nickname,points,status,ratedtotal,startotal,unattend,ttattend) VALUES (mem_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT mbrno,mbract,mbrpw,mbrname,mbrimg,to_char(birth,'yyyy-mm-dd') birth,sex,mail,phone,mbrac,nickname,points,status,ratedtotal,startotal,unattend,ttattend FROM mbrpf order by mbrno";
		private static final String GET_ONE_STMT = 
			"SELECT mbrno,mbract,mbrpw,mbrname,mbrimg,to_char(birth,'yyyy-mm-dd') birth,sex,mail,phone,mbrac,nickname,points,status,ratedtotal,startotal,unattend,ttattend FROM mbrpf where mbrno = ?";
		private static final String DELETE = 
			"DELETE FROM mbrpf where mbrno = ?";
		private static final String UPDATE = 
			"UPDATE mbrpf set mbract=?,mbrpw=?,mbrname=?,mbrimg=?,birth=?,sex=?,mail=?,phone=?,mbrac=?,nickname=?,points=?,status=?,ratedtotal=?,startotal=?,unattend=?,ttattend=? where mbrno = ?";
		private static final String FIND_BY_MBRACT = 
				"SELECT mbract,mbrpw,mbrname FROM mbrpf where mbract = ?";
		private static final String LOGIN =
				"SELECT * FROM mbrpf WHERE mbract = ?";
		private static final String UPDATEPOINT = 
				"UPDATE mbrpf set points=? where mbrno = ?";
	@Override
	public void insert(MbrpfVO mbrpfVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

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

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			con = ds.getConnection();
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
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mbrno);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			con = ds.getConnection();
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
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			con = ds.getConnection();
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
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

				
				con = ds.getConnection();
				pstmt = con.prepareStatement(FIND_BY_MBRACT);

				pstmt.setString(1, mbract);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// mbrpfVO  也稱為 Domain objects
					mbrpfVO = new MbrpfVO();
					mbrpfVO.setMbract(rs.getString("mbract"));
					mbrpfVO.setMbrpw(rs.getString("mbrpw"));
					mbrpfVO.setMbrname(rs.getString("mbrname"));
					mbrpfVO.setMbrimg(rs.getBytes("mbrimg"));
					
				}

				// Handle any driver errors
			}catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGIN);

			pstmt.setString(1, mbract);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// mbrpfVO  也稱為 Domain objects
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
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATEPOINT);

			pstmt.setInt(1, mbrpfVO.getPoints());
			pstmt.setString(2, mbrpfVO.getMbrno());
			pstmt.executeUpdate();

			// Handle any driver errors
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