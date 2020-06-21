package com.shgm.model;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.*;
import javax.sql.DataSource;


public class ShgmDAO implements ShgmDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/EA101G6DB");
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_NOCHECK_STMT =
			"INSERT INTO SHGM "
			+ "(shgmno,sellerno,buyerno,shgmname,price,intro,img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime) "
			+ "VALUES"
			+ "('CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),?,?,?,?,?,?,?,null,?,?,?,?,?,?,?,null)";
	private static final String INSERT_CHECK_STMT =
			"INSERT INTO SHGM "
			+ "(shgmno,sellerno,buyerno,shgmname,price,intro,img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime) "
			+ "VALUES"
			+ "('CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,?,?,?,?,?,?,null)";
	private static final String INSERT_SOLD_STMT =
			"INSERT INTO SHGM "
			+ "(shgmno,sellerno,buyerno,shgmname,price,intro,img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime) "
			+ "VALUES"
			+ "('CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String SELLER_STMT =
			"INSERT INTO SHGM"
			+ "(shgmno,sellerno,buyerno,shgmname,price,intro,img,upcheck,uptime,take,takernm,takerph,address,boxstatus,paystatus,status,soldtime) "
			+ "VALUES"
			+ "('CA'||LPAD(shgame_seq.NEXTVAL,5,'0'),?,null,?,?,?,?,0,null,null,null,null,null,0,0,0,null)";
	private static final String UPDATE_STMT =
			"UPDATE SHGM SET sellerno=?,buyerno=?,shgmname=?,price=?,intro=?,img=?,upcheck=?,uptime=null,"
			+ "take=?,takernm=?,takerph=?,address=?,boxstatus=?,paystatus=?,status=?,soldtime=null WHERE shgmno=?";
	private static final String UPDATE_CHECK1_STMT =
			"UPDATE SHGM SET sellerno=?,buyerno=?,shgmname=?,price=?,intro=?,img=?,upcheck=?,uptime=CURRENT_TIMESTAMP,"
			+ "take=?,takernm=?,takerph=?,address=?,boxstatus=?,paystatus=?,status=? WHERE shgmno=?";
	private static final String SELLER_UPDATE_STMT=
			"UPDATE SHGM SET shgmname=?,price=?,intro=?,img=? WHERE shgmno=?";
	private static final String DEALING_STMT =
			"UPDATE SHGM SET buyerno=?,take=?,takernm=?,takerph=?,address=?,boxstatus=?,paystatus=?,status=? WHERE shgmno=?";
	private static final String ODCOMPLETE_STMT =
			"UPDATE SHGM SET soldtime=CURRENT_TIMESTAMP WHERE shgmno=?";
	private static final String DELETE_STMT =
			"DELETE FROM SHGM WHERE shgmno=?";
	private static final String GET_ONE_STMT=
			"SELECT * FROM SHGM WHERE shgmno=?";
	private static final String GET_ALL_STMT =
			"SELECT * FROM SHGM ORDER BY CAST(SUBSTR(shgmno, 5) AS INT)";
	private static final String MAINPAGE_GETALL_STMT =
			"SELECT * FROM SHGM WHERE (upcheck=1 AND boxstatus=0 AND paystatus=0 AND status=0) ORDER BY CAST(SUBSTR(shgmno, 5) AS INT)";
	@Override
	public void insert(ShgmVO shgmvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			
			if(shgmvo.getBoxstatus() == 2 && shgmvo.getPaystatus() == 1 && shgmvo.getStatus() == 2){
				pstmt = con.prepareStatement(INSERT_SOLD_STMT);
			} else if(shgmvo.getUpcheck() == 1) {
				pstmt = con.prepareStatement(INSERT_CHECK_STMT);
			} else {
				pstmt = con.prepareStatement(INSERT_NOCHECK_STMT);
			}
					
			pstmt.setString(1, shgmvo.getSellerno());
			pstmt.setString(2, shgmvo.getBuyerno());
			pstmt.setString(3, shgmvo.getShgmname());
			pstmt.setInt(4, shgmvo.getPrice());
			Clob clob = con.createClob();
			clob.setString(1, shgmvo.getIntro());
			pstmt.setClob(5, clob);
			pstmt.setBytes(6, shgmvo.getImg());
			pstmt.setInt(7, shgmvo.getUpcheck());
			pstmt.setString(8, shgmvo.getTake());
			pstmt.setString(9, shgmvo.getTakernm());
			pstmt.setString(10, shgmvo.getTakerph());
			pstmt.setString(11, shgmvo.getAddress());
			pstmt.setInt(12, shgmvo.getBoxstatus());
			pstmt.setInt(13, shgmvo.getPaystatus());
			pstmt.setInt(14, shgmvo.getStatus());
			
			pstmt.executeUpdate();
			
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
	}
	
	@Override
	public void sellshgm(ShgmVO shgmvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELLER_STMT);
			
			pstmt.setString(1, shgmvo.getSellerno());
			pstmt.setString(2, shgmvo.getShgmname());
			pstmt.setInt(3, shgmvo.getPrice());
			Clob clob = con.createClob();
			clob.setString(1, shgmvo.getIntro());
			pstmt.setClob(4, clob);
			pstmt.setBytes(5, shgmvo.getImg());
			
			pstmt.executeUpdate();
			
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
	}
	
	@Override
	public void update(ShgmVO shgmvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			if(shgmvo.getUpcheck() == 1) {
				pstmt = con.prepareStatement(UPDATE_CHECK1_STMT);
				//已上架審核的狀態下，不應該有可以修改商品資料的行為，需要再修改
			} else {
				pstmt = con.prepareStatement(UPDATE_STMT);
			}
			
			pstmt.setString(1, shgmvo.getSellerno());
			pstmt.setString(2, shgmvo.getBuyerno());
			pstmt.setString(3, shgmvo.getShgmname());
			pstmt.setInt(4, shgmvo.getPrice());
			Clob clob = con.createClob();
			clob.setString(1, shgmvo.getIntro());
			pstmt.setClob(5, clob);
			pstmt.setBytes(6, shgmvo.getImg());
			pstmt.setInt(7, shgmvo.getUpcheck());
			pstmt.setString(8, shgmvo.getTake());
			pstmt.setString(9, shgmvo.getTakernm());
			pstmt.setString(10, shgmvo.getTakerph());
			pstmt.setString(11, shgmvo.getAddress());
			pstmt.setInt(12, shgmvo.getBoxstatus());
			pstmt.setInt(13, shgmvo.getPaystatus());
			pstmt.setInt(14, shgmvo.getStatus());
			pstmt.setString(15, shgmvo.getShgmno());
			
			pstmt.executeUpdate();
			
			if(shgmvo.getBoxstatus() == 2 && shgmvo.getPaystatus() == 1 && shgmvo.getStatus() == 2){
				pstmt = con.prepareStatement(ODCOMPLETE_STMT);
				pstmt.setString(1, shgmvo.getShgmno());
				
				pstmt.executeUpdate();
			} 
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
	public void sellerUpdate(ShgmVO shgmvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELLER_UPDATE_STMT);
			
			pstmt.setString(1, shgmvo.getShgmname());
			pstmt.setInt(2, shgmvo.getPrice());
			Clob clob = con.createClob();
			clob.setString(1, shgmvo.getIntro());
			pstmt.setClob(3, clob);
			pstmt.setBytes(4, shgmvo.getImg());
			pstmt.setString(5, shgmvo.getShgmno());
			
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
	public void dealingshgm(ShgmVO shgmvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DEALING_STMT);

			pstmt.setString(1, shgmvo.getBuyerno());
			pstmt.setString(2, shgmvo.getTake());
			pstmt.setString(3, shgmvo.getTakernm());
			pstmt.setString(4, shgmvo.getTakerph());
			pstmt.setString(5, shgmvo.getAddress());
			pstmt.setInt(6, shgmvo.getBoxstatus());
			pstmt.setInt(7, shgmvo.getPaystatus());
			pstmt.setInt(8, shgmvo.getStatus());
			pstmt.setString(9, shgmvo.getShgmno());
			
//			一次連線中直接在DAO裡面判斷，還是寫一個方法在controller呼叫？
//			if(shgmvo.getBoxstatus() == 2 && shgmvo.getPaystatus() == 1 && shgmvo.getStatus() == 2){
//				pstmt = con.prepareStatement(ODCOMPLETE_STMT);
//				pstmt.setString(1, shgmvo.getShgmno());
//			} 
			
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
	public void odComplete(String shgmno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ODCOMPLETE_STMT);
			
			pstmt.setString(1, shgmno);
			
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
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
			
			while(rs.next()) {
				shgmvo = new ShgmVO();
				shgmvo.setShgmno(rs.getString(1));
				shgmvo.setSellerno(rs.getString(2));
				shgmvo.setBuyerno(rs.getString(3));
				shgmvo.setShgmname(rs.getString(4));
				shgmvo.setPrice(rs.getInt(5));
				Clob clob = rs.getClob(6);
				String intro = clob.getSubString(1,(int)clob.length()).replace("\n", "<br>\n");
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
		return shgmvo;
	}

	@Override
	public List<ShgmVO> getall() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ShgmVO> list= new ArrayList<ShgmVO>();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ShgmVO shgmvo = new ShgmVO();
				shgmvo.setShgmno(rs.getString(1));
				shgmvo.setSellerno(rs.getString(2));
				shgmvo.setBuyerno(rs.getString(3));
				shgmvo.setShgmname(rs.getString(4));
				shgmvo.setPrice(rs.getInt(5));
				Clob clob = rs.getClob(6);
				String intro = clob.getSubString(1,(int)clob.length()).replace("\n", "<br>\n");
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
				shgmvo.setSoldtime(rs.getTimestamp(17));;
				
				list.add(shgmvo);
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
