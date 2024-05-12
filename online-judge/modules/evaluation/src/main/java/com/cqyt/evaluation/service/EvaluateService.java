package com.cqyt.evaluation.service;

import com.cqyt.core.entity.Result;

public interface EvaluateService {
    Result run(String userId, String language, String code);
}
