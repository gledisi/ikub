package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import dao.UserDao;
import entitete.Role;
import entitete.User;
import utility.Messages;

@ManagedBean
@ViewScoped
public class UserCrudBean {

	private User user;
	private UserDao userDao;
	private String newPassword;
	private String confirmPassword;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@PostConstruct
	public void init() {
		this.userDao = UserDao.getInstance();
		this.user = new User();
	}

	public String addUser() {
		
		Role roli = new Role();
		roli.setId(1);
		user.setRoli(roli);

		if (notExistUserWithThisEmail(user.getEmail())) {
			if (userDao.add(user)) {
				Messages.addMessage("Rregjistrimi u krye me sukses!");
			} else {
				Messages.addMessage("Rregjistrimi nuk u krye !!!");
				return null;
			}
		} else {
			Messages.addMessage("Ky email ekziston!");
			return null;
		}

		return "login.xhtml?faces-redirect=true";
	}

	public String editUser() {

		User editUser = userBean.getUser();
		if (notExistUserWithThisEmail(editUser.getEmail())) {
			if(userDao.edit(editUser)) {
				Messages.addMessage("Editimi u krye me sukses!");
			}else {
				Messages.addMessage("Editimi nuk krye !!!");
			}
		}else {
			Messages.addMessage("Ky email ekziston!");
		}

		return null;
	}

	public String changePassword() {

		User editUser = userBean.getUser();
		if (this.user.getPassword().equals(userDao.getUserFromId(editUser.getId()).getPassword())) {
			if (this.confirmPassword.equals(this.newPassword)) {
				editUser.setPassword(this.newPassword);
				if (userDao.edit(editUser)) {
					Messages.addMessage("Passwordi u ndryshua!");
				} else {
					Messages.addMessage("Passwordi nuk ndryshua!");
				}
			} else {
				Messages.addMessage("Passwordi i ri dhe konfirmimi passwordit jane te ndryshem!");
			}
		} else {
			Messages.addMessage("Passwordi i pasakte!");
		}

		return null;
	}

	public boolean notExistUserWithThisEmail(String email) {
		boolean notExist=true;
		User user = userDao.getUserByEmail(email);
		if (this.user.getId()!=null && user.getId()!=this.user.getId()) {
			notExist=false;
		} 
		return notExist;
	}

	// GETTERS AND SETTERS

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
