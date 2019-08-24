package com.rokoassessment.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class JwtUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String first_name;
	private String email;
	private String password;
	private String token;
    private int id;


    public JwtUserDetails(String email, int id, String token) {

        this.email = email;
        this.id = id;
        this.token= token;
    }

    public JwtUserDetails(String first_name, String email) {
		this.first_name = first_name;
		this.email = email;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public JwtUserDetails() {
		super();
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
	public String getUsername() {
		return null;
	}

    @Override
    public String getPassword() {
        return password;
    }
   

	public void setPassword(String password) {
		this.password = password;
	}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFirst_name() {
		return first_name;
	}

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }


    public int getId() {
        return id;
    }


}
