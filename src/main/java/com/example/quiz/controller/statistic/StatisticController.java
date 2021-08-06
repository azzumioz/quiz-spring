package com.example.quiz.controller.statistic;

import com.example.quiz.dto.HistoryDTO;
import com.example.quiz.service.HistoryService;
import com.example.quiz.util.HistoryUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Administrator Statistic controller")
@RestController
@RequestMapping(value = StatisticController.REST_URL)
public class StatisticController {
    private static final Logger LOG = LoggerFactory.getLogger(StatisticController.class);
    static final String REST_URL = "/api/statistic";

    @Autowired
    private HistoryService historyService;

    @Operation(summary = "Get statistics for all users")
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<HistoryDTO>> getHistory() {
        LOG.info("Get statistics for all users");
        List<HistoryDTO> historyDTOList = historyService.getHistoryAll()
                .stream()
                .map(HistoryUtil::asTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(historyDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Get scores for user by quiz with id = quizId")
    @GetMapping("/{quizId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Integer> getScore(@PathVariable int quizId) {
        LOG.info("Get scores for user by quiz {}", quizId);
        Integer score = historyService.getScore(quizId);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

}
