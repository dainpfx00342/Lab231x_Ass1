package funix.lab231x_ass1.respository;

import funix.lab231x_ass1.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
