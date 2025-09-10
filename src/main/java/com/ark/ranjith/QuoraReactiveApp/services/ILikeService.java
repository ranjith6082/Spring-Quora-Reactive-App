package com.ark.ranjith.QuoraReactiveApp.services;

import com.ark.ranjith.QuoraReactiveApp.dto.LikeRequestDTO;
import com.ark.ranjith.QuoraReactiveApp.dto.LikeResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ILikeService {

    Mono<LikeRequestDTO> createLike(LikeRequestDTO likeRequestDTO);

    Mono<LikeResponseDTO> countLikesByTargetIdAndTargetType(String targetId, String targetType);

    Mono<LikeResponseDTO> countDisLikesByTargetIdAndTargetType(String targetId, String targetType);

    Mono<LikeResponseDTO> toggleLike(String targetId, String targetType);
}
