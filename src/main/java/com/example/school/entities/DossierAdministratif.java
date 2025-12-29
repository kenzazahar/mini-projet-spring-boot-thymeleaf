@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class DossierAdministratif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroInscription;

    @Column(nullable = false)
    private LocalDate dateCreation;

    @OneToOne(mappedBy = "dossierAdministratif")
    private Eleve eleve;
}