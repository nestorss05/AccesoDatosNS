package nestorss.pruebasT5;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuarios {
	private int seq;
	private String userName;
	private String password;
	private Date createDate;
	private String createUser;
	private Date modifyDate;
	private String modifyUser;
	
	public Usuarios() {}
	
	@Id
	@Column(name = "SEQ", unique = true, nullable = false)
	public int getSeq() {
		return seq;
	}
 
	public void setSeq(int seq) {
		this.seq = seq;
	}
 
	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}
 
	public void setUserName(String userName) {
		this.userName = userName;
	}
 
	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}
 
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
 
	@Column(name = "CREATE_USER")
	public String getCreateUser() {
		return createUser;
	}
 
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
 
	@Column(name = "MODIFY_DATE")
	public Date getModifyDate() {
		return modifyDate;
	}
 
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
 
	@Column(name = "MODIFY_USER")
	public String getModifyUser() {
		return modifyUser;
	}
 
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	
}
