package com.cg.PaymentWallet.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
		Wallet wall = entityManager.find(Wallet.class,phone);
		if(wall!=null)
		{
			BigDecimal update=wall.getBalance().add(balance);
			wall.setBalance(update);
			String deposit=wall.getStatement();
			String add=deposit.concat("you have deposited "+balance+" on "+LocalDateTime.now()+" zzz" );
			wall.setStatement(add);
			entityManager.merge(wall);
		}
		
		/*String qStr = "SELECT pwc FROM PaymentWallets pwc WHERE pwc.phoneNumber=phone";
		TypedQuery<Wallet> query = entityManager.createQuery(qStr, Wallet.class);
		query.setParameter("phone", "%"+phone+"%");
		Wallet wall= (Wallet) query.getResultList();
		BigDecimal m=wall.getBalance().add(balance);
		*/
		return wall;
		
	}

	public Wallet withdrawMoney(String phone, BigDecimal balance) throws PaymentWalletException {
		
		Wallet wall = entityManager.find(Wallet.class,phone);
		if(wall!=null)
		{
			BigDecimal m=wall.getBalance().subtract(balance);
			wall.setBalance(m);
			String deposit=wall.getStatement();
			String add=deposit.concat("you have withdrawed "+balance+" on "+LocalDateTime.now()+" zzz" );
			wall.setStatement(add);
			entityManager.merge(wall);
		}
		
		
		
		/*String qStr = "SELECT pwc FROM PaymentWallets pwc WHERE pwc.phoneNumber LIKE :phone";
		TypedQuery<Wallet> query = entityManager.createQuery(qStr, Wallet.class);
		query.setParameter("phone", "%"+phone+"%");
		Wallet wall= (Wallet) query.getResultList();
		BigDecimal m=wall.getBalance().subtract(balance);
		wall.setBalance(m);
		entityManager.merge(wall);*/
		return wall;
	}

	public Wallet fundTransfer(String sourcePhone, String targetPhone, BigDecimal balance)
			throws PaymentWalletException {
		
		Wallet wall = entityManager.find(Wallet.class,sourcePhone);
		if(wall!=null)
		{
			BigDecimal m=wall.getBalance().subtract(balance);
			wall.setBalance(m);
			String deposit=wall.getStatement();
			String add=deposit.concat("you have funded "+balance+" on "+LocalDateTime.now()+" zzz" );
			wall.setStatement(add);
			entityManager.merge(wall);
		}
		
		Wallet wall1 = entityManager.find(Wallet.class,targetPhone);
		if(wall1!=null)
		{
			BigDecimal update=wall.getBalance().add(balance);
			wall1.setBalance(update);
			String deposit=wall.getStatement();
			String add=deposit.concat("you have got funded "+balance+" on "+LocalDateTime.now()+" zzz" );
			wall.setStatement(add);
			entityManager.merge(wall1);
		}
		
		
		/*String qStr = "SELECT pwc FROM PaymentWallets pwc WHERE pwc.phoneNumber LIKE :phone";
		TypedQuery<Wallet> query = entityManager.createQuery(qStr, Wallet.class);
		query.setParameter("phone", "%"+targetPhone+"%");
		Wallet wall= (Wallet) query.getResultList();
		BigDecimal m=wall.getBalance().add(balance);
		wall.setBalance(m);
		entityManager.merge(wall);*/
		
		
		/*String qStr1 = "SELECT pwc FROM PaymentWallet pwc WHERE pwc.phoneNumber LIKE :phone";
		TypedQuery<Wallet> query1 = entityManager.createQuery(qStr, Wallet.class);
		query.setParameter("phone", "%"+sourcePhone+"%");
		Wallet wall1= (Wallet) query.getResultList();
		BigDecimal m1=wall.getBalance().subtract(balance);
		wall1.setBalance(m1);
		entityManager.merge(wall1);*/
		return wall;
	}

	public Wallet showBalance(String phone) throws PaymentWalletException {
		/*String qStr = "SELECT pwc FROM PaymentWallets pwc WHERE phoneNumber LIKE :phone";
		TypedQuery<Wallet> query = entityManager.createQuery(qStr, Wallet.class);
		query.setParameter("phone", "%"+phone+"%");
		Wallet wall=(Wallet) query.getResultList();*/
		Wallet wall = entityManager.find(Wallet.class,phone);
		if(wall!=null)
		{
			return wall;
		}
		return wall;
		
		
	}

	public String printTransaction(String phone) throws PaymentWalletException {
		Wallet wall = entityManager.find(Wallet.class,phone);
		String statement=null;
		if(wall!=null)
		{
			statement=wall.getStatement();
		}
		return statement;
	}

	public boolean checkLogin(String number, String password) {
	
	Wallet wall = entityManager.find(Wallet.class,number);
	if(wall!=null)
	{
		if((wall.getPhoneNumber().equals(number))&&(wall.getPassword().equals(password)))
		{
			
			return true;
		}
	}
		return false;
	}

	public void commitTransaction() {
		
		entityManager.getTransaction().commit();
	}

	public void beginTransaction() {

		entityManager.getTransaction().begin();
	}

}