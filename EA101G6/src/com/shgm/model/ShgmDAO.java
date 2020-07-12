package com.shgm.model;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.*;
import javax.sql.DataSource;

import com.mbrpf.model.MbrpfService;
import com.mbrpf.model.MbrpfVO;

public class ShgmDAO implements ShgmDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA101G6DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO SHGM "
			+ "(shgmno,sellerno,buyerno,shgmname,price,intro,img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime) "
			+ "VALUES" + "('CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE SHGM SET sellerno=?,buyerno=?,shgmname=?,price=?,intro=?,img=?,upcheck=?,uptime=?,"
			+ "take=?,takernm=?,takerph=?,address=?,boxstatus=?,paystatus=?,status=?,soldtime=? WHERE shgmno=?";
	private static final String DELETE_STMT = "DELETE FROM SHGM WHERE shgmno=?";
	private static final String GET_ONE_STMT = "SELECT * FROM SHGM WHERE shgmno=?";
	private static final String GET_ONE_INFO = "SELECT shgmno,sellerno,buyerno,shgmname,price,replace(intro,CHR(10), '<BR>'),img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime FROM SHGM WHERE shgmno=?";
	private static final String GET_ALL_STMT = "SELECT shgmno,sellerno,buyerno,shgmname,price,replace(intro,CHR(10), '<BR>'),img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime"
			+ " FROM SHGM ORDER BY CAST(SUBSTR(shgmno, 5) AS INT)";
	private static final String GET_ALL_FOR_SELLER_STMT = "SELECT shgmno,sellerno,buyerno,shgmname,price,replace(intro,CHR(10), '<BR>'),img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime"
			+ " FROM SHGM WHERE sellerno=? ORDER BY CAST(SUBSTR(shgmno, 5) AS INT)";
	private static final String GET_ALL_FOR_BUYER_STMT = "SELECT shgmno,sellerno,buyerno,shgmname,price,replace(intro,CHR(10), '<BR>'),img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime"
			+ " FROM SHGM WHERE buyerno=? ORDER BY CAST(SUBSTR(shgmno, 5) AS INT)";
	private static final String MAINPAGE_GETALL_STMT = "SELECT shgmno,sellerno,buyerno,shgmname,price,replace(intro,CHR(10), '<BR>'),img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime"
			+ " FROM SHGM WHERE (upcheck=1 AND boxstatus=0 AND paystatus=0 AND status=0) ORDER BY CAST(SUBSTR(shgmno, 5) AS INT)";
	private static final String GET_ALL_FOR_PERSONALMKT = "SELECT shgmno,sellerno,buyerno,shgmname,price,replace(intro,CHR(10), '<BR>'),img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime"
			+ " FROM SHGM WHERE (upcheck=1 AND boxstatus=0 AND paystatus=0 AND status=0 AND sellerno=?) ORDER BY CAST(SUBSTR(shgmno, 5) AS INT)";
	private static final String SEARCH_STMT = "SELECT shgmno,sellerno,buyerno,shgmname,price,replace(intro,CHR(10), '<BR>'),img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime"
			+ " FROM SHGM WHERE (upcheck=1 AND boxstatus=0 AND paystatus=0 AND status=0 AND UPPER(shgmname) LIKE UPPER(?)) ORDER BY CAST(SUBSTR(shgmno, 5) AS INT)";

	public void insertShgm(ShgmVO shgmvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, shgmvo.getSellerno());
			pstmt.setString(2, shgmvo.getBuyerno());
			pstmt.setString(3, shgmvo.getShgmname());
			pstmt.setInt(4, shgmvo.getPrice());
			Clob clob = con.createClob();
			clob.setString(1, shgmvo.getIntro());
			pstmt.setClob(5, clob);
			pstmt.setBytes(6, shgmvo.getImg());
			pstmt.setInt(7, shgmvo.getUpcheck());
			pstmt.setTimestamp(8, shgmvo.getUptime());
			pstmt.setString(9, shgmvo.getTake());
			pstmt.setString(10, shgmvo.getTakernm());
			pstmt.setString(11, shgmvo.getTakerph());
			pstmt.setString(12, shgmvo.getAddress());
			pstmt.setInt(13, shgmvo.getBoxstatus());
			pstmt.setInt(14, shgmvo.getPaystatus());
			pstmt.setInt(15, shgmvo.getStatus());
			pstmt.setTimestamp(16, shgmvo.getSoldtime());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(ShgmVO shgmvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, shgmvo.getSellerno());
			pstmt.setString(2, shgmvo.getBuyerno());
			pstmt.setString(3, shgmvo.getShgmname());
			pstmt.setInt(4, shgmvo.getPrice());
			Clob clob = con.createClob();
			clob.setString(1, shgmvo.getIntro());
			pstmt.setClob(5, clob);
			pstmt.setBytes(6, shgmvo.getImg());
			pstmt.setInt(7, shgmvo.getUpcheck());
			pstmt.setTimestamp(8, shgmvo.getUptime());
			pstmt.setString(9, shgmvo.getTake());
			pstmt.setString(10, shgmvo.getTakernm());
			pstmt.setString(11, shgmvo.getTakerph());
			pstmt.setString(12, shgmvo.getAddress());
			pstmt.setInt(13, shgmvo.getBoxstatus());
			pstmt.setInt(14, shgmvo.getPaystatus());
			pstmt.setInt(15, shgmvo.getStatus());
			pstmt.setTimestamp(16, shgmvo.getSoldtime());
			pstmt.setString(17, shgmvo.getShgmno());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(ShgmVO shgmvo, MbrpfVO mbrpfVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, shgmvo.getSellerno());
			pstmt.setString(2, shgmvo.getBuyerno());
			pstmt.setString(3, shgmvo.getShgmname());
			pstmt.setInt(4, shgmvo.getPrice());
			Clob clob = con.createClob();
			clob.setString(1, shgmvo.getIntro());
			pstmt.setClob(5, clob);
			pstmt.setBytes(6, shgmvo.getImg());
			pstmt.setInt(7, shgmvo.getUpcheck());
			pstmt.setTimestamp(8, shgmvo.getUptime());
			pstmt.setString(9, shgmvo.getTake());
			pstmt.setString(10, shgmvo.getTakernm());
			pstmt.setString(11, shgmvo.getTakerph());
			pstmt.setString(12, shgmvo.getAddress());
			pstmt.setInt(13, shgmvo.getBoxstatus());
			pstmt.setInt(14, shgmvo.getPaystatus());
			pstmt.setInt(15, shgmvo.getStatus());
			pstmt.setTimestamp(16, shgmvo.getSoldtime());
			pstmt.setString(17, shgmvo.getShgmno());
			pstmt.executeUpdate();

			MbrpfService mbrpfsvc = new MbrpfService();
			mbrpfsvc.updateMbrpf(mbrpfVO, con);

			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(ShgmVO shgmvo, Connection con) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, shgmvo.getSellerno());
			pstmt.setString(2, shgmvo.getBuyerno());
			pstmt.setString(3, shgmvo.getShgmname());
			pstmt.setInt(4, shgmvo.getPrice());
			Clob clob = con.createClob();
			clob.setString(1, shgmvo.getIntro());
			pstmt.setClob(5, clob);
			pstmt.setBytes(6, shgmvo.getImg());
			pstmt.setInt(7, shgmvo.getUpcheck());
			pstmt.setTimestamp(8, shgmvo.getUptime());
			pstmt.setString(9, shgmvo.getTake());
			pstmt.setString(10, shgmvo.getTakernm());
			pstmt.setString(11, shgmvo.getTakerph());
			pstmt.setString(12, shgmvo.getAddress());
			pstmt.setInt(13, shgmvo.getBoxstatus());
			pstmt.setInt(14, shgmvo.getPaystatus());
			pstmt.setInt(15, shgmvo.getStatus());
			pstmt.setTimestamp(16, shgmvo.getSoldtime());
			pstmt.setString(17, shgmvo.getShgmno());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(String shgmno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, shgmno);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public ShgmVO findByPrimaryKey(String shgmno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ShgmVO shgmvo = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, shgmno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				shgmvo = new ShgmVO();
				shgmvo.setShgmno(rs.getString(1));
				shgmvo.setSellerno(rs.getString(2));
				shgmvo.setBuyerno(rs.getString(3));
				shgmvo.setShgmname(rs.getString(4));
				shgmvo.setPrice(rs.getInt(5));
				Clob clob = rs.getClob(6);
				String intro = clob.getSubString(1, (int) clob.length());
				shgmvo.setIntro(intro);
				shgmvo.setImg(rs.getBytes(7));
				shgmvo.setUpcheck(rs.getInt(8));
				shgmvo.setUptime(rs.getTimestamp(9));
				shgmvo.setTake(rs.getString(10));
				shgmvo.setTakernm(rs.getString(11));
				shgmvo.setTakerph(rs.getString(12));
				shgmvo.setAddress(rs.getString(13));
				shgmvo.setBoxstatus(rs.getInt(14));
				shgmvo.setPaystatus(rs.getInt(15));
				shgmvo.setStatus(rs.getInt(16));
				shgmvo.setSoldtime(rs.getTimestamp(17));
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return shgmvo;
	}

	@Override
	public ShgmVO getOneForInfo(String shgmno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ShgmVO shgmvo = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_INFO);

			pstmt.setString(1, shgmno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				shgmvo = new ShgmVO();
				shgmvo.setShgmno(rs.getString(1));
				shgmvo.setSellerno(rs.getString(2));
				shgmvo.setBuyerno(rs.getString(3));
				shgmvo.setShgmname(rs.getString(4));
				shgmvo.setPrice(rs.getInt(5));
				Clob clob = rs.getClob(6);
				String intro = clob.getSubString(1, (int) clob.length());
				shgmvo.setIntro(intro);
				shgmvo.setImg(rs.getBytes(7));
				shgmvo.setUpcheck(rs.getInt(8));
				shgmvo.setUptime(rs.getTimestamp(9));
				shgmvo.setTake(rs.getString(10));
				shgmvo.setTakernm(rs.getString(11));
				shgmvo.setTakerph(rs.getString(12));
				shgmvo.setAddress(rs.getString(13));
				shgmvo.setBoxstatus(rs.getInt(14));
				shgmvo.setPaystatus(rs.getInt(15));
				shgmvo.setStatus(rs.getInt(16));
				shgmvo.setSoldtime(rs.getTimestamp(17));
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return shgmvo;
	}

	@Override
	public Set<ShgmVO> getall() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<ShgmVO> set = new LinkedHashSet<ShgmVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(rs.getString(1));
				shgmvo.setSellerno(rs.getString(2));
				shgmvo.setBuyerno(rs.getString(3));
				shgmvo.setShgmname(rs.getString(4));
				shgmvo.setPrice(rs.getInt(5));
				Clob clob = rs.getClob(6);
				String intro = clob.getSubString(1, (int) clob.length());
				shgmvo.setIntro(intro);
				shgmvo.setImg(rs.getBytes(7));
				shgmvo.setUpcheck(rs.getInt(8));
				shgmvo.setUptime(rs.getTimestamp(9));
				shgmvo.setTake(rs.getString(10));
				shgmvo.setTakernm(rs.getString(11));
				shgmvo.setTakerph(rs.getString(12));
				shgmvo.setAddress(rs.getString(13));
				shgmvo.setBoxstatus(rs.getInt(14));
				shgmvo.setPaystatus(rs.getInt(15));
				shgmvo.setStatus(rs.getInt(16));
				shgmvo.setSoldtime(rs.getTimestamp(17));

				set.add(shgmvo);
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return set;
	}

	@Override
	public Set<ShgmVO> allForSeller(String sellerno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<ShgmVO> set = new LinkedHashSet<ShgmVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_FOR_SELLER_STMT);

			pstmt.setString(1, sellerno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(rs.getString(1));
				shgmvo.setSellerno(rs.getString(2));
				shgmvo.setBuyerno(rs.getString(3));
				shgmvo.setShgmname(rs.getString(4));
				shgmvo.setPrice(rs.getInt(5));
				Clob clob = rs.getClob(6);
				String intro = clob.getSubString(1, (int) clob.length());
				shgmvo.setIntro(intro);
				shgmvo.setImg(rs.getBytes(7));
				shgmvo.setUpcheck(rs.getInt(8));
				shgmvo.setUptime(rs.getTimestamp(9));
				shgmvo.setTake(rs.getString(10));
				shgmvo.setTakernm(rs.getString(11));
				shgmvo.setTakerph(rs.getString(12));
				shgmvo.setAddress(rs.getString(13));
				shgmvo.setBoxstatus(rs.getInt(14));
				shgmvo.setPaystatus(rs.getInt(15));
				shgmvo.setStatus(rs.getInt(16));
				shgmvo.setSoldtime(rs.getTimestamp(17));

				set.add(shgmvo);
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return set;
	}

	@Override
	public Set<ShgmVO> allForBuyer(String Buyerno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<ShgmVO> set = new LinkedHashSet<ShgmVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_FOR_BUYER_STMT);

			pstmt.setString(1, Buyerno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(rs.getString(1));
				shgmvo.setSellerno(rs.getString(2));
				shgmvo.setBuyerno(rs.getString(3));
				shgmvo.setShgmname(rs.getString(4));
				shgmvo.setPrice(rs.getInt(5));
				Clob clob = rs.getClob(6);
				String intro = clob.getSubString(1, (int) clob.length());
				shgmvo.setIntro(intro);
				shgmvo.setImg(rs.getBytes(7));
				shgmvo.setUpcheck(rs.getInt(8));
				shgmvo.setUptime(rs.getTimestamp(9));
				shgmvo.setTake(rs.getString(10));
				shgmvo.setTakernm(rs.getString(11));
				shgmvo.setTakerph(rs.getString(12));
				shgmvo.setAddress(rs.getString(13));
				shgmvo.setBoxstatus(rs.getInt(14));
				shgmvo.setPaystatus(rs.getInt(15));
				shgmvo.setStatus(rs.getInt(16));
				shgmvo.setSoldtime(rs.getTimestamp(17));

				set.add(shgmvo);
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return set;
	}

	@Override
	public Set<ShgmVO> getAllForMain() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<ShgmVO> set = new LinkedHashSet<ShgmVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(MAINPAGE_GETALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(rs.getString(1));
				shgmvo.setSellerno(rs.getString(2));
				shgmvo.setBuyerno(rs.getString(3));
				shgmvo.setShgmname(rs.getString(4));
				shgmvo.setPrice(rs.getInt(5));
				Clob clob = rs.getClob(6);
				String intro = clob.getSubString(1, (int) clob.length());
				shgmvo.setIntro(intro);
				shgmvo.setImg(rs.getBytes(7));
				shgmvo.setUpcheck(rs.getInt(8));
				shgmvo.setUptime(rs.getTimestamp(9));
				shgmvo.setTake(rs.getString(10));
				shgmvo.setTakernm(rs.getString(11));
				shgmvo.setTakerph(rs.getString(12));
				shgmvo.setAddress(rs.getString(13));
				shgmvo.setBoxstatus(rs.getInt(14));
				shgmvo.setPaystatus(rs.getInt(15));
				shgmvo.setStatus(rs.getInt(16));
				shgmvo.setSoldtime(rs.getTimestamp(17));

				set.add(shgmvo);
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return set;
	}

	@Override
	public java.util.List<ShgmVO> getAllForInfoShuffle() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ShgmVO> list = new ArrayList<ShgmVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(MAINPAGE_GETALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(rs.getString(1));
				shgmvo.setSellerno(rs.getString(2));
				shgmvo.setBuyerno(rs.getString(3));
				shgmvo.setShgmname(rs.getString(4));
				shgmvo.setPrice(rs.getInt(5));
				Clob clob = rs.getClob(6);
				String intro = clob.getSubString(1, (int) clob.length());
				shgmvo.setIntro(intro);
				shgmvo.setImg(rs.getBytes(7));
				shgmvo.setUpcheck(rs.getInt(8));
				shgmvo.setUptime(rs.getTimestamp(9));
				shgmvo.setTake(rs.getString(10));
				shgmvo.setTakernm(rs.getString(11));
				shgmvo.setTakerph(rs.getString(12));
				shgmvo.setAddress(rs.getString(13));
				shgmvo.setBoxstatus(rs.getInt(14));
				shgmvo.setPaystatus(rs.getInt(15));
				shgmvo.setStatus(rs.getInt(16));
				shgmvo.setSoldtime(rs.getTimestamp(17));

				list.add(shgmvo);
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public Set<ShgmVO> allForPpersonalMkt(String sellerno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<ShgmVO> set = new LinkedHashSet<ShgmVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_FOR_PERSONALMKT);

			pstmt.setString(1, sellerno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(rs.getString(1));
				shgmvo.setSellerno(rs.getString(2));
				shgmvo.setBuyerno(rs.getString(3));
				shgmvo.setShgmname(rs.getString(4));
				shgmvo.setPrice(rs.getInt(5));
				Clob clob = rs.getClob(6);
				String intro = clob.getSubString(1, (int) clob.length());
				shgmvo.setIntro(intro);
				shgmvo.setImg(rs.getBytes(7));
				shgmvo.setUpcheck(rs.getInt(8));
				shgmvo.setUptime(rs.getTimestamp(9));
				shgmvo.setTake(rs.getString(10));
				shgmvo.setTakernm(rs.getString(11));
				shgmvo.setTakerph(rs.getString(12));
				shgmvo.setAddress(rs.getString(13));
				shgmvo.setBoxstatus(rs.getInt(14));
				shgmvo.setPaystatus(rs.getInt(15));
				shgmvo.setStatus(rs.getInt(16));
				shgmvo.setSoldtime(rs.getTimestamp(17));

				set.add(shgmvo);
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return set;
	}

	@Override
	public Set<ShgmVO> searchForMain(String word) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<ShgmVO> set = new LinkedHashSet<ShgmVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCH_STMT);
			pstmt.setString(1, "%" + word + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(rs.getString(1));
				shgmvo.setSellerno(rs.getString(2));
				shgmvo.setBuyerno(rs.getString(3));
				shgmvo.setShgmname(rs.getString(4));
				shgmvo.setPrice(rs.getInt(5));
				Clob clob = rs.getClob(6);
				String intro = clob.getSubString(1, (int) clob.length());
				shgmvo.setIntro(intro);
				shgmvo.setImg(rs.getBytes(7));
				shgmvo.setUpcheck(rs.getInt(8));
				shgmvo.setUptime(rs.getTimestamp(9));
				shgmvo.setTake(rs.getString(10));
				shgmvo.setTakernm(rs.getString(11));
				shgmvo.setTakerph(rs.getString(12));
				shgmvo.setAddress(rs.getString(13));
				shgmvo.setBoxstatus(rs.getInt(14));
				shgmvo.setPaystatus(rs.getInt(15));
				shgmvo.setStatus(rs.getInt(16));
				shgmvo.setSoldtime(rs.getTimestamp(17));

				set.add(shgmvo);
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return set;
	}

}
