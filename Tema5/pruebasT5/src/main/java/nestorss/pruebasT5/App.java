package nestorss.pruebasT5;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class App 
{
    public static void main( String[] args )
    {
    	Usuarios user = new Usuarios();
    	
    	user.setUserName("nsanchez");
    	user.setPassword("12345");
    	user.setCreateDate(new Date());
    	user.setCreateUser("Nestor");
    	
    	Transaction tx = null;
    	
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	
    	try{
    		tx = session.beginTransaction();
    		session.save(user); 
    		tx.commit();
    		System.out.println("Saved Successfully.");
	  	} catch (HibernateException e) {
	  	    if(tx!=null){
		        System.err.println("ERROR: se ha producido un error");
		  	    tx.rollback();
	  	    }
	  	    e.printStackTrace(); 
	  	} finally {
	  	    session.close(); 
	  	}
    	
    }
}
