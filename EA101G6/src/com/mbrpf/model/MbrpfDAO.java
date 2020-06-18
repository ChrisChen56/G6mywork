package com.mbrpf.model;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.*;
import javax.sql.DataSource;


public class MbrpfDAO implements MbrpfDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/EA101G6DB");
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String LOGIN_STMT = "SELECT mbrno,mbract,mbrpw,mbrname,points FROM MBRPF WHERE mbract=? AND mbrpw=?";
	private static final String UPDATE_STMT =
			"UPDATE MBRPF SET points=? WHERE mbrno=?";
	private static final String GET_ONE_STMT=
			"SELECT mbrno,mbract,mbrpw,mbrname,points FROM MBRPF WHERE mbrno=?";
	private static final String GET_ALL_STMT =
			"SELECT mbrno,mbract,mbrpw,mbrname,points FROM MBRPF";
	
	public boolean check(String mbract, String mbrpw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGIN_STMT);
			
			pstmt.setString(1, mbract);
			pstmt.setString(2, mbrpw);
			System.out.println("2");
			if(pstmt.executeUpdate() != 0) 
				result = true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public MbrpfVO findByActPw(String mbract, String mbrpw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MbrpfVO mbrpfvo = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGIN_STMT);
			
			pstmt.setString(1, mbract);
			pstmt.setString(2, mbrpw);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				mbrpfvo = new MbrpfVO();
				mbrpfvo.setMbrno(rs.getString(1));
				mbrpfvo.setMbract(rs.getString(2));
				mbrpfvo.setMbrpw(rs.getString(3));
				mbrpfvo.setMbrname(rs.getString(4));
				mbrpfvo.setPoints(rs.getInt(5));
			}
			
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return mbrpfvo;
	}
	
	@Override
	public void update(MbrpfVO mbrpfvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, mbrpfvo.getPoints());
			pstmt.setString(2, mbrpfvo.getMbrno());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public MbrpfVO findByPrimaryKey(String shgmno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MbrpfVO mbrpfvo = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, shgmno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				mbrpfvo = new MbrpfVO();
				mbrpfvo.setMbrno(rs.getString(1));
				mbrpfvo.setMbract(rs.getString(2));
				mbrpfvo.setMbrpw(rs.getString(3));
				mbrpfvo.setMbrname(rs.getString(4));
				mbrpfvo.setPoints(rs.getInt(5));
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return mbrpfvo;
	}

	@Override
	public List<MbrpfVO> getall() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MbrpfVO> list= new ArrayList<MbrpfVO>();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MbrpfVO mbrpfvo = new MbrpfVO();
				mbrpfvo.setMbrno(rs.getString(1));
				mbrpfvo.setMbract(rs.getString(2));
				mbrpfvo.setMbrpw(rs.getString(3));
				mbrpfvo.setMbrname(rs.getString(4));
				mbrpfvo.setPoints(rs.getInt(5));
				
				list.add(mbrpfvo);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
