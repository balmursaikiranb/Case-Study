package com.cg.PaymentWallet.dao;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.cg.PaymentWallet.dto.Wallet;
import com.cg.PaymentWallet.exception.PaymentWalletException;




public class PaymentWalletDaoImpl implements IPaymentWalletDao{

	private EntityManager entityManager;

	public PaymentWalletDaoImpl() {
		entityManager = JPAUtil.getEntityManager();
	}


	public Wallet registerCustomer(Wallet wallet) throws PaymentWalletException {
		entityManager.persist(wallet);
		return wallet;
	
		
	}

	public Wallet depositMoney(String phone, BigDecimal balance) throws PaymentWalletException {
		String qStr = "SELECT pwc FROM PaymentWalletCustomers pwc WHERE pwc.phone LIKE :phone";
		TypedQuery<Wallet> query = entityManager.createQuery(qStr, Wallet.class);
		query.setParameter("phone", "%"+phone+"%");
		Wallet wall= (Wallet) query.getResultList();
		BigDecimal m=wall.getBalance().add(balance);
		wall.setBalance(m);
		entityManager.merge(wall);
		return wall;
		
	}

	public Wallet withdrawMoney(String phone, BigDecimal balance) throws PaymentWalletException {
		String qStr = "SELECT pwc FROM PaymentWalletCustomers pwc WHERE pwc.phone LIKE :phone";
		TypedQuery<Wallet> query = entityManager.createQuery(qStr, Wallet.class);
		query.setParameter("phone", "%"+phone+"%");
		Wallet wall= (Wallet) query.getResultList();
		BigDecimal m=wall.getBalance().subtract(balance);
		wall.setBalance(m);
		entityManager.merge(wall);
		return wall;
	}

	public Wallet fundTransfer(String sourcePhone, String targetPhone, BigDecimal balance)
			throws PaymentWalletException {
		String qStr = "SELECT pwc FROM PaymentWalletCustomers pwc WHERE pwc.phone LIKE :phone";
		TypedQuery<Wallet> query = entityManager.createQuery(qStr, Wallet.class);
		query.setParameter("phone", "%"+targetPhone+"%");
		Wallet wall= (Wallet) query.getResultList();
		BigDecimal m=wall.getBalance().add(balance);
		wall.setBalance(m);
		entityManager.merge(wall);
		
		
		String qStr1 = "SELECT pwc FROM PaymentWalletCustomers pwc WHERE pwc.phone LIKE :phone";
		TypedQuery<Wallet> query1 = entityManager.createQuery(qStr, Wallet.class);
		query.setParameter("phone", "%"+sourcePhone+"%");
		Wallet wall1= (Wallet) query.getResultList();
		BigDecimal m1=wall.getBalance().subtract(balance);
		wall1.setBalance(m1);
		entityManager.merge(wall1);
		return wall1;
	}

	public Wallet showBalance(String phone) throws PaymentWalletException {
		String qStr = "SELECT pwc FROM PaymentWalletCustomers pwc WHERE book.title LIKE :ptitle";
		TypedQuery<Wallet> query = entityManager.createQuery(qStr, Wallet.class);
		query.setParameter("phone", "%"+phone+"%");
		Wallet wall=(Wallet) query.getResultList();
		return wall;
		
	}

	public String printTransaction(String phone) throws PaymentWalletException {

		return null;
	}

	public boolean checkLogin(String number, String password) {
	
		return false;
	}

	public void commitTransaction() {
		
		entityManager.getTransaction().commit();
	}

	public void beginTransaction() {

		entityManager.getTransaction().begin();
	}

}