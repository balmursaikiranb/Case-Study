package com.cg.PaymentWallet.service;
import java.math.BigDecimal;

import com.cg.PaymentWallet.dao.IPaymentWalletDao;
import com.cg.PaymentWallet.dao.PaymentWalletDaoImpl;
import com.cg.PaymentWallet.dto.Wallet;
import com.cg.PaymentWallet.exception.PaymentWalletException;


public class PaymentWalletServiceImpl implements IPaymentsWalletService{
IPaymentWalletDao dao=new PaymentWalletDaoImpl(); 
	
	public Wallet withdrawMoney(String phone, BigDecimal balance) throws PaymentWalletException {
		dao.beginTransaction();
		Wallet dto= dao.withdrawMoney(phone, balance);
		dao.commitTransaction();
		return dto;
	}
	
	public Wallet showBalance(String phone) throws PaymentWalletException {
		dao.beginTransaction();
		Wallet dto=dao.showBalance(phone);
		dao.commitTransaction();
		return dto;
	}
	
	public Wallet registerCustomer(Wallet wallet) throws PaymentWalletException {
		dao.beginTransaction();
		Wallet dto=dao.registerCustomer(wallet);
		dao.commitTransaction();
		return dto;
	}
	
	public String printTransaction(String phone) throws PaymentWalletException {
		dao.beginTransaction();
		String str=dao.printTransaction(phone);
		dao.commitTransaction();
		return str;
	}
	
	public Wallet fundTransfer(String sourcePhone, String targetPhone, BigDecimal balance)
			throws PaymentWalletException {
		dao.beginTransaction();
		Wallet dto=dao.fundTransfer(sourcePhone, targetPhone, balance);
		dao.commitTransaction();
		return dto;
	}
	
	public Wallet depositMoney(String phone, BigDecimal balance) throws PaymentWalletException {
		dao.beginTransaction();
		Wallet dto=dao.depositMoney(phone, balance);
		dao.commitTransaction();
		return dto;
	}
	
	
	
	public boolean checkLogin(String number, String password) {
		dao.beginTransaction();
		boolean status=dao.checkLogin(number, password);
		dao.commitTransaction();
		return status;
	}
	
	

	
	public boolean validatePhone(String phoneNumber) throws PaymentWalletException {
		String pattern = "\\d{10}";
		if (!(phoneNumber.matches(pattern))) {
			throw new PaymentWalletException("enter valid phone number");
		}
		return true;
	}

	public boolean validateMoney(BigDecimal balance) throws PaymentWalletException {
		String amou = balance.toString();
		if (!(amou.matches("\\d+"))) {
			throw new PaymentWalletException("enter valid money");
		}
		return true;
	}

	public boolean validateAge(Integer age) throws PaymentWalletException {
		String age1 = age.toString();
		if (!(age1.matches("\\d{2}"))) {
			throw new PaymentWalletException("enter valid age");
		}
		return true;
	}

	public boolean validateGender(String gender) throws PaymentWalletException {
		if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("m")) {
			return true;
		} else if (gender.equalsIgnoreCase("female")
				|| gender.equalsIgnoreCase("f")) {
			return true;
		} else {
			throw new PaymentWalletException("enter valid gender");
		}
	}

	public boolean validateName(String name) throws PaymentWalletException {
		if (!(name.matches("[a-zA-Z]+"))) {
			throw new PaymentWalletException("enter valid name");
		}
		return true;
	}

	public boolean validateEmail(String emailId) throws PaymentWalletException {
		if (!(emailId.matches("[a-zA-Z0-9.]+@[a-zA-Z]+.[a-zA-Z]{2,}"))) {
			throw new PaymentWalletException("enter valid email");
		}
		return true;
	}

	public boolean validatePassword(String pass) throws PaymentWalletException {
		if (!((pass.length()) >= 8)) {
			throw new PaymentWalletException("enter correct password");
		}
		return true;
	}

	
}