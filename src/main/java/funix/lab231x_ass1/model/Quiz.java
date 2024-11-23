package funix.lab231x_ass1.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.sql.In;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String question;

    @Column
    private String answer1;

    @Column
    private String answer2;

    @Column
    private String answer3;

    @Column
    private String answer4;

    @Column(nullable = false)
    private Integer correctAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}
