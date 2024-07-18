package com.smilehelper.application.repository;

import com.smilehelper.application.domain.Statistics;
import com.smilehelper.application.domain.User;
import com.smilehelper.application.enums.ExpressionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Optional<Statistics> findByUserAndExpressionType(User user, ExpressionType expressionType);
    List<Statistics> findByUser(User user);
}
