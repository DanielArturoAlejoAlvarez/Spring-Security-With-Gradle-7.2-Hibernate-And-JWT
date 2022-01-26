# SPRING BOOT AND SPRING SECURITY

## Description

This repository is a Software of Application with JAVA.

## Installation

Using Spring Boot, JPA, Thymeleaf, Spring Security, Lombok, MySQL-Driver, etc preferably.

## Software and Automation

Apply Gradle 7.3

## Tools

Java version JDK-16

## IDE

IntelliJ IDEA

## Database

Using H2Database, MySQL,etc

## Client Rest

Postman, Insomnia, Talend API Tester,etc

## Usage

```html
$ git clone https://github.com/DanielArturoAlejoAlvarez/Spring-Security-With-Gradle-7.2-Hibernate-And-JWT.git
[NAME APP]

```

Follow the following steps and you're good to go! Important:

![alt text](https://cdn.einnovator.org/ei-home/docs/quickguide/quickguide-12-spring-security/spring-security-arch.png)
## Coding

### Config
```java
...
@Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http
                .authorizeRequests()
                .antMatchers(resources)
                .permitAll()
                .antMatchers("/", "/index","/signup")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/userForm")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .csrf()
                .disable()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login?logout");
    }
        ...
```

### Database
```java
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/demo_db?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.jpa.hibernate.ddl-auto=update

server.port=8081
```

### Controllers
```java
...
@GetMapping("/signup")
    public String signup(Model model) {
        Role userRole = roleRepo.findByName("USER");
        List<Role> roles = Arrays.asList(userRole);

        model.addAttribute("signup",true);
        model.addAttribute("userForm", new User());
        model.addAttribute("roles",roles);
        return "user-form/user-signup";
    }

    @PostMapping("/signup")
    public String signupAction(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
        Role userRole = roleRepo.findByName("USER");
        List<Role> roles = Arrays.asList(userRole);
        model.addAttribute("userForm", user);
        model.addAttribute("roles",roles);
        model.addAttribute("signup",true);

        if(result.hasErrors()) {
            return "user-form/user-signup";
        }else {
            try {
                service.createUser(user);
            } catch (CustomeFieldValidationException cfve) {
                result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
            }catch (Exception e) {
                model.addAttribute("formErrorMessage",e.getMessage());
            }
        }
        return index();
    }
        ...
```

### Models
```java
...
@Entity
@Table(name = "tbl_users")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank
    @Size(min = 4, max = 100, message = "Not the correct size!")
    @Column(unique = true)
    private String username;
    @NotBlank
    @Column(nullable = false)
    private String password;

    @Transient
    @NotBlank
    private String passwordConfirmation;

    @Column(length = 512)
    private String imgURL;

    private Boolean status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tbl_user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    ...
}
```

### Services
```java
...
@Override
    public User createUser(User user) throws Exception {
        if (checkPasswordValid(user) && checkUsernameAvailable(user)) {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user = repo.save(user);
        }
        return user;
    }

@Override
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public void deleteUser(Long id) throws Exception {
        User user = findById(id);
        repo.delete(user);
        }

@Override
public User changePassword(ChangePasswordForm form) throws Exception {
        User user = findById(form.getId());
        if (!isLoggedUserADMIN() && !user.getPassword().equals(form.getCurrentPassword())) {
        throw new Exception("Current user invalid!");
        }
        if (user.getPassword().equals(form.getNewPassword())) {
        throw new Exception("The new password must not be the same as the current one!");
        }
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
        throw new Exception("The new password and confirmation password do not match!");
        }
        String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
        user.setPassword(encodePassword);
        return repo.save(user);
        }

private boolean isLoggedUserADMIN() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUser = null;
        Object roles = null;

        //Verify obj get user session
        if (principal instanceof UserDetails) {
        loggedUser = (UserDetails) principal;
        roles = loggedUser.getAuthorities().stream()
        .filter(x -> "ROLE_ADMIN".equals(x.getAuthority()))
        .findFirst()
        .orElse(null);
        }

        return roles != null;

        }


private boolean checkUsernameAvailable(User user) throws Exception {
        Optional<User> userFound = repo.findByUsername(user.getUsername());
        if (userFound.isPresent()) {
        throw new Exception("User not available!");
        }
        return true;
        }

private boolean checkPasswordValid(User user) throws Exception {
        if (user.getPasswordConfirmation() == null || user.getPasswordConfirmation().isEmpty()) {
        throw new Exception("Confirm Password is required!");
        }

        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
        throw new Exception("Password and confirmation password are not the same");
        }
        return true;
        }

protected void mapUser(User from, User to) {
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setEmail(from.getEmail());
        to.setUsername(from.getUsername());
        to.setRoles(from.getRoles());
        to.setImgURL(from.getImgURL());
        }
        ...
```

### Views
```html
...
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalTitle" aria-hidden="true">
<div class="modal-dialog modal-dialog-centered" role="document">
<div class="modal-content">
<div class="modal-header">
<h5 class="modal-title" id="exampleModalLongTitle">Confirm Delete</h5>
<button type="button" class="close" data-dismiss="modal" aria-label="Close">
<span aria-hidden="true">&times;</span>
</button>
</div>
<div class="modal-body">
        Are you sure you want to delete this user?
<input type="hidden" id="userIdHiddenInput" name="userIdHiddenInput"/>
</div>
<div class="modal-footer">
<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
<button type="button" class="btn btn-primary" th:onclick="deleteUser()">Delete User</button>
</div>
</div>
</div>
</div>
        ...
```
        

## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/DanielArturoAlejoAlvarez/Spring-Security-With-Gradle-7.2-Hibernate-And-JWT. This project is intended to be a safe, welcoming space for collaboration, and contributors are expected to adhere to the [Contributor Covenant](http://contributor-covenant.org) code of conduct.

## License

The gem is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).
````