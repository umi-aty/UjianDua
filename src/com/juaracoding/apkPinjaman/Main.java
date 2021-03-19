package com.juaracoding.apkPinjaman;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;


public class Main {
	
	static Scanner scan = new Scanner(System.in);
	static InputStreamReader data = new InputStreamReader(System.in);
	static BufferedReader br = new BufferedReader(data);
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/bankir";
	
	static Connection conn;
	static Statement stat;
	static ResultSet rs;
	
	public static void main (String [] args) {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, "root", "");
			stat = conn.createStatement();

			while (!conn.isClosed()) {
				showMenu();
			}
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	static void showMenu() {
		System.out.println("=================================");
		System.out.println("1. Masukkan Data");
		System.out.println("2. Tampilkan Data");
		System.out.println("3. Keluar");
		System.out.println("Masukkan Pilihan = ");
		try {
			int pil = Integer.parseInt(br.readLine());
			switch (pil) {
			case 1: {
				insertData();
				break;
			}
			case 2: {
				showData();
				break;
			}
			case 3: {
				System.exit(0);
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + pil);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void insertData() {
		try {
			Date date = new Date();
			System.out.println("Tanggal = "+date);
			System.out.print("Platfon = ");
			int platfon = Integer.parseInt(br.readLine());
			System.out.print("Bunga = ");
			double bunga = Double.parseDouble(br.readLine());
			System.out.print("Lama Pinjaman = ");
			int lamapinjaman = Integer.parseInt(br.readLine());
			
			String qry = "INSERT INTO `pinjaman`(`platfon`, `bunga`, `lamapinjaman`) VALUES (%d,%f,%d)";

			qry = String.format(qry, platfon,bunga,lamapinjaman);
			
			stat.execute(qry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void showData() {
		String qry = "SELECT * FROM pinjaman ";
		try {
			rs = stat.executeQuery(qry);
			System.out.println("===== Data Pinjaman =====");
			while (rs.next()) {
				Date tanggal = rs.getDate("dateFrom");
				int platfon = rs.getInt("platfon");
				double bunga = rs.getDouble("bunga");
				int lamapinjaman = rs.getInt("lamapinjaman");
				
				System.out.println(String.format("Tanggal		: "+"%s", tanggal));
				System.out.println(String.format("Platfon		: "+"%d", platfon));
				System.out.println(String.format("Bunga		: "+"%f", bunga));
				System.out.println(String.format("Lama Pinjaman	: "+"%d", lamapinjaman));
				System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
				
//				int id = rs.getInt("id");
//				Date tanggal = rs.getDate("dateFrom");
//				int pinjaman = rs.getInt("platfon");
//				double totalAngsuran = rs.getDouble("totalAngsuran");
//				double angsuranPokok = rs.getDouble("angsuranPokok");
//				double angsuranBunga = rs.getDouble("angsuranBunga");
//				double sisaPinjaman = rs.getDouble("sisaPinjaman");
//				
//				System.out.println(String.format("Angsuran ke		: "+"%d", id));
//				System.out.println(String.format("Tanggal			: "+"%s", tanggal));
//				System.out.println(String.format("Pinjaman			: "+"%d", pinjaman));
//				System.out.println(String.format("Total Angsuran	: "+"%d", totalAngsuran));
//				System.out.println(String.format("Angsuran Pokok	: "+"%d", angsuranPokok));
//				System.out.println(String.format("Angsuran Bunga	: "+"%d", angsuranBunga));
//				System.out.println(String.format("Sisa Pinjaman		: "+"%d", sisaPinjaman));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
