package dbTables;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImportImgs {
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "EA101";
	String password = "123456";
	String UPDATE_STMT = "UPDATE SHGAME SET img=? WHERE shgmno=?";
	
	public static void main(String[] args) {
		ImportImgs ii = new ImportImgs();
		ii.gogoimgs();
	}
	
	public byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while( (i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();
		
		return baos.toByteArray();
	}
	
	public void gogoimgs() {
		Connection con = null;
		PreparedStatement pstmt = null;
		int shgmno = 5601;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			for(int j=1; j<6; j++) {
				byte[] img = getPictureByteArray("src/images/"+j+".jpg");
				pstmt.setBytes(1, img);
				pstmt.setInt(2, shgmno);
				shgmno += 1;
				
				pstmt.executeUpdate();
				System.out.println(j + "筆資料已送出");
				pstmt.clearParameters();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
}
