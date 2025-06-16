@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void register(UserRegisterDto dto) {
        if (!dto.getUsername().matches("^[a-zA-Z0-9]{8,20}$")) {
            throw new RuntimeException("Invalid username format.");
        }
        if (!EmailValidator.getInstance().isValid(dto.getEmail())) {
            throw new RuntimeException("Invalid email format.");
        }
        if (Period.between(dto.getBirthday(), LocalDate.now()).getYears() < 13) {
            throw new RuntimeException("User must be over 13 years old.");
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBirthday(dto.getBirthday());

        userRepository.save(user);
    }

    public User login(UserLoginDto dto) {
        return userRepository.findByUsernameOrEmail(dto.getUsernameOrEmail(), dto.getUsernameOrEmail())
                .filter(u -> u.getPassword().equals(dto.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}
