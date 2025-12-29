@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String nom;

    @OneToMany(mappedBy = "filiere")
    private List<Eleve> eleves = new ArrayList<>();

    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL)
    private List<Cours> cours = new ArrayList<>();
}