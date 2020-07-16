package cn.xhxc.blog.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author hasee
 *
 */
@Entity
@Table(name = "user",uniqueConstraints = @UniqueConstraint(columnNames = { "userName" }))
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class User implements UserDetails,Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY,generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.xhxc.blog.core.CustomIDGenerator")
	private Long id;

	private String username;
	
	@Column(length = 5)
	private String nickName;

	@JsonIgnore
	private String password;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	
	//头像
	private String avatar;
	
	//介绍
	@Column(length = 22)
	private String synopsis;
	
	@JsonIgnore
	private boolean accountNonExpired;
	@JsonIgnore
    private boolean accountNonLocked;
	@JsonIgnore
    private boolean credentialsNonExpired;
	
    private boolean enabled;
    
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Article> articles;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}
//
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", username=" + username + ", nickName=" + nickName + ", password=" + password
//				+ ", date=" + date + ", avatar=" + avatar + ", synopsis=" + synopsis + ", accountNonExpired="
//				+ accountNonExpired + ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired="
//				+ credentialsNonExpired + ", enabled=" + enabled + ", roles=" + roles + "]";
//	}

	
}