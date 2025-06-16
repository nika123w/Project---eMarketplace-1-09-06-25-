@Entity
public class User {
    @Id
    private UUID id;
    private String username;
    private String email;
    private String password;
    private LocalDate birthday;
}
