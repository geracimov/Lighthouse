package ru.geracimov.otus.spring.lighthouse.componentserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geracimov.otus.spring.lighthouse.componentserver.entity.ComponentTo;

@Repository
public interface ComponentRepository extends JpaRepository<ComponentTo, Integer> {
}
