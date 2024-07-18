package com.smilehelper.application.service;

import com.smilehelper.application.domain.Statistics;
import com.smilehelper.application.domain.User;
import com.smilehelper.application.dto.StatisticsDTO;
import com.smilehelper.application.repository.StatisticsRepository;
import com.smilehelper.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final UserRepository userRepository;

    @Autowired
    public StatisticsService(StatisticsRepository statisticsRepository, UserRepository userRepository) {
        this.statisticsRepository = statisticsRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveStatistics(String id, List<StatisticsDTO> statisticsDTOs) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            for (StatisticsDTO statisticsDTO : statisticsDTOs) {
                Statistics statistics = statisticsRepository.findByUserAndExpressionType(user, statisticsDTO.getExpressionType())
                        .orElse(new Statistics(user, statisticsDTO.getExpressionType(), 0, 0, 0));

                statistics.setSuccessCount(statistics.getSuccessCount() + statisticsDTO.getSuccessCount());
                statistics.setFailCount(statistics.getFailCount() + statisticsDTO.getFailCount());
                statistics.setTotalCount(statistics.getTotalCount() + statisticsDTO.getSuccessCount() + statisticsDTO.getFailCount());

                statisticsRepository.save(statistics);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<StatisticsDTO> getStatisticsByUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Statistics> statisticsList = statisticsRepository.findByUser(user);
            return statisticsList.stream().map(this::toDTO).collect(Collectors.toList());
        }
        return List.of();
    }

    private StatisticsDTO toDTO(Statistics statistics) {
        return StatisticsDTO.builder()
                .expressionType(statistics.getExpressionType())
                .successCount(statistics.getSuccessCount())
                .failCount(statistics.getFailCount())
                .build();
    }
}