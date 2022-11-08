package com.afj.solution.buyitapp.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.afj.solution.buyitapp.model.product.Product;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

/**
 * @author Tomash Gombosh
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = -6302264803709958147L;

    @Id
    @Type(type = "uuid-binary")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "home_address")
    private String homeAddress;

    @Column(name = "date_of_birth")
    private ZonedDateTime dateOfBirth;

    @Column(name = "privacy_policy")
    private boolean privacyPolicy;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @ElementCollection(fetch = EAGER)
    private Set<GrantedAuthority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = ALL)
    private Set<Product> products = new HashSet<>();

    public User(final Consumer<User> builder) {
        requireNonNull(builder).accept(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String toString() {
        return String.format("{ \"id\": \"%s\", \"email\": \"%s\", \"username\": \"%s\", \"firstName\": \"%s\", "
                        + "\"lastName\": \"%s\", \"homeAddress\": \"%s\", \"phoneNumber\": \"%s\", \"roles\": \"%s\","
                        + " \"createdAt\": \"%s\", \"updatedAt\": \"%s\" }",
                this.getId(),
                this.getEmail(),
                this.getUsername(),
                this.getFirstName(),
                this.getLastName(),
                this.getHomeAddress(),
                this.getPhoneNumber(),
                this.getAuthorities(),
                this.getCreatedAt(),
                this.getUpdatedAt());
    }

    public User update(final User newUser, final User currentUser) {
        final String username = isNull(newUser.username) ? this.username : newUser.username;
        final String email = isNull(newUser.email) ? this.email : newUser.email;
        final String firstName = isNull(newUser.firstName) ? this.firstName : newUser.firstName;
        final String lastName = isNull(newUser.lastName) ? this.lastName : newUser.lastName;
        final String phoneNumber = isNull(newUser.phoneNumber) ? this.phoneNumber : newUser.phoneNumber;
        final String homeAddress = isNull(newUser.homeAddress) ? this.homeAddress : newUser.homeAddress;
        final ZonedDateTime dateOfBirth = isNull(newUser.dateOfBirth) ? this.dateOfBirth : newUser.dateOfBirth;
        final Set<GrantedAuthority> authorities = isNull(newUser.authorities) ? this.authorities : newUser.authorities;
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setEmail(email);
        currentUser.setUsername(username);
        currentUser.setPhoneNumber(phoneNumber);
        currentUser.setHomeAddress(homeAddress);
        currentUser.setDateOfBirth(dateOfBirth);
        currentUser.setAuthorities(authorities);
        currentUser.setPrivacyPolicy(newUser.isPrivacyPolicy());
        return currentUser;
    }
}
