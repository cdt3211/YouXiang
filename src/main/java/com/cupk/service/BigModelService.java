package com.cupk.service;

public interface BigModelService {
    String generateSummary(String content);
    String generateSummaryForPostsByProductId(Integer productId);
    String generateAbstractByPostId(Integer postId);
}
