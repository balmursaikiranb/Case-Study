package com.cg.paymentwallet.dao;

import java.io.InputStream;
import java.math.BigDecimal;


import com.cg.paymentwallet.dto.Wallet;
import com.cg.paymentwallet.exception.PaymentWalletException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PaymentWalletDao implements IPaymentWalletDao{

	Connection con = null;
	public PaymentWalletDao(){
		try {
			con = DBUtil.getConnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("cannot make connection");
		} // Establishing connection
	}
	public Wallet registerCustomer(Wallet wallet) throws PaymentWalletException {
		try {
			
			String sql = "INSERT INTO PaymentWalletCustomers VALUES(?, ?, ?, ?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, wallet.getPhoneNumber());
			pstmt.setString(2, wallet.getName());
			pstmt.setString(3, wallet.getEmailId());
			pstmt.setString(4, wallet.getGender());
			pstmt.setInt(5, wallet.getAge());
			pstmt.setBigDecimal(6, wallet.getBalance());
			
			int row = pstmt.executeUpdate();
			
			if(row==1)
			{
				//System.out.println("entered");
				String sql1 = "INSERT INTO LoginCustomers VALUES(?, ?)";
				PreparedStatement pstmt1 = con.prepareStatement(sql1);
				pstmt1.setString(1,wallet.getPhoneNumber());
				pstmt1.setString(2,wallet.getPassword());
				int row1=pstmt1.executeUpdate();
				if(row1==1)
				{
					//System.out.println("entered");
					
				}
			}
			//System.out.println("entered");
			String sql2 = "INSERT INTO PaymentWalletTransactions VALUES(?, ?)";
			PreparedStatement pstmt2 = con.prepareStatement(sql2);
			pstmt2.setString(1, wallet.getPhoneNumber());
			//System.out.println("entered");
			pstmt2.setString(2,"zzz");
			int row2=pstmt2.executeUpdate();
			//System.out.println("entered");

		} catch (Exception e) {
			System.out.println("cannot register customer");
		}
		return wallet;

	}


	public Wallet depositMoney(String phone, BigDecimal balance) throws PaymentWalletException {
		BigDecimal bal = null; 
		String data=null;
		Wallet wall=new Wallet();
		try{
			String sql1="SELECT balance from PaymentWalletCustomers WHERE phoneNumber=?";

			PreparedStatement pstmt1 = con.prepareStatement(sql1);
			pstmt1.setString(1,phone);
			ResultSet res1 = pstmt1.executeQuery();
			if (res1.next()) {

				bal = res1.getBigDecimal(1);
			}
			String	sql2="UPDATE PaymentWalletCustomers SET balance = ? WHERE phoneNumber = ?";
			PreparedStatement preparedStmt2 = con.prepareStatement(sql2);
			preparedStmt2.setBigDecimal(1,(bal.add(balance)));
			preparedStmt2.setString(2,phone);
			preparedStmt2.executeUpdate();
			
	//	System.out.println("hai");
			String sql3 = "SELECT * from PaymentWalletTransactions WHERE phone=?";
			//System.out.println("hai");
			PreparedStatement pstmt3 = con.prepareStatement(sql3);
			//System.out.println("hai");
			pstmt3.setString(1, phone);
			//System.out.println("hai");
			ResultSet row3=pstmt3.executeQuery();
			while(row3.next())
			{
			data=row3.getString(2);
			}
			
			//System.out.println("hai");
			
			//System.out.println("hai");
			String datanext=data;
			//System.out.println("saikiran");
			datanext=datanext.concat("you have deposited money ".concat(balance.toString())+" on ".concat((LocalDateTime.now().toString()))+"zzz");
			String sql4 = "UPDATE PaymentWalletTransactions SET statement = ? WHERE phone = ?";
			//System.out.println("saikiran");
			PreparedStatement pstmt4 = con.prepareStatement(sql4);
			pstmt4.setString(1,datanext);
			pstmt4.setString(2,phone);
			int row4=pstmt4.executeUpdate();
			
		
			String sql5="SELECT * from PaymentWalletCustomers WHERE phoneNumber=?";
		
			PreparedStatement pstmt5 = con.prepareStatement(sql5);
			pstmt5.setString(1,phone);
			ResultSet res5= pstmt5.executeQuery();
				
				while(res5.next())
				{
					
					wall.setPhoneNumber(res5.getString(1));
					wall.setName(res5.getString(2));
					wall.setEmailId(res5.getString(3));
					wall.setGender(res5.getString(4));
					wall.setAge(res5.getInt(5));
				wall.setBalance(res5.getBigDecimal(6));
				}
			
				
			
		}catch(SQLException e)
		{
			e.printStackTrace();
			//System.out.println("cannot deposit money");
		}
		return wall;
	

	}

	public Wallet withdrawMoney(String phone, BigDecimal balance) throws PaymentWalletException {
		Wallet wall=new Wallet();
		BigDecimal bal = null; 
		String data=null;
		try{
			String sql1="SELECT balance from PaymentWalletCustomers WHERE phoneNumber=?";

			PreparedStatement pstmt1 = con.prepareStatement(sql1);
			pstmt1.setString(1,phone);
			ResultSet res1 = pstmt1.executeQuery();
			if (res1.next()) {
				bal = res1.getBigDecimal(1);
			}
			String	sql2="UPDATE PaymentWalletCustomers SET balance = ? WHERE phoneNumber = ?";
			PreparedStatement preparedStmt2 = con.prepareStatement(sql2);
			preparedStmt2.setBigDecimal(1,(bal.subtract(balance)));
			preparedStmt2.setString(2,phone);
			preparedStmt2.executeUpdate();
//			System.out.println("hai");
					String sql3 = "SELECT * from PaymentWalletTransactions WHERE phone=?";
					//System.out.println("hai");
					PreparedStatement pstmt3 = con.prepareStatement(sql3);
					//System.out.println("hai");
					pstmt3.setString(1, phone);
					//System.out.println("hai");
					ResultSet row3=pstmt3.executeQuery();
					while(row3.next())
					{
					data=row3.getString(2);
					}
					
					//System.out.println("hai");
					
					//System.out.println("hai");
					String datanext=data;
					//System.out.println("saikiran");
					datanext=datanext.concat("you have withdrawed money ".concat(balance.toString())+" on ".concat((LocalDateTime.now().toString()))+"zzz");
					String sql4 = "UPDATE PaymentWalletTransactions SET statement = ? WHERE phone = ?";
					//System.out.println("saikiran");
					PreparedStatement pstmt4 = con.prepareStatement(sql4);
					pstmt4.setString(1,datanext);
					pstmt4.setString(2,phone);
					int row4=pstmt4.executeUpdate();
			String sql5="SELECT * from PaymentWalletCustomers WHERE phoneNumber=?";
			
			PreparedStatement pstmt5 = con.prepareStatement(sql5);
			pstmt5.setString(1,phone);
			ResultSet res5= pstmt5.executeQuery();
				
				while(res5.next())
				{
					
					wall.setPhoneNumber(res5.getString(1));
					wall.setName(res5.getString(2));
					wall.setEmailId(res5.getString(3));
					wall.setGender(res5.getString(4));
					wall.setAge(res5.getInt(5));
				wall.setBalance(res5.getBigDecimal(6));
				}
			
		}catch(SQLException e)
		{
			System.out.println("cannot withdraw money");
		}
		return wall;

	}

	public Wallet fundTransfer(String sourcePhone, String targetPhone, BigDecimal balance) throws PaymentWalletException {
		Wallet wall=new Wallet();
		BigDecimal bal = null; 
		BigDecimal bal1=null;
		String data = null;
		try{
			String sql1="SELECT balance from PaymentWalletCustomers WHERE phoneNumber=?";

			PreparedStatement pstmt1 = con.prepareStatement(sql1);
			pstmt1.setString(1,sourcePhone);
			ResultSet res1 = pstmt1.executeQuery();
			if (res1.next()) {
				bal = res1.getBigDecimal(1);
			}
			String	sql2="UPDATE PaymentWalletCustomers SET balance = ? WHERE phoneNumber = ?";
			PreparedStatement preparedStmt2 = con.prepareStatement(sql2);
			preparedStmt2.setBigDecimal(1,(bal.subtract(balance)));
			preparedStmt2.setString(2,sourcePhone);
			preparedStmt2.executeUpdate();	
			
			
//			System.out.println("hai");
					String sql12 = "SELECT * from PaymentWalletTransactions WHERE phone=?";
					//System.out.println("hai");
					PreparedStatement pstmt12 = con.prepareStatement(sql12);
					//System.out.println("hai");
					pstmt12.setString(1, sourcePhone);
					//System.out.println("hai");
					ResultSet row12=pstmt12.executeQuery();
					while(row12.next())
					{
					data=row12.getString(2);
					}
				
					String datanext=data;
					//System.out.println("saikiran");
					datanext=datanext.concat("you have made fund transfer of money ".concat(balance.toString())+" on ".concat((LocalDateTime.now().toString()))+"zzz");
					String sql4 = "UPDATE PaymentWalletTransactions SET statement = ? WHERE phone = ?";
					//System.out.println("saikiran");
					PreparedStatement pstmt4 = con.prepareStatement(sql4);
					pstmt4.setString(1,datanext);
					pstmt4.setString(2,sourcePhone);
					int row4=pstmt4.executeUpdate();
					
					
//					System.out.println("hai");
							String sql10 = "SELECT * from PaymentWalletTransactions WHERE phone=?";
							//System.out.println("hai");
							PreparedStatement pstmt10 = con.prepareStatement(sql10);
							//System.out.println("hai");
							pstmt10.setString(1, targetPhone);
							//System.out.println("hai");
							ResultSet row10=pstmt10.executeQuery();
							while(row10.next())
							{
							data=row10.getString(2);
							}
							
							//System.out.println("hai");
							
							//System.out.println("hai");
							String datanext1=data;
							//System.out.println("saikiran");
							datanext=datanext.concat("you have got fund transfer of money ".concat(balance.toString())+" on ".concat((LocalDateTime.now().toString()))+"zzz");
							String sql11 = "UPDATE PaymentWalletTransactions SET statement = ? WHERE phone = ?";
							//System.out.println("saikiran");
							PreparedStatement pstmt11 = con.prepareStatement(sql11);
							pstmt11.setString(1,datanext);
							pstmt11.setString(2,sourcePhone);
							int row11=pstmt11.executeUpdate();
			/*String sql3 = "SELECT * from PaymentWalletTransactions WHERE phoneNumber=?";
			PreparedStatement pstmt3 = con.prepareStatement(sql3);
			pstmt3.setString(1, sourcePhone);
			ResultSet row3=pstmt3.executeQuery();
			String data=row3.getString(2);
			String add="you have funded money ".concat(balance.toString())+" on ".concat((LocalDateTime.now().toString()))+"zzz";
			String sql4 = "UPDATE PaymentWalletTransactions SET statement = ? WHERE phoneNumber = ?";
			PreparedStatement pstmt4 = con.prepareStatement(sql4);
			pstmt4.setString(1,sourcePhone);
			pstmt4.setString(2,add);
			int row4=pstmt4.executeUpdate();*/

			String sql3="SELECT balance from PaymentWalletCustomers WHERE phoneNumber=?";

			PreparedStatement pstmt3 = con.prepareStatement(sql1);
			pstmt3.setString(1,sourcePhone);
			ResultSet res3 = pstmt3.executeQuery();
			if (res3.next()) {
				bal1 = res3.getBigDecimal(1);
			}

			String	sql6="UPDATE PaymentWalletCustomers SET balance = ? WHERE phoneNumber = ?";
			PreparedStatement preparedStmt6 = con.prepareStatement(sql6);
			preparedStmt6.setBigDecimal(1,(bal1.add(balance)));
			preparedStmt6.setString(2,targetPhone);
			preparedStmt6.executeUpdate();
			
			/*String sql7 = "SELECT * from PaymentWalletTransactions WHERE phoneNumber=?";
			PreparedStatement pstmt7 = con.prepareStatement(sql7);
			pstmt7.setString(1, targetPhone);
			ResultSet row7=pstmt7.executeQuery();
			String data1=row7.getString(2);
			String add1="you have withdrawed money ".concat(balance.toString())+" on ".concat((LocalDateTime.now().toString()))+"zzz";
			String sql8 = "UPDATE PaymentWalletTransactions SET statement = ? WHERE phoneNumber = ?";
			PreparedStatement pstmt8 = con.prepareStatement(sql8);
			pstmt8.setString(1,targetPhone);
			pstmt8.setString(2,add1);
			int row8=pstmt8.executeUpdate();*/
			String sql5="SELECT * from PaymentWalletCustomers WHERE phoneNumber=?";
			
			PreparedStatement pstmt5 = con.prepareStatement(sql5);
			pstmt5.setString(1,sourcePhone);
			ResultSet res5= pstmt5.executeQuery();
				
				while(res5.next())
				{
					
					wall.setPhoneNumber(res5.getString(1));
					wall.setName(res5.getString(2));
					wall.setEmailId(res5.getString(3));
					wall.setGender(res5.getString(4));
					wall.setAge(res5.getInt(5));
				wall.setBalance(res5.getBigDecimal(6));
				}
		}catch(SQLException e)
		{
			System.out.println("cannot transfer funds");
		}	


		return wall;
	}

	public Wallet showBalance(String phone) throws PaymentWalletException {
Wallet wall=new Wallet();
		try{
			String sql5="SELECT * from PaymentWalletCustomers WHERE phoneNumber=?";
			
			PreparedStatement pstmt5 = con.prepareStatement(sql5);
			pstmt5.setString(1,phone);
			ResultSet res5= pstmt5.executeQuery();
				
				while(res5.next())
				{
					
					wall.setPhoneNumber(res5.getString(1));
					wall.setName(res5.getString(2));
					wall.setEmailId(res5.getString(3));
					wall.setGender(res5.getString(4));
					wall.setAge(res5.getInt(5));
				wall.setBalance(res5.getBigDecimal(6));
				}
		}catch(SQLException e){
			System.out.println("cannot show balance");
		}
		return wall;
	}

	public String printTransaction(String phone) throws PaymentWalletException {
		String str1=null;
		try{
			String sql1="SELECT * from PaymentWalletTransactions WHERE phone=?";


			PreparedStatement pstmt1 = con.prepareStatement(sql1);
			pstmt1.setString(1,phone);
			ResultSet res1 = pstmt1.executeQuery();
			while(res1.next()) {
				
				 str1 = res1.getString(2);
				
			}
		}catch(SQLException e){
			//System.out.println("cannot print transactions");
			e.printStackTrace();
		}
		return str1;

	}
	@Override
	public boolean checkLogin(String number, String password) {
		// TODO Auto-generated method stub
		try{
			String sql2="SELECT * from LoginCustomers WHERE phoneNumber=?";

			PreparedStatement pstmt2 = con.prepareStatement(sql2);
			pstmt2.setString(1,number);
			ResultSet res2 = pstmt2.executeQuery();
			if (res2.next()) {
				String str = res2.getString(1);
				String str1=res2.getString(2);
				if((number.equals(str)&&(password.equals(password))))
				{
					return true;
				}

			}
		}catch(SQLException e){
			System.out.println("password or number not available");
		}
		return false;
	}



}
