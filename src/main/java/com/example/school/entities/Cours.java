@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String intitule;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    @ManyToMany(mappedBy = "cours")
    private List<Eleve> eleves = new ArrayList<>();
}