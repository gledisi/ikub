package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import dao.UserDao;
import entitete.User;
import utility.Messages;

@ManagedBean
@RequestScoped
public class LoginBean {

	private String email;
	private String password;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	private UserDao userDao = UserDao.getInstance();

	public String logIn() {

		User user = userDao.getLoggedUser(email, password);
		if (user != null) {
			userBean.setUser(user);
			String roli = user.getRoli().getRoli();
			if (roli.equalsIgnoreCase("Client")) {
				
				return "client/clientHome.xhtml?faces-redirect=true";
			} else  {
				return "admin/movie.xhtml?faces-redirect=true";
			}
		}else {
			Messages.addMessage("Email or Password is incorrect!");
		}

		return null;
	}

	public String logOut() {

		userBean.logOut();
		return "/login.xhtml?faces-redirect=true";
	}

	// GETTERS AND SETTERS

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}
