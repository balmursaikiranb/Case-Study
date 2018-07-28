package com.cg.PaymentWallet.dao;
import com.cg.PaymentWallet.dto.*;
import java.math.BigDecimal;

import com.cg.PaymentWallet.exception.*;

public interface IPaymentWalletDao {
	com.cg.PaymentWallet.dto.Wallet registerCustomer(Wallet wallet) throws PaymentWalletException;
	Wallet depositMoney(String phone,BigDecimal balance) throws PaymentWalletException;
	Wallet withdrawMoney(String phone,BigDecimal balance) throws PaymentWalletException;
	Wallet fundTransfer(String sourcePhone,String targetPhone,BigDecimal balance) throws PaymentWalletException;
	Wallet showBalance(String phone) throws PaymentWalletException;
	
	String printTransaction(String phone) throws PaymentWalletException;
	boolean checkLogin(String number, String password);
	public abstract void commitTransaction();

	public abstract void beginTransaction();
}
