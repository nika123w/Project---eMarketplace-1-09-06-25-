@Entity
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String description;
    private LocalDateTime submissionTime;
    private String photoUrl;

    @ManyToOne
    private User user;
}
